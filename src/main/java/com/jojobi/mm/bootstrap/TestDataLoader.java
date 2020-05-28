package com.jojobi.mm.bootstrap;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterparty;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CounterpartyService;
import com.jojobi.mm.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile({"default", "test"})
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    public static final Long ACCOUNT1_ID = 1L;
    public static final String ACCOUNT1_BIC = "ABCDEF1234";
    public static final String ACCOUNT1_ISIN = "DE1234567891234";
    public static final Long ACCOUNT2_ID = 2L;
    public static final String ACCOUNT2_BIC = "XYZDEF6789";
    public static final String ACCOUNT2_ISIN = "SE9876543219876";
    public static final Long COUNTERPARTY1_ID = 1L;
    public static final String COUNTERPARTY1_NAME = "Big Business AG";

    private AccountService accountService;
    private CounterpartyService counterpartyService;
    private TransactionService transactionService;

    public TestDataLoader(AccountService accountService, CounterpartyService counterpartyService, TransactionService transactionService) {
        this.accountService = accountService;
        this.counterpartyService = counterpartyService;
        this.transactionService = transactionService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Run {}", this.getClass().getSimpleName());
        loadTestCounterparties();

    }

    private void loadTestCounterparties() {

        Account acc1 = Account.builder()
                .bic(ACCOUNT1_BIC)
                .isin(ACCOUNT1_ISIN)
                .build();

        Account acc2 = Account.builder()
                .bic(ACCOUNT2_BIC)
                .isin(ACCOUNT2_ISIN)
                .build();

        Counterparty cp = Counterparty.builder()
                .name(COUNTERPARTY1_NAME)
                .accounts(List.of(acc1, acc2))
                .build();

        acc1.setCounterparty(cp);
        acc2.setCounterparty(cp);

        counterpartyService.save(cp);

    }
}
