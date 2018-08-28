package com.gzjy.checkitems.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gzjy.checkitems.model.CheckItemsCatalogMapping;

public interface CheckItemsCatalogMappingService {
    CheckItemsCatalogMapping selectByPrimaryKey(String id);

    int insert(CheckItemsCatalogMapping record);

    int updateByPrimaryKeySelective(CheckItemsCatalogMapping record);

    int deleteByPrimaryKey(String id);

    List<HashMap<String, Object>> selectCheckItemsById(String catalogId);

    public void importFile(MultipartFile file, String catalogId);
}
