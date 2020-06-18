package com.jojobi.mm.controller;

import com.jojobi.mm.bootstrap.TestDataLoader;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.LegalEntity;
import com.jojobi.mm.service.LegalEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class LegalEntityControllerITest extends AbstractControllerITest {

    @Test
    public void testHandleCounterpartRequestSuccessfully() throws Exception {

        URI uri = UriComponentsBuilder.fromPath("/counterpart")
                .pathSegment("{counterpart_id}")
                .build(TestDataLoader.CP_EMPLOYER_ID);

        perform(uri)
            .andExpect(status().isOk())
            .andExpect(view().name("counterpart"))
            .andExpect(model().attribute("myAccount", instanceOf(Account.class)))
            .andExpect(model().attribute("counterpart", instanceOf(LegalEntity.class)))
            .andExpect(model().attribute("legalEntityService", instanceOf(LegalEntityService.class)));
    }
}