package com.sideprj.ipoAlarm.util.csv;

import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Component
public class CSVReader {

    @Transactional
    public List<Ipo> readCSV(String filePath) throws FileNotFoundException {

        Map<String, Ipo> ipoMap = new HashMap<>();

        try(Reader reader = new FileReader(filePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord record : csvParser) {
                String name = record.get("name");
                Ipo ipo = ipoMap.getOrDefault(name, new Ipo());
                SimpleDateFormat startDateFormatter = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat endDateFormatter = new SimpleDateFormat("MM/dd");

                ipo.builder()
                        .ipoName(name)
                        .confirmPrice(record.get("confirmPrice"))
                        .ipoPrice(record.get("ipoPrice"))
                        .competitionRate(record.get("competitionRate"))
                        .securities(record.get("securities"))
                        .startDate(startDateFormatter.parse(record.get("start_date")))
                        .endDate(endDateFormatter.parse(record.get("end_date")))
                        .build();

                ipoMap.put(name, ipo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(ipoMap.values());

    }
}
