package com.jojobi.mm.repo;

import com.jojobi.mm.model.Nature;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NatureRepo extends CrudRepository<Nature, Long> {

    List<Nature> findAllByGroupIsNullOrderByName();

}
