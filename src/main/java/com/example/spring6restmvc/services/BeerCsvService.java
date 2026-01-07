package com.example.spring6restmvc.services;

import com.example.spring6restmvc.models.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);
}
