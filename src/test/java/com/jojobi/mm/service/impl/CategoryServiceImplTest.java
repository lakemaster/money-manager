package com.jojobi.mm.service.impl;

import com.jojobi.mm.info.CategoryInfo;
import com.jojobi.mm.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceImplTest {


    @Test
    void getPath() {
        Category g1 = Category.builder().id(1L).name("g1").build();
        Category g2 = Category.builder().id(2L).name("g2").group(g1).build();
        Category g3 = Category.builder().id(3L).name("g3").group(g2).build();
        g1.setSubCategories(List.of(g2));
        g2.setSubCategories(List.of(g3));

        CategoryInfo.PathEntry pe1 = new CategoryInfo.PathEntry(g1.getName(), g1.getId());
        CategoryInfo.PathEntry pe2 = new CategoryInfo.PathEntry(g2.getName(), g2.getId());

        assertEquals(true, CategoryServiceImpl.getPath(g1).isEmpty());
        assertEquals(1, CategoryServiceImpl.getPath(g2).size());
        assertEquals(2, CategoryServiceImpl.getPath(g3).size());

        assertEquals(pe1, CategoryServiceImpl.getPath(g2).get(0));
        assertEquals(pe1, CategoryServiceImpl.getPath(g3).get(0));
        assertEquals(pe2, CategoryServiceImpl.getPath(g3).get(1));
    }
}