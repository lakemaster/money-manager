package com.jojobi.mm.controller;

import com.jojobi.mm.model.Category;
import com.jojobi.mm.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
public class CategoryController {
    public static final String BASE_CATEGORY_URL = "/category";
    public static final String TOP_LEVEL_CATEGORIES_URL = "/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(TOP_LEVEL_CATEGORIES_URL)
    public String getAllTopLevelNatures(Model model) {
        model.addAttribute("categories", categoryService.getAllTopLevelCategories());
        return "categories";
    }

    @GetMapping(BASE_CATEGORY_URL + "/{id}")
    public String getCategory(@PathVariable Long id, Model model) {
        model.addAttribute("categoryInfo", categoryService.getCategoryInfo(id));
        return "category";
    }

    @GetMapping(BASE_CATEGORY_URL + "/{id}/modify")
    public String modifyCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "categoryForm";
    }

    @GetMapping(BASE_CATEGORY_URL + "/{id}/add_subcategory")
    public String addSubCategory(@PathVariable Long id, Model model) {
        Category parent = categoryService.findById(id);
        Category newChild = Category.builder().group(parent).build();

        model.addAttribute("category", newChild);
        model.addAttribute("addMode", Boolean.TRUE);
        return "categoryForm";
    }


    @PostMapping(BASE_CATEGORY_URL)
    public String saveCategory(@ModelAttribute Category category_in, @RequestParam String action) {
        log.debug("Save {} action={}", category_in, action);
        Category category;
        if ( action.equals("Save As New") || action.equals("Add") ) {
            category_in.setId(0L);
            category = categoryService.save(category_in);
        } else {
            // merge unmanaged category into managed category and save
            category = categoryService.findById(category_in.getId());
            category.merge(category_in);
            category = categoryService.save(category);
        }

        // todo: maybe move to service
        Category parent = category.getGroup();
        if ( action.equals("Add") && parent != null ) {
            parent.getSubCategories().add(category);
            categoryService.save(parent);
            log.debug("Category saved: {}", parent);
            category = parent;
        }

        return String.format("redirect:%s/%d", BASE_CATEGORY_URL, category.getId());
    }

    @GetMapping(BASE_CATEGORY_URL + "/{id}/delete")
    public String deleteCategory(@PathVariable Long id, @RequestParam(required = false) boolean with_subcategories) {
        Category category = categoryService.findById(id);
        log.debug("Delete {}, with_subcategories={}", category, with_subcategories);
        Category parent = category.getGroup();
        categoryService.delete(category, with_subcategories);

        return parent == null ? "redirect:/categories" :
                String.format("redirect:%s/%d", BASE_CATEGORY_URL, parent.getId());
    }


}
