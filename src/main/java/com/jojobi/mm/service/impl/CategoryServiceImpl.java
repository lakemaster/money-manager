package com.jojobi.mm.service.impl;

import com.jojobi.mm.info.CategoryInfo;
import com.jojobi.mm.model.Category;
import com.jojobi.mm.repo.CategoryRepo;
import com.jojobi.mm.service.CategoryService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl extends AbstractCrudServiceImpl<Category, Long, CategoryRepo> implements CategoryService {

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        super(categoryRepo);
    }

    @Override
    public List<Category> getAllTopLevelNatures() {
        return repo.findAllByGroupIsNullOrderByName();
    }

    @Override
    public CategoryInfo getCategoryInfo(Long categoryId) {
        return repo.findById(categoryId).map(category -> new CategoryInfo(category, getPath(category))).orElse(null);
    }

    static String getPath(Category category) {
        String path = getSubPath(category.getGroup());
        return path.isEmpty() ? "/" : path;
    }

    private static String getSubPath(Category category) {
        if ( category == null )
            return "";

        return getSubPath(category.getGroup()) + "/" + category.getName();
    }
}
