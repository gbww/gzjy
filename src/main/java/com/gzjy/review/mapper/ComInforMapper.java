package com.gzjy.review.mapper;

import com.gzjy.review.modle.ComInfor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComInforMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComInfor record);

    int insertSelective(ComInfor record);

    ComInfor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComInfor record);

    int updateByPrimaryKey(ComInfor record);
}