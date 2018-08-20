package com.gzjy.log.service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gzjy.contract.model.Contract;
import com.gzjy.log.model.LogModel;

public interface LogService {
	
	public void insertLog(LogModel logModel);
	
	public void insertLog(String operation,String target, String extra);
	
	public List<LogModel> selectAll(LogModel logModel);
	
	public int deleteByCreateTime(Date createTime);
	
	PageInfo<LogModel> getPageList(Integer pageNum, Integer pageSize, String operateUserId, 
			String operateUser, String target, String operation);
	
}
