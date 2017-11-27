package com.gzjy.log.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.util.UUID;
import com.gzjy.contract.model.Contract;
import com.gzjy.log.mapper.LogMapper;
import com.gzjy.log.model.LogModel;
import com.gzjy.log.service.LogService;
import com.gzjy.user.UserService;
import com.gzjy.user.model.User;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper;
	
	@Autowired
	UserService userService;
	
	public void insertLog(LogModel logModel) {
		logMapper.insert(logModel);		
	}

	public List<LogModel> selectAll(LogModel logModel) {
		return logMapper.selectAll(logModel);
	}

	public int deleteByCreateTime(Date createTime) {	
		SimpleDateFormat f=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		return logMapper.deleteByCreateTime(f.format(createTime));
	}
	
	public void insertLog(String operation, String target, String extra) {
		User currentUser = userService.getCurrentUser();
		LogModel logModel = new LogModel();
		logModel.setId(UUID.random());
		logModel.setOperateUserId(currentUser.getId());
		logModel.setOperateUser(currentUser.getName());
		logModel.setOperation(operation);
		logModel.setTarget(target);
		logModel.setCreateTime(new Date());
		logModel.setExtra(extra);
		logMapper.insert(logModel);
	}

	public PageInfo<LogModel> getPageList(Integer pageNum, Integer pageSize, String operateUserId,
			String operateUser, String target, String operation) {
		List<LogModel> list = new ArrayList<LogModel>();
	    PageInfo<LogModel> pages = new PageInfo<LogModel>(list);
	    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
	        @Override
	        public void doSelect() {
	        	logMapper.selectAll(operateUserId,operateUser, target, operation);
	        }
	    });
	    return pages;
	}

}
