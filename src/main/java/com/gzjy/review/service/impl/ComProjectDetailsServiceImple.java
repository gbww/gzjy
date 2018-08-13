package com.gzjy.review.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.review.mapper.ComProjectDetailsMapper;
import com.gzjy.review.modle.ComProjectDetails;
import com.gzjy.review.service.ComProjectDetailsService;

/**
 * @Description: 企业评审项目明细服务层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:38
 */
@Service
public class ComProjectDetailsServiceImple implements ComProjectDetailsService {


	@Autowired
	private ComProjectDetailsMapper comProjectDetailsMapper;


	@Override
	public List<ComProjectDetails> selectByComType(String comType) {
		// TODO Auto-generated method stub
		return comProjectDetailsMapper.selectByComType(comType);
	}
}
