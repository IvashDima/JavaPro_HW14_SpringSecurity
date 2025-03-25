package org.example.springbank.services;

import org.example.springbank.enums.CurrencyType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoDataService {
    private final ClientService clientService;
    private final AccountService accountService;

    public DemoDataService(ClientService clientService, AccountService accountService) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @Transactional
    public void generateDemoData() {
        clientService.deleteAllClients();
        accountService.deleteAllAccounts();

        Client client;
        Account account;

        for (int i = 0; i < 13; i++) {
            client = new Client("Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
            clientService.addClient(client);
            account = new Account(client, 1000, CurrencyType.EUR);
            accountService.addAccount(account);
            account = new Account(client, 1000, CurrencyType.USD);
            accountService.addAccount(account);
            account = new Account(client, 1000, CurrencyType.UAH);
            accountService.addAccount(account);
        }
    }
}
