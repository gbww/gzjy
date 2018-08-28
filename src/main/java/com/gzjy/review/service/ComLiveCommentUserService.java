package com.gzjy.review.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gzjy.review.modle.ComLiveCommentUser;

/**
 * @Description:
 * @Auther: wuyongfa
 * @Date: 2018/6/3010:27
 */
public interface ComLiveCommentUserService {
	/**
	 * 添加参与评审人员信息
	 */
	int  insertUser(ComLiveCommentUser comLiveCommentUser);

	/**
	 * 批量添加参与评审人员信息
	 */
	int  batchInsertUser(List<ComLiveCommentUser> comLiveCommentUser);
	/**
	 * 修改参与评审人员信息
	 */
	int update(ComLiveCommentUser comLiveCommentUser);
	/**
	 * 根据主键批量删除参与评审人员信息
	 */
	int batchDeleteByIds(List<String> ids);

	/**
	 *  根据主键查询参与评审人员信息
	 */
	ComLiveCommentUser selectByPrimaryKey(String id);

	/**
	 * 分页查询参与评审人员信息
	 */
	PageInfo<ComLiveCommentUser> selectByPages(Integer pageNum, Integer pageSize);


	/**
	 * 根据报告编号查询人员信息
	 */
	List<ComLiveCommentUser> selectByReviewReportId(String reviewReportId);

	/**
	 * 根据主键删除单个数据
	 * @param id
	 * @return
	 */
	int batchDeleteById(String id);
}
