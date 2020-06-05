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
public class TransactionServiceImpl extends AbstractCrudServiceImpl<Transaction, Long, TransactionRepo> implements TransactionService {

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        super(transactionRepo);
    }

    @Override
    public List<Transaction> findAllByAccount(Account account) {
        return IterableUtils.toList(repo.findAllByAccountOrderByValueDate(account));
    }

    @Override
    public List<Transaction> findAllByCounterpart(Counterpart counterpart) {
        return IterableUtils.toList(repo.findAllByCounterpartOrderByValueDate(counterpart));
    }

    @Override
    public List<Transaction> findAllByForeignAccount(Account account) {
        return IterableUtils.toList(repo.findAllByCounterpartAccountOrderByValueDate(account));
    }
}
