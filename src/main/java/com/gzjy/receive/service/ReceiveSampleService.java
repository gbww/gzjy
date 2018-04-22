/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
package com.gzjy.receive.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.TransBeanToMap;
import com.gzjy.common.util.UUID;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;
import com.gzjy.receive.mapper.ReceiveSampleItemMapper;
import com.gzjy.receive.mapper.ReceiveSampleMapper;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.receive.model.SampleItemCountView;
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
	@Autowired
	private EpicNFSService epicNFSService;
	
	@Transactional
	public Boolean addReceiveSample(ReceiveSample record) {
	    if(!StringUtils.isBlank(record.getDrawUser())){
	        if(!userClient.nameExist(record.getDrawUser()))
	            throw new BizException("编制人在数据库中不存在");
	    }
	    if(!StringUtils.isBlank(record.getExamineUser())){
            if(!userClient.nameExist(record.getExamineUser()))
                throw new BizException("审核人在数据库中不存在");
        }
	    if(!StringUtils.isBlank(record.getApprovalUser())){
            if(!userClient.nameExist(record.getApprovalUser()))
                throw new BizException("批准人在数据库中不存在");
        }
	    if(receiveSampleMapper.selectByPrimaryKey(record.getReceiveSampleId())!=null){
	        throw new BizException("抽样单ID已经存在");
	    }
	    if(receiveSampleMapper.selectByReportId(record.getReportId()).size()>0){
            throw new BizException("报告编号已经存在");
        }
	   
	    
	    
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
    public Boolean checkReceiveSampleIsFinished(String receiveSampleId) {

        List<ReceiveSampleItem> doingItems = receiveSampleItemMapper
                .selectDoingItems(receiveSampleId);
        if (doingItems.size() > 0)
            return false;
        else
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
			if (StringUtils.isBlank(item.getId())) {  //添加检测项
			    ReceiveSample receive= receiveSampleMapper.selectByPrimaryKey(item.getReceiveSampleId());
			    if(receive==null) {			        
			        System.out.println("接样单ID不存在");
                    logger.error("接样单ID不存在");
                    throw new BizException("抽样单ID不存在");
			    }
			    else {
			        if(receive.getStatus()==1) {  //抽样单处于完成状态了
			            receive.setStatus(0);
			            receiveSampleMapper.updateByPrimaryKeySelective(receive);
			        }
			        
			    }
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
		ReceiveSample existRecord = receiveSampleMapper.selectByPrimaryKey(record.getReceiveSampleId());
	     
	        
		if (existRecord != null) {
		    if(!StringUtils.isBlank(record.getDrawUser())){
                if(!userClient.nameExist(record.getDrawUser()))
                    throw new BizException("编制人在数据库中不存在");
            }
            if(!StringUtils.isBlank(record.getExamineUser())){
                if(!userClient.nameExist(record.getExamineUser()))
                    throw new BizException("审核人在数据库中不存在");
            }
            if(!StringUtils.isBlank(record.getApprovalUser())){
                if(!userClient.nameExist(record.getApprovalUser()))
                    throw new BizException("批准人在数据库中不存在");
            }
            
            if(!StringUtils.isBlank(record.getAppendix())) {
                deleteAttachment(existRecord.getAppendix());
            }
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
	
	
	@Transactional
	public PageInfo<ReceiveSample> selectAllCompareReportStaus(Integer pageNum, Integer pageSize, String order, Map<String, Object> filter,Date timeStart,Date timeEnd) {

		List<ReceiveSample> list = new ArrayList<ReceiveSample>();
		PageInfo<ReceiveSample> pages = new PageInfo<ReceiveSample>(list);
		Timestamp start = timeStart == null ? null : new Timestamp(timeStart.getTime());
        Timestamp end = timeEnd == null ? null : new Timestamp(timeEnd.getTime());
		pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				receiveSampleMapper.selectAllCompareReportStaus(filter, order,start,end);
			}
		});
		return pages;
	}
	
	
	@Transactional
    public List<ReceiveSample> selectNoPage( String order, Map<String, Object> filter,Date timeStart,Date timeEnd) {

        List<ReceiveSample> list = new ArrayList<ReceiveSample>();
       
        Timestamp start = timeStart == null ? null : new Timestamp(timeStart.getTime());
        Timestamp end = timeEnd == null ? null : new Timestamp(timeEnd.getTime());
       
        list= receiveSampleMapper.selectAll(filter, order,start,end);
         
        return list;
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
	
	
	   public List<SampleItemCountView> selectCountItemByDepartment( Map<String, Object> filter,String order,Date timeStart,Date timeEnd) {
	       Timestamp start = timeStart == null ? null : new Timestamp(timeStart.getTime());
	        Timestamp end = timeEnd == null ? null : new Timestamp(timeEnd.getTime());
	        List<SampleItemCountView> list = receiveSampleItemMapper.selectCountGroupByDepartment(filter, order, start, end);
	        return list;
	     
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
    
    
    public PageInfo<ReceiveSample> selectUnderDetection(Integer pageNum, Integer pageSize,String order,Map<String, Object> filter) {
        User u=userClient.getCurrentUser();
        List<ReceiveSample> list = new ArrayList<ReceiveSample>();
        PageInfo<ReceiveSample> pages = new PageInfo<ReceiveSample>(list);
        pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                receiveSampleMapper.selectUnderDetection(u.getName(), filter,order);
            }
        });
        return pages;
    }
    
    public List<ReceiveSample> selectUnderDetectionForNOPage(String order,Map<String, Object> filter) {
        User u=userClient.getCurrentUser(); 
        List<ReceiveSample> list = new ArrayList<ReceiveSample>();
      
        list=receiveSampleMapper.selectUnderDetection(u.getName(), filter,order);
         
        return list;
    }
    
    
    public PageInfo<ReceiveSampleItem> selectUnderDetectionReceiveSampleItems(Integer pageNum, Integer pageSize,String order,Map<String, Object> filter) {
        User u=userClient.getCurrentUser();
       boolean superUser= u.getRole().isSuperAdmin();
       if(!superUser) {
           String department=u.getOrganization().getName();
           filter.put("test_room", department);
       }   
        List<ReceiveSampleItem> list = new ArrayList<ReceiveSampleItem>();
        PageInfo<ReceiveSampleItem> pages = new PageInfo<ReceiveSampleItem>(list);
        pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                receiveSampleItemMapper.select(filter, order);
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
	
	@Transactional
	public Boolean upload(MultipartFile file,String receiveSampleId) throws IOException {
	   
	  // 使用文件系统      
	        if (file.getSize() / (1024 * 1024) > 10) {
	          
	                throw new BizException("文件[" + file.getOriginalFilename()
	                        + "]过大，请上传大小不超过10M的文件");
	            }
	        EpicNFSClient epicNFSClient = epicNFSService.getClient("gzjy");
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	        Calendar cal=Calendar.getInstance();
	       
	        String curDate = simpleDateFormat.format(cal.getTime());  
	        
	        String parentPath = "receive"+"/"+curDate ;
	        if (!epicNFSClient.hasRemoteDir(parentPath)) {
	            epicNFSClient.createRemoteDir(parentPath);
	        }	        
	        String filePath=parentPath+"/"+file.getOriginalFilename();
	       ReceiveSample sample=receiveSampleMapper.selectByPrimaryKey(receiveSampleId);
	        if(sample!=null) {
	            if(!StringUtils.isBlank(sample.getAppendix())) {
	                deleteAttachment(sample.getAppendix());
	            }
	            ReceiveSample record=new ReceiveSample();
                record.setAppendix(filePath);
                record.setReceiveSampleId(sample.getReceiveSampleId());	           	                
	                receiveSampleMapper.updateByPrimaryKeySelective(record);
	                InputStream in=file.getInputStream();
	                try {
                        epicNFSClient.upload(in,
                                filePath);
                    } catch (Exception e) {                       
                        e.printStackTrace();
                       
                    } finally {
                        if(in!=null)
                            in.close();
                        epicNFSClient.close();     
                    }
	                return true;
	        }
	        else
	            return false;
	        
	       
	         	
	    }
	
	public void deleteAttachment(String path) {
	    EpicNFSClient epicNFSClient = null;
	    epicNFSClient = epicNFSService.getClient("gzjy");
	    try {
            epicNFSClient.deleteFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
	    finally {
	        if (epicNFSClient != null) {
                epicNFSClient.close();
            }
        }
	}
	
	 @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	    public HttpServletResponse  download(String path,HttpServletResponse response) throws IOException {
	        EpicNFSClient epicNFSClient = null;
	        InputStream inputStream = null;	        
	        try {
	            
	            epicNFSClient = epicNFSService.getClient("gzjy");
	           String realPath= epicNFSClient.getPath(path);
	           File file = new File(realPath);
	           String filename = file.getName();
	           inputStream =  new BufferedInputStream(epicNFSClient.download(path));
	           OutputStream  outputStream = new BufferedOutputStream(response.getOutputStream());
	           byte[] buffer = new byte[1024];
	           int len = -1;
	           while ((len = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, len);
	            }
	           
	           response.addHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
	           response.addHeader("Content-Length", "" + file.length());
	           response.setContentType("application/octet-stream;charset=UTF-8");
	           outputStream.flush();
	           outputStream.close();
	        } catch (IOException e) {
	            throw new BizException("文件下载失败");
	        } finally {
	            try {
	                if (inputStream != null) {
	                    inputStream.close();
	                    
	                }
	                if (epicNFSClient != null) {
	                    epicNFSClient.close();
	                }
	            } catch (IOException e) {
	                throw new BizException("IO流关闭失败");
	            }
	        }
	        return response;
	       
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
				if(value.trim().indexOf("&") ==0) {
					logger.info("----->Get param: " + value);
					String key = value.substring(1);
					if(key.contains("Date") && mapdata.get(key)!=null) {						
						cell.setCellValue(formatter.format(mapdata.get(key)));
					}else {
						cell.setCellValue(mapdata.get(key)!=null?(mapdata.get(key)+""):"");
					}
					logger.info("----->Set value: " + mapdata.get(key));
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
	
	public void generateExcelReport(Workbook workbook, List<HashMap<String, String>> datas) throws IOException, IllegalAccessException, InvocationTargetException, ParseException {
		Sheet sheet = workbook.createSheet();		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String key[] = new String[] {"id","id","id"};
		int i = 0;
		int max = 1;
		//先找出列表中item的最大值
		Map<String, Integer> max_item = new HashMap<String, Integer>();
		for(HashMap<String, String> data: datas){
			if(max_item.containsKey(data.get("receive_sample_id"))){
				int temp = max_item.get(data.get("receive_sample_id"));
				max_item.put(data.get("receive_sample_id"), temp+1);
				if (max < temp+1) {
					max = temp+1;
				}
			}
			else {
				max_item.put(data.get("receive_sample_id"), 1);
			}			
		}
		for(HashMap<String, String> data: datas){
			Row row = sheet.createRow(i);
			int j = 0;
			for(j=0;j<31+max;j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(data.get(key[0]));
			}
		}		
	}

}
