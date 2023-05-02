package com.epam.esm.repository.impl;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
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
public class TagRepositoryImpl implements TagRepository {
    private static final String SAVE = """
            INSERT INTO tag(name)\040
            VALUES (?)
            """;
    private static final String EXISTS = """
            SELECT EXISTS(SELECT t.name FROM tag AS t WHERE t.name =?)
            """;
    private static final String FIND_BY_ID = """
            SELECT * FROM tag AS t
            WHERE t.id = ?
            """;
    private static final String FIND_BY_NAME = """
            SELECT * FROM tag AS t
            WHERE t.name = ?
            """;
    private static final String FIND_ALL = """
            SELECT * FROM tag
            """;
    private static final String FIND_ALL_BY_CERTIFICATE = """
            SELECT t.id, t.name FROM tag AS t
            JOIN gift_certificate_tag gct ON t.id = gct.tag_id
            JOIN gift_certificate gc on gct.gift_certificate_id = gc.id
            WHERE gc.id = ?
            """;
    private static final String DELETE = """
            DELETE FROM tag WHERE id = ?
            """;
    private final JdbcTemplate template;

    @Override
    public Tag save(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SAVE);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKeyAs(Long.class))
                .orElseThrow(() -> new NotFoundException("Tag was not found."));
    }

    @Override
    public boolean existsByName(String name) {
        return Boolean.TRUE.equals(template.queryForObject(
                EXISTS,
                Boolean.class,
                name
        ));
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return template.query(
                        FIND_BY_ID,
                        new BeanPropertyRowMapper<>(Tag.class),
                        id
                )
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return template.query(
                        FIND_BY_NAME,
                        new BeanPropertyRowMapper<>(Tag.class),
                        name
                )
                .stream()
                .findFirst();
    }

    @Override
    public List<Tag> findAll() {
        return template.query(
                        FIND_ALL,
                        new BeanPropertyRowMapper<>(Tag.class)
                );
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(Long certificateId) {
        return template.query(
                FIND_ALL_BY_CERTIFICATE,
                new BeanPropertyRowMapper<>(Tag.class),
                certificateId
        );
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE, id);
    }
}
