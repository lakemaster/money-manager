package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.Nature;
import com.jojobi.mm.repo.NatureRepo;
import com.jojobi.mm.service.NatureService;

import java.util.List;

public class NatureServiceImpl extends AbstractCrudServiceImpl<Nature, Long, NatureRepo> implements NatureService {

    public NatureServiceImpl(NatureRepo natureRepo) {
        super(natureRepo);
    }

    @Override
    public List<Nature> getAllTopLevelNatures() {
        return repo.findAllByGroupIsNullOrderByName();
    }
}
