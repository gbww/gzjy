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
	public void generateExcel(HSSFWorkbook workbook, ReceiveSample data) {
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
					cell.setCellValue(data.getReportId());
				}
				if (value.contains("&sampleType")) {
					cell.setCellValue(data.getSampleType());
				}
				if (value.contains("&receiveSampleId")) {
					cell.setCellValue(data.getReceiveSampleId());
				}
				if (value.contains("&checkType")) {
					cell.setCellValue(data.getCheckType());
				}
				if (value.contains("&reportLayout")) {
					cell.setCellValue(data.getReportLayout());
				}
				if (value.contains("&coverLayout")) {
					cell.setCellValue(data.getCoverLayout());
				}
				if (value.contains("&sampleType")) {
					cell.setCellValue(data.getSampleType());
				}
				if (value.contains("&entrustedUnit")) {
					cell.setCellValue(data.getEntrustedUnit());
				}
				if (value.contains("&entrustedUnitAddress")) {
					cell.setCellValue(data.getEntrustedUnitAddress());
				}
				if (value.contains("&entrustedUser")) {
					cell.setCellValue(data.getEntrustedUser());
				}
				if (value.contains("&entrustedUserPhone")) {
					cell.setCellValue(data.getEntrustedUserPhone());
				}
				if (value.contains("&entrustedUserEmail")) {
					cell.setCellValue(data.getEntrustedUserEmail());
				}
				if (value.contains("&inspectedUnit")) {
					cell.setCellValue(data.getInspectedUnit());
				}
				if (value.contains("&inspectedUnitAddress")) {
					cell.setCellValue(data.getInspectedUnitAddress());
				}
				if (value.contains("&inspectedUser")) {
					cell.setCellValue(data.getInspectedUser());
				}
				if (value.contains("&inspectedUserPhone")) {
					cell.setCellValue(data.getInspectedUserPhone());
				}
				if (value.contains("&inspectedUserEmail")) {
					cell.setCellValue(data.getInspectedUserEmail());
				}
				if (value.contains("&sampleAddress")) {
					cell.setCellValue(data.getSampleAddress());
				}
				if (value.contains("&sampleName")) {
					cell.setCellValue(data.getSampleName());
				}
				if (value.contains("&sampleLink")) {
					cell.setCellValue(data.getSampleLink());
				}
				if (value.contains("&sampleTrademark")) {
					cell.setCellValue(data.getSampleTrademark());
				}
				if (value.contains("&sampleReportId")) {
					cell.setCellValue(data.getSampleReportId());
				}
				if (value.contains("&sampleCirculate")) {
					cell.setCellValue(data.getSampleCirculate());
				}
				if (value.contains("&sampleCirculateDate")) {
					cell.setCellValue(data.getSampleCirculateDate());
				}
				if (value.contains("&sampleDate")) {
					cell.setCellValue(data.getSampleDate());
				}
				if (value.contains("&sampleWay")) {
					cell.setCellValue(data.getSampleWay());
				}
				if (value.contains("&specificationModel")) {
					cell.setCellValue(data.getSpecificationModel());
				}
				if (value.contains("&executeStandard")) {
					cell.setCellValue(data.getExecuteStandard());
				}
				if (value.contains("&sampleNames")) {
					cell.setCellValue(data.getSampleNames());
				}
				if (value.contains("&processingTechnology")) {
					cell.setCellValue(data.getProcessingTechnology());
				}
				if (value.contains("&closedStatus")) {
					cell.setCellValue(data.getClosedStatus());
				}
				if (value.contains("&sampleNumber")) {
					cell.setCellValue(data.getSampleNumber());
				}
				if (value.contains("&sampleStatus")) {
					cell.setCellValue(data.getSampleStatus());
				}
				if (value.contains("&sampleBasenumber")) {
					cell.setCellValue(data.getSampleBasenumber());
				}
				if (value.contains("&saveWay")) {
					cell.setCellValue(data.getSaveWay());
				}
				if (value.contains("&productionUnit")) {
					cell.setCellValue(data.getProductionUnit());
				}
				if (value.contains("&productionAddress")) {
					cell.setCellValue(data.getProductionAddress());
				}
				if (value.contains("&productionUser")) {
					cell.setCellValue(data.getProductionUser());
				}
				if (value.contains("&productionPhone")) {
					cell.setCellValue(data.getProductionPhone());
				}
				if (value.contains("&cost")) {
					cell.setCellValue(data.getCost());
				}
				if (value.contains("&remarks")) {
					cell.setCellValue(data.getRemarks());
				}
				if (value.contains("&dataRemarks")) {
					cell.setCellValue(data.getDataRemarks());
				}
				if (value.contains("&responsiblePerson")) {
					cell.setCellValue(data.getResponsiblePerson());
				}
				if (value.contains("&sampleHolder")) {
					cell.setCellValue(data.getSampleHolder());
				}
				if (value.contains("&receiveUser")) {
					cell.setCellValue(data.getReceiveUser());
				}
				if (value.contains("&receiveDate")) {
					cell.setCellValue(data.getReceiveDate()+"");
				}
				if (value.contains("&arrangeFinishDate")) {
					cell.setCellValue(data.getArrangeFinishDate()+"");
				}
				if (value.contains("&finishDate")) {
					cell.setCellValue(data.getFinishDate()+"");
				}
				if (value.contains("&protocolId")) {
					cell.setCellValue(data.getProtocolId());
				}
				if (value.contains("&subpackage")) {
					cell.setCellValue(data.getSubpackage());
				}
				if (value.contains("&result")) {
					cell.setCellValue(data.getResult());
				}
				if (value.contains("&determine")) {
					cell.setCellValue(data.getDetermine());
				}
				if (value.contains("&approvalUser")) {
					cell.setCellValue(data.getApprovalUser());
				}
				if (value.contains("&examineUser")) {
					cell.setCellValue(data.getExamineUser());
				}
				if (value.contains("&drawUser")) {
					cell.setCellValue(data.getDrawUser());
				}
				if (value.contains("&principalInspector")) {
					cell.setCellValue(data.getPrincipalInspector());
				}
				if (value.contains("&createdAt")) {
					cell.setCellValue(data.getCreatedAt()+"");
				}
			}
		}		
	}
}
