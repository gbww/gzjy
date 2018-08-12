package com.gzjy.review.service;

<<<<<<< Updated upstream
import java.util.List;

=======
import com.github.pagehelper.PageInfo;
>>>>>>> Stashed changes
import com.gzjy.review.modle.ComProjectDetails;

/**
 * @Description:
 * @Auther: wuyongfa
 * @Date: 2018/6/30 10:37
 */
public interface ComProjectDetailsService {

<<<<<<< Updated upstream
	List<ComProjectDetails> selectByComType(String comType);
=======
	ComProjectDetails selectByPrimaryKey(String id);

	int insertreviewers(ComProjectDetails comProjectDetails);

	int updatereviewer(ComProjectDetails comProjectDetails);

	int deleteByPrimaryKey(String id);

	PageInfo<ComProjectDetails> selectALL(Integer pageNum, Integer pageCount);


>>>>>>> Stashed changes
}
