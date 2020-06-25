package com.jojobi.mm.service.impl;

import com.jojobi.mm.info.CategoryInfo;
import com.jojobi.mm.model.Category;
import com.jojobi.mm.repo.CategoryRepo;
import com.jojobi.mm.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl extends AbstractCrudServiceImpl<Category, Long, CategoryRepo> implements CategoryService {

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        super(categoryRepo);
    }

    static List<CategoryInfo.PathEntry> getPath(Category category) {
        List<CategoryInfo.PathEntry> path = new ArrayList<>();

        if (category != null) {
            for (Category c = category.getGroup(); c != null; c = c.getGroup()) {
                path.add(new CategoryInfo.PathEntry(c.getName(), c.getId()));
            }
        }

        Collections.reverse(path);
        return path;
    }

    @Override
    public List<Category> getAllTopLevelNatures() {
        return repo.findAllByGroupIsNullOrderByName();
    }

    @Override
    public CategoryInfo getCategoryInfo(Long categoryId) {
        return repo.findById(categoryId).map(category -> new CategoryInfo(category, getPath(category))).orElse(null);
    }

}
