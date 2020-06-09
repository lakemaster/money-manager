package com.jojobi.mm.repo;

import com.jojobi.mm.model.LegalEntity;
import org.springframework.data.repository.CrudRepository;

public interface CounterpartRepo extends CrudRepository<LegalEntity, Long> {
}
