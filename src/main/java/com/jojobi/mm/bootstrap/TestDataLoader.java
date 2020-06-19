package com.jojobi.mm.bootstrap;

import com.jojobi.mm.model.*;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.LegalEntityService;
import com.jojobi.mm.service.NatureService;
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

    public static final Long CP_BANK_ID = 4L;
    public static final String CP_BANK_NAME = "Country Bank";
    public static final Long CP_BANK_ACCOUNT_ID = 5L;
    public static final String CP_BANK_ACCOUNT_BIC = "ZIUZDHG187263";
    public static final String CP_BANK_ACCOUNT_ISIN = "FR9832754928";


    private final AccountService accountService;
    private final LegalEntityService legalEntityService;
    private final TransactionService transactionService;
    private final NatureService natureService;

    public TestDataLoader(AccountService accountService, LegalEntityService legalEntityService, TransactionService transactionService, NatureService natureService) {
        this.accountService = accountService;
        this.legalEntityService = legalEntityService;
        this.transactionService = transactionService;
        this.natureService = natureService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Run {}", this.getClass().getSimpleName());

        loadTestLegelEntitiesAndTransactions();
        loadNatures();
    }

    private void loadTestLegelEntitiesAndTransactions() {

        // myself
        Account myAccount = Account.builder()
                .id(MYSELF_ACCOUNT_ID)
                .bic(MYSELF_ACCOUNT_BIC)
                .isin(MYSELF_ACCOUNT_ISIN)
                .build();

        LegalEntity myself = LegalEntity.builder()
                .id(MYSELF_ID)
                .name(MYSELF_NAME)
                .accounts(List.of(myAccount))
                .build();

        myAccount.setOwner(myself);

        legalEntityService.save(myself);


        // Counterpart Employer
        Account employerAccount = Account.builder()
                .id(CP_EMPLOYER_ACCOUNT_ID)
                .bic(CP_EMPLOYER_ACCOUNT_BIC)
                .isin(CP_EMPLOYER_ACCOUNT_ISIN)
                .build();

        LegalEntity employer = LegalEntity.builder()
                .id(CP_EMPLOYER_ID)
                .name(CP_EMPLOYER_NAME)
                .accounts(List.of(employerAccount))
                .build();

        employerAccount.setOwner(employer);

        employer = legalEntityService.save(employer);


        // Counterpart Insurance Company
        Account insuranceAccount1 = Account.builder()
                .id(CP_INSURANCE_ACCOUNT1_ID)
                .bic(CP_INSURANCE_ACCOUNT1_BIC)
                .isin(CP_INSURANCE_ACCOUNT1_ISIN)
                .build();

        Account insuranceAccount2 = Account.builder()
                .id(CP_INSURANCE_ACCOUNT2_ID)
                .bic(CP_INSURANCE_ACCOUNT2_BIC)
                .isin(CP_INSURANCE_ACCOUNT2_ISIN)
                .build();

        LegalEntity insuranceCompany = LegalEntity.builder()
                .id(CP_INSURANCE_COMPANY_ID)
                .name(CP_INSURANCE_COMPANY_NAME)
                .accounts(List.of(insuranceAccount1, insuranceAccount2))
                .creditorId(CP_INSURANCE_CREDITOR_ID)
                .build();

        insuranceAccount1.setOwner(insuranceCompany);
        insuranceAccount2.setOwner(insuranceCompany);

        insuranceCompany = legalEntityService.save(insuranceCompany);

        // Bank
        Account bankAccount = Account.builder()
                .id(CP_BANK_ACCOUNT_ID)
                .bic(CP_BANK_ACCOUNT_BIC)
                .isin(CP_BANK_ACCOUNT_ISIN)
                .build();

        LegalEntity bank = LegalEntity.builder()
                .id(CP_BANK_ID)
                .name(CP_BANK_NAME)
                .accounts(List.of(bankAccount))
                .build();

        bankAccount.setOwner(bank);

        bank = legalEntityService.save(bank);



        // Transactions
        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.DEBIT)
                .amount(MY_SALARY_AMOUNT)
                .counterpart(employer)
                .counterpartAccount(employerAccount)
                .text(MY_SALARY_TRANSACTION_TEXT)
                .bookingDate(MY_SALARY_BOOKING_DATE)
                .valueDate(MY_SALARY_VALUE_DATE)
                .build());

        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.CREDIT)
                .amount(MY_INSURANCE_PREMIUM)
                .counterpart(insuranceCompany)
                .counterpartAccount(insuranceAccount1)
                .text(MY_INSURANCE_TRANSACTION_TEXT)
                .bookingDate(MY_INSURANCE_BOOKING_DATE)
                .valueDate(MY_INSURANCE_VALUE_DATE)
                .customerReference(MY_INSURANCE_CUSTOMER_REFERENCE)
                .mandate(MY_INSURANCE_MANDATE)
                .build());

        Transaction t = Transaction.builder()
                .account(myAccount)
                .type(TransactionType.CREDIT)
                .amount(100d)
                .counterpart(bank)
                .counterpartAccount(bankAccount)
                .text("cash point withdrawal")
                .bookingDate(LocalDate.of(2020, 1, 20))
                .valueDate(LocalDate.of(2020, 1, 20))
                .build();

        transactionService.save(t);

        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.CREDIT)
                .amount(150d)
                .counterpart(bank)
                .counterpartAccount(bankAccount)
                .text("cash point withdrawal")
                .bookingDate(LocalDate.of(2020, 1, 23))
                .valueDate(LocalDate.of(2020, 1, 23))
                .build());

        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.CREDIT)
                .amount(50d)
                .counterpart(bank)
                .counterpartAccount(bankAccount)
                .text("cash point withdrawal")
                .bookingDate(LocalDate.of(2020, 1, 27))
                .valueDate(LocalDate.of(2020, 1, 27))
                .build());

    }


    private void loadNatures(){

        Nature retirementProvision = Nature.builder()
                .name("Retirement Provision")
                .description("Retirement Provision")
                .build();

        Nature savings = Nature.builder()
                .name("Savings")
                .description("General Savings")
                .build();

        Nature precaution = Nature.builder()
                .name("Precaution")
                .description("General financial precaution")
                .subNatures(List.of(retirementProvision, savings))
                .build();

        retirementProvision.setGroup(precaution);
        savings.setGroup(precaution);


        Nature giroCard1 = Nature.builder()
                .name("Cash Withdrawal GC 1")
                .description("Cash Withdrawal Girocard 1")
                .build();

        Nature giroCard2 = Nature.builder()
                .name("Cash Withdrawal GC 2")
                .description("Cash Withdrawal Girocard 2")
                .build();

        Nature masterCard = Nature.builder()
                .name("Cash Withdrawal MC")
                .description("Cash Withdrawal Mastercard")
                .build();

        Nature cashWithdrawal = Nature.builder()
                .name("Cash Withdrawal")
                .description("General Cash Withdrawal")
                .subNatures(List.of(giroCard1, giroCard2, masterCard))
                .build();

        giroCard1.setGroup(cashWithdrawal);
        giroCard2.setGroup(cashWithdrawal);
        masterCard.setGroup(cashWithdrawal);

        Nature insurances = Nature.builder()
                .name("Insurence")
                .description("General Insurance Costs")
                .build();

        natureService.save(precaution);
        natureService.save(cashWithdrawal);
        natureService.save(insurances);
    }
}
