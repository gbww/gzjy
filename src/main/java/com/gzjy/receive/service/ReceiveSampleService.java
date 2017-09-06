/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
package com.gzjy.receive.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.receive.mapper.ReceiveSampleItemMapper;
import com.gzjy.receive.mapper.ReceiveSampleMapper;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
@Service
public class ReceiveSampleService {
	private static Logger logger = LoggerFactory.getLogger(ReceiveSampleService.class);

	@Autowired
	private ReceiveSampleMapper receiveSampleMapper;
	@Autowired
	private ReceiveSampleItemMapper receiveSampleItemMapper;

	@Transactional
	public Boolean addReceiveSample(ReceiveSample record) {
		if (StringUtils.isBlank(record.getReceiveSampleId())) {
			return false;
		} else {
			receiveSampleMapper.insert(record);
		}

		return true;
	}

	@Transactional
	public Boolean addReceiveSampleItems(List<ReceiveSampleItem> items) {
		if (items.size() == 0) {
			return false;
		}

		for (ReceiveSampleItem item : items) {
			if (StringUtils.isBlank(item.getName())) {
				continue;
			}
			if (StringUtils.isBlank(item.getId())) {
				item.setId(UUID.random());
				receiveSampleItemMapper.insert(item);
			} else {
				ReceiveSampleItem exitItem = receiveSampleItemMapper.selectByPrimaryKey(item.getId());
				if (exitItem != null) {
					receiveSampleItemMapper.updateByPrimaryKey(item);
				} else {
					System.out.println("接样单中不存在此检验项ID");
					logger.error("接样单中不存在此检验项ID");
				}
			}

		}
		return true;
	}

	@Transactional
	public Boolean deleteReceiveSampleItems(List<String> itemIds) {

		for (String item : itemIds) {

			receiveSampleItemMapper.deleteByPrimaryKey(item);
		}
		return true;
	}

	@Transactional
	public int delete(String recordId) {
		int i = 1;
		try {
			receiveSampleMapper.deleteByPrimaryKey(recordId);
			receiveSampleItemMapper.deleteByReceiveSampleId(recordId);
		} catch (Exception e) {
			i = 0;
			e.printStackTrace();
			throw new BizException("删除失败");
		}
		return i;
	}

	@Transactional
	public ReceiveSample updateReceiveSample(ReceiveSample record) {
		ReceiveSample exitrecord = receiveSampleMapper.selectByPrimaryKey(record.getReceiveSampleId());
		if (exitrecord != null) {
			receiveSampleMapper.updateByPrimaryKeySelective(record);
		}

		return record;
	}

	@Transactional
	public PageInfo<ReceiveSample> select(Integer pageNum, Integer pageSize, String order, Map<String, Object> filter) {

		List<ReceiveSample> list = new ArrayList<ReceiveSample>();
		PageInfo<ReceiveSample> pages = new PageInfo<ReceiveSample>(list);
		;
		pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				receiveSampleMapper.selectAll(filter, order);
			}
		});
		return pages;
	}

	public ReceiveSample getReceiveSample(String id) {

		ReceiveSample record = receiveSampleMapper.selectByPrimaryKey(id);
		if (record != null) {
			return record;
		} else {
			throw new BizException("查询失败，id输入有误");
		}

	}

	public List<ReceiveSampleItem> getItemsByReceiveSampleId(String ReceiveSampleId) {
		if (StringUtils.isBlank(ReceiveSampleId)) {
			throw new BizException("接样ID参数是空值");
		}
		List<ReceiveSampleItem> record = receiveSampleItemMapper.selectByReceiveSampleId(ReceiveSampleId);
		return record;

	}

	public ReceiveSampleItem getItem(String itemId) {

		ReceiveSampleItem record = receiveSampleItemMapper.selectByPrimaryKey(itemId);
		if (record != null) {
			return record;
		} else {
			throw new BizException("查询失败，id输入有误");
		}

	}
	
	/**
	 * 复制文件
	 * @param oldPath
	 * @param newPath
	 */
	public void copyFile(String oldPath, String newPath) {
		try {
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int bytesum = 0;
				int byteread = 0;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}else
				throw new Exception("模板文件不存在");
		} catch (Exception e) {
			logger.error("复制文件出错");
		}
	}
	
	/**
	 * 删除文件
	 * @param filePath
	 * @throws Exception
	 */
	public void deleteFile(String filePath) throws Exception {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			if (!file.delete()) {
				throw new Exception("临时模板文件删除失败");
			}
		}
	}
	
	/**
	 * 处理excel
	 * @param workbook
	 * @param data
	 */
	public void generateExcel(HSSFWorkbook workbook, HashMap<String, String> data) {
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {			
			Row row = rows.next();
			Iterator<Cell> cells = row.iterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();				
				String value = null;
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					value = cell.getNumericCellValue() + "";
					break;
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue() + "";
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue() + "";
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					value = cell.getCellFormula() + "";
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR:
					value = "ERROR";
					break;
				default:
					break;
				}				
				if (value.contains("&reportId")) {
					cell.setCellValue(data.get("reportId"));
				}
				if (value.contains("&sampleType")) {
					cell.setCellValue(data.get("sampleType"));
				}
				if (value.contains("&receiveSampleId")) {
					cell.setCellValue(data.get("receiveSampleId"));
				}
				if (value.contains("&checkType")) {
					cell.setCellValue(data.get("checkType"));
				}
				if (value.contains("&reportLayout")) {
					cell.setCellValue(data.get("reportLayout"));
				}
				if (value.contains("&coverLayout")) {
					cell.setCellValue(data.get("coverLayout"));
				}
				if (value.contains("&sampleType")) {
					cell.setCellValue(data.get("sampleType"));
				}
				if (value.contains("&entrustedUnit")) {
					cell.setCellValue(data.get("entrustedUnit"));
				}
				if (value.contains("&entrustedUnitAddress")) {
					cell.setCellValue(data.get("entrustedUnitAddress"));
				}
				if (value.contains("&entrustedUser")) {
					cell.setCellValue(data.get("entrustedUser"));
				}
				if (value.contains("&entrustedUserPhone")) {
					cell.setCellValue(data.get("entrustedUserPhone"));
				}
				if (value.contains("&entrustedUserEmail")) {
					cell.setCellValue(data.get("entrustedUserEmail"));
				}
				if (value.contains("&inspectedUnit")) {
					cell.setCellValue(data.get("inspectedUnit"));
				}
				if (value.contains("&inspectedUnitAddress")) {
					cell.setCellValue(data.get("inspectedUnitAddress"));
				}
				if (value.contains("&inspectedUser")) {
					cell.setCellValue(data.get("inspectedUser"));
				}
				if (value.contains("&inspectedUserPhone")) {
					cell.setCellValue(data.get("inspectedUserPhone"));
				}
				if (value.contains("&inspectedUserEmail")) {
					cell.setCellValue(data.get("inspectedUserEmail"));
				}
				if (value.contains("&sampleAddress")) {
					cell.setCellValue(data.get("sampleAddress"));
				}
				if (value.contains("&sampleName")) {
					cell.setCellValue(data.get("sampleName"));
				}
				if (value.contains("&sampleLink")) {
					cell.setCellValue(data.get("sampleLink"));
				}
				if (value.contains("&sampleTrademark")) {
					cell.setCellValue(data.get("sampleTrademark"));
				}
				if (value.contains("&sampleReportId")) {
					cell.setCellValue(data.get("sampleReportId"));
				}
				if (value.contains("&sampleCirculate")) {
					cell.setCellValue(data.get("sampleCirculate"));
				}
				if (value.contains("&sampleCirculateDate")) {
					cell.setCellValue(data.get("sampleCirculateDate"));
				}
				if (value.contains("&sampleDate")) {
					cell.setCellValue(data.get("sampleDate"));
				}
				if (value.contains("&sampleWay")) {
					cell.setCellValue(data.get("sampleWay"));
				}
				if (value.contains("&specificationModel")) {
					cell.setCellValue(data.get("specificationModel"));
				}
				if (value.contains("&executeStandard")) {
					cell.setCellValue(data.get("executeStandard"));
				}
				if (value.contains("&sampleNames")) {
					cell.setCellValue(data.get("sampleNames"));
				}
				if (value.contains("&processingTechnology")) {
					cell.setCellValue(data.get("processingTechnology"));
				}
				if (value.contains("&closedStatus")) {
					cell.setCellValue(data.get("closedStatus"));
				}
				if (value.contains("&sampleNumber")) {
					cell.setCellValue(data.get("sampleNumber"));
				}
				if (value.contains("&sampleStatus")) {
					cell.setCellValue(data.get("sampleStatus"));
				}
				if (value.contains("&sampleBasenumber")) {
					cell.setCellValue(data.get("sampleBasenumber"));
				}
				if (value.contains("&saveWay")) {
					cell.setCellValue(data.get("saveWay"));
				}
				if (value.contains("&productionUnit")) {
					cell.setCellValue(data.get("productionUnit"));
				}
				if (value.contains("&productionAddress")) {
					cell.setCellValue(data.get("productionAddress"));
				}
				if (value.contains("&productionUser")) {
					cell.setCellValue(data.get("productionUser"));
				}
				if (value.contains("&productionPhone")) {
					cell.setCellValue(data.get("productionPhone"));
				}
				if (value.contains("&cost")) {
					cell.setCellValue(data.get("cost"));
				}
				if (value.contains("&remarks")) {
					cell.setCellValue(data.get("remarks"));
				}
				if (value.contains("&dataRemarks")) {
					cell.setCellValue(data.get("dataRemarks"));
				}
				if (value.contains("&responsiblePerson")) {
					cell.setCellValue(data.get("responsiblePerson"));
				}
				if (value.contains("&sampleHolder")) {
					cell.setCellValue(data.get("sampleHolder"));
				}
				if (value.contains("&receiveUser")) {
					cell.setCellValue(data.get("receiveUser"));
				}
				if (value.contains("&receiveDate")) {
					cell.setCellValue(data.get("receiveDate"));
				}
				if (value.contains("&arrangeFinishDate")) {
					cell.setCellValue(data.get("arrangeFinishDate"));
				}
				if (value.contains("&finishDate")) {
					cell.setCellValue(data.get("finishDate"));
				}
				if (value.contains("&protocolId")) {
					cell.setCellValue(data.get("protocolId"));
				}
				if (value.contains("&subpackage")) {
					cell.setCellValue(data.get("subpackage"));
				}
				if (value.contains("&result")) {
					cell.setCellValue(data.get("result"));
				}
				if (value.contains("&determine")) {
					cell.setCellValue(data.get("determine"));
				}
				if (value.contains("&approvalUser")) {
					cell.setCellValue(data.get("approvalUser"));
				}
				if (value.contains("&examineUser")) {
					cell.setCellValue(data.get("examineUser"));
				}
				if (value.contains("&drawUser")) {
					cell.setCellValue(data.get("drawUser"));
				}
				if (value.contains("&principalInspector")) {
					cell.setCellValue(data.get("principalInspector"));
				}
				if (value.contains("&createdAt")) {
					cell.setCellValue(data.get("createdAt"));
				}
			}
		}		
	}
}
