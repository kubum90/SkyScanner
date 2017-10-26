package com.web.sky.member;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data @Component
public class Member {
	private String email,sirname,first_name,
	country,regdate,password;
	private int budget,news_seq;
	
	
}
