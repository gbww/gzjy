package com.gzjy.receive.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.receive.model.ReceiveSampleItem;
@Mapper
public interface ReceiveSampleItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReceiveSampleItem record);

    int insertSelective(ReceiveSampleItem record);

    ReceiveSampleItem selectByPrimaryKey(String id);
    //根据接样ID查询检验项列表
    List<ReceiveSampleItem> selectByReceiveSampleId(String receiveSampleId);

    int updateByPrimaryKeySelective(ReceiveSampleItem record);

    int updateByPrimaryKey(ReceiveSampleItem record);
    //根据接收样品的id删除
    int deleteByReceiveSampleId(String id);
}