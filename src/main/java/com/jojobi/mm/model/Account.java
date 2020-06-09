package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "counterpart")
@EqualsAndHashCode(callSuper = true, exclude = "counterpart")
@Entity
public class Account extends BaseEntity {

    private String isin;
    private String bic;

    @ManyToOne
    private Counterpart owner;


    @Builder
    public Account(Long id, String isin, String bic, Counterpart owner) {
        super(id);
        this.isin = isin;
        this.bic = bic;
        this.owner = owner;
    }
}
