package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.model.Transaction;
import com.jojobi.mm.repo.TransactionRepo;
import com.jojobi.mm.service.TransactionService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl extends AbstractCrudServiceImpl<Transaction, Long, TransactionRepo>
        implements TransactionService {

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        super(transactionRepo);
    }

    @Override
    public List<Transaction> findAllByAccount(Account account, Counterpart counterpart, Account counterpartAccount) {

        Iterable<Transaction> transactions;
        if ( counterpart != null && counterpartAccount != null ) {
            transactions = repo.findAllByAccountAndCounterpartAndCounterpartAccountOrderByValueDate(
                    account, counterpart, counterpartAccount);
        } else if ( counterpart != null ) {
            transactions = repo.findAllByAccountAndCounterpartOrderByValueDate(account, counterpart);
        } else {
            transactions = repo.findAllByAccountOrderByValueDate(account);
        }

        return IterableUtils.toList(transactions);
    }
}
