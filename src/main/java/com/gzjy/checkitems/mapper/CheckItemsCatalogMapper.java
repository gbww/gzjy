package com.gzjy.checkitems.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.checkitems.model.CheckItemsCatalog;

@Mapper
public interface CheckItemsCatalogMapper {
    CheckItemsCatalog selectByPrimaryKey(String id);

    int insert(CheckItemsCatalog record);

    List<CheckItemsCatalog> selectByParentId(String parentId);

    int updateByPrimaryKeySelective(CheckItemsCatalog record);

    void deleteByPrimaryKey(String id);
}
