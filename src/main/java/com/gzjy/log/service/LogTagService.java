package com.gzjy.log.service;

import java.util.List;

import com.gzjy.log.model.LogTag;

public interface LogTagService {
	
	int insert(LogTag record);
	List<LogTag> selectAll();
}
