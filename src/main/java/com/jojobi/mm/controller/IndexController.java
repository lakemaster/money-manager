package com.jojobi.mm.controller;

import com.jojobi.mm.model.Counterparty;
import com.jojobi.mm.service.CounterpartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final CounterpartyService counterpartyService;

    public IndexController(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting index page");
        model.addAttribute("myself", counterpartyService.findById(1L));
        return "index";
    }
}
