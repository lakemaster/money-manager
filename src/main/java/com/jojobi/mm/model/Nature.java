package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    private List<Nature> subNatures;
}
