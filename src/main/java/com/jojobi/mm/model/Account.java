package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Account extends BaseEntity {

    private String isin;
    private String bic;

    @ManyToOne
    private LegalEntity owner;


    @Builder
    public Account(Long id, String isin, String bic, LegalEntity owner) {
        super(id);
        this.isin = isin;
        this.bic = bic;
        this.owner = owner;
    }
}
