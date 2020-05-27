package com.jojobi.mm.repo;

import com.jojobi.mm.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepo extends CrudRepository<Account, Long> {
}
