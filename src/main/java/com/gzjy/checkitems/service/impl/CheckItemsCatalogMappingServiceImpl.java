package com.gzjy.checkitems.service.impl;

import java.util.HashMap;
import java.util.List;

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

	public int insert(CheckItemsCatalogMapping record) {
		return checkItemsCatalogMappingMapper.insert(record);
	}

	public int updateByPrimaryKeySelective(CheckItemsCatalogMapping record) {
		return checkItemsCatalogMappingMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(String id) {
		return checkItemsCatalogMappingMapper.deleteByPrimaryKey(id);
	}

	public List<HashMap<String, Object>> selectCheckItemsById(String catalogId) {
		return checkItemsCatalogMappingMapper.selectCheckItemsById(catalogId);
	}

}
