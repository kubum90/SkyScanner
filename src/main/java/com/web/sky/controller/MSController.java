package com.web.sky.controller;

import java.util.HashMap;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.sky.command.Command;

import com.web.sky.hotel.Hotel;
import com.web.sky.hotel.RespMap;
import com.web.sky.mapper.MSMapper;
import com.web.sky.member.Member;
import com.web.sky.service.IGetService;
import com.web.sky.service.IListService;
import com.web.sky.service.IPostService;


@RestController
public class MSController {
	@Autowired
	Command cmd;
	@Autowired
	MSMapper ms;
	@Autowired
	Member member;

	private static final Logger logger = LoggerFactory.getLogger(MSController.class);

	@RequestMapping(value = "/join", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody Map<?, ?> join (@RequestBody Member mem) {
		Map<String, Object> map = new HashMap<>();
		
		cmd.setSearch(mem.getEmail());
		cmd.setColumn(mem.getPassword());
		System.out.println("받은 이메일::" + cmd.getSearch());
		System.out.println("받은 패스워드::" + cmd.getColumn());
		
		IPostService postService = x->{
				ms.insert(cmd);
					
		};
		postService.execute(cmd);
		
/*		System.out.println("실행 후 회원수"+(ms.count(cmd)));
*/	/*	new IPostService() {
			@Override
			public void execute(Object o) {
				System.out.println("실행 전 체크"+cmd.getSearch());
				System.out.println("실행 전 회원수"
						+
						(ms.count(cmd)));
				ms.memberInsert(cmd);	
				
				System.out.println("실행 전 체크"
						+
						((Member)ms.selectOne(cmd)).getPassword());
				System.out.println("실행 후 회원수"
						+
						(ms.count(cmd)));
				
			}
		}.execute(null);*/
		map.put("success", "들어가");
		return map;
	};
	
		
	@RequestMapping(value="/login",
            method=RequestMethod.POST,
            consumes="application/json")
	public @ResponseBody Map<?, ?> login(@RequestBody Member mem){
		logger.info("로그인 컨트롤러 진입!!");
		Map<String, Object> map = new HashMap<>();
		
		logger.info("디테일 컨트롤러로 넘어온 아이디: {}",mem.getEmail());
		logger.info("디테일 컨트롤러로 넘어온 비밀번호: {}",mem.getPassword());
		cmd.setSearch(mem.getEmail());
		cmd.setColumn(mem.getPassword());
		IGetService loginService=(x)->{
			return ms.selectOne(cmd);
		};
		Member bean = (Member) loginService.execute(cmd);
		
			map.put("success", "통신성공");	
			map.put("bean", bean);
			
		
		System.out.println("빈은 ::"+bean);
		
		
		return map;
	};
	/*@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody Map<?, ?> login(@RequestBody Member mem) {
		Map<String, Object> map = new HashMap<>();
		cmd.setSearch(mem.getEmail());
		cmd.setColumn(mem.getPassword());
		System.out.println("받은 이메일::" + cmd.getSearch());
		System.out.println("받은 패스워드::" + cmd.getColumn());
		member.setEmail((String) ms.selectOne(cmd));
		member.setEmail(mem.getEmail());
		
		IGetService loginService = x->{
				
			return ms.selectOne(cmd);
		};
		loginService.execute(cmd);
		System.out.println("execute 실행 후 이메일 :: "+member.getEmail());
		if(member.getEmail()==null) {
			System.out.println("실패");
		}else {
			map.put("success", "통신성공");
			map.put("bean", member);	
		}
		System.out.println("aaaa =>"+member.getEmail());
		return map;
		
		IGetService detailService = (x) -> {
			System.out.println("llllll"+((Member)ms.selectOne(cmd)).getEmail());
			return ms.selectOne(cmd);
		};

			 m=(Member)new IGetService() {
			
			@Override
			public Object execute(Object o) {
				
				return ms.selectOne(cmd);
			}
		}.execute(null);
		//Member bean = (Member) detailService.execute(cmd);
		
	};
*/
	/*
	 * String result=""; if(member.getEmail().equals(cmd.getSearch())) {
	 * result="success"; }else {
	 * 
	 * result="fail"; } map.put("msg", result);
	 */


	@RequestMapping(value="/suggest",
			method=RequestMethod.POST,
            consumes="application/json")
	public @ResponseBody Map<?, ?> searchSuggestions(@RequestBody Hotel hotel) {
		logger.info("검색어 추천 컨트롤러 진입!!");
		logger.info("검색한 단어: {}",hotel.getDestination());
		Map<String, Object> map = new HashMap<>();
		IListService listService = null;
		cmd.setAction(hotel.getDestination());
		listService = (x)->{
			return ms.selectList(cmd);
		};
		logger.info("DB에서 추천한 검색어 : {} ", listService.execute(cmd));
		if(listService.execute(cmd).isEmpty()) {
			map.put("sgst", null);
		}else{
			map.put("sgst", listService.execute(cmd));
		};
		return map;
	};
	@RequestMapping(value="/filter",
			method=RequestMethod.POST,
            consumes="application/json")
	public @ResponseBody Map<?, ?> filter() {
		logger.info("필터 컨트롤러 진입!!");
		Map<String, Object> map = new HashMap<>();
		IGetService serivce = null;
		serivce = (x)->{
			return ms.selectFilter(cmd);
		};
		logger.info("DB에서 가져온 filter 내용들 : {} ", serivce.execute(cmd));
		map.put("filter", serivce.execute(cmd));
		return map;
	};
	@RequestMapping(value="/recommandView",
			method=RequestMethod.POST,
            consumes="application/json")
	public @ResponseBody Map<?, ?> recommandView(@RequestBody RespMap resp) {
		logger.info("검색어 추천 컨트롤러 진입!!");
		logger.info("검색한 단어: {}",resp.getDestination());
		Map<String, Object> map = new HashMap<>();
		IListService listService = null;
			
		cmd.setAction(resp.getDestination());
		listService = (x)->{
			return ms.recommandList(cmd);
		};
		logger.info("DB에서 추천한 호텔목록 : {} ", listService.execute(cmd));
		map.put("rview", listService.execute(cmd));
		
		return map;
	};

	@RequestMapping(value="/hotelReservation",
			method=RequestMethod.POST,
            consumes="application/json")
	public @ResponseBody Map<?, ?> hotelReservation(@RequestBody RespMap resp) {
		logger.info("예약 컨트롤러 진입!!");
		logger.info("예약 컨트롤러로 넘어온 이메일 : {}", resp.getEmail());
		logger.info("예약 컨트롤러로 넘어온 비밀번호 : {}", resp.getPassword());
		logger.info("예약 컨트롤러로 넘어온 호텔 이름 : {}", resp.getHotelName());
		Map<String, Object> map = new HashMap<>();
		map.put("r", "통신 성공");
		return map;
	};
	
}
