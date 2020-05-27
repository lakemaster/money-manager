package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.repo.AccountRepo;
import com.jojobi.mm.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends AbstractCrudServiceImpl<Account, Long, AccountRepo> implements AccountService {

    public AccountServiceImpl(AccountRepo accountRepo) {
        super(accountRepo);
    }
}
