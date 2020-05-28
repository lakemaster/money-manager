package com.jojobi.mm.service;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Transaction;

import java.util.List;

public interface TransactionService extends CrudService<Transaction, Long> {

    List<Transaction> findAllByAccount(Account account);
}
