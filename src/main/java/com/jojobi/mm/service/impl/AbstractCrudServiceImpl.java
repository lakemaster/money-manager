package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.BaseEntity;
import com.jojobi.mm.service.CrudService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.function.Function;

public abstract class AbstractCrudServiceImpl<T extends BaseEntity, ID, REPO extends JpaRepository<T, ID>> implements CrudService<T, ID> {

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
    public T save(T obj, boolean asIs) {
        if (asIs || obj.getId() == null || obj.getId() == 0)
            return repo.save(obj);

        T obj2 = repo.findById((ID) obj.getId()).map(t -> (T) t.merge(obj)).orElse(obj);
        return repo.save(obj2);
    }
}
