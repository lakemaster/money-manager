package com.jojobi.mm.repo;

import com.jojobi.mm.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepo extends CrudRepository<Account, Long> {
    Optional<Account> findByIsin(String isin);

}
