package com.jojobi.mm.controller;

import com.jojobi.mm.exception.NotFoundException;
import com.jojobi.mm.model.Counterparty;
import com.jojobi.mm.service.CounterpartyService;
import com.jojobi.mm.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller()
@RequestMapping("/counterparty")
public class CounterpartyController {

    private final CounterpartyService counterpartyService;
    private final TransactionService transactionService;

    public CounterpartyController(CounterpartyService counterpartyService, TransactionService transactionService) {
        this.counterpartyService = counterpartyService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{counterparty_id}")
    private String handleCounterpartyRequest(@PathVariable("counterparty_id") Long counterpartyId, Model model) {
        log.debug("Handle counterparty page request, counterparty_id = {}", counterpartyId);

        Counterparty counterparty = counterpartyService.findById(counterpartyId);
        if ( counterparty == null ) {
            throw new NotFoundException("Account id=" + counterpartyId + " not found");
        }

        model.addAttribute("counterparty", counterparty);
        //model.addAttribute("transactions", transactionService.findAllByAccount(account));

        return "counterparty";
    }
}
