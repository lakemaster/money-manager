package com.jojobi.mm.service;

import com.jojobi.mm.model.Nature;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NatureService extends CrudService<Nature, Long> {

    List<Nature> getAllTopLevelNatures();
}
