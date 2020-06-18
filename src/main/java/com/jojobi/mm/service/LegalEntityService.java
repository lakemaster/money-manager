package com.jojobi.mm.service;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.LegalEntity;

public interface LegalEntityService extends CrudService<LegalEntity, Long> {
    long getNumberOfTransactions(Account account, LegalEntity legalEntity, Account counterpartAccount);
}
