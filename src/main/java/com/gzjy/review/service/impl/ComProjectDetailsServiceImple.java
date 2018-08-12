package com.gzjy.review.service.impl;

<<<<<<< Updated upstream
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

=======
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream


	@Autowired
	private ComProjectDetailsMapper comProjectDetailsMapper;


	@Override
	public List<ComProjectDetails> selectByComType(String comType) {
		// TODO Auto-generated method stub
		return comProjectDetailsMapper.selectByComType(comType);
	}
=======
	@Autowired
	private ComProjectDetailsMapper comProjectDetailsMapper;

	@Override
	public ComProjectDetails selectByPrimaryKey(String id) {
		return comProjectDetailsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insertreviewers(ComProjectDetails comProjectDetails) {

		return comProjectDetailsMapper.insertSelective(comProjectDetails);
	}

	@Override
	public int updatereviewer(ComProjectDetails comProjectDetails) {

		return comProjectDetailsMapper.updateByPrimaryKeySelective(comProjectDetails);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return comProjectDetailsMapper.deleteByPrimaryKey(id);

	}

	@Override
	public PageInfo<ComProjectDetails> selectALL(Integer pageNum, Integer pageCount) {
		List<ComProjectDetails> list = new ArrayList<ComProjectDetails>();
		PageInfo<ComProjectDetails> pages = new PageInfo<ComProjectDetails>(list);
		pages = PageHelper.startPage(pageNum, pageCount).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				comProjectDetailsMapper.selectAll();
			}
		});
		return pages;
	}

>>>>>>> Stashed changes
}
