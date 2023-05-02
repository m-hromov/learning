package com.epam.esm.repository;

import com.epam.esm.model.paging.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrdRepository<ID, TYPE> {
    TYPE save(TYPE obj);

    Optional<TYPE> findById(ID id);

    List<TYPE> findAll(Pageable paging);

    void delete(ID id);
}
