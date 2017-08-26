package com.gzjy.checkitems.service;

import com.gzjy.checkitems.model.CheckItem;

public interface CheckItemService {
	CheckItem selectByPrimaryKey(String id);
}
