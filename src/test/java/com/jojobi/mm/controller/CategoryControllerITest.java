package com.jojobi.mm.controller;

import com.jojobi.mm.bootstrap.TestDataLoader;
import com.jojobi.mm.info.CategoryInfo;
import com.jojobi.mm.model.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CategoryControllerITest extends AbstractControllerITest {

    // workaround to extract model attribute from MvcResult
    private List<Category> topLevelCategories;
    private CategoryInfo categoryInfo;

    private void extractModelAttributesFromMvcResult(MvcResult mvcResult) {
        topLevelCategories = (List<Category>) mvcResult.getModelAndView().getModel().get("categories");
        categoryInfo = (CategoryInfo) mvcResult.getModelAndView().getModel().get("categoryInfo");
    }

    @Test
    void getAllTopLevelNatures() throws Exception {
        URI uri = new URI(CategoryController.TOP_LEVEL_CATEGORIES_URL);

        perform(uri)
                .andExpect(status().isOk())
                .andExpect(view().name("categories"))
                .andExpect(model().attribute("categories", instanceOf(List.class)))
                .andDo(this::extractModelAttributesFromMvcResult);

        assertThat(topLevelCategories.size()).isEqualTo(3);
    }

    @Test
    void getCategory() throws Exception {
        URI uri = UriComponentsBuilder.fromPath(CategoryController.BASE_CATEGORY_URL)
                .pathSegment("{category_id}")
                .build(TestDataLoader.PRECAUTION_ID);

        perform(uri)
                .andExpect(status().isOk())
                .andExpect(view().name("category"))
                .andExpect(model().attribute("categoryInfo", instanceOf(CategoryInfo.class)))
                .andDo(this::extractModelAttributesFromMvcResult);

        Category category = categoryInfo.getCategory();
        assertThat(category.getId()).isEqualTo(TestDataLoader.precaution.getId());
        assertThat(category.getName()).isEqualTo(TestDataLoader.precaution.getName());
    }

    @Test
    void modifyCategory() {
    }

    @Test
    void addSubCategory() {
    }

    @Test
    void saveCategory() {
    }

    @Test
    void deleteCategory() {
    }
}