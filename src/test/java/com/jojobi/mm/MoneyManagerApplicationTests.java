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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
        assertThat(counterparties.size()).isGreaterThanOrEqualTo(3);
    }

    @Test
    void checkCounterparty() {
        Counterparty insuranceCompany = counterpartyService.findById(TestDataLoader.CP_INSURANCE_COMPANY_ID).orElseThrow();
        assertThat(insuranceCompany.getId()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(insuranceCompany.getName()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_NAME);

        Account acc1 = insuranceCompany.getAccounts().get(0);
        assertThat(acc1.getBic()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT1_BIC);
        assertThat(acc1.getIsin()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT1_ISIN);
        assertThat(acc1.getCounterparty().getId()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(acc1.getCounterparty()).isEqualTo(insuranceCompany);

        Account acc2 = insuranceCompany.getAccounts().get(1);
        assertThat(acc2.getBic()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT2_BIC);
        assertThat(acc2.getIsin()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT2_ISIN);
        assertThat(acc2.getCounterparty().getId()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(acc2.getCounterparty()).isEqualTo(insuranceCompany);

        Counterparty employer = counterpartyService.findById(TestDataLoader.CP_EMPLOYER_ID).orElseThrow();
        assertThat(employer.getId()).isEqualTo(TestDataLoader.CP_EMPLOYER_ID);
        assertThat(employer.getName()).isEqualTo(TestDataLoader.CP_EMPLOYER_NAME);

        Account acc = employer.getAccounts().get(0);
        assertThat(acc.getBic()).isEqualTo(TestDataLoader.CP_EMPLOYER_ACCOUNT_BIC);
        assertThat(acc.getIsin()).isEqualTo(TestDataLoader.CP_EMPLOYER_ACCOUNT_ISIN);
        assertThat(acc.getCounterparty().getId()).isEqualTo(TestDataLoader.CP_EMPLOYER_ID);
        assertThat(acc.getCounterparty()).isEqualTo(employer);

        Counterparty myself = counterpartyService.findById(TestDataLoader.MYSELF_ID).orElseThrow();
        assertThat(myself.getId()).isEqualTo(TestDataLoader.MYSELF_ID);
        assertThat(myself.getName()).isEqualTo(TestDataLoader.MYSELF_NAME);

        Account myAcc = myself.getAccounts().get(0);
        assertThat(myAcc.getBic()).isEqualTo(TestDataLoader.MYSELF_ACCOUNT_BIC);
        assertThat(myAcc.getIsin()).isEqualTo(TestDataLoader.MYSELF_ACCOUNT_ISIN);
        assertThat(myAcc.getCounterparty().getId()).isEqualTo(TestDataLoader.MYSELF_ID);
        assertThat(myAcc.getCounterparty()).isEqualTo(myself);
    }

    @Test
    void checkTransactions() {
        Account myAccount = accountService.findAccountByIsin(TestDataLoader.MYSELF_ACCOUNT_ISIN).orElseThrow();
        assertThat(myAccount.getIsin()).isEqualTo(TestDataLoader.MYSELF_ACCOUNT_ISIN);
        assertThat(myAccount.getBic()).isEqualTo(TestDataLoader.MYSELF_ACCOUNT_BIC);

        Counterparty myself = myAccount.getCounterparty();
        assertThat(myself.getId()).isEqualTo(TestDataLoader.MYSELF_ID);
        assertThat(myself.getName()).isEqualTo(TestDataLoader.MYSELF_NAME);
        assertThat(myself.getAccounts().size()).isEqualTo(1);
        assertThat(myself.getAccounts().get(0).getId()).isEqualTo(myAccount.getId());
        assertThat(myself.getAccounts().get(0)).isEqualTo(myAccount);

        List<Transaction> transactions = transactionService.findAllByAccount(myAccount);
        assertThat(transactions.size()).isEqualTo(2);

        Transaction tr1 = transactions.get(0);
        assertThat(tr1.getAccount().getId()).isEqualTo(myAccount.getId());
        assertThat(tr1.getAmount()).isEqualTo(TestDataLoader.MY_SALARY_AMOUNT);
        assertThat(tr1.getBookingDate()).isEqualTo(TestDataLoader.MY_SALARY_BOOKING_DATE);
        assertThat(tr1.getValueDate()).isEqualTo(TestDataLoader.MY_SALARY_VALUE_DATE);
        assertThat((tr1.getCustomerReference())).isNullOrEmpty();
        assertThat(tr1.getMandate()).isNullOrEmpty();;
        assertThat(tr1.getText()).isEqualTo(TestDataLoader.MY_SALARY_TRANSACTION_TEXT);

        Counterparty employer = counterpartyService.findById(TestDataLoader.CP_EMPLOYER_ID).orElseThrow();
        assertThat(tr1.getCounterparty().getId()).isEqualTo(TestDataLoader.CP_EMPLOYER_ID);
        assertThat(tr1.getCounterparty()).isEqualTo(employer);
        assertThat(tr1.getCounterPartyAccount().getId()).isEqualTo(TestDataLoader.CP_EMPLOYER_ACCOUNT_ID);
        assertThat(tr1.getCounterPartyAccount()).isEqualTo(employer.getAccounts().get(0));

        Transaction tr2 = transactions.get(1);
        assertThat(tr2.getAccount().getId()).isEqualTo(myAccount.getId());
        assertThat(tr2.getAmount()).isEqualTo(TestDataLoader.MY_INSURANCE_PREMIUM);
        assertThat(tr2.getBookingDate()).isEqualTo(TestDataLoader.MY_INSURANCE_BOOKING_DATE);
        assertThat(tr2.getValueDate()).isEqualTo(TestDataLoader.MY_INSURANCE_VALUE_DATE);
        assertThat(tr2.getCustomerReference()).isEqualTo(TestDataLoader.MY_INSURANCE_CUSTOMER_REFERENCE);
        assertThat(tr2.getMandate()).isEqualTo(TestDataLoader.MY_INSURANCE_MANDATE);
        assertThat(tr2.getText()).isEqualTo(TestDataLoader.MY_INSURANCE_TRANSACTION_TEXT);

        Counterparty insuranceCompany = counterpartyService.findById(TestDataLoader.CP_INSURANCE_COMPANY_ID).orElseThrow();
        assertThat(tr2.getCounterparty().getId()).isEqualTo(TestDataLoader.CP_INSURANCE_COMPANY_ID);
        assertThat(tr2.getCounterparty()).isEqualTo(insuranceCompany);
        assertThat(tr2.getCounterPartyAccount().getId()).isEqualTo(TestDataLoader.CP_INSURANCE_ACCOUNT1_ID);
        assertThat(tr2.getCounterPartyAccount()).isEqualTo(insuranceCompany.getAccounts().get(0));


    }
}
