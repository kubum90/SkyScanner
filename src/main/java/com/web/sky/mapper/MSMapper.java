package com.web.sky.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.sky.command.Command;
import com.web.sky.member.Member;

@Repository
public interface MSMapper {
	public void memberInsert(Object o);
	public List<?> selectList(Command cmd);
	public Object selectOne(Command cmd);
	public String count(Command cmd);
	public void update(Object o);
	public void delete(Command cmd);
}
