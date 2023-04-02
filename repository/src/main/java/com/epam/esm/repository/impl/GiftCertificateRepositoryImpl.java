package com.epam.esm.repository.impl;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String SAVE = """
            INSERT INTO gift_certificate(name,description,price,duration,create_date,last_update_date)\040
            VALUES (?,?,?,?,?,?)
            """;
    private static final String SAVE_TAG = """
            INSERT INTO gift_certificate_tag(gift_certificate_id, tag_id)\040
            VALUES (?,?)
            """;
    private static final String FIND_BY_ID = """
            SELECT * FROM gift_certificate AS gc
            WHERE gc.id = ?
            """;
    private static final String FIND_ALL = """
            SELECT * FROM gift_certificate
            """;
    private static final String DELETE = """
            DELETE FROM gift_certificate WHERE id = ?
            """;
    private final JdbcTemplate template;

    @Override
    public GiftCertificate save(GiftCertificate cert) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SAVE);
            ps.setObject(1, cert.getName());
            ps.setObject(2, cert.getDescription());
            ps.setObject(3, cert.getPrice());
            ps.setObject(4, cert.getDuration());
            ps.setObject(5, cert.getCreateDate());
            ps.setObject(6, cert.getLastUpdateDate());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKeyAs(Long.class))
                .orElseThrow(() -> new NotFoundException("Certificate was not found."));
    }

    @Override
    public void saveCertificateTag(Long certificateId, Long tagId) {
        template.update(
                SAVE_TAG,
                certificateId,
                tagId
        );
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return template.query(
                        FIND_BY_ID,
                        new BeanPropertyRowMapper<>(GiftCertificate.class),
                        id
                )
                .stream()
                .findFirst();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return template.query(
                FIND_ALL,
                new BeanPropertyRowMapper<>(GiftCertificate.class)
        );
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE, id);
    }
}
