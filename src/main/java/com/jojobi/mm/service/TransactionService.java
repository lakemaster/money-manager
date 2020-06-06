package com.jojobi.mm.service;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.model.Transaction;

import java.util.List;

public interface TransactionService extends CrudService<Transaction, Long> {

    List<Transaction> findAllByAccount(Account account, Counterpart counterpart, Account counterpartAccount);

    default List<Transaction> findAllByAccount(Account account) {
        return  findAllByAccount(account, null, null);
    }

    default List<Transaction> findAllByAccount(Account account, Counterpart counterpart) {
        return  findAllByAccount(account, counterpart, null);
    }

}
