package com.gzjy.checkitems.service;

import com.gzjy.checkitems.model.CheckItems;

public interface CheckItemsService {
	CheckItems selectByPrimaryKey(String id);
}
