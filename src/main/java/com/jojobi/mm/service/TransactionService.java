package com.jojobi.mm.service;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.LegalEntity;
import com.jojobi.mm.model.Transaction;

import java.util.List;

public interface TransactionService extends CrudService<Transaction, Long> {

    List<Transaction> findAllByAccount(Account account, LegalEntity legalEntity, Account counterpartAccount);

    default List<Transaction> findAllByAccount(Account account) {
        return  findAllByAccount(account, null, null);
    }

    default List<Transaction> findAllByAccount(Account account, LegalEntity legalEntity) {
        return  findAllByAccount(account, legalEntity, null);
    }

}
