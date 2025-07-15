package com.example.spring6restmvc.services;

import com.example.spring6restmvc.models.Beer;
import com.example.spring6restmvc.models.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.LAGER)
                .upc("929292")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(233)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Beer Tom")
                .beerStyle(BeerStyle.STOUT)
                .upc("32121")
                .price(new BigDecimal("35.99"))
                .quantityOnHand(265)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galactic Dog")
                .beerStyle(BeerStyle.IPA)
                .upc("969594")
                .price(new BigDecimal("85.94"))
                .quantityOnHand(192)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeer(UUID id) {
        log.debug("Ger Beer by Id - in service. Id: " + id.toString());

        return beerMap.get(id);
    }
}
