package com.gzjy.log.service;

import java.util.Date;
import java.util.List;

import com.gzjy.log.model.LogModel;

public interface LogService {
	
	public void insertLog(LogModel logModel);
	
	public List<LogModel> selectAll(LogModel logModel);
	
	public int deleteByCreateTime(Date createTime);
	
}
