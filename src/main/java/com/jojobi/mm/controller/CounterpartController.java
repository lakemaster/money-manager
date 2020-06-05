package com.jojobi.mm.controller;

import com.jojobi.mm.exception.NotFoundException;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.service.CounterpartService;
import com.jojobi.mm.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller()
@RequestMapping("/counterpart")
public class CounterpartController {

    private final CounterpartService counterpartService;
    private final TransactionService transactionService;

    public CounterpartController(CounterpartService counterpartService, TransactionService transactionService) {
        this.counterpartService = counterpartService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{counterpart_id}")
    private String handleCounterpartRequest(@PathVariable("counterpart_id") Long counterpartId, Model model) {
        log.debug("Handle counterpart page request, counterpart_id = {}", counterpartId);

        Counterpart counterpart = counterpartService.findById(counterpartId);
        if ( counterpart == null ) {
            throw new NotFoundException("Account id=" + counterpartId + " not found");
        }

        model.addAttribute("counterpart", counterpart);
        model.addAttribute("counterpartService", counterpartService);

        return "counterpart";
    }
}
