package com.gzjy.checkitems.service;

import com.gzjy.checkitems.model.CheckItem;

public interface CheckItemService {
	CheckItem selectByPrimaryKey(String id);
	int insert(CheckItem checkItem);
	int updateByPrimaryKeySelective(CheckItem checkItem);
	int deleteByPrimaryKey(String id);
}
