package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Nature extends BaseEntity {

    private String name;
    private String description;

    @OneToOne
    private Nature group;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Nature> subNatures = new ArrayList<>();

    @Builder
    public Nature(Long id, String name, String description, Nature group, List<Nature> subNatures) {
        super(id);
        this.name = name;
        this.description = description;
        this.subNatures = Optional.ofNullable(subNatures).orElse(this.subNatures);
    }
}
