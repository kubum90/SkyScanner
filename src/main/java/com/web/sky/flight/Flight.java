package com.web.sky.flight;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data @Lazy @Component
public class Flight {
   private String airline_stop,stop_count,agency,airline,cabit_class, place_from ,place_to, date_depart, date_return, time_depart, time_return;
   private int price;
   
}