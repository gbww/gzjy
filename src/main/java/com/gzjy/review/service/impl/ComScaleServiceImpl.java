package com.gzjy.review.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.review.mapper.ComScaleMapper;
import com.gzjy.review.modle.ComScale;
import com.gzjy.review.service.ComScaleService;

/**
 * @Description: 企业规模信息
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:54
 */
@Service
public class ComScaleServiceImpl implements ComScaleService {
	@Autowired
	private ComScaleMapper comScaleMapper;

	@Override
	public List<ComScale> selectALL() {
		// TODO Auto-generated method stub
		return comScaleMapper.selectALL();
	}
}
