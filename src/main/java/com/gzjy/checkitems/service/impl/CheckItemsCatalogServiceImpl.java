package com.gzjy.checkitems.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzjy.checkitems.mapper.CheckItemsCatalogMapper;
import com.gzjy.checkitems.mapper.CheckItemsCatalogMappingMapper;
import com.gzjy.checkitems.model.CheckItemsCatalog;
import com.gzjy.checkitems.service.CheckItemsCatalogService;

@Service
public class CheckItemsCatalogServiceImpl implements CheckItemsCatalogService {
	
	
	
	@Autowired
	private CheckItemsCatalogMapper checkItemsCatalogMapper;
	@Autowired
	private CheckItemsCatalogMappingMapper checkItemsCatalogMappingMapper;
	
	private static Logger logger = LoggerFactory.getLogger(CheckItemsCatalogServiceImpl.class);

	
	public CheckItemsCatalog selectByPrimaryKey(String id) {
		return checkItemsCatalogMapper.selectByPrimaryKey(id);
	}

	public int createCheckItemsCatalog(CheckItemsCatalog record) {
		return checkItemsCatalogMapper.insert(record);
	}	
	
	public List<CheckItemsCatalog> selectByParentId(String parentId) {
		return checkItemsCatalogMapper.selectByParentId(parentId);
	}
	public int updateByPrimaryKeySelective(CheckItemsCatalog record) {
		return checkItemsCatalogMapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * 递归删除目录下的子目录
	 */
	@Transactional
	public void deleteCheckItemsCatalog(String id) {
		List<CheckItemsCatalog> children= checkItemsCatalogMapper.selectByParentId(id);
		if(children!=null && children.size()>0) {
			List<String> catalogIdList = new ArrayList<String>();			
			for(CheckItemsCatalog child: children) {
				catalogIdList.add(child.getId());				
				deleteCheckItemsCatalog(child.getId());
			}
			checkItemsCatalogMappingMapper.deleteByIds(catalogIdList);
		}
		checkItemsCatalogMapper.deleteByPrimaryKey(id);
	}	
	
}
