package com.jojobi.mm.service.impl;

import com.jojobi.mm.model.NatureGroup;
import com.jojobi.mm.repo.NatureGroupRepo;

public class NatureGroupServiceImpl extends AbstractCrudServiceImpl<NatureGroup, Long, NatureGroupRepo> {

    public NatureGroupServiceImpl(NatureGroupRepo natureGroupRepo) {
        super(natureGroupRepo);
    }


}
