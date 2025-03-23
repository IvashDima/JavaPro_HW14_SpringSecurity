package org.example.springbank.controllers;

import org.example.springbank.enums.CurrencyType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.services.AccountService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class AccountController {
    static final int DEFAULT_CLIENT_ID = -1;
    static final int ITEMS_PER_PAGE = 5;

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/accounts_page")
    public String accounts_page(Model model,
                                @RequestParam(required = false, defaultValue = "0") Integer page){
        if (page < 0) page = 0;

        List<Account> accounts = accountService
                .findAll(PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("clients", accountService.findClients());
        model.addAttribute("accounts", accounts);
        model.addAttribute("allPages", getPageCount());

        return "accounts_page";
    }

    @GetMapping("/reset")
    public String reset() {
        accountService.reset();
        return "redirect:/accounts_page";
    }

    @GetMapping("/client/{id}")
    public String listClient(
            @PathVariable(value = "id") long clientId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model)
    {
        Client client = (clientId != DEFAULT_CLIENT_ID) ? accountService.findClient(clientId) : null;
        if (page < 0) page = 0;

        List<Account> accounts = accountService
                .findByClient(client, PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("clients", accountService.findClients());
        model.addAttribute("accounts", accounts);
        model.addAttribute("byClientPages", getPageCount(client));
        model.addAttribute("clientId", clientId);

        return "accounts_page";
    }

    @PostMapping(value = "/search")
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("clients", accountService.findClients());
        model.addAttribute("accounts", accountService.findByPattern(pattern, null));

        return "accounts_page";
    }

    @PostMapping(value="/account/add")
    public String accountAdd(@RequestParam(value = "client") long clientId,
                             @RequestParam double balance,
                             @RequestParam CurrencyType currency)
    {
        Client client = (clientId != DEFAULT_CLIENT_ID) ? accountService.findClient(clientId) : null;

        Account account = new Account(client, balance, currency);
        accountService.addAccount(account);

        return "redirect:/accounts_page";
    }

    private long getPageCount() {
        long totalCount = accountService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Client client) {
        long totalCount = accountService.countByClient(client);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
