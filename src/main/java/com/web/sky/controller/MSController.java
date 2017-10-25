package com.web.sky.controller;

import java.util.HashMap;
import java.util.List;
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
import com.web.sky.mapper.MSMapper;
import com.web.sky.member.Member;
import com.web.sky.service.IGetService;
import com.web.sky.service.IListService;

@RestController
public class MSController {
   @Autowired Command cmd;
   @Autowired MSMapper ms;
   private static final Logger logger = LoggerFactory.getLogger(MSController.class);
   
   @RequestMapping(value="/{email}/{pw}",
            method=RequestMethod.POST,
            consumes="application/json")
   public @ResponseBody Map<?, ?> get(@PathVariable String email,@PathVariable String pw){
      logger.info("로그인 컨트롤러 진입!!");
      Map<String, Object> map = new HashMap<>();
      IGetService detailService=null;
      Member bean=null;
      logger.info("디테일 컨트롤러로 넘어온 아이디: {}",email);
      logger.info("디테일 컨트롤러로 넘어온 비밀번호: {}",pw);
      cmd.setSearch(email);
      cmd.setDir(pw);
      detailService=(x)->{
         return ms.selectOne(cmd);
      };
      bean = (Member) detailService.execute(cmd);
      map.put("success", "통신성공");
      map.put("bean", bean);
      logger.info("respmap에 담긴 내용 {}",map.get("bean"));
      return map;
   };
   
   @RequestMapping(value="/suggest")
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
      map.put("sgst", listService.execute(cmd));
      return map;
      
   }
}