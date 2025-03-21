package org.example.springbank.repositories;

import org.example.springbank.models.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

//    @Query("SELECT c FROM Client c WHERE c.group = :group")
//    List<Client> findByGroup(@Param("group") Group group, Pageable pageable);
//
//    @Query("SELECT COUNT(c) FROM Client c WHERE c.group = :group")
//    long countByGroup(@Param("group") Group group);

    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Client> findByPattern(@Param("pattern") String pattern, Pageable pageable);

    // List<Contact> findByNameOrEmailOrderById(String name, String email);
    // List<Contact> findByNameAndEmail(String name, String email);

}
