package org.example.springbank.services;

import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.models.Transaction;
import org.example.springbank.repositories.TransactionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void addTransaction(Transaction transaction){transactionRepository.save(transaction);}

    @Transactional(readOnly=true)
    public List<Transaction> findByPattern(String pattern, Pageable pageable) {
        return transactionRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly=true)
    public List<Transaction> findBySenderAccount(Account account, Pageable pageable) {
        return transactionRepository.findBySenderAccount(account, pageable);
    }

    @Transactional(readOnly=true)
    public List<Transaction> findByReceiverAccount(Account account, Pageable pageable) {
        return transactionRepository.findByReceiverAccount(account, pageable);
    }

    @Transactional(readOnly = true)
    public long countBySenderAccount(Account account) {
        return transactionRepository.countBySenderAccount(account);
    }

    @Transactional(readOnly = true)
    public long countByReceiverAccount(Account account) {
        return transactionRepository.countByReceiverAccount(account);
    }
}
