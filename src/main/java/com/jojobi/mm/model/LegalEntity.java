package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "accounts")
@EqualsAndHashCode(callSuper = true, exclude = "accounts")
@Entity
public class LegalEntity extends BaseEntity {

    private String name;
    private String creditorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();

    @Builder
    public LegalEntity(Long id, String name, String creditorId, List<Account> accounts) {
        super(id);
        this.name = name;
        this.creditorId = creditorId;
        this.accounts = Optional.ofNullable(accounts).orElse(this.accounts);
    }

    public Account getAccount(String isin) {
        return accounts.stream().filter(acc -> acc.getIsin().equals(isin)).findFirst().orElse(null);
    }
}
