package com.jojobi.mm.repo;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {

    Iterable<Transaction> findAllByAccountOrderByValueDate(Account account);
}
