package com.gzjy.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.review.modle.ComProjectReview;
@Mapper
public interface ComProjectReviewMapper {
	int deleteByPrimaryKey(String id);
	
	int deleteByCompanyId(String companyId);

	int insert(ComProjectReview record);

	int insertSelective(ComProjectReview record);

	ComProjectReview selectByPrimaryKey(String id);


	int updateByPrimaryKeySelective(ComProjectReview record);

	int updateByPrimaryKey(ComProjectReview record);

	/**
	 * 批量添加企业审核项目
	 * @param comProjectReview
	 * @return
	 */

	int batchInsertcomProjectReview(@Param("comProjectReview") List<ComProjectReview> comProjectReview);


	/**
	 * 根据报告Id查询
	 * @param reviewReportId 报告Id
	 * @return
	 */

	List<ComProjectReview> selectByReviewReportId(String reviewReportId);
	/**
	 * 批量修改企业审核项目
	 * @param comProjectReview
	 * @return
	 */
	int batchupdate(@Param("comProjectReview1") List<ComProjectReview> comProjectReview1);
}