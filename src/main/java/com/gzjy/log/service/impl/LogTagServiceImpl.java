package com.gzjy.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.log.mapper.LogTagMapper;
import com.gzjy.log.model.LogTag;
import com.gzjy.log.service.LogTagService;

@Service
public class LogTagServiceImpl implements LogTagService {

	@Autowired
	private LogTagMapper logTagMapper;
	
	public int insert(LogTag record) {
		
		return logTagMapper.insert(record);
	}

	public List<LogTag> selectAll() {		
		return logTagMapper.selectAll();
	}	

}
