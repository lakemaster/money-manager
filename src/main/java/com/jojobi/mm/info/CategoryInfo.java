package com.jojobi.mm.info;

import com.jojobi.mm.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryInfo {
    private final Category category;
    private final String path;
}
