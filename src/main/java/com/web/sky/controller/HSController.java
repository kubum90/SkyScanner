package com.web.sky.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.sky.command.Command;
import com.web.sky.mapper.HSMapper;
import com.web.sky.member.Member;
import com.web.sky.service.IGetService;
import com.web.sky.service.IListService;

@RestController
public class HSController {
	private static final Logger logger = LoggerFactory.getLogger(HSController.class);
	@Autowired HSMapper hs ;
	@Autowired Command cmd;
	@SuppressWarnings("null")
	
	@RequestMapping("/list/{cate}")
	public @ResponseBody Map<?, ?> countDB(Model model,@PathVariable String cate) {
		logger.info("HS ContList{}","진입");
		Map<String, Object> map=new HashMap<>();
		System.out.println("/list/에 들어옴!!");
		switch(cate){
		case "member":
			logger.info("/list/member/에 들어옴");
			String s = (String) new IGetService() {
				@Override
				public Object execute(Object o) {
					return hs.countMember(cmd);
				}
			}.execute(null);
			
			System.out.println("count " + s);
			map.put("result","success");
			map.put("total",s);
			break;
		case "flight":
			logger.info("/list/flight/에 들어옴");
			String f = (String) new IGetService() {
				@Override
				public Object execute(Object o) {
					return hs.countFlight(cmd);
				}
			}.execute(null);
			
			System.out.println("count " + f);
			map.put("result","success");
			map.put("total",f);
			break;
		case "hotel":
			logger.info("/list/hotel/에 들어옴");
			String h = (String) new IGetService() {
				@Override
				public Object execute(Object o) {
					return hs.countHotel(cmd);
				}
			}.execute(null);
			
			System.out.println("count " + h);
			map.put("result","success");
			map.put("total",h);
			break;
		
	
		
		default:
			break;
		}
		return map;
	}
	
	@RequestMapping("/admin/{cate}")
	public @ResponseBody Map<?, ?> adminPlaceholder(Model model,@PathVariable String cate) {
		logger.info("admin플레이스 ContList{}","진입");
		Map<String, Object> map=new HashMap<>();
		switch(cate){
		case "now":
			logger.info("/admin/now/에 들어옴");
			
			String e = (String) new IGetService() {
				@Override
				public Object execute(Object o) {
					return hs.selectAdminEmail(cmd);
				}
			}.execute(null);
			
			String s = (String) new IGetService() {
				@Override
				public Object execute(Object o) {
					return hs.selectAdminSir(cmd);
				}
			}.execute(null);
			String n = (String) new IGetService() {
				@Override
				public Object execute(Object o) {
					return hs.selectAdminFirst(cmd);
				}
			}.execute(null);
			
			String p = (String) new IGetService() {
				@Override
				public Object execute(Object o) {
					return hs.selectAdminPassword(cmd);
				}
			}.execute(null);
			
			map.put("result","success");
			map.put("email",e);
			map.put("sirname",s);
			map.put("firstname",n);
			map.put("password",p);
			break;
		default:
			break;
	
		}
		return map;
	}
	
	@RequestMapping(value="/{email}/{sirname}/{firstname}/{password}",
			method=RequestMethod.POST,
	         consumes="application/json")
	public @ResponseBody Map<?, ?> get(@PathVariable String email,@PathVariable String sirname,@PathVariable String firstname,@PathVariable String password){
		logger.info("컨트롤러 진입!!");
		Map<String, Object> map = new HashMap<>();
		Member bean=null;
		logger.info("지금 관리자 이메일: {}",email);
		cmd.setSearch(email);
		cmd.setDir(sirname);
		cmd.setColumn(firstname);
		cmd.setAction(password);
		
		IGetService updateService=(x)->{
			return hs.updateAdmin(cmd);
		};
		bean = (Member) updateService.execute(cmd);
		map.put("success", "통신성공");
		map.put("bean", bean);	
		return map;
	};
	
	@RequestMapping(value="/search/{search}",
			method=RequestMethod.POST,
	         consumes="application/json")
	public @ResponseBody Map<?, ?> searchMember(@PathVariable String search){
		logger.info("컨트롤러 진입!!");
		Map<String, Object> map = new HashMap<>();
		
		logger.info("찾는 내용: {}",search);		
		Command cmd=new Command();
		cmd.setSearch(search);	
		
		map.put("searchMember",new IListService() {
			@Override
			public List<?> execute(Object o) {
				return hs.searchMember(cmd);
			}
		}.execute(cmd));
		
		map.put("success", "통신성공");
		
		return map;
	};
	
	@RequestMapping(value="/a/{email}/{password}",
			method=RequestMethod.POST,
	         consumes="application/json")
	public @ResponseBody Map<?, ?> put(@PathVariable String email,@PathVariable String password){
		logger.info("컨트롤러 진입!!");
		Map<String, Object> map = new HashMap<>();
		Member bean=null;
		logger.info("지금 관리자 이메일: {}",email);
		cmd.setSearch(email);
		cmd.setAction(password);
		
		IGetService updateService1=(x)->{
			return hs.newAdmin1(cmd);
		};
		IGetService updateService=(x)->{
			return hs.newAdmin(cmd);
		};
		bean = (Member) updateService1.execute(cmd);
		bean = (Member) updateService.execute(cmd);
		map.put("success", "통신성공");
		map.put("bean", bean);	
		return map;
	};
	
	@RequestMapping("/a/list/{cate}")
	public @ResponseBody Map<?, ?> memberList(Model model,@PathVariable String cate) {
		logger.info("회원정보 하는거 진입");
		Map<String, Object> map=new HashMap<>();
		System.out.println("/member/list에 들어옴!!");
		
		switch(cate){
		case "member":
			logger.info("member 리스트에 들어옴");
		
			map.put("result","success");
			map.put("memberCount",new IGetService() {
				@Override
				public Object execute(Object o) {
					return hs.countMember(cmd);
				}
			}.execute(null));
			map.put("memberList",new IListService() {
				@Override
				public List<?> execute(Object o) {
					return hs.memberList(cmd);
					
				}
			}.execute(null));
			break;
	
		
		default:
			break;
		}
		return map;
	}
	
}