package com.example.spring6restmvc.services;

import com.example.spring6restmvc.models.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeer(UUID id);
}
