package com.jojobi.mm.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T obj);
}
