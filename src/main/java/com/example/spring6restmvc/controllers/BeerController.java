package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.models.Beer;
import com.example.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;

    public Beer getBeer(UUID id) {
        log.debug("Ger Beer by Id in controller was called");

        return beerService.getBeer(id);
    }
}
