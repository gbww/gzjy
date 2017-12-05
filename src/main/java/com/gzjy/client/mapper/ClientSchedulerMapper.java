package com.gzjy.client.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.client.model.ClientScheduler;
@Mapper
public interface ClientSchedulerMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClientScheduler record);

    int insertSelective(ClientScheduler record);

    ClientScheduler selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClientScheduler record);

    int updateByPrimaryKey(ClientScheduler record);
    List<ClientScheduler> selectByClientNum(@Param("clientNum")Integer clientNum,@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
}