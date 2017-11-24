package com.gzjy.checkitems.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gzjy.checkitems.mapper.CheckItemsCatalogMapper;
import com.gzjy.checkitems.model.CheckItemsCatalog;
import com.gzjy.checkitems.service.CheckItemsCatalogService;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;

@Service
public class CheckItemsCatalogServiceImpl implements CheckItemsCatalogService {
	
	@Autowired
	private EpicNFSService epicNFSService;
	
	@Autowired
	private CheckItemsCatalogMapper checkItemsCatalogMapper;
	
	private static Logger logger = LoggerFactory.getLogger(CheckItemsCatalogServiceImpl.class);

	
	public CheckItemsCatalog selectByPrimaryKey(String id) {
		return checkItemsCatalogMapper.selectByPrimaryKey(id);
	}

	public int createCheckItemsCatalog(CheckItemsCatalog record) {
		return checkItemsCatalogMapper.insert(record);
	}	
	
	public List<CheckItemsCatalog> selectByParentId(String parentId) {
		return checkItemsCatalogMapper.selectByParentId(parentId);
	}
	public int updateByPrimaryKeySelective(CheckItemsCatalog record) {
		return checkItemsCatalogMapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * 递归删除目录下的子目录
	 */
	@Transactional
	public void deleteCheckItemsCatalog(String id) {
		List<CheckItemsCatalog> children= checkItemsCatalogMapper.selectByParentId(id);
		if(children!=null && children.size()>0) {
			for(CheckItemsCatalog child: children) {
				deleteCheckItemsCatalog(child.getId());
			}
		}
		checkItemsCatalogMapper.deleteByPrimaryKey(id);
	}

	
	public void importFile(MultipartFile file) {
		EpicNFSClient client = epicNFSService.getClient("gzjy");
		// 建立远程存放excel模板文件目录
		if (!client.hasRemoteDir("template")) {
			client.createRemoteDir("template");
		}
		//存放在服务器的模板文件是随机生成的，避免重复
		String excelName = ShortUUID.getInstance().generateShortID()+".xls";
		Workbook wb = null;
		try {
			client.upload(file.getInputStream(), "template/" + excelName);
			client.close();	
			logger.info("文件上传到服务器成功");
			InputStream inputStream = new FileInputStream("/var/lib/docs/gzjy/template/"+excelName);
			wb = new HSSFWorkbook(inputStream);
			Sheet sheet = wb.getSheetAt(0);
			for(int rowNum=0; rowNum< sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
			}
			wb.close();
	        
		} catch (Exception e) {
			logger.info("文件上传失败:"+e);
			throw new BizException("文件上传失败");
		}
	}
}
