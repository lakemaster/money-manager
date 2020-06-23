package com.jojobi.mm.repo;

import com.jojobi.mm.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepo extends CrudRepository<Category, Long> {

    List<Category> findAllByGroupIsNullOrderByName();

}
