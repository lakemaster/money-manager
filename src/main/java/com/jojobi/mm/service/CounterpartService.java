package com.jojobi.mm.service;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterpart;

public interface CounterpartService extends CrudService<Counterpart, Long> {
    long getNumberOfTransactions(Counterpart counterpart);
    long getNumberOfTransactions(Account account);
}
