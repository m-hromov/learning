package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {H2Config.class,
        GiftCertificateRepositoryImpl.class,
        TagRepositoryImpl.class})
@Sql("classpath:V1__initial.sql")
class GiftCertificateRepositoryImplITest {
    @Autowired
    private GiftCertificateRepositoryImpl giftCertificateRepository;
    @Autowired
    private TagRepositoryImpl tagRepository;

    @Disabled("KeyHolder doesn't work with H2 for some reason")
    @Test
    void save() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("NAME")
                .build();
        GiftCertificate saved = giftCertificateRepository.save(giftCertificate);
        List<GiftCertificate> result = giftCertificateRepository.findAll();

        assertEquals(3, result.size());
        assertNotNull(saved.getId());
    }

    @Test
    void saveCertificateTag() {
        giftCertificateRepository.saveCertificateTag(2L, 1L);
        List<Tag> tags = tagRepository.findAllByGiftCertificateId(2L);

        assertEquals(2, tags.size());
    }

    @Test
    void findById() {
        Optional<GiftCertificate> opt = giftCertificateRepository.findById(1L);

        assertTrue(opt.isPresent());
        assertEquals("AB", opt.get().getName());
    }

    @Test
    void findAll() {
        List<GiftCertificate> result = giftCertificateRepository.findAll();

        assertEquals(2, result.size());
        //...
    }

    @Test
    void delete() {
        giftCertificateRepository.delete(1L);
        List<GiftCertificate> result = giftCertificateRepository.findAll();

        assertEquals(1, result.size());
        //...
    }
}