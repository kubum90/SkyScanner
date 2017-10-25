package com.web.sky.hotel;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data @Lazy @Component
public class Hotel {
   private String hotel_site, hotel_name,destination,district,rate,type,hotel_date;
   private int price;
   
}