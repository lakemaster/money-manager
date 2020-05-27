package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Transaction;
import com.jojobi.mm.repo.TransactionRepo;
import com.jojobi.mm.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends AbstractCrudServiceImpl<Transaction, Long, TransactionRepo> implements TransactionService {

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        super(transactionRepo);
    }
}
