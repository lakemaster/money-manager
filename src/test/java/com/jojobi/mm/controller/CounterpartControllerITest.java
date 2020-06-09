package com.jojobi.mm.controller;

import com.jojobi.mm.bootstrap.TestDataLoader;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.service.CounterpartService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CounterpartControllerITest extends AbstractControllerITest {

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