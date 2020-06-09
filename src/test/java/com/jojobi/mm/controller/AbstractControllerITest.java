package com.jojobi.mm.controller;

import com.jojobi.mm.bootstrap.TestDataLoader;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.service.CounterpartService;
import com.jojobi.mm.session.SessionParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
abstract class AbstractControllerITest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;
    protected MockHttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        SessionParameters sessionParameters = new SessionParameters();
        sessionParameters.setMyAccountId(TestDataLoader.MYSELF_ACCOUNT_ID);
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("scopedTarget.sessionParameters", sessionParameters);
    }
}