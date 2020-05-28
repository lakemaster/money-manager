package com.jojobi.mm;

import com.jojobi.mm.bootstrap.TestDataLoader;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterparty;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CounterpartyService;
import com.jojobi.mm.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        assertThat(counterparties.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void checkCounterparty() {
        Counterparty cp1 = counterpartyService.findById(TestDataLoader.COUNTERPARTY1_ID).orElseThrow();
        assertThat(cp1.getId()).isEqualTo(TestDataLoader.COUNTERPARTY1_ID);
        assertThat(cp1.getName()).isEqualTo(TestDataLoader.COUNTERPARTY1_NAME);

        Account acc1 = cp1.getAccounts().get(0);
        assertThat(acc1.getBic()).isEqualTo(TestDataLoader.CP1_ACCOUNT1_BIC);
        assertThat(acc1.getIsin()).isEqualTo(TestDataLoader.CP1_ACCOUNT1_ISIN);
        assertThat(acc1.getCounterparty()).isEqualTo(cp1);
        assertThat(acc1.getCounterparty().getId()).isEqualTo(TestDataLoader.COUNTERPARTY1_ID);

        Account acc2 = cp1.getAccounts().get(1);
        assertThat(acc2.getBic()).isEqualTo(TestDataLoader.CP1_ACCOUNT2_BIC);
        assertThat(acc2.getIsin()).isEqualTo(TestDataLoader.CP1_ACCOUNT2_ISIN);
        assertThat(acc2.getCounterparty()).isEqualTo(cp1);
        assertThat(acc2.getCounterparty().getId()).isEqualTo(TestDataLoader.COUNTERPARTY1_ID);

        Counterparty cp2 = counterpartyService.findById(TestDataLoader.COUNTERPARTY2_ID).orElseThrow();
        assertThat(cp2.getId()).isEqualTo(TestDataLoader.COUNTERPARTY2_ID);
        assertThat(cp2.getName()).isEqualTo(TestDataLoader.COUNTERPARTY2_NAME);

        Account acc = cp2.getAccounts().get(0);
        assertThat(acc.getBic()).isEqualTo(TestDataLoader.CP2_ACCOUNT_BIC);
        assertThat(acc.getIsin()).isEqualTo(TestDataLoader.CP2_ACCOUNT_ISIN);
        assertThat(acc.getCounterparty()).isEqualTo(cp2);
        assertThat(acc.getCounterparty().getId()).isEqualTo(TestDataLoader.COUNTERPARTY2_ID);
    }
}
