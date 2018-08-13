package com.gzjy.review.service;

import java.util.List;

import com.gzjy.review.modle.ComProjectDetails;

/**
 * @Description:
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:37
 */
public interface ComProjectDetailsService {

	List<ComProjectDetails> selectByComType(String comType);
}
