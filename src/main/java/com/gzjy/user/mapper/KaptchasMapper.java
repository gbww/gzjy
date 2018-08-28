package com.gzjy.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.user.model.Kaptchas;

@Mapper
public interface KaptchasMapper {
    int deleteById(String id);
    
    int deleteBySessionId(String sessionId);

    int insert(Kaptchas record);

    int insertSelective(Kaptchas record);

    Kaptchas selectById(String id);
    
    Kaptchas selectBySessionId(String sessionId);
    
    List<Kaptchas> selectAll();

    int updateByIdSelective(Kaptchas record);
    
    int updateBySessionIdSelective(Kaptchas record);

    int updateById(Kaptchas record);
}