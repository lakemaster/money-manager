package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account extends BaseEntity {

    private String isin;
    private String bic;

    @ManyToOne
    private Counterparty counterparty;

}
