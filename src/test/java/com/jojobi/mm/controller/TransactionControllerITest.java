package com.jojobi.mm.controller;

import com.jojobi.mm.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
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
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .session(mockHttpSession))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("account", instanceOf(Account.class)))
                .andExpect(model().attribute("transactions", instanceOf(List.class)));
    }
}
