package com.gzjy.template.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.template.model.Template;

public interface TemplateService {
    Template selectByName(String name);

    int insert(Template record);

    int updateByPrimaryKeySelective(Template record);

    void ModifyTemplateFile(MultipartFile file, Template record, String roleIdList);

    void uploadFile(MultipartFile file, String name, String description, String category, Integer type, String roleIdList);

    PageInfo<Template> getPageList(Integer pageNum, Integer pageSize, String name, String type, String category);

    ArrayList<String> selectTypeByCategory(String category);

    int deleteByPrimaryKey(String id) throws Exception;
}
