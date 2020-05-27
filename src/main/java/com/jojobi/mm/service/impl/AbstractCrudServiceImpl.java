package com.jojobi.mm.service.impl;

import com.jojobi.mm.service.CrudService;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudServiceImpl<T, ID, REPO extends CrudRepository> implements CrudService<T, ID> {

    private REPO repo;

    public AbstractCrudServiceImpl(REPO repo) {
        this.repo = repo;
    }

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();
        repo.findAll().forEach(obj -> result.add((T) obj));
        return result;
    }

    @Override
    public Optional<T> findById(ID id) {
        return repo.findById(id);
    }

    @Override
    public T save(T obj) {
        return (T) repo.save(obj);
    }
}
