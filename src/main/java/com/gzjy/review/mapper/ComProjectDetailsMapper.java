package com.gzjy.review.mapper;

import java.util.List;

<<<<<<< Updated upstream
import org.apache.ibatis.annotations.Mapper;
=======
import com.gzjy.review.modle.ComProjectDetails;
>>>>>>> Stashed changes

import com.gzjy.review.modle.ComProjectDetails;
@Mapper
public interface ComProjectDetailsMapper {
	int deleteByPrimaryKey(String id);

	int insert(ComProjectDetails record);

	int insertSelective(ComProjectDetails record);

	ComProjectDetails selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ComProjectDetails record);

	int updateByPrimaryKey(ComProjectDetails record);

<<<<<<< Updated upstream
	List<ComProjectDetails> selectByComType(String comType);
=======
    int updateByPrimaryKey(ComProjectDetails record);

	List<ComProjectDetails> selectAll();
>>>>>>> Stashed changes
}