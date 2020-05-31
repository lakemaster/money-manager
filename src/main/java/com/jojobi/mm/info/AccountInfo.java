package com.jojobi.mm.info;

import com.jojobi.mm.model.Account;

import java.time.LocalDate;

public class AccountInfo {
    private Account account;
    private Integer numberOfTransaction = 0;
    private LocalDate from = LocalDate.of(1900,1,1);
    private LocalDate to = LocalDate.of(1900,1,1);

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getNumberOfTransaction() {
        return numberOfTransaction;
    }

    public void setNumberOfTransaction(Integer numberOfTransaction) {
        this.numberOfTransaction = numberOfTransaction;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
