package com.gzjy.log.service.impl;

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

}
