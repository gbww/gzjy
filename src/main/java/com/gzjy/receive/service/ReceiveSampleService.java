/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
package com.gzjy.receive.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.TransBeanToMap;
import com.gzjy.common.util.UUID;
import com.gzjy.receive.mapper.ReceiveSampleItemMapper;
import com.gzjy.receive.mapper.ReceiveSampleMapper;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.user.UserService;
import com.gzjy.user.mapper.UserSignMapper;
import com.gzjy.user.model.User; 
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
	
	@Autowired
	private UserSignMapper userSignMapper;
	@Autowired
    private UserService userClient;
	
	@Transactional
	public Boolean addReceiveSample(ReceiveSample record) {
		if (StringUtils.isBlank(record.getReceiveSampleId())) {
			return false;
		} else {
		    record.setCreatedAt(new Date());
			receiveSampleMapper.insertSelective(record);
		}
		logger.info("添加成功");
		return true;
	}
	@Transactional
	public Boolean updateSampleItemsResoult(List<ReceiveSampleItem> items) {
	    Boolean result=true;
	    if (items.size() == 0) {
            return false;
        }

	    for(ReceiveSampleItem item:items) {
	        ReceiveSampleItem exitItem = receiveSampleItemMapper.selectByPrimaryKey(item.getId());
            if (exitItem != null) {
                item.setUpdatedAt(new Date());
                receiveSampleItemMapper.updateByPrimaryKey(item);
            } else {
                System.out.println("接样单中不存在此检验项ID");
                logger.error("接样单中不存在此检验项ID");
                result=false;
            }
	    }
	    return result;
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
			if (StringUtils.isBlank(item.getId())) {  //添加检测项
				item.setId(UUID.random());
				 item.setUpdatedAt(new Date());
				 if(item.getStatus()==null){
				     item.setStatus(0);
				     
				 }
				receiveSampleItemMapper.insert(item);
			} else {
				ReceiveSampleItem exitItem = receiveSampleItemMapper.selectByPrimaryKey(item.getId());
				if (exitItem != null) {
				    item.setUpdatedAt(new Date());
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
	public PageInfo<ReceiveSample> select(Integer pageNum, Integer pageSize, String order, Map<String, Object> filter,Date timeStart,Date timeEnd) {

		List<ReceiveSample> list = new ArrayList<ReceiveSample>();
		PageInfo<ReceiveSample> pages = new PageInfo<ReceiveSample>(list);
		Timestamp start = timeStart == null ? null : new Timestamp(timeStart.getTime());
        Timestamp end = timeEnd == null ? null : new Timestamp(timeEnd.getTime());
		pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				receiveSampleMapper.selectAll(filter, order,start,end);
			}
		});
		return pages;
	}
	
	
	/**
	 * 
	测试
	 */
	public PageInfo<ReceiveSample> selectTest(Integer pageNum, Integer pageSize, Map<String, Object> filter) {

        List<ReceiveSample> list = new ArrayList<ReceiveSample>();
        PageInfo<ReceiveSample> pages = new PageInfo<ReceiveSample>(list);
        pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                receiveSampleMapper.selectTest(filter);
            }
        });
        return pages;
    }
	
	
    public PageInfo<ReceiveSampleItem> selectCurrentUserItems(Integer pageNum, Integer pageSize,String order,Map<String, Object> filter) {
        User u=userClient.getCurrentUser();
       boolean superUser= u.getRole().isSuperAdmin();
       if(!superUser) {
           String name=u.getName();
           filter.put("test_user", name);
       }    
        List<ReceiveSampleItem> list = new ArrayList<ReceiveSampleItem>();
        PageInfo<ReceiveSampleItem> pages = new PageInfo<ReceiveSampleItem>(list);
        pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                receiveSampleItemMapper.selectByUser(filter,order);
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
	public Boolean setStatus(String receiveSampleId,Integer status) {
	    ReceiveSample record=new ReceiveSample();
	    record.setReceiveSampleId(receiveSampleId);
	    record.setStatus(status);
	    int resoult=receiveSampleMapper.updateByPrimaryKeySelective(record);
	    if(resoult==1) {
	        return true;
	    }
	    else
        return false;

    }

	
	
	
	
	
	/**
	 * 处理excel
	 * @param workbook
	 * @param data
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ParseException 
	 */
	public void generateExcel(Workbook workbook, ReceiveSample data) throws IOException, IllegalAccessException, InvocationTargetException, ParseException {
		List<ReceiveSampleItem> receiveSampleItems =null;
		if(data.getReceiveSampleId()!=null) {
			receiveSampleItems = getItemsByReceiveSampleId(data.getReceiveSampleId());
		}
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList <String> userIdList = new ArrayList<String>();
		userIdList.add(data.getExamineUser());
		userIdList.add(data.getApprovalUser());
		userIdList.add(data.getPrincipalInspector());
		//通过接口查询到报告中涉及到用户电子签名的路径
		List<HashMap<String, String>> signList = userSignMapper.getSignList(userIdList);
		HashMap<String, String> signUser = new HashMap<String, String>();
		for(HashMap<String, String> temp: signList) {
			signUser.put(temp.get("id"), temp.get("path"));
		}
		Map<String, Object> mapdata = TransBeanToMap.transBean2Map(data);
		int checkResultRowIndex = -1;
		CellStyle checkResultCellStype =null;
		short remarkRowHeight = 0;
		short resultRowHeight = 0;
		while (rows.hasNext()) {
			Row row = rows.next();
			Iterator<Cell> cells = row.iterator();
			int cellIndex = -1;
			while (cells.hasNext()) {
				cellIndex +=1;
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
				if(value.indexOf("&") ==0) {
					String key = value.substring(1);
					if(key.contains("Date") && mapdata.get(key)!=null) {						
						cell.setCellValue(formatter.format(mapdata.get(key)));
					}else {
						cell.setCellValue(mapdata.get(key)!=null?(mapdata.get(key)+""):"");
					}
					if("&remark".equals(value)) {
						remarkRowHeight = row.getHeight();						
					}
					if("&result".equals(value)) {
						resultRowHeight = row.getHeight();						
					}					
				}
				if(value.indexOf("##")!=-1) {
					checkResultRowIndex = row.getRowNum();
					cell.setCellValue(value.substring(2));
					checkResultCellStype = cell.getCellStyle();
					System.out.println("checkResultRowIndex="+checkResultRowIndex);
				}		
				if (value.contains("#principalInspector")) {
					cell.setCellValue("");
					addPictureToExcel(workbook, "/var/lib/docs/gzjy/"+signUser.get(data.getApprovalUser()), cell.getRowIndex()+receiveSampleItems.size(), cellIndex);					 
				}
				if (value.contains("#examineUser")) {
					cell.setCellValue("");
					addPictureToExcel(workbook, "/var/lib/docs/gzjy/sign/zhuleilei.png", cell.getRowIndex()+receiveSampleItems.size(), cellIndex);					 
				}
				if (value.contains("#approvalUser")) {
					cell.setCellValue("");
					addPictureToExcel(workbook, "/var/lib/docs/gzjy/sign/zhuleilei.png", cell.getRowIndex()+receiveSampleItems.size(), cellIndex);					 
				}
			}
		}
		if(checkResultRowIndex!=-1 && receiveSampleItems.size()!=0) {
			sheet.shiftRows(checkResultRowIndex+1, sheet.getLastRowNum()+1, receiveSampleItems.size());
			for(int t=0;t<receiveSampleItems.size();t++) {
				int newRowIndex = checkResultRowIndex+1+t;
				sheet.createRow(newRowIndex);
				CellRangeAddress regionOne=new CellRangeAddress(newRowIndex, newRowIndex, 3, 4);
				sheet.addMergedRegion(regionOne);
				CellRangeAddress regionTwo=new CellRangeAddress(newRowIndex, newRowIndex, 7, 8);
				sheet.addMergedRegion(regionTwo);
				sheet.getRow(checkResultRowIndex+1+receiveSampleItems.size()).setHeight(remarkRowHeight);
				sheet.getRow(checkResultRowIndex+1+receiveSampleItems.size()+3).setHeight(resultRowHeight);
			}
			for(int x=0;x<receiveSampleItems.size();x++) {
				int insertRowIndex = x+checkResultRowIndex+1;
				ReceiveSampleItem item = receiveSampleItems.get(x);
				Row newRow = sheet.getRow(insertRowIndex);
				for(int y=0;y<9;y++) {
					Cell cell = newRow.createCell(y);
					if(checkResultCellStype!=null) {
						cell.setCellStyle(checkResultCellStype);
					}
					switch(y) {
						case 0:
							cell.setCellValue(x+1);
							break;
						case 1:
							cell.setCellValue(item.getName());
							break;
						case 2:
							cell.setCellValue(item.getUnit());
							break;
						case 3:
							cell.setCellValue(item.getMethod());
							break;						
						case 5:
							cell.setCellValue(item.getDevice());
							break;
						case 6:
							cell.setCellValue(item.getItemResult());
							break;
						case 7:
							cell.setCellValue(item.getDetectionLimit());
							break;
						default:
							break;
					}						
				}
			}		
		}
	}

	private void addPictureToExcel(Workbook workbook, String filePath, int rowIndex, int cellIndex) throws IOException {
		BufferedImage bufferImg = null;
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
		File file = new File(filePath);
		if(file.exists()) {
			bufferImg = ImageIO.read(file);        
	        ImageIO.write(bufferImg, "png", byteArrayOut);
	        Drawing  patriarch = workbook.getSheetAt(0).createDrawingPatriarch();
	        HSSFClientAnchor anchor = new HSSFClientAnchor(rowIndex, cellIndex,rowIndex+1, cellIndex+1, 
	        		(short)(cellIndex), rowIndex, (short) (cellIndex+1), rowIndex+1);
	        anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
	        patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
		}        
	}	
	
	public List<HashMap<String, String>>selectAllItem(ReceiveSample record){
		return receiveSampleMapper.selectAllItem(record);
	}
}
