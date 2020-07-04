package com.jojobi.mm.bootstrap;

import com.jojobi.mm.model.*;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.LegalEntityService;
import com.jojobi.mm.service.CategoryService;
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
    public static final String CP_EMPLOYER_ACCOUNT_BIC = "GHTRF4564";
    public static final String CP_EMPLOYER_ACCOUNT_ISIN = "CH36453658763";

    public static final Long CP_INSURANCE_COMPANY_ID = 3L;
    public static final String CP_INSURANCE_COMPANY_NAME = "Big Business AG";
    public static final String CP_INSURANCE_ACCOUNT1_BIC = "ABCDEF1234";
    public static final String CP_INSURANCE_ACCOUNT1_ISIN = "DE1234567891234";
    public static final String CP_INSURANCE_ACCOUNT2_BIC = "XYZDEF6789";
    public static final String CP_INSURANCE_ACCOUNT2_ISIN = "SE9876543219876";

    public static final String RETIREMENT_PROVISION = "Retirement Provision";

    private final AccountService accountService;
    private final LegalEntityService legalEntityService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    public static Account myAccount;
    public static LegalEntity myself;
    public static Account employerAccount;
    public static LegalEntity employer;
    public static Account insuranceAccount1;
    public static Account insuranceAccount2;
    public static LegalEntity insuranceCompany;
    public static Account bankAccount;
    public static LegalEntity bank;
    public static Category retirementProvision;
    public static Category savingsOnSavingsAccount;
    public static Category buildingSaving;
    public static Category savings;
    public static Category precaution;
    public static Category giroCard1;
    public static Category giroCard2;
    public static Category masterCard;
    public static Category cashWithdrawal;
    public static Category insurances;

    public TestDataLoader(AccountService accountService, LegalEntityService legalEntityService, TransactionService transactionService, CategoryService categoryService) {
        this.accountService = accountService;
        this.legalEntityService = legalEntityService;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Run {}", this.getClass().getSimpleName());

        loadTestLegelEntitiesAndTransactions();
        loadCategories();
    }

    private void loadTestLegelEntitiesAndTransactions() {

        // myself
        myAccount = Account.builder()
                .id(1L)
                .bic("IUDEF239875")
                .isin("GB9081143983")
                .build();

        myself = LegalEntity.builder()
                .id(1L)
                .name("Max Mustermann")
                .accounts(List.of(myAccount))
                .build();

        myAccount.setOwner(myself);

        legalEntityService.save(myself);


        // Counterpart Employer
        employerAccount = Account.builder()
                .id(2L)
                .bic("GHTRF4564")
                .isin("CH36453658763")
                .build();

        employer = LegalEntity.builder()
                .id(2L)
                .name("Small Business GmbH")
                .accounts(List.of(employerAccount))
                .build();

        employerAccount.setOwner(employer);

        employer = legalEntityService.save(employer);


        // Counterpart Insurance Company
        insuranceAccount1 = Account.builder()
                .id(3L)
                .bic("ABCDEF1234")
                .isin("DE1234567891234")
                .build();

        insuranceAccount2 = Account.builder()
                .id(4L)
                .bic("XYZDEF6789")
                .isin("SE9876543219876")
                .build();

        insuranceCompany = LegalEntity.builder()
                .id(3L)
                .name("Big Business AG")
                .accounts(List.of(insuranceAccount1, insuranceAccount2))
                .creditorId("DE98C90385-01")
                .build();

        insuranceAccount1.setOwner(insuranceCompany);
        insuranceAccount2.setOwner(insuranceCompany);

        insuranceCompany = legalEntityService.save(insuranceCompany);

        // Bank
        bankAccount = Account.builder()
                .id(5L)
                .bic("ZIUZDHG187263")
                .isin("FR9832754928")
                .build();

        bank = LegalEntity.builder()
                .id(4L)
                .name("Country Bank")
                .accounts(List.of(bankAccount))
                .build();

        bankAccount.setOwner(bank);

        bank = legalEntityService.save(bank);



        // Transactions
        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.DEBIT)
                .amount(3500.00)
                .counterpart(employer)
                .counterpartAccount(employerAccount)
                .text("salary")
                .bookingDate(LocalDate.of(2020, 1, 2))
                .valueDate(LocalDate.of(2020, 1, 2))
                .build());

        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.CREDIT)
                .amount(325.00)
                .counterpart(insuranceCompany)
                .counterpartAccount(insuranceAccount1)
                .text("insurance")
                .bookingDate(LocalDate.of(2020, 1, 17))
                .valueDate(LocalDate.of(2020, 1, 20))
                .customerReference("54273813656")
                .mandate("CSDA357987654398")
                .build());

        transactionService.save(Transaction.builder()
                .account(myAccount)
                .type(TransactionType.CREDIT)
                .amount(100d)
                .counterpart(bank)
                .counterpartAccount(bankAccount)
                .text("cash point withdrawal")
                .bookingDate(LocalDate.of(2020, 1, 20))
                .valueDate(LocalDate.of(2020, 1, 20))
                .build());

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


    private void loadCategories(){

        retirementProvision = Category.builder()
                .name(RETIREMENT_PROVISION)
                .description("Retirement Provision")
                .build();

        savingsOnSavingsAccount = Category.builder()
                .name("Savings on Savings Account")
                .description("Savings on Savings Account")
                .build();

        buildingSaving = Category.builder()
                .name("Building Saving")
                .description("Building Saving Contract")
                .build();

        savings = Category.builder()
                .name("Savings")
                .description("General Savings")
                .subCategories(List.of(savingsOnSavingsAccount, buildingSaving))
                .build();

        precaution = Category.builder()
                .name("Precaution")
                .description("General financial precaution")
                .subCategories(List.of(retirementProvision, savings))
                .build();

        savingsOnSavingsAccount.setGroup(savings);
        buildingSaving.setGroup(savings);
        retirementProvision.setGroup(precaution);
        savings.setGroup(precaution);

        giroCard1 = Category.builder()
                .name("Cash Withdrawal GC 1")
                .description("Cash Withdrawal Girocard 1")
                .build();

        giroCard2 = Category.builder()
                .name("Cash Withdrawal GC 2")
                .description("Cash Withdrawal Girocard 2")
                .build();

        masterCard = Category.builder()
                .name("Cash Withdrawal MC")
                .description("Cash Withdrawal Mastercard")
                .build();

        cashWithdrawal = Category.builder()
                .name("Cash Withdrawal")
                .description("General Cash Withdrawal")
                .subCategories(List.of(giroCard1, giroCard2, masterCard))
                .build();

        giroCard1.setGroup(cashWithdrawal);
        giroCard2.setGroup(cashWithdrawal);
        masterCard.setGroup(cashWithdrawal);

        insurances = Category.builder()
                .name("Insurence")
                .description("General Insurance Costs")
                .build();

        precaution = categoryService.save(precaution);
        categoryService.save(cashWithdrawal);
        categoryService.save(insurances);
    }
}
