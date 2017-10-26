package com.web.sky.hotel;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data @Lazy @Component
public class Hotel {
   private String hotel_name,destination,district,guest;
   private int price;
   
}