package com.jojobi.mm.controller;

import com.jojobi.mm.info.AccountInfo;
import com.jojobi.mm.model.Counterpart;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.CounterpartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class IndexController {

    private final CounterpartService counterpartService;
    private final AccountService accountService;

    public IndexController(CounterpartService counterpartService, AccountService accountService) {
        this.counterpartService = counterpartService;
        this.accountService = accountService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting index page");
        Counterpart myself = counterpartService.findById(1L);
        List<AccountInfo> accountInfos = new ArrayList<>();
        myself.getAccounts().forEach(account -> accountInfos.add(accountService.getAccountInfo(account)));

        model.addAttribute("myself", myself);
        model.addAttribute("accountInfos", accountInfos);
        return "index";
    }
}
