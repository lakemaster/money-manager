package com.jojobi.mm.controller;

import com.jojobi.mm.exception.NotFoundException;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CounterpartService;
import com.jojobi.mm.service.TransactionService;
import com.jojobi.mm.session.SessionParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller()
@RequestMapping("/counterpart")
public class CounterpartController {

    private final SessionParameters sessionParameters;
    private final AccountService accountService;
    private final CounterpartService counterpartService;
    private final TransactionService transactionService;

    public CounterpartController(SessionParameters sessionParameters,
                                 AccountService accountService,
                                 CounterpartService counterpartService,
                                 TransactionService transactionService) {
        this.sessionParameters = sessionParameters;
        this.accountService = accountService;
        this.counterpartService = counterpartService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{counterpart_id}")
    private String handleCounterpartRequest(@RequestHeader Map<String, String> headers,
                                            @PathVariable("counterpart_id") Long counterpartId, Model model) {

        log.debug("Handle counterpart page request, counterpart_id = {}", counterpartId);
        headers.forEach((key, value) -> log.info("Header '{}' = '{}'", key, value));

        Counterpart counterpart = counterpartService.findById(counterpartId);
        if ( counterpart == null ) {
            throw new NotFoundException("Account id=" + counterpartId + " not found");
        }

        model.addAttribute("counterpart", counterpart);
        model.addAttribute("counterpartService", counterpartService);

        return "counterpart";
    }

    @ModelAttribute("myAccount")
    public Account populateMyAccount() {
        return sessionParameters.getMyAccountId().map(accountService::findById).orElse(null);
    }
}
