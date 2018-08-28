package com.gzjy.review.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.review.mapper.ComVarietyFormatsMapper;
import com.gzjy.review.modle.ComVarietyFormats;
import com.gzjy.review.service.ComVarietyFormatsService;

/**
 * @Description: 品种业态信息
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:57
 */
@Service
public class ComVarietyFormatsServiceImpl implements ComVarietyFormatsService {

	@Autowired
	private ComVarietyFormatsMapper comVarietyFormatsMapper;

	@Override
	public List<ComVarietyFormats> selectALL() {

		return comVarietyFormatsMapper.selectALL();
	}
}
