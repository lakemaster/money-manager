package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "natures")
@EqualsAndHashCode(callSuper = true, exclude = "natures")
@Entity
public class NatureGroup extends BaseEntity {
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Nature> natures;

}
