package org.example.springbank.controllers;

import org.example.springbank.enums.CurrencyType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Transaction;
import org.example.springbank.services.AccountService;
import org.example.springbank.services.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TransactionController {
    static final int ITEMS_PER_PAGE = 10;

    private final TransactionService transactionService;

    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/transaction/")
    public String transactionPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "accountId", required = false) Long accountId,
            Model model)
    {
        if (page < 0) page = 0;

        Account account = (accountId != null) ? accountService.findById(accountId) : null;

        model.addAttribute("transactions", transactionService.findByReceiverAccount(account, null));
//        model.addAttribute("currencies", CurrencyType.values());
        model.addAttribute("allPages", getPageCount());
        return "/transaction/index";
    }

    private long getPageCount() {
        long totalCount = transactionService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
