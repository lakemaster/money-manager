package com.jojobi.mm.controller;

import com.jojobi.mm.model.Account;
import com.jojobi.mm.session.SessionParameters;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
public class TransactionControllerITest extends AbstractControllerITest {

    @Test
    public void testTransactionRequest() throws Exception {
        URI uri = UriComponentsBuilder.fromPath("/transactions")
                .pathSegment("{account_id}")
                .queryParam("counterpart_id", 1)
                .queryParam("counterpart_account_id", 2)
                .build(1L);

        SessionParameters sessionParameters = new SessionParameters();
        sessionParameters.setMyAccountId(4711L);
        perform(uri, sessionParameters)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(model().attribute("account", instanceOf(Account.class)))
            .andExpect(model().attribute("transactions", instanceOf(List.class)));

        assertTrue(sessionParameters.getMyAccountId().isPresent());
        assertThat(sessionParameters.getMyAccountId().get(), equalTo(1L));
    }
}
