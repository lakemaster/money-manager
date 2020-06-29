package com.jojobi.mm.repo;

import com.jojobi.mm.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findAllByGroupIsNullOrderByName();

}
