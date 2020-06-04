package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.repo.CounterpartRepo;
import com.jojobi.mm.service.CounterpartService;
import org.springframework.stereotype.Service;

@Service
public class CounterpartServiceImpl extends AbstractCrudServiceImpl<Counterpart, Long, CounterpartRepo> implements CounterpartService {

    public CounterpartServiceImpl(CounterpartRepo counterpartRepo) {
        super(counterpartRepo);
    }
}
