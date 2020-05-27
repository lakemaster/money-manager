package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@Entity
public class Transaction extends BaseEntity {

    private LocalDate valueDate;
    private LocalDate bookingDate;
    private TransactionType type;
    private String text;
    private Double amount;
    private String mandate;
    private String customerReference;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Counterparty counterparty;

    @ManyToOne
    private Account counterPartyAccount;

}
