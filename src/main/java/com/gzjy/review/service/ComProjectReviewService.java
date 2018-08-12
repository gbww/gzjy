package com.gzjy.review.service;

<<<<<<< Updated upstream
import java.util.List;

=======
import com.github.pagehelper.PageInfo;
>>>>>>> Stashed changes
import com.gzjy.review.modle.ComProjectReview;

/**
 * @Description: 企业评审信息服务层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:44
 */
public interface ComProjectReviewService {

<<<<<<< Updated upstream


	List<ComProjectReview> selectByReviewReportId(String reviewReportId);

	int batchupdate(List<ComProjectReview> comProjectReview);
=======
	PageInfo<ComProjectReview> selectALL(Integer pageNum, Integer pageCount);

	ComProjectReview selectByPrimaryKey(String id);

	int insertreviewers(ComProjectReview comProjectReview);

	int updatereviewer(ComProjectReview comProjectReview);

	int deletereviewer(String id);
>>>>>>> Stashed changes
}
