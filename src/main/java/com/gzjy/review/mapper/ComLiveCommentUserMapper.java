package com.gzjy.review.mapper;

import java.util.List;

<<<<<<< Updated upstream
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

=======
import org.apache.ibatis.annotations.Param;

import com.gzjy.checkitems.model.CheckItem;
>>>>>>> Stashed changes
import com.gzjy.review.modle.ComLiveCommentUser;

@Mapper
public interface ComLiveCommentUserMapper {
	int deleteByPrimaryKey(String id);

	int insert(ComLiveCommentUser record);

	int insertSelective(ComLiveCommentUser record);

	ComLiveCommentUser selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ComLiveCommentUser record);

	int updateByPrimaryKey(ComLiveCommentUser record);


	int batchDeleteById(@Param("ids") List<String> ids);

	List<ComLiveCommentUser> selectAll();


	List<ComLiveCommentUser> selectByReviewReportId(String reviewReportId);


	int batchInsertUser(@Param("comLiveCommentUser") List<ComLiveCommentUser> comLiveCommentUser);


<<<<<<< Updated upstream
=======
    int updateByPrimaryKey(ComLiveCommentUser record);
    
    List<ComLiveCommentUser> selectAll();
>>>>>>> Stashed changes
}