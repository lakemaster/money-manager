package com.jojobi.mm.service.impl;

import com.jojobi.mm.info.CategoryInfo;
import com.jojobi.mm.model.Category;
import com.jojobi.mm.repo.CategoryRepo;
import com.jojobi.mm.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public List<Category> getAllTopLevelCategories() {
        return repo.findAllByGroupIsNullOrderByName();
    }

    @Override
    public Category findByName(String categoryName) {
        return repo.findCategoryByName(categoryName);
    }

    @Override
    public CategoryInfo getCategoryInfo(Long categoryId) {
        return repo.findById(categoryId).map(category -> new CategoryInfo(category, getPath(category))).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Category category, boolean withSubCategories) {
        Category parent = category.getGroup();

        // remove link from parent
        if ( parent != null ) {
            parent.getSubCategories().remove(category);
            repo.saveAndFlush(parent);
            category.setGroup(null);
            repo.saveAndFlush(category);
        }

        // remove all sub categories
        List<Category> children = new ArrayList<>(category.getSubCategories());
        category.getSubCategories().clear();
        repo.saveAndFlush(category);

        // remove links from children
        children.forEach(subCat -> {
            if ( withSubCategories ) {
                delete(subCat, withSubCategories);
            } else {
                if (parent != null) {
                    parent.getSubCategories().add(subCat);
                    repo.saveAndFlush(parent);
                }
                subCat.setGroup(parent);
                repo.saveAndFlush(subCat);
            }
        });

        repo.delete(category);
        repo.flush();
    }
}
