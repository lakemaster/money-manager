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

    public static final Long COUNTERPARTY1_ID = 1L;
    public static final String COUNTERPARTY1_NAME = "Big Business AG";
    public static final Long CP1_ACCOUNT1_ID = 1L;
    public static final String CP1_ACCOUNT1_BIC = "ABCDEF1234";
    public static final String CP1_ACCOUNT1_ISIN = "DE1234567891234";
    public static final Long CP1_ACCOUNT2_ID = 2L;
    public static final String CP1_ACCOUNT2_BIC = "XYZDEF6789";
    public static final String CP1_ACCOUNT2_ISIN = "SE9876543219876";

    public static final Long COUNTERPARTY2_ID = 2L;
    public static final String COUNTERPARTY2_NAME = "Small Business GmbH";
    public static final Long CP2_ACCOUNT_ID = 3L;
    public static final String CP2_ACCOUNT_BIC = "GHTRF4564";
    public static final String CP2_ACCOUNT_ISIN = "CH36453658763";


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

        // Counterparty 1
        Account acc1 = Account.builder()
                .bic(CP1_ACCOUNT1_BIC)
                .isin(CP1_ACCOUNT1_ISIN)
                .build();

        Account acc2 = Account.builder()
                .bic(CP1_ACCOUNT2_BIC)
                .isin(CP1_ACCOUNT2_ISIN)
                .build();

        Counterparty cp1 = Counterparty.builder()
                .name(COUNTERPARTY1_NAME)
                .accounts(List.of(acc1, acc2))
                .build();

        acc1.setCounterparty(cp1);
        acc2.setCounterparty(cp1);

        counterpartyService.save(cp1);

        // Counterparty 2
        Account acc3 = Account.builder()
                .bic(CP2_ACCOUNT_BIC)
                .isin(CP2_ACCOUNT_ISIN)
                .build();

        Counterparty cp2 = Counterparty.builder()
                .name(COUNTERPARTY2_NAME)
                .accounts(List.of(acc3))
                .build();

        acc3.setCounterparty(cp2);

        counterpartyService.save(cp2);


        // Transactions


    }
}
