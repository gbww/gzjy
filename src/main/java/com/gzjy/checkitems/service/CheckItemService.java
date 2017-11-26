package com.gzjy.checkitems.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.checkitems.model.CheckItem;

public interface CheckItemService {
	CheckItem selectByPrimaryKey(String id);
	int insert(CheckItem checkItem);
	int updateByPrimaryKeySelective(CheckItem checkItem);
	int deleteByPrimaryKey(String id);
	PageInfo<CheckItem> getPageList(Integer pageNum, Integer pageSize, String name, String method);
	boolean validateCheckItem(CheckItem checkItem);
	void importFile(MultipartFile file);
}
