package com.sideprj.ipoAlarm.domain.ipo.service.impl;

import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepository;
import com.sideprj.ipoAlarm.domain.ipo.service.IpoService;
import com.sideprj.ipoAlarm.util.csv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class IpoServiceImpl implements IpoService {

    private final IpoRepository ipoRepository;
    private final CSVReader csvReader;


    @Override
    @Transactional
    public void save() throws FileNotFoundException {
        String filePath = "localhost:8080/Downloads/ipo_data.csv";
        List<Ipo> ipos = csvReader.readCSV(filePath);
        ipoRepository.saveAll(ipos);
    }

}
