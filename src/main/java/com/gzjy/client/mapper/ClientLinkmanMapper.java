package com.gzjy.client.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.client.model.ClientLinkman;
import com.gzjy.client.model.GzClient;
@Mapper
public interface ClientLinkmanMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClientLinkman record);

    int insertSelective(ClientLinkman record);

    ClientLinkman selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClientLinkman record);

    int updateByPrimaryKey(ClientLinkman record);
    
    List<ClientLinkman> selectByClientNum(@Param("clientNum")Integer clientNum,@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
}