package com.jojobi.mm;

import com.jojobi.mm.bootstrap.TestDataLoader;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Category;
import com.jojobi.mm.model.LegalEntity;
import com.jojobi.mm.model.Transaction;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CategoryService;
import com.jojobi.mm.service.LegalEntityService;
import com.jojobi.mm.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
class MoneyManagerApplicationTests {

    @Autowired
    private LegalEntityService legalEntityService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CategoryService categoryService;

    @Test
    void contextLoads() {
    }

    @Test
    void checkCounterparts() {
        List<LegalEntity> legalEntities = legalEntityService.findAll();
        assertThat(legalEntities.size()).isGreaterThanOrEqualTo(3);
    }

    @Test
    void checkLegalEntitiesAndAccounts() {
        LegalEntity insuranceCompany = legalEntityService.findById(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(insuranceCompany.getId()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(insuranceCompany.getName()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_NAME);

        Account acc1 = insuranceCompany.getAccounts().get(0);
        assertThat(acc1.getBic()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT1_BIC);
        assertThat(acc1.getIsin()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT1_ISIN);
        assertThat(acc1.getOwner().getId()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(acc1.getOwner()).isEqualTo(insuranceCompany);

        Account acc2 = insuranceCompany.getAccounts().get(1);
        assertThat(acc2.getBic()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT2_BIC);
        assertThat(acc2.getIsin()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT2_ISIN);
        assertThat(acc2.getOwner().getId()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(acc2.getOwner()).isEqualTo(insuranceCompany);

        LegalEntity employer = legalEntityService.findById(TestDataLoader.CP_EMPLOYER_ID);
        assertThat(employer.getId()).isEqualTo(TestDataLoader.CP_EMPLOYER_ID);
        assertThat(employer.getName()).isEqualTo(TestDataLoader.CP_EMPLOYER_NAME);

        Account acc = employer.getAccounts().get(0);
        assertThat(acc.getBic()).isEqualTo(TestDataLoader.CP_EMPLOYER_ACCOUNT_BIC);
        assertThat(acc.getIsin()).isEqualTo(TestDataLoader.CP_EMPLOYER_ACCOUNT_ISIN);
        assertThat(acc.getOwner().getId()).isEqualTo(TestDataLoader.CP_EMPLOYER_ID);
        assertThat(acc.getOwner()).isEqualTo(employer);

        LegalEntity myself = legalEntityService.findById(TestDataLoader.MYSELF_ID);
        assertThat(myself.getId()).isEqualTo(TestDataLoader.MYSELF_ID);
        assertThat(myself.getName()).isEqualTo(TestDataLoader.MYSELF_NAME);

        Account myAcc = myself.getAccounts().get(0);
        assertThat(myAcc.getBic()).isEqualTo(TestDataLoader.myAccount.getBic());
        assertThat(myAcc.getIsin()).isEqualTo(TestDataLoader.myAccount.getIsin());
        assertThat(myAcc.getOwner().getId()).isEqualTo(TestDataLoader.MYSELF_ID);
        assertThat(myAcc.getOwner()).isEqualTo(myself);
    }

    @Test
    void checkTransactions() {
        // check my account
        Account myAccount = accountService.findAccountByIsin(TestDataLoader.myAccount.getIsin());
        assertThat(myAccount).isEqualTo(TestDataLoader.myAccount);

        // check myself
        LegalEntity myself = myAccount.getOwner();
        assertThat(myself).isEqualTo(TestDataLoader.myself);
        myself.getAccounts().forEach(acc -> {
            assertThat(acc).isEqualTo(TestDataLoader.myself.getAccount(acc.getIsin()));
        });

        // check number of transactions on my account
        List<Transaction> transactions = transactionService.findAllByAccount(myAccount);
        assertThat(transactions.size()).isEqualTo(5);

        // check first transaction
        Transaction tr1 = transactions.get(0);
        assertThat(tr1.getAccount().getId()).isEqualTo(myAccount.getId());
        assertThat(tr1.getAmount()).isEqualTo(TestDataLoader.MY_SALARY_AMOUNT);
        assertThat(tr1.getBookingDate()).isEqualTo(TestDataLoader.MY_SALARY_BOOKING_DATE);
        assertThat(tr1.getValueDate()).isEqualTo(TestDataLoader.MY_SALARY_VALUE_DATE);
        assertThat((tr1.getCustomerReference())).isNullOrEmpty();
        assertThat(tr1.getMandate()).isNullOrEmpty();
        assertThat(tr1.getText()).isEqualTo(TestDataLoader.MY_SALARY_TRANSACTION_TEXT);

        LegalEntity employer = legalEntityService.findById(TestDataLoader.employer.getId());
        assertThat(tr1.getCounterpart()).isEqualTo(TestDataLoader.employer);
        assertThat(tr1.getCounterpart()).isEqualTo(employer);
        assertThat(tr1.getCounterpartAccount()).isEqualTo(TestDataLoader.employerAccount);

        // check second transaction
        Transaction tr2 = transactions.get(1);
        assertThat(tr2.getAccount().getId()).isEqualTo(myAccount.getId());
        assertThat(tr2.getAmount()).isEqualTo(TestDataLoader.MY_INSURANCE_PREMIUM);
        assertThat(tr2.getBookingDate()).isEqualTo(TestDataLoader.MY_INSURANCE_BOOKING_DATE);
        assertThat(tr2.getValueDate()).isEqualTo(TestDataLoader.MY_INSURANCE_VALUE_DATE);
        assertThat(tr2.getCustomerReference()).isEqualTo(TestDataLoader.MY_INSURANCE_CUSTOMER_REFERENCE);
        assertThat(tr2.getMandate()).isEqualTo(TestDataLoader.MY_INSURANCE_MANDATE);
        assertThat(tr2.getText()).isEqualTo(TestDataLoader.MY_INSURANCE_TRANSACTION_TEXT);

        LegalEntity insuranceCompany = legalEntityService.findById(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(tr2.getCounterpart()).isEqualTo(TestDataLoader.insuranceCompany);
        assertThat(tr2.getCounterpart()).isEqualTo(insuranceCompany);
        assertThat(tr2.getCounterpartAccount()).isEqualTo(TestDataLoader.insuranceAccount1);
    }

    @Test
    public void checkCategories() {
        List<Category> allTopLevelCategories = categoryService.getAllTopLevelCategories();
        assertThat(allTopLevelCategories.size()).isGreaterThan(0);

        Category precaution = categoryService.findByName(TestDataLoader.precaution.getName());
        assertThat(precaution).isEqualTo(TestDataLoader.precaution);
        assertThat(allTopLevelCategories.contains(precaution));

        Category retirementProvision = categoryService.findByName(TestDataLoader.retirementProvision.getName());
        Category savings = categoryService.findByName(TestDataLoader.savings.getName());
        assertThat(precaution.getSubCategories().size()).isEqualTo(2);
        assertThat(precaution.getSubCategories().contains(retirementProvision));
        assertThat(precaution.getSubCategories().contains(savings));

        Category savingsOnSavingsAccount = categoryService.findByName(TestDataLoader.savingsOnSavingsAccount.getName());
        Category buildingSaving = categoryService.findByName(TestDataLoader.buildingSaving.getName());

        assertThat(savings.getSubCategories().size()).isEqualTo(2);
        assertThat(savings.getSubCategories().contains(savingsOnSavingsAccount));
        assertThat(savings.getSubCategories().contains(buildingSaving));
    }
}
