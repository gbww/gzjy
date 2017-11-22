package com.gzjy.log.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.log.mapper.LogMapper;
import com.gzjy.log.model.LogModel;
import com.gzjy.log.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper;
	
	public void insertLog(LogModel logModel) {
		logMapper.insert(logModel);		
	}

	public List<LogModel> selectAll(LogModel logModel) {
		return logMapper.selectAll(logModel);
	}

	public int deleteByCreateTime(Date createTime) {	
		SimpleDateFormat f=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		return logMapper.deleteByCreateTime(f.format(createTime));
	}

}
