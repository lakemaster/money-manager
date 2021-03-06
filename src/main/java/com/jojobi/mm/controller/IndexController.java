package com.jojobi.mm.controller;

import com.jojobi.mm.model.LegalEntity;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.LegalEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    private final LegalEntityService legalEntityService;
    private final AccountService accountService;

    public IndexController(LegalEntityService legalEntityService, AccountService accountService) {
        this.legalEntityService = legalEntityService;
        this.accountService = accountService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting index page");
        LegalEntity myself = legalEntityService.findById(1L);

        model.addAttribute("myself", myself);
        model.addAttribute("accountInfos", accountService.getAccountInfos(myself.getAccounts()));
        return "index";
    }
}
