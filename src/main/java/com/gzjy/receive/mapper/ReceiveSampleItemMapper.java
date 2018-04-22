package com.gzjy.receive.mapper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.receive.model.SampleItemCountView;
@Mapper
public interface ReceiveSampleItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReceiveSampleItem record);

    int insertSelective(ReceiveSampleItem record);

    ReceiveSampleItem selectByPrimaryKey(String id);
    //查询授予当前用户的检验项列表
    List<ReceiveSampleItem> selectByUser(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
    //根据接样ID查询检验项列表
    List<ReceiveSampleItem> selectByReceiveSampleId(String receiveSampleId);

    int updateByPrimaryKeySelective(ReceiveSampleItem record);

    int updateByPrimaryKey(ReceiveSampleItem record);
    //根据接收样品的id删除
    int deleteByReceiveSampleId(String id);
    //测试一对一连表查询
    List<ReceiveSampleItem> selectTestDetail(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
    
    //按照部门查询每种状态的检测项数量
    List<SampleItemCountView> selectCountGroupByDepartment(@Param("filters") Map<String, Object> filter,@Param("orderby")String order,@Param("timeStart") Timestamp timeStart, 
            @Param("timeEnd") Timestamp timeEnd);
    
    
    List<ReceiveSampleItem> selectDoingItems(String receiveSampleId);
    
    List<ReceiveSampleItem> select(@Param("filters") Map<String, Object> filter,@Param("orderby")String order);
    
    List<HashMap<String,String>> selectCountGroupByUser(@Param("receiveSampleId")String receiveSampleId);
}