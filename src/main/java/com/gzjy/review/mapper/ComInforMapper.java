package com.gzjy.review.mapper;

import com.gzjy.review.modle.ComInfor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

@Mapper
public interface ComInforMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComInfor record);

    int insertSelective(ComInfor record);

    ComInfor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComInfor record);

    int updateByPrimaryKey(ComInfor record);

    /**
     * 条件查询企业信息
     * @param filterMaps
     * @return
     */
    List<ComInfor> selectByFilters(@Param("filterMaps") Map<String,List<String>> filterMaps);

    /**
     * 批量删除企业信息
     * @param ids
     * @return
     */
    int deleteByKeys(@Param("ids") List<String> ids);
}