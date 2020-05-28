package com.jojobi.mm.bootstrap;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterparty;
import com.jojobi.mm.model.Transaction;
import com.jojobi.mm.model.TransactionType;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CounterpartyService;
import com.jojobi.mm.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@Profile({"default", "test"})
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    public static final Long MYSELF_ID = 1L;
    public static final String MYSELF_NAME = "Max Mustermann";
    public static final Long MYSELF_ACCOUNT_ID = 1L;
    public static final String MYSELF_ACCOUNT_BIC = "IUDEF239875";
    public static final String MYSELF_ACCOUNT_ISIN = "GB9081143983";
    public static final double MY_SALARY_AMOUNT = 3500.00;
    public static final String MY_SALARY_TRANSACTION_TEXT = "salary";
    public static final LocalDate MY_SALARY_BOOKING_DATE = LocalDate.of(2020, 1, 2);
    public static final LocalDate MY_SALARY_VALUE_DATE = LocalDate.of(2020, 1, 2);
    public static final double MY_INSURANCE_PREMIUM = 325.00;
    public static final String MY_INSURANCE_TRANSACTION_TEXT = "insurance";
    public static final LocalDate MY_INSURANCE_BOOKING_DATE = LocalDate.of(2020, 1, 17);
    public static final LocalDate MY_INSURANCE_VALUE_DATE = LocalDate.of(2020, 1, 20);
    public static final String MY_INSURANCE_CUSTOMER_REFERENCE = "54273813656";
    public static final String MY_INSURANCE_MANDATE = "CSDA357987654398";

    public static final Long CP_EMPLOYER_ID = 2L;
    public static final String CP_EMPLOYER_NAME = "Small Business GmbH";
    public static final Long CP_EMPLOYER_ACCOUNT_ID = 2L;
    public static final String CP_EMPLOYER_ACCOUNT_BIC = "GHTRF4564";
    public static final String CP_EMPLOYER_ACCOUNT_ISIN = "CH36453658763";

    public static final Long CP_INSURANCE_COMPANY_ID = 3L;
    public static final String CP_INSURANCE_COMPANY_NAME = "Big Business AG";
    public static final String CP_INSURANCE_CREDITOR_ID = "DE98C90385-01";
    public static final Long CP_INSURANCE_ACCOUNT1_ID = 3L;
    public static final String CP_INSURANCE_ACCOUNT1_BIC = "ABCDEF1234";
    public static final String CP_INSURANCE_ACCOUNT1_ISIN = "DE1234567891234";
    public static final Long CP_INSURANCE_ACCOUNT2_ID = 4L;
    public static final String CP_INSURANCE_ACCOUNT2_BIC = "XYZDEF6789";
    public static final String CP_INSURANCE_ACCOUNT2_ISIN = "SE9876543219876";


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

        // myself
        Account myAccount = Account.builder()
                .bic(MYSELF_ACCOUNT_BIC)
                .isin(MYSELF_ACCOUNT_ISIN)
                .build();

        Counterparty myself = Counterparty.builder()
                .name(MYSELF_NAME)
                .accounts(List.of(myAccount))
                .build();

        myAccount.setCounterparty(myself);

        myself = counterpartyService.save(myself);


        // Counterparty Employer
        Account employerAccount = Account.builder()
                .bic(CP_EMPLOYER_ACCOUNT_BIC)
                .isin(CP_EMPLOYER_ACCOUNT_ISIN)
                .build();

        Counterparty employer = Counterparty.builder()
                .name(CP_EMPLOYER_NAME)
                .accounts(List.of(employerAccount))
                .build();

        employerAccount.setCounterparty(employer);

        employer = counterpartyService.save(employer);


        // Counterparty Insurance Company
        Account insuranceAccount1 = Account.builder()
                .bic(CP_INSURANCE_ACCOUNT1_BIC)
                .isin(CP_INSURANCE_ACCOUNT1_ISIN)
                .build();

        Account insuranceAccount2 = Account.builder()
                .bic(CP_INSURANCE_ACCOUNT2_BIC)
                .isin(CP_INSURANCE_ACCOUNT2_ISIN)
                .build();

        Counterparty insuranceCompany = Counterparty.builder()
                .name(CP_INSURANCE_COMPANY_NAME)
                .accounts(List.of(insuranceAccount1, insuranceAccount2))
                .creditorId(CP_INSURANCE_CREDITOR_ID)
                .build();

        insuranceAccount1.setCounterparty(insuranceCompany);
        insuranceAccount2.setCounterparty(insuranceCompany);

        insuranceCompany = counterpartyService.save(insuranceCompany);



        // Transactions
        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.DEBIT)
                .amount(MY_SALARY_AMOUNT)
                .counterparty(employer)
                .counterPartyAccount(employerAccount)
                .text(MY_SALARY_TRANSACTION_TEXT)
                .bookingDate(MY_SALARY_BOOKING_DATE)
                .valueDate(MY_SALARY_VALUE_DATE)
                .build());

        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.CREDIT)
                .amount(MY_INSURANCE_PREMIUM)
                .counterparty(insuranceCompany)
                .counterPartyAccount(insuranceAccount1)
                .text(MY_INSURANCE_TRANSACTION_TEXT)
                .bookingDate(MY_INSURANCE_BOOKING_DATE)
                .valueDate(MY_INSURANCE_VALUE_DATE)
                .customerReference(MY_INSURANCE_CUSTOMER_REFERENCE)
                .mandate(MY_INSURANCE_MANDATE)
                .build());
    }
}
