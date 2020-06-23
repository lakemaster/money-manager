package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Category;
import com.jojobi.mm.repo.CategoryRepo;
import com.jojobi.mm.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends AbstractCrudServiceImpl<Category, Long, CategoryRepo> implements CategoryService {

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        super(categoryRepo);
    }

    @Override
    public List<Category> getAllTopLevelNatures() {
        return repo.findAllByGroupIsNullOrderByName();
    }
}
