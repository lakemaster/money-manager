package com.jojobi.mm.controller;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.LegalEntity;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.LegalEntityService;
import com.jojobi.mm.service.TransactionService;
import com.jojobi.mm.session.SessionParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LegalEntityControllerTest {

    Long accountId = 1L;
    Account account = Account.builder().id(accountId).isin("ABC123").bic("XYZ789").build();
    Long counterpartId = 2L;
    LegalEntity legalEntity = LegalEntity.builder().id(counterpartId).name("abc").creditorId("xyz").build();

    @Mock
    SessionParameters sessionParameters;
    @Mock
    AccountService accountService;
    @Mock
    LegalEntityService legalEntityService;
    @Mock
    TransactionService transactionService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(new CounterpartController(
                sessionParameters, accountService, legalEntityService, transactionService)).build();
    }

    @Test
    public void testHandleCounterpartRequestSuccessfully() throws Exception {

        Mockito.when(sessionParameters.getMyAccountId()).thenReturn(Optional.of(accountId));
        Mockito.when(accountService.findById(eq(accountId))).thenReturn(account);
        Mockito.when(legalEntityService.findById(eq(counterpartId))).thenReturn(legalEntity);

        mockMvc.perform(get("/counterpart/" + counterpartId))
                .andExpect(status().isOk())
                .andExpect(view().name("counterpart"))
                .andExpect(model().attribute("myAccount", equalTo(account)))
                .andExpect(model().attribute("counterpart", equalTo(legalEntity)))
                .andExpect(model().attribute("legalEntityService", is(legalEntityService)));
    }
}