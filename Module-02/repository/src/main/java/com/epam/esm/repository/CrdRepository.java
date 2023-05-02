package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface CrdRepository<ID, TYPE> {
    TYPE save(TYPE obj);

    Optional<TYPE> findById(ID id);

    List<TYPE> findAll();

    void delete(ID id);
}
