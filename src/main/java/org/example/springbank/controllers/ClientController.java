package org.example.springbank.controllers;

import org.example.springbank.models.Client;
import org.example.springbank.services.ClientService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClientController {

    static final int ITEMS_PER_PAGE = 5;
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false, defaultValue = "0") Integer page){
        if(page < 0) page = 0;

        List<Client> clients = clientService.findAll(PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("clients", clients);
        model.addAttribute("allPages", getPageCount());
        return "index";
    }

    private long getPageCount() {
        long totalCount = clientService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
