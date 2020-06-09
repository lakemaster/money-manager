package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.LegalEntity;
import com.jojobi.mm.repo.CounterpartRepo;
import com.jojobi.mm.service.CounterpartService;
import com.jojobi.mm.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class CounterpartServiceImpl extends AbstractCrudServiceImpl<LegalEntity, Long, CounterpartRepo> implements CounterpartService {

    private final TransactionService transactionService;

    public CounterpartServiceImpl(CounterpartRepo counterpartRepo, TransactionService transactionService) {
        super(counterpartRepo);
        this.transactionService = transactionService;
    }

    @Override
    public long getNumberOfTransactions(Account myAccount, LegalEntity legalEntity, Account counterpartAccount) {
        return transactionService.findAllByAccount(myAccount, legalEntity, counterpartAccount).size();
    }
}
