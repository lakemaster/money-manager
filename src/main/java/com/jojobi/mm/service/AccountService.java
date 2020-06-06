package com.jojobi.mm.service;

import com.jojobi.mm.info.AccountInfo;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface AccountService extends CrudService<Account, Long> {
    Account findAccountByIsin(String isin);

    AccountInfo getAccountInfo(Account account);

    List<AccountInfo> getAccountInfos(List<Account> accounts);
}
