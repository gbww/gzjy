package com.gzjy.review.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.ShortUUID;
import com.gzjy.review.mapper.ComLiveCommentUserMapper;
import com.gzjy.review.modle.ComLiveCommentUser;
import com.gzjy.review.service.ComLiveCommentUserService;

/**
 * @Description: 参与评审人员业务层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:28
 */
@Service
public class ComLiveCommentUserServiceImpl implements ComLiveCommentUserService {



	@Autowired
	private ComLiveCommentUserMapper comLiveCommentUserMapper;

	/**
	 * 添加参与评审人员信息
	 */
	@Override
	public int insertUser(ComLiveCommentUser comLiveCommentUser) {
		comLiveCommentUser.setId(ShortUUID.getInstance().generateShortID());
		return comLiveCommentUserMapper.insertSelective(comLiveCommentUser);
	}

	/**
	 * 修改参与评审人员信息
	 */
	@Override
	public int update(ComLiveCommentUser comLiveCommentUser) {

		return comLiveCommentUserMapper.updateByPrimaryKeySelective(comLiveCommentUser);
	}

	/**
	 * 根据主键批量删除参与评审人员信息
	 */
	@Override
	public int batchDeleteByIds(List<String> ids) {

		return comLiveCommentUserMapper.batchDeleteById(ids);
	}

	/**
	 *  根据主键查询参与评审人员信息
	 */
	@Override
	public ComLiveCommentUser selectByPrimaryKey(String id) {

		return comLiveCommentUserMapper.selectByPrimaryKey(id);
	}

	/**
	 * 分页查询参与评审人员信息
	 */
	@Override
	public PageInfo<ComLiveCommentUser> selectByPages(Integer pageNum, Integer pageSize) {
		List<ComLiveCommentUser> list = new ArrayList<ComLiveCommentUser>();
		PageInfo<ComLiveCommentUser> pages = new PageInfo<ComLiveCommentUser>(list);
		pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				comLiveCommentUserMapper.selectAll();
			}
		});
		return pages;
	}

	/**
	 * 根据报告编号查询人员信息
	 */
	@Override
	public List<ComLiveCommentUser> selectByReviewReportId(String reviewReportId) {

		return comLiveCommentUserMapper.selectByReviewReportId(reviewReportId);
	}

	/**
	 * 批量添加参与评审人员信息
	 */
	@Override
	public int batchInsertUser(List<ComLiveCommentUser> comLiveCommentUser) {
		for (ComLiveCommentUser comLiveCommentUser2 : comLiveCommentUser) {
			comLiveCommentUser2.setId(ShortUUID.getInstance().generateShortID());
			System.out.println(comLiveCommentUser2.getId());
		}

		return comLiveCommentUserMapper.batchInsertUser(comLiveCommentUser);
	}

	@Override
	public int batchDeleteById(String id) {
		
		return comLiveCommentUserMapper.deleteByPrimaryKey(id);
	}
	
	
}
