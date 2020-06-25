package com.jojobi.mm.info;

import com.jojobi.mm.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryInfo {
    private final Category category;
    private final List<PathEntry> path;

    @Data
    @AllArgsConstructor
    public static class PathEntry {
        String categoryName;
        Long categoryId;
    }
}
