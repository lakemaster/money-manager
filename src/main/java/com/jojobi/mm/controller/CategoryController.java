package com.jojobi.mm.controller;

import com.jojobi.mm.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        model.addAttribute("category", categoryService.getCategoryInfo(id));
        return "category";
    }
}
