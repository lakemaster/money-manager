package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceImplTest {


    @Test
    void getPath() {
        Category g1 = Category.builder().name("g1").build();
        Category g2 = Category.builder().name("g2").group(g1).build();
        Category g3 = Category.builder().name("g3").group(g2).build();
        g1.setSubCategories(List.of(g2));
        g2.setSubCategories(List.of(g3));

        assertEquals("/", CategoryServiceImpl.getPath(g1));
        assertEquals("/g1", CategoryServiceImpl.getPath(g2));
        assertEquals("/g1/g2", CategoryServiceImpl.getPath(g3));
    }
}