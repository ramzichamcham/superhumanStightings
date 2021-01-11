package com.sg.superhumanSightings.ServiceLayer;

import java.sql.Date;
import java.time.LocalDateTime;

public interface SuperhumanSightingsServiceLayer {
    public Date localDateTimetoSQLDate(LocalDateTime dateTime);
}
