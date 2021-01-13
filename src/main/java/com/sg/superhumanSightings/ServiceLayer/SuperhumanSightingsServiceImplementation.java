package com.sg.superhumanSightings.ServiceLayer;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class SuperhumanSightingsServiceImplementation implements SuperhumanSightingsServiceLayer{

    @Override
    public Date localDateTimetoSQLDate(LocalDateTime dateTime) {
        java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());
        return sqlDate;
    }

    @Override
    public LocalDateTime localDateTimeNow() {
        return LocalDateTime.now().withNano(0);
    }

    @Override
    public LocalDateTime stringsToLocalDatetime(String date, String time) {
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return localDateTime;
    }
}
