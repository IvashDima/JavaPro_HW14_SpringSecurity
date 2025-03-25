package org.example.springbank.services;

import org.example.springbank.enums.CurrencyType;
import org.example.springbank.enums.TransactionType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.models.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoDataService {
    private final ClientService clientService;
    private final AccountService accountService;

    private final TransactionService transactionService;

    public DemoDataService(ClientService clientService, AccountService accountService, TransactionService transactionService) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Transactional
    public void generateDemoData() {
        clientService.deleteAllClients();
        accountService.deleteAllAccounts();

        transactionService.deleteAllTransactions();

        Client client;
        Account account;
        Transaction transaction;

        for (int i = 0; i < 5; i++) {
            client = new Client("Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
            clientService.addClient(client);
            for (CurrencyType currencyType : CurrencyType.values()){
                account = new Account(client, 0, currencyType);
                accountService.addAccount(account);
                transaction = new Transaction(null, account, 1000, TransactionType.deposit);
                transactionService.deposit(transaction);
            }
        }
    }
}
