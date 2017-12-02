package com.gzjy.organization.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gzjy.organization.model.Organization;
import com.gzjy.organization.model.OrganizationExample;
@Mapper
public interface OrganizationMapper {
    int deleteById(String id);

    int insert(Organization record);

    int insertSelective(Organization record);

    List<Organization> selectByExample(OrganizationExample example);

    Organization selectById(String id);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);
    
    List<Organization> selectAll();
}