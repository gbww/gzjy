package com.gzjy.contract.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.contract.model.Contract;;

@Mapper
public interface ContractMapper {

	// @Select("select * from user where userName = #{name}")
	Contract getById(@Param("id") String id);

	ArrayList<Contract> getPageList(
			@Param("id") String id, 
			@Param("start") int start,
			@Param("end") int end
	);
}