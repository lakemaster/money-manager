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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller()
@RequestMapping("/transactions")
public class TransactionsController {

    private final SessionParameters sessionParameters;
    private final AccountService accountService;
    private final CounterpartService counterpartService;
    private final TransactionService transactionService;


    public TransactionsController(SessionParameters sessionParameters,
                                  AccountService accountService,
                                  CounterpartService counterpartService,
                                  TransactionService transactionService) {
        this.sessionParameters = sessionParameters;
        this.accountService = accountService;
        this.counterpartService = counterpartService;
        this.transactionService = transactionService;
    }


    @GetMapping("/{account_id}")
    private String handleTransactionsRequest(@PathVariable("account_id") Long accountId,
                                             @RequestParam(name = "counterpartId", required = false) Long counterpartId,
                                             @RequestParam(name = "counterpartAccountId", required = false) Long counterpartAccountId,
                                             Model model) {
        log.debug("Handle transaction page request, account_id={}, counterpartId={}, counterpartAccountId={}",
                accountId, counterpartId, counterpartAccountId);

        sessionParameters.setMyAccountId(accountId);

        Account account = accountService.findById(accountId);
        if ( account == null ) {
            throw new NotFoundException("Account id=" + accountId + " not found");
        }

        Counterpart counterpart = null;
        Account counterpartAccount = null;
        if ( counterpartId != null && counterpartId.longValue() > 0 ) {
            counterpart = counterpartService.findById(counterpartId);
            if ( counterpart == null ) {
                throw new NotFoundException("Counterpart id=" + counterpartId + " not found");
            }
            if ( counterpartAccountId != null && counterpartAccountId > 0 ) {
                counterpartAccount = accountService.findById(accountId);
                if ( counterpartAccount == null ) {
                    throw new NotFoundException("Counterpart Account id=" + counterpartAccountId + " not found");
                }
            }
        }

        model.addAttribute("account", account);
        model.addAttribute("transactions", transactionService.findAllByAccount(account, counterpart, counterpartAccount));

        return "transactions";
    }
}
