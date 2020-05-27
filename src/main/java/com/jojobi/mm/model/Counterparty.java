package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Counterparty extends BaseEntity {

    private String name;
    private String creditorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "counterparty")
    private List<Account> accounts;
}
