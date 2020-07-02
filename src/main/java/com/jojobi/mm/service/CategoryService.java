package com.jojobi.mm.service;

import com.jojobi.mm.info.CategoryInfo;
import com.jojobi.mm.model.Category;

import java.util.List;

public interface CategoryService extends CrudService<Category, Long> {

    List<Category> getAllTopLevelCategories();

    Category findByName(String categoryName);

    CategoryInfo getCategoryInfo(Long categoryId);

    void delete(Category category, boolean withSubCategories);
}
