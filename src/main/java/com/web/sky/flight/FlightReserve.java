package com.web.sky.flight;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data @Lazy @Component
public class FlightReserve {
	private String airline,email;
	private int flight_reserve_no;
	
}
