package com.jojobi.mm.repo;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.LegalEntity;
import com.jojobi.mm.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    Iterable<Transaction> findAllByAccountOrderByValueDate(Account account);

    Iterable<Transaction> findAllByAccountAndCounterpartOrderByValueDate(Account account, LegalEntity legalEntity);

    Iterable<Transaction> findAllByAccountAndCounterpartAndCounterpartAccountOrderByValueDate(
            Account account, LegalEntity legalEntity, Account counterpartAccount);
}
