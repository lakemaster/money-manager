package com.jojobi.mm.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
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
    private Counterpart counterpart;

    @ManyToOne
    private Account counterPartyAccount;

    @Builder
    public Transaction(Long id, LocalDate valueDate, LocalDate bookingDate, TransactionType type,
                       String text, Double amount, String mandate, String customerReference, Account account,
                       Counterpart counterpart, Account counterPartyAccount) {
        super(id);
        this.valueDate = valueDate;
        this.bookingDate = bookingDate;
        this.type = type;
        this.text = text;
        this.amount = amount;
        this.mandate = mandate;
        this.customerReference = customerReference;
        this.account = account;
        this.counterpart = counterpart;
        this.counterPartyAccount = counterPartyAccount;
    }
}
