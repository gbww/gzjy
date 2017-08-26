package com.gzjy.checkitems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.checkitems.mapper.CheckItemMapper;
import com.gzjy.checkitems.model.CheckItem;
import com.gzjy.checkitems.service.CheckItemService;

@Service
public class CheckItemServiceImpl implements CheckItemService {

	@Autowired
	private CheckItemMapper checkItemMapper;
	
	public CheckItem selectByPrimaryKey(String id) {
		return checkItemMapper.selectByPrimaryKey(id);
	}

	public int insert(CheckItem checkItem) {
		return checkItemMapper.insert(checkItem);
	}

	public int updateByPrimaryKeySelective(CheckItem checkItem) {
		return checkItemMapper.updateByPrimaryKeySelective(checkItem);
	}

	public int deleteByPrimaryKey(String id) {
		return checkItemMapper.deleteByPrimaryKey(id);
	}

}
