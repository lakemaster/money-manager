package com.jojobi.mm.controller;

import com.jojobi.mm.bootstrap.TestDataLoader;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CounterpartService;
import com.jojobi.mm.service.TransactionService;
import com.jojobi.mm.session.SessionParameters;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CounterpartControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockHttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        SessionParameters sessionParameters = new SessionParameters();
        sessionParameters.setMyAccountId(TestDataLoader.MYSELF_ACCOUNT_ID);
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("scopedTarget.sessionParameters", sessionParameters);
    }

    @Test
    public void testHandleCounterpartRequestSuccessfully() throws Exception {

        mockMvc.perform(get("/counterpart/" + TestDataLoader.CP_EMPLOYER_ID)
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("counterpart"))
                .andExpect(model().attribute("myAccount", instanceOf(Account.class)))
                .andExpect(model().attribute("counterpart", instanceOf(Counterpart.class)))
                .andExpect(model().attribute("counterpartService", instanceOf(CounterpartService.class)));
    }
}