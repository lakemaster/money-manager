package com.jojobi.mm.controller;

import com.jojobi.mm.info.AccountInfo;
import com.jojobi.mm.model.Counterparty;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CounterpartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class IndexController {

    private final CounterpartyService counterpartyService;
    private final AccountService accountService;

    public IndexController(CounterpartyService counterpartyService, AccountService accountService) {
        this.counterpartyService = counterpartyService;
        this.accountService = accountService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting index page");
        Counterparty myself = counterpartyService.findById(1L);
        List<AccountInfo> accountInfos = new ArrayList<>();
        myself.getAccounts().forEach(account -> accountInfos.add(accountService.getAccountInfo(account)));

        model.addAttribute("myself", myself);
        model.addAttribute("accountInfos", accountInfos);
        return "index";
    }
}
