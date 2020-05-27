package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Counterparty;
import com.jojobi.mm.repo.CounterpartyRepo;
import com.jojobi.mm.service.CounterpartyService;
import org.springframework.stereotype.Service;

@Service
public class CounterpartyServiceImpl extends AbstractCrudServiceImpl<Counterparty, Long, CounterpartyRepo> implements CounterpartyService {

    public CounterpartyServiceImpl(CounterpartyRepo counterpartyRepo) {
        super(counterpartyRepo);
    }
}
