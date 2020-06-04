package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "accounts")
@EqualsAndHashCode(callSuper = true, exclude = "accounts")
@Entity
public class Counterpart extends BaseEntity {

    private String name;
    private String creditorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "counterpart", fetch = FetchType.EAGER)
    private List<Account> accounts;

    @Builder
    public Counterpart(Long id, String name, String creditorId, List<Account> accounts) {
        super(id);
        this.name = name;
        this.creditorId = creditorId;
        this.accounts = new ArrayList<>(accounts);
    }
}
