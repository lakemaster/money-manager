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

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String getAllTopLevelNatures(Model model) {
        model.addAttribute("categories", categoryService.getAllTopLevelNatures());
        return "categories";
    }

    @GetMapping("/category/{id}")
    public String getCategory(@PathVariable Long id, Model model) {
        model.addAttribute("categoryInfo", categoryService.getCategoryInfo(id));
        return "category";
    }

    @GetMapping("/category/{id}/modify")
    public String modifyCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "categoryForm";
    }

    @GetMapping("/category/{id}/add_subcategory")
    public String addSubCategory(@PathVariable Long id, Model model) {
        Category parent = categoryService.findById(id);
        Category newChild = Category.builder().group(parent).build();

        model.addAttribute("category", newChild);
        model.addAttribute("addMode", Boolean.TRUE);
        return "categoryForm";
    }


    @PostMapping("/category")
    public String saveCategory(@ModelAttribute Category category, @RequestParam String action, Model model) {
        log.debug("Save {} action={}", category, action);
        if ( action.equals("Save As New") || action.equals("Add") ) {
            category.setId(0L);
        }

        Long id;

        // todo: maybe move to service
        // todo: does not work
        Category parent = categoryService.findById(category.getGroup().getId());
        if ( parent != null ) {
            parent.getSubCategories().add(category);
            categoryService.save(parent);
            log.debug("Category saved: {}", parent);
            id = parent.getId();
        } else {
            category = categoryService.save(category);
            log.debug("Category saved: {}", category);
            id = category.getId();
        }

        return String.format("redirect:/category/%d", id);
    }

    @GetMapping("/category/{id}/delete")
    public String deleteCategory(@PathVariable Long id, @RequestParam(required = false) boolean with_subcategories) {
        Category category = categoryService.findById(id);
        log.debug("Delete {}, with_subcategories={}", category, with_subcategories);
        Category parent = category.getGroup();
        categoryService.delete(category, with_subcategories);

        return parent == null ? "redirect:/categories" :
                String.format("redirect:/category/%d", parent.getId());
    }


}
