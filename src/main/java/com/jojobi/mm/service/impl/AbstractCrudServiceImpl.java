package com.jojobi.mm.service.impl;

import com.jojobi.mm.service.CrudService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudServiceImpl<T, ID, REPO extends CrudRepository<T, ID>> implements CrudService<T, ID> {

    protected REPO repo;

    public AbstractCrudServiceImpl(REPO repo) {
        this.repo = repo;
    }

    @Override
    public List<T> findAll() {
        return IterableUtils.toList(repo.findAll());
    }

    @Override
    public T findById(ID id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public T save(T obj) {
        return repo.save(obj);
    }
}
