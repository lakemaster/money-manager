package com.jojobi.mm;

import com.jojobi.mm.bootstrap.TestDataLoader;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterparty;
import com.jojobi.mm.model.Transaction;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CounterpartyService;
import com.jojobi.mm.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
class MoneyManagerApplicationTests {

    @Autowired
    private CounterpartyService counterpartyService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Test
    void contextLoads() {
    }

    @Test
    void checkCounterparties() {
        List<Counterparty> counterparties = counterpartyService.findAll();
        assertThat(counterparties.size()).isGreaterThan(0);
    }

    @Test
    void checkCounterparty() {
        Counterparty cp = counterpartyService.findById(1L).orElseThrow();
        assertThat(cp.getId()).isEqualTo(1);
        assertThat(cp.getName()).isEqualTo(TestDataLoader.COUNTERPARTY1_NAME);

        Account acc1 = cp.getAccounts().get(0);
        assertThat(acc1.getBic()).isEqualTo(TestDataLoader.ACCOUNT1_BIC);
        assertThat(acc1.getIsin()).isEqualTo(TestDataLoader.ACCOUNT1_ISIN);
        assertThat(acc1.getCounterparty()).isEqualTo(cp);
        assertThat(acc1.getCounterparty().getId()).isEqualTo(1L);

        Account acc2 = cp.getAccounts().get(0);
        assertThat(acc2.getBic()).isEqualTo(TestDataLoader.ACCOUNT1_BIC);
        assertThat(acc2.getIsin()).isEqualTo(TestDataLoader.ACCOUNT1_ISIN);
        assertThat(acc2.getCounterparty()).isEqualTo(cp);
        assertThat(acc2.getCounterparty().getId()).isEqualTo(1L);
    }
}
