package org.example.springbank.controllers;

import org.example.springbank.enums.CurrencyType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.models.Transaction;
import org.example.springbank.services.AccountService;
import org.example.springbank.services.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TransactionController {
    static final int ITEMS_PER_PAGE = 10;

    static final int DEFAULT_ACCOUNT_ID = -1;

    private final TransactionService transactionService;

    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

//    @GetMapping("/transaction/")
//    public String transactionPage(Model model,
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(value = "accountId", required = false) Long accountId)
//    {
//        if (page < 0) page = 0;
//
//        Account account = (accountId != null) ? accountService.findById(accountId) : null;
//
//        List<Transaction> transactions = transactionService
//                .findByReceiverAccount(account,PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));
//
//        model.addAttribute("transactions", transactions);
//        model.addAttribute("byReceiverAccountPages",getPageCount(account));
////        model.addAttribute("allPages", getPageCount());
//        return "/transaction/index";
//    }

    @GetMapping("/transaction/account/{id}")
    public String listTransaction(
            @PathVariable(value = "id") long accountId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model)
    {
        Account account = (accountId != DEFAULT_ACCOUNT_ID) ? transactionService.findAccount(accountId) : null;
        if (page < 0) page = 0;

        List<Transaction> transactions = transactionService
                .findByReceiverAccount(account, PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("accounts", transactionService.findAccounts());
        model.addAttribute("transactions", transactions);
        model.addAttribute("byReceiverAccountPages", getPageCount(account));
        model.addAttribute("accountId", accountId);

        return "transaction/index";
    }

    private long getPageCount() {
        long totalCount = transactionService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Account account) {
        long totalCount = transactionService.countByReceiverAccount(account);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
