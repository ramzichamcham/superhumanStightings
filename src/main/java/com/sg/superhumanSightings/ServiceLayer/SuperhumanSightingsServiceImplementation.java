package com.sg.superhumanSightings.ServiceLayer;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

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
}
