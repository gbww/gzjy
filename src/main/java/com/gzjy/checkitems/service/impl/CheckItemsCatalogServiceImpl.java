package com.gzjy.checkitems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			for(CheckItemsCatalog child: children) {
				deleteCheckItemsCatalog(child.getId());
			}
		}
		checkItemsCatalogMapper.deleteByPrimaryKey(id);
	}
}
