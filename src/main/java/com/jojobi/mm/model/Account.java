package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "counterparty")
@EqualsAndHashCode(callSuper = true, exclude = "counterparty")
@Entity
public class Account extends BaseEntity {

    private String isin;
    private String bic;

    @ManyToOne
    private Counterparty counterparty;


    @Builder
    public Account(Long id, String isin, String bic, Counterparty counterparty) {
        super(id);
        this.isin = isin;
        this.bic = bic;
        this.counterparty = counterparty;
    }
}
