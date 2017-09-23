package com.gzjy.checkitems.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

	public PageInfo<CheckItem> getPageList(Integer pageNum, Integer pageSize, String name, String method) {
		List<CheckItem> list = new ArrayList<CheckItem>();
	    PageInfo<CheckItem> pages = new PageInfo<CheckItem>(list);
	    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
	        @Override
	        public void doSelect() {
	        	checkItemMapper.selectAll(name, method);
	        }
	    });
	    return pages;
	}

	public boolean validateCheckItem(CheckItem checkItem) {
		int result=checkItemMapper.validateCheckItem(checkItem);
		return result>0;
	}
}
