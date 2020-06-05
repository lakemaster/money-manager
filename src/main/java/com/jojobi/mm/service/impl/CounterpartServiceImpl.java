package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.model.Transaction;
import com.jojobi.mm.repo.CounterpartRepo;
import com.jojobi.mm.service.CounterpartService;
import com.jojobi.mm.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterpartServiceImpl extends AbstractCrudServiceImpl<Counterpart, Long, CounterpartRepo> implements CounterpartService {

    private final TransactionService transactionService;

    public CounterpartServiceImpl(CounterpartRepo counterpartRepo, TransactionService transactionService) {
        super(counterpartRepo);
        this.transactionService = transactionService;
    }

    @Override
    public long getNumberOfTransactions(Counterpart counterpart) {
        return transactionService.findAllByCounterpart(counterpart).size();
    }

    @Override
    public long getNumberOfTransactions(Account account) {
        return transactionService.findAllByForeignAccount(account).size();
    }
}
