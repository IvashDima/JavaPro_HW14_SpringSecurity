package org.example.springbank.repositories;

import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT c FROM Transaction c WHERE LOWER(c.amount) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Transaction> findByPattern(@Param("pattern") String pattern, Pageable pageable);

    @Query("SELECT c FROM Transaction c WHERE c.senderAccount = :senderAccount")
    List<Transaction> findBySenderAccount(@Param("senderAccount") Account account, Pageable pageable);

    @Query("SELECT c FROM Transaction c WHERE c.receiverAccount = :receiverAccount")
    List<Transaction> findByReceiverAccount(@Param("receiverAccount") Account account, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Transaction c WHERE c.senderAccount = :senderAccount")
    long countBySenderAccount(@Param("senderAccount") Account account);
    @Query("SELECT COUNT(c) FROM Transaction c WHERE c.receiverAccount = :receiverAccount")
    long countByReceiverAccount(@Param("receiverAccount") Account account);
}
