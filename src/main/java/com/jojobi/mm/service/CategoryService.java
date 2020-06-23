package com.jojobi.mm.service;

import com.jojobi.mm.model.Category;

import java.util.List;

public interface CategoryService extends CrudService<Category, Long> {

    List<Category> getAllTopLevelNatures();
}
