package com.gzjy.checkitems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.checkitems.mapper.CheckItemsCatalogMapper;
import com.gzjy.checkitems.model.CheckItemsCatalog;
import com.gzjy.checkitems.service.CheckItemsCatalogService;

@Service
public class CheckItemsCatalogServiceImpl implements CheckItemsCatalogService {
	
	@Autowired
	private CheckItemsCatalogMapper checkItemsCatalogMapper;
	
	public CheckItemsCatalog selectByPrimaryKey(String id) {
		return checkItemsCatalogMapper.selectByPrimaryKey(id);
	}

}
