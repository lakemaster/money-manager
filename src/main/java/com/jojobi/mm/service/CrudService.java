package com.jojobi.mm.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {

    List<T> findAll();

    T findById(ID id);

    default T save(T obj) {
        return save(obj, false);
    }

    T save(T obj, boolean asIs);
}
