package com.gzjy.checkitems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.checkitems.mapper.CheckItemsMapper;
import com.gzjy.checkitems.model.CheckItems;
import com.gzjy.checkitems.service.CheckItemsService;

@Service
public class CheckItemsServiceImpl implements CheckItemsService {
	
	@Autowired
	private CheckItemsMapper checkItemsMapper;
	
	public CheckItems selectByPrimaryKey(String id) {
		return checkItemsMapper.selectByPrimaryKey(id);
	}

}
