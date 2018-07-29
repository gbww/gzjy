package com.gzjy.template.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gzjy.template.model.FineReportTemplateModel;;

public interface FineReportTemplateService {
	FineReportTemplateModel selectByName(String name);
	int insert(FineReportTemplateModel record); 
	int updateByPrimaryKeySelective(FineReportTemplateModel record);
	public void ModifyTemplateFile(MultipartFile file, FineReportTemplateModel record);
	void uploadFile(MultipartFile file, String name, String description,String category);
	PageInfo<FineReportTemplateModel> getPageList(Integer pageNum, Integer pageSize, String name, String type, String category);
	ArrayList<String> selectTypeByCagegory(String category);
	int deleteByPrimaryKey(String id) throws Exception ;
}
