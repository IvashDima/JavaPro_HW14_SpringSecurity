package org.example.springbank.services;

import org.example.springbank.models.Client;
import org.example.springbank.repositories.ClientRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void addClient(Client client){clientRepository.save(client);}

    @Transactional
    public void deleteClient(long[] idList) {
        for (long id : idList)
            clientRepository.deleteById(id);
    }

    @Transactional(readOnly=true)
    public List<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly=true)
    public List<Client> findByPattern(String pattern, Pageable pageable) {
        return clientRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return clientRepository.count();
    }

    @Transactional
    public void reset() {
        clientRepository.deleteAll();

        Client client;

        for (int i = 0; i < 13; i++) {
            client = new Client("Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
            addClient(client);
        }
    }
}
