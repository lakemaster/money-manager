package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@Entity
public class Account extends BaseEntity {

    private String isin;
    private String bic;

    @ManyToOne
    private Counterparty counterparty;

}
