package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Transaction;
import com.jojobi.mm.repo.AccountRepo;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl extends AbstractCrudServiceImpl<Account, Long, AccountRepo> implements AccountService {

    public AccountServiceImpl(AccountRepo accountRepo) {
        super(accountRepo);
    }

    @Override
    public Optional<Account> findAccountByIsin(String isin) {
        return repo.findByIsin(isin);
    }
}
