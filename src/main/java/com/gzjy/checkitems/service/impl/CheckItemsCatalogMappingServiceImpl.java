package com.gzjy.checkitems.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gzjy.checkitems.mapper.CheckItemsCatalogMappingMapper;
import com.gzjy.checkitems.model.CheckItemsCatalogMapping;
import com.gzjy.checkitems.service.CheckItemsCatalogMappingService;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.FileOperate;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;

@Service
public class CheckItemsCatalogMappingServiceImpl implements CheckItemsCatalogMappingService {

	@Autowired
	private CheckItemsCatalogMappingMapper checkItemsCatalogMappingMapper;
	@Autowired
	private EpicNFSService epicNFSService;
	private static Logger logger = LoggerFactory.getLogger(CheckItemsCatalogMappingServiceImpl.class);

	public CheckItemsCatalogMapping selectByPrimaryKey(String id) {
		return checkItemsCatalogMappingMapper.selectByPrimaryKey(id);
	}

	public int insert(CheckItemsCatalogMapping record) {
		return checkItemsCatalogMappingMapper.insert(record);
	}

	public int updateByPrimaryKeySelective(CheckItemsCatalogMapping record) {
		return checkItemsCatalogMappingMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(String id) {
		return checkItemsCatalogMappingMapper.deleteByPrimaryKey(id);
	}

	public List<HashMap<String, Object>> selectCheckItemsById(String catalogId) {
		return checkItemsCatalogMappingMapper.selectCheckItemsById(catalogId);
	}

	public void importFile(MultipartFile file) {
		EpicNFSClient client = epicNFSService.getClient("gzjy");
		// 建立远程存放excel模板文件目录
		if (!client.hasRemoteDir("temp")) {
			client.createRemoteDir("temp");
		}
		String fileSuffix = file.getOriginalFilename().endsWith("xlsx") ? ".xlsx" : ".xls";
		// 存放在服务器的模板文件是随机生成的，避免重复
		String excelName = ShortUUID.getInstance().generateShortID() + fileSuffix;
		Workbook wb = null;
		String fileName = null;
		try {
			client.upload(file.getInputStream(), "temp/" + excelName);
			client.close();
			logger.info("文件上传到服务器成功");
			fileName = "/var/lib/docs/gzjy/temp/" + excelName;
			InputStream inputStream = new FileInputStream(fileName);
			if (fileSuffix.equals(".xlsx")) {
				wb = new XSSFWorkbook(inputStream);
			} else {
				wb = new HSSFWorkbook(inputStream);
			}
			Sheet sheet = wb.getSheetAt(0);
			if (sheet.getRow(0).getPhysicalNumberOfCells() < 8) {
				throw new BizException("文件少于12列，格式不对");
			}
			List<CheckItemsCatalogMapping> dataList = new ArrayList<CheckItemsCatalogMapping>();
			for (int rowNum = 1; rowNum < sheet.getLastRowNum() + 1; rowNum++) {
				Row row = sheet.getRow(rowNum);
				CheckItemsCatalogMapping item_mapping = new CheckItemsCatalogMapping();
				item_mapping.setId(ShortUUID.getInstance().generateShortID());
				item_mapping.setCatalogId(row.getCell(0) + "");
				item_mapping.setName(row.getCell(1) + "");
				item_mapping.setMethod(row.getCell(2) + "");
				item_mapping.setUnit(row.getCell(3) + "");
				item_mapping.setStandardValue(row.getCell(4) + "");
				item_mapping.setDetectionLimit(row.getCell(5) + "");
				item_mapping.setQuantitationLimit(row.getCell(6) + "");
				item_mapping.setDevice(row.getCell(7) + "");
				item_mapping.setDefaultPrice(Double.parseDouble(row.getCell(8) + ""));
				item_mapping.setCreatedAt(new Date());
				item_mapping.setDepartment(row.getCell(9) + "");
				item_mapping.setSubpackage(row.getCell(10) + "");
				item_mapping.setLaw(row.getCell(11) + "");
				dataList.add(item_mapping);
			}
			//删除重复选项
			/*checkItemsCatalogMappingMapper.importData(dataList);
			List<String> idList = checkItemsCatalogMappingMapper.selectDistinctIds();
			if (idList != null && idList.size() > 0) {
				checkItemsCatalogMappingMapper.deleteByIds(idList);
			}*/
			wb.close();
		} catch (Exception e) {
			logger.info("文件导入异常:" + e);
			throw new BizException("文件导入异常" + e);
		} finally {
			// 删除文件
			try {
				FileOperate.deleteFile(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
