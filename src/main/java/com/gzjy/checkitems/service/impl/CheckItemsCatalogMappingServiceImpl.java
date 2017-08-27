package com.gzjy.checkitems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.checkitems.mapper.CheckItemsCatalogMappingMapper;
import com.gzjy.checkitems.model.CheckItemsCatalogMapping;
import com.gzjy.checkitems.service.CheckItemsCatalogMappingService;

@Service
public class CheckItemsCatalogMappingServiceImpl implements CheckItemsCatalogMappingService {

	@Autowired
	private CheckItemsCatalogMappingMapper checkItemsCatalogMappingMapper;
	
	public CheckItemsCatalogMapping selectByPrimaryKey(String id) {
		return checkItemsCatalogMappingMapper.selectByPrimaryKey(id);
	}

}
