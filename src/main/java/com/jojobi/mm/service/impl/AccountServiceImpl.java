package com.jojobi.mm.service.impl;

import com.jojobi.mm.info.AccountInfo;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Transaction;
import com.jojobi.mm.repo.AccountRepo;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl extends AbstractCrudServiceImpl<Account, Long, AccountRepo> implements AccountService {

    private final TransactionService transactionService;

    public AccountServiceImpl(AccountRepo accountRepo, TransactionService transactionService) {
        super(accountRepo);
        this.transactionService = transactionService;
    }

    @Override
    public Account findAccountByIsin(String isin) {
        return repo.findByIsin(isin).orElse(null);
    }

    @Override
    public AccountInfo getAccountInfo(Account account) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccount(account);

        List<Transaction> allTransactions = transactionService.findAllByAccount(account);
        if ( allTransactions.size() > 0 ) {
            accountInfo.setFrom(allTransactions.get(0).getValueDate());
            accountInfo.setTo(allTransactions.get(allTransactions.size() - 1).getValueDate());
            accountInfo.setNumberOfTransaction(allTransactions.size());
        }

        return accountInfo;
    }
}
