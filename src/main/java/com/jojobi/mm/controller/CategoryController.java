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

    @PostMapping("/category")
    public String saveCategory(@ModelAttribute Category category, @RequestParam String action) {
        log.debug("Save {} action={}", category, action);
        if ( action.equals("Save As New")) {
            category.setId(0L);
        }
        Category savedCategory = categoryService.save(category);
        log.debug("Category saved: {}", savedCategory);
        return String.format("redirect:/category/%d", savedCategory.getId());
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
