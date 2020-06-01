package com.jojobi.mm.controller;

import com.jojobi.mm.exception.NotFoundException;
import com.jojobi.mm.model.Account;
import com.jojobi.mm.service.AccountService;
import com.jojobi.mm.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller()
@RequestMapping("/transactions")
public class TransactionsController {

    private final AccountService accountService;
    private final TransactionService transactionService;


    public TransactionsController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


    @GetMapping("/{account_id}")
    private String handleTransactionsRequest(@PathVariable("account_id") Long accountId, Model model) {
        log.debug("Handle transaction page request, account_id = {}", accountId);

        Account account = accountService.findById(accountId);
        if ( account == null ) {
            throw new NotFoundException("Account id=" + accountId + " not found");
        }

        model.addAttribute("account", account);
        model.addAttribute("transactions", transactionService.findAllByAccount(account));

        return "transactions";
    }
}
