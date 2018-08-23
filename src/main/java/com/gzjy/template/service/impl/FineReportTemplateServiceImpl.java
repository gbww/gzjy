package com.gzjy.template.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;
import com.gzjy.receive.service.ReceiveSampleService;
import com.gzjy.template.mapper.FineReportTemplateMapper;
import com.gzjy.template.mapper.FineReportTemplateRoleMappingMapper;
import com.gzjy.template.model.FineReportTemplateModel;
import com.gzjy.template.model.FineReportTemplateRoleMappingModel;
import com.gzjy.template.service.FineReportTemplateService;

@Service
public class FineReportTemplateServiceImpl implements FineReportTemplateService {

	@Autowired
	private FineReportTemplateMapper fineReportTemplateMapper;
	
	@Autowired
	private FineReportTemplateRoleMappingMapper fineReportTemplateRoleMappingMapper;

	@Autowired
	private EpicNFSService epicNFSService;
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveSampleService.class);

	public FineReportTemplateModel selectByName(String name) {
		return fineReportTemplateMapper.selectByName(name);
	}

	public int insert(FineReportTemplateModel record) {
		return fineReportTemplateMapper.insert(record);
	}

	public void uploadFile(MultipartFile file, String name, String description,String category,String roleIdList) {
		FineReportTemplateModel temp = fineReportTemplateMapper.selectByName(name);
		if(temp!=null) {
			throw new BizException("模板名称已存在");
		}
		EpicNFSClient client = epicNFSService.getClient("gzjy");
		// 建立远程存放模板文件目录
		if (!client.hasRemoteDir("template/fr")) {
			client.createRemoteDir("template/fr");
		}		
		String fileName=ShortUUID.getInstance().generateShortID()+".cpt";
		try {
			client.upload(file.getInputStream(), "template/fr/" + fileName);
			client.close();	
			logger.info("文件上传到服务器成功");
			FineReportTemplateModel record=new FineReportTemplateModel();
			String random_id = ShortUUID.getInstance().generateShortID();
			record.setId(random_id);
			record.setName(name);
			record.setFileName(fileName);
			record.setCreatedAt(new Date());
			record.setCategory(category);
			record.setDescription(description);
			record.setVisitUrl("/software/tomcat/apache-tomcat-8.0.52/webapps/WebReport/"+fileName);
			fineReportTemplateMapper.insert(record);
			String [] roleList = roleIdList.split(";");
			for(int i=0;i<roleList.length;i++) {
				FineReportTemplateRoleMappingModel frRecord = new FineReportTemplateRoleMappingModel();
				frRecord.setId(ShortUUID.getInstance().generateShortID());
				frRecord.setTemplateId(random_id);
				frRecord.setCreatedAt(new Date());
				frRecord.setRoleId(roleList[i]);
				fineReportTemplateRoleMappingMapper.insert(frRecord);
			}
		} catch (Exception e) {
			logger.info("文件上传失败:"+e);
			throw new BizException("文件上传失败");
		}
	}
	
	public void ModifyTemplateFile(MultipartFile file, FineReportTemplateModel record, String roleIdList) {
		EpicNFSClient client = epicNFSService.getClient("gzjy");		
		String excelName = file.getOriginalFilename();
		try {			
			FineReportTemplateModel oldTemplate = fineReportTemplateMapper.selectById(record.getId());
			String filePath = "/var/lib/docs/gzjy/template/fr/"+oldTemplate.getFileName();
			File oldFile = new File(filePath);
			if (oldFile.exists() && oldFile.isFile()) {
				logger.info("File path:" + oldFile.getAbsolutePath());
				if (!oldFile.delete()) {
					throw new Exception("模板文件删除失败");
				}
				logger.info("旧模板文件删除成功");
			}
			client.upload(file.getInputStream(), "template/fr/" + excelName);
			client.close();	
			logger.info("新模板文件成功上传到服务器");
			record.setFileName(excelName);
			fineReportTemplateMapper.updateByPrimaryKeySelective(record);
			String [] roleList = roleIdList.split(";");
			ArrayList<String> oldRoleIdList = fineReportTemplateRoleMappingMapper.selectRoleIdListById(record.getId());
			if(oldRoleIdList.size()>0) {
				fineReportTemplateRoleMappingMapper.deleteByIds(oldRoleIdList);
			}
			/*ArrayList<String> delRoleIdList = new ArrayList<String>();
			ArrayList<String> addRoleIdList = new ArrayList<String>();*/
			for(int i=0;i<roleList.length;i++) {
				FineReportTemplateRoleMappingModel frRecord = new FineReportTemplateRoleMappingModel();
				frRecord.setId(ShortUUID.getInstance().generateShortID());
				frRecord.setTemplateId(record.getId());
				frRecord.setCreatedAt(new Date());
				frRecord.setRoleId(roleList[i]);
				fineReportTemplateRoleMappingMapper.insert(frRecord);
			}
		} catch (Exception e) {
			logger.info("文件上传失败:"+e);
			throw new BizException("文件上传失败");
		}
	}
	
	public PageInfo<FineReportTemplateModel> getPageList(Integer pageNum, Integer pageSize, String name, String type, String category){
		List<FineReportTemplateModel> list = new ArrayList<FineReportTemplateModel>();
	    PageInfo<FineReportTemplateModel> pages = new PageInfo<FineReportTemplateModel>(list);
	    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
	        @Override
	        public void doSelect() {
	        	fineReportTemplateMapper.selectAll(name, type, category);
	        }
	    });
	    return pages;
	}

	public int updateByPrimaryKeySelective(FineReportTemplateModel record) {
		return fineReportTemplateMapper.updateByPrimaryKeySelective(record);
	}

	public ArrayList<String> selectTypeByCagegory(String category) {
		return fineReportTemplateMapper.selectTypeByCagegory(category);
	}
	
	public int deleteByPrimaryKey(String id) throws Exception {
		FineReportTemplateModel data = fineReportTemplateMapper.selectById(id);
		String filePath = "/var/lib/docs/gzjy/template/"+data.getFileName();
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			logger.info("File path:" + file.getAbsolutePath());
			if (!file.delete()) {
				throw new Exception("临时模板文件删除失败");
			}
		}
		return fineReportTemplateMapper.deleteByPrimaryKey(id);
	}
}
