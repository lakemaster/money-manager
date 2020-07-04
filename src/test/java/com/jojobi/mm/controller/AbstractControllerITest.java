package com.jojobi.mm.controller;

import com.jojobi.mm.session.SessionParameters;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
abstract class AbstractControllerITest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockHttpSession mockHttpSession;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        SessionParameters sessionParameters = new SessionParameters();
        sessionParameters.setMyAccountId(1L);
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("scopedTarget.sessionParameters", sessionParameters);
    }

    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    protected ResultActions perform(URI uri, SessionParameters sessionParameters) throws Exception {
        mockHttpSession.setAttribute("scopedTarget.sessionParameters", sessionParameters);
        return perform(uri);
    }

    protected ResultActions perform(URI uri) throws Exception {
        return getMockMvc().perform(MockMvcRequestBuilders.get(uri)
                .session(mockHttpSession));
    }

}