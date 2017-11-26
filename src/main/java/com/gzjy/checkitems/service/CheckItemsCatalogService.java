package com.gzjy.checkitems.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gzjy.checkitems.model.CheckItemsCatalog;

public interface CheckItemsCatalogService {
	CheckItemsCatalog selectByPrimaryKey(String id);
	int createCheckItemsCatalog(CheckItemsCatalog record);	
	List<CheckItemsCatalog> selectByParentId(String parentId);
	int updateByPrimaryKeySelective(CheckItemsCatalog record);	
	void deleteCheckItemsCatalog(String id);	
}
