package com.web.sky.flight;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data @Lazy @Component
public class FlightReserve {
   private String revervationNo, gender, surname, firstname, country, phone, email, flight_no, airline, cabin_class, departCity, arriveCity, departDate, arriveDate,authority;

}