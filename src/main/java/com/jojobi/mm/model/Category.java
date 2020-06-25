package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = false, exclude = {"group", "subCategories"})
@EqualsAndHashCode(callSuper = true)
@Entity
public class Category extends BaseEntity {

    private String name;
    private String description;

    @OneToOne
    private Category group;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Category> subCategories = new ArrayList<>();

    @Builder
    public Category(Long id, String name, String description, Category group, List<Category> subCategories) {
        super(id);
        this.name = name;
        this.description = description;
        this.group = group;
        this.subCategories = Optional.ofNullable(subCategories).orElse(this.subCategories);
    }
}
