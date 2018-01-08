/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
package com.gzjy.receive.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Add;
import com.gzjy.common.Response;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.Update;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.ExcelToPdf;
import com.gzjy.common.util.FileOperate;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.receive.service.ReceiveSampleService;
import com.gzjy.user.UserService;
import com.gzjy.user.model.User;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
@RestController
@RequestMapping(value = "v1/ahgz")
public class ReceiveSampleController {

	private static Logger logger = LoggerFactory.getLogger(ReceiveSampleService.class);

	@Autowired
	private ReceiveSampleService receiveSampleService;

	@Autowired
	private EpicNFSService epicNFSService;
	@Autowired
	private UserService userService;

	// 添加接样基本信息
	@RequestMapping(value = "/sample", method = RequestMethod.POST)
	@Privileges(name = "SAMPLE-ADD", scope = { 1 })
	public Response addSample(@Validated(value = { Add.class }) @RequestBody ReceiveSample receiveSample,
			BindingResult result) {
		if (result.hasErrors()) {
			return Response.fail(result.getFieldError().getDefaultMessage());
		}
		if (StringUtils.isBlank(receiveSample.getReceiveSampleId())) {
			return Response.fail("接收样品编号不能为空");
		}
		Boolean flag = receiveSampleService.addReceiveSample(receiveSample);
		return Response.success(flag);
	}

	// 变更接样中检验项基本信息（添加检验项和编辑检验项）
	@Privileges(name = "SAMPLE-ADDITEM", scope = { 1 })
	@RequestMapping(value = "/sample/item/{receiveSampleId}", method = RequestMethod.POST)
	public Response addItem(@PathVariable("receiveSampleId") String receiveSample,
			@Validated(value = { Add.class }) @RequestBody List<ReceiveSampleItem> items, BindingResult result) {
		if (result.hasErrors()) {
			return Response.fail(result.getFieldError().getDefaultMessage());
		}
		for (ReceiveSampleItem item : items) {
			if (!item.getReceiveSampleId().equals(receiveSample)) {
				return Response.fail("接样单ID存在异常");
			}
		}
		boolean flag = receiveSampleService.addReceiveSampleItems(items);

		return Response.success("操作成功：" + flag);
	}

	@Privileges(name = "SAMPLE-UPDATEITEMRESULT", scope = { 1 })
	@RequestMapping(value = "/sample/item/result", method = RequestMethod.POST)
	public Response updateItemResoult(@Validated(value = { Update.class }) @RequestBody List<ReceiveSampleItem> items,
			BindingResult result) {
		if (result.hasErrors()) {
			return Response.fail(result.getFieldError().getDefaultMessage());
		}
		
		boolean flag = receiveSampleService.updateSampleItemsResoult(items);
		for (ReceiveSampleItem item : items) {
		    if(receiveSampleService.checkReceiveSampleIsFinished(item.getReceiveSampleId())) { //如果接样单的检测项都完成了结果录入
		        receiveSampleService.setStatus(item.getReceiveSampleId(), 2);
		    }
        }

		return Response.success("操作成功：" + flag);
	}

	// 删除接样单（包括接样单中的检验项）
	@Privileges(name = "SAMPLE-DELETESAMPLE", scope = { 1 })
	@RequestMapping(value = "/sample/delete", method = RequestMethod.POST)
	public Response deleteSample(@RequestBody List<String> ids, BindingResult result) {
		String record = null;
		if (result.hasErrors()) {
			return Response.fail(result.getFieldError().getDefaultMessage());
		}

		for (String id : ids) {
			receiveSampleService.delete(id);
			record += id + "、";
		}

		record = record.substring(0, record.length() - 1);
		return Response.success(record + "删除成功：");
	}

	// 删除接样中检验项基本信息（删除检验项时直接调用后台接口删除）
	@Privileges(name = "SAMPLE-DELETEITEM", scope = { 1 })
	@RequestMapping(value = "/sample/items/{receiveSampleId}/delete", method = RequestMethod.POST)
	public Response deleteItem(@PathVariable("receiveSampleId") String receiveSample, @RequestBody List<String> items,
			BindingResult result) {
		if (result.hasErrors()) {
			return Response.fail(result.getFieldError().getDefaultMessage());
		}
		for (String id : items) {
			ReceiveSampleItem itemRecord = receiveSampleService.getItem(id);
			if (!itemRecord.getReceiveSampleId().equals(receiveSample)) {
				throw new BizException("传递了一个错误的检验项ID");
			}
		}

		return Response.success(receiveSampleService.deleteReceiveSampleItems(items));
	}

	// 更新接样基本信息
	@Privileges(name = "SAMPLE-UPDATESAMPLE", scope = { 1 })
	@RequestMapping(value = "/sample", method = RequestMethod.PUT)
	public Response updateSample(@Validated(value = { Update.class }) @RequestBody ReceiveSample receiveSample,
			BindingResult result) {
		if (result.hasErrors()) {
			return Response.fail(result.getFieldError().getDefaultMessage());
		}
		if (StringUtils.isBlank(receiveSample.getReceiveSampleId())) {
			return Response.fail("接收样品编号不能为空");
		}
		receiveSample = receiveSampleService.updateReceiveSample(receiveSample);
		
		return Response.success(receiveSample);
	}

	// 查询接样信息
	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-SELECT", scope = { 1 })
	public Response list(@RequestParam(name = "receiveSampleId", required = false) String id,
	        @RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
			@RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
			@RequestParam(name = "sampleName", required = false) String sampleName,
			@RequestParam(name = "executeStandard", required = false) String executeStandard,
			@RequestParam(name = "productionUnit", required = false) String productionUnit,
			@RequestParam(name = "sampleType", required = false) String sampleType,
			@RequestParam(name = "checkType", required = false) String checkType,
			@RequestParam(name = "reportStatus", required = false) Integer reportStatus,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "status", defaultValue = "5") int status,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		Map<String, Object> filter = new HashMap<String, Object>();

		if (StringUtils.isBlank(startTime)) {
			startTime = null;
		}
		if (StringUtils.isBlank(endTime)) {
		    endTime=null;
        }
		if(!StringUtils.isBlank(reportId)) {
		    filter.put("report_id", reportId);
		}
		if(!StringUtils.isBlank(inspectedUnit)) {
            filter.put("inspected_unit", inspectedUnit);
        }
		if(!StringUtils.isBlank(sampleName)) {
            filter.put("sample_name", sampleName);
        }
		if(!StringUtils.isBlank(executeStandard)) {
            filter.put("execute_standard", executeStandard);
        }
		if(!StringUtils.isBlank(productionUnit)) {
            filter.put("production_unit", productionUnit);
        }
		if (!StringUtils.isBlank(id)) {
			filter.put("receive_sample_id", id);
		}
		if (!StringUtils.isBlank(entrustedUnit)) {
			filter.put("entrusted_unit", entrustedUnit);
		}
		if (!StringUtils.isBlank(sampleType)) {
			filter.put("sample_type", sampleType);
		}
		if (!StringUtils.isBlank(checkType)) {
            filter.put("check_type", checkType);
        }
		if (reportStatus!=null) {
            filter.put("report_status", reportStatus);
        }
		if (StringUtils.isBlank(order)) {
			order = "created_at desc";
		}
		if (status != 5) {
			filter.put("status", status);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;

		try {
			start = startTime == null ? null : sdf.parse(startTime);
			end = endTime == null ? null : sdf.parse(endTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException("输入的时间格式不合法！");
		}

		return Response.success(receiveSampleService.select(pageNum, pageSize, order, filter, start, end));
	}
	
	
	
	
	// 查询未分配的接样信息
    @RequestMapping(value = "/sample/listForDistribute", method = RequestMethod.GET)
    public Response listSampleForDistribute(@RequestParam(name = "receiveSampleId", required = false) String id,
            @RequestParam(name = "reportId", required = false) String reportId,
            @RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
            @RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
            @RequestParam(name = "sampleName", required = false) String sampleName,
            @RequestParam(name = "executeStandard", required = false) String executeStandard,
            @RequestParam(name = "productionUnit", required = false) String productionUnit,
            @RequestParam(name = "sampleType", required = false) String sampleType,
            @RequestParam(name = "checkType", required = false) String checkType,
            @RequestParam(name = "reportStatus", required = false) Integer reportStatus,
            @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "status", defaultValue = "0") int status,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        Map<String, Object> filter = new HashMap<String, Object>();

        if (StringUtils.isBlank(startTime)) {
            startTime = null;
        }
        if (StringUtils.isBlank(endTime)) {
            endTime=null;
        }
        if(!StringUtils.isBlank(reportId)) {
            filter.put("report_id", reportId);
        }
        if(!StringUtils.isBlank(inspectedUnit)) {
            filter.put("inspected_unit", inspectedUnit);
        }
        if(!StringUtils.isBlank(sampleName)) {
            filter.put("sample_name", sampleName);
        }
        if(!StringUtils.isBlank(executeStandard)) {
            filter.put("execute_standard", executeStandard);
        }
        if(!StringUtils.isBlank(productionUnit)) {
            filter.put("production_unit", productionUnit);
        }
        if (!StringUtils.isBlank(id)) {
            filter.put("receive_sample_id", id);
        }
        if (!StringUtils.isBlank(entrustedUnit)) {
            filter.put("entrusted_unit", entrustedUnit);
        }
        if (!StringUtils.isBlank(sampleType)) {
            filter.put("sample_type", sampleType);
        }
        if (!StringUtils.isBlank(checkType)) {
            filter.put("check_type", checkType);
        }
        if (reportStatus!=null) {
            filter.put("report_status", reportStatus);
        }
        if (StringUtils.isBlank(order)) {
            order = "created_at desc";
        }
        if (status != 5) {
            filter.put("status", status);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;

        try {
            start = startTime == null ? null : sdf.parse(startTime);
            end = endTime == null ? null : sdf.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("输入的时间格式不合法！");
        }

        return Response.success(receiveSampleService.select(pageNum, pageSize, order, filter, start, end));
    }
    
	
	
		
	
	//查看报告列表
	@RequestMapping(value = "/sample/reports", method = RequestMethod.GET)
	@Privileges(name = "SAMPLE-REPORTLIST", scope = { 1 })
    public Response listReports(@RequestParam(name = "receiveSampleId", required = false) String id,
            @RequestParam(name = "reportId", required = false) String reportId,
            @RequestParam(name = "entrustedUnit", required = false) String entrustedUnit,
            @RequestParam(name = "inspectedUnit", required = false) String inspectedUnit,
            @RequestParam(name = "sampleName", required = false) String sampleName,
            @RequestParam(name = "executeStandard", required = false) String executeStandard,
            @RequestParam(name = "productionUnit", required = false) String productionUnit,
            @RequestParam(name = "sampleType", required = false) String sampleType,
            @RequestParam(name = "checkType", required = false) String checkType,
            @RequestParam(name = "reportStatus", required = false) Integer reportStatus,
            @RequestParam(name = "order", required = false) String order,
            @RequestParam(name = "status", defaultValue = "5") int status,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        Map<String, Object> filter = new HashMap<String, Object>();

        if (StringUtils.isBlank(startTime)) {
            startTime = null;
        }
        if (StringUtils.isBlank(endTime)) {
            endTime=null;
        }
        if(!StringUtils.isBlank(reportId)) {
            filter.put("report_id", reportId);
        }
        if(!StringUtils.isBlank(inspectedUnit)) {
            filter.put("inspected_unit", inspectedUnit);
        }
        if(!StringUtils.isBlank(sampleName)) {
            filter.put("sample_name", sampleName);
        }
        if(!StringUtils.isBlank(executeStandard)) {
            filter.put("execute_standard", executeStandard);
        }
        if(!StringUtils.isBlank(productionUnit)) {
            filter.put("production_unit", productionUnit);
        }
        if (!StringUtils.isBlank(id)) {
            filter.put("receive_sample_id", id);
        }
        if (!StringUtils.isBlank(entrustedUnit)) {
            filter.put("entrusted_unit", entrustedUnit);
        }
        if (!StringUtils.isBlank(sampleType)) {
            filter.put("sample_type", sampleType);
        }
        if (!StringUtils.isBlank(checkType)) {
            filter.put("check_type", checkType);
        }
        if (reportStatus!=null&&reportStatus!=5) {  //所有的不加登录人的过滤
            filter.put("report_status", reportStatus);
            User u=userService.getCurrentUser();
            boolean superUser= u.getRole().isSuperAdmin();
            if(!superUser) {
                String name=u.getName();               
                    if(reportStatus==0) {
                        filter.put("draw_user", name);
                    }
                    if(reportStatus==1) {
                        filter.put("examine_user", name);
                    }
                    if(reportStatus==2) {
                        filter.put("approval_user", name);
                    }                             
            }    
            
        }
        if (StringUtils.isBlank(order)) {
            order = "created_at desc";
        }
        if (status != 5) {
            filter.put("status", status);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;

        try {
            start = startTime == null ? null : sdf.parse(startTime);
            end = endTime == null ? null : sdf.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("输入的时间格式不合法！");
        }

        return Response.success(receiveSampleService.select(pageNum, pageSize, order, filter, start, end));
    }
    
	
	


	// test
	@RequestMapping(value = "/sampletest", method = RequestMethod.GET)
	public Response list(@RequestParam(name = "entrustedunit", required = false) String entrustedUnit,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		Map<String, Object> filter = new HashMap<String, Object>();

		filter.put("check_type", "抽检");

		return Response.success(receiveSampleService.selectTest(pageNum, pageSize, filter));
	}
	
	
    @RequestMapping(value = "/sample/items/countByDepartment", method = RequestMethod.GET)
    public Response selectCountItemByDepartment(@RequestParam(name = "order", required = false) String order,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime){
        if (StringUtils.isBlank(order)) {
            order = "updated_at desc";
        }
        Map<String, Object> filter = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;
        
        try {
            start = startTime == null ? null : sdf.parse(startTime);
            end = endTime == null ? null : sdf.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("输入的时间格式不合法！");
        }
        if(start==null) {
            start=new Date();
        }
        if(end==null) {
            end=new Date(new Date().getTime()-1592000000);//某人一个月前
        }
        if(start.after(end))
            throw new BizException("输入的时间格式不合法,开始时间大于了结束时间");
        
        
        return Response.success(receiveSampleService.selectCountItemByDepartment(filter, order, start, end));
        
    }
	// 根据ID获取接样信息
	@RequestMapping(value = "/sample/{id}", method = RequestMethod.GET)
	public Response getReceiveSample(@PathVariable(name = "id") String id) {

		return Response.success(receiveSampleService.getReceiveSample(id));
	}

	// 根据接样ID获得接样对应的检验项信息
	@RequestMapping(value = "/sample/items/{id}", method = RequestMethod.GET)
	public Response getItems(@PathVariable(name = "id") String id) {

		return Response.success(receiveSampleService.getItemsByReceiveSampleId(id));
	}

	// 根据ID获得单个检验项信息
	@RequestMapping(value = "/sample/items/item/{id}", method = RequestMethod.GET)
	public Response getItem(@PathVariable(name = "id") String itemId) {

		return Response.success(receiveSampleService.getItem(itemId));
	}

	// 设置接样单的状态
	@RequestMapping(value = "/sample/{receivesampleid}/status/{status}", method = RequestMethod.GET)
	public Response setSampleStatus(@PathVariable(name = "receivesampleid") String receiveSampleId,
			@PathVariable(name = "status") Integer status) {

		return Response.success(receiveSampleService.setStatus(receiveSampleId, status));
	}

	// 查询当前用户检验项信息(检测人员关心的检验项)
	@Privileges(name = "SAMPLE-SELECTITEM", scope = { 1 })
	@RequestMapping(value = "/sampleItem", method = RequestMethod.GET)
	public Response listItemByCurrentUser(@RequestParam(name = "status", defaultValue = "0") int status,
			@RequestParam(name = "order", required = false) String order,
			 @RequestParam(name = "receiveSampleId", required = false) String receiveSampleId,
			@RequestParam(name = "reportId", required = false) String reportId,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		Map<String, Object> filter = new HashMap<String, Object>();
		if (status != 5) {
			filter.put("status", status);
		}
		if (StringUtils.isBlank(order)) {
			order = "updated_at desc";
		}
		if (!StringUtils.isBlank(receiveSampleId)) {
		    filter.put("receive_sample_id", receiveSampleId);
        }
		if (!StringUtils.isBlank(reportId)) {
            filter.put("report_id", reportId);
        }
		return Response.success(receiveSampleService.selectCurrentUserItems(pageNum, pageSize, order, filter));
	}
	
	
	
	// 查询当前用户接样单信息(检测人员关心的接样单)
    @RequestMapping(value = "/sampleItem/sample", method = RequestMethod.GET)
    public Response listReceiveItemByCurrentUser(@RequestParam(name = "status", defaultValue = "0") int status,
            @RequestParam(name = "order", required = false) String order,
             @RequestParam(name = "receiveSampleId", required = false) String receiveSampleId,
            @RequestParam(name = "reportId", required = false) String reportId,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> filter = new HashMap<String, Object>();
        if (status != 5) {
            filter.put("status", status);
        }
        if (StringUtils.isBlank(order)) {
            order = "updated_at desc";
        }
        if (!StringUtils.isBlank(receiveSampleId)) {
            filter.put("receive_sample_id", receiveSampleId);
        }
        if (!StringUtils.isBlank(reportId)) {
            filter.put("report_id", reportId);
        }
        return Response.success(receiveSampleService.selectUnderDetection(pageNum, pageSize, order, filter));
    }

	/**
	 * 通过模板导出报告(excel or pdf)并下载
	 * 
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sample/items/report/{id}", method = RequestMethod.GET)
	public Response getReport(HttpServletResponse response, @PathVariable(name = "id") String id,
			@RequestParam(required = true) String templateFileName, @RequestParam(required = true) String type) {
		EpicNFSClient client = epicNFSService.getClient("gzjy");
		// 生成临时模板excel文件
		String fileSuffix = templateFileName.endsWith("xlsx") ? ".xlsx" : ".xls";
		String tempFileName = ShortUUID.getInstance().generateShortID() + fileSuffix;
		// 建立远程存放excel模板文件目录
		if (!client.hasRemoteDir("temp")) {
			client.createRemoteDir("temp");
		}
		// 服务器模板文件存放目录
		String serverTemplatePath = "/var/lib/docs/gzjy/template/";
		// 根据接口参数得到服务器模板文件的实际路径
		String serverTemplateFile = serverTemplatePath + templateFileName;
		// 建立服务器缓存模板文件
		String tempFile = "/var/lib/docs/gzjy/temp/" + tempFileName;
		OutputStream out = null;
		String tempPdf = null;
		Workbook wb = null;
		try {
			// 将模板文件复制到缓存文件
			FileOperate.copyFile(serverTemplateFile, tempFile);
			// 获取报告数据
			ReceiveSample receiveSample = receiveSampleService.getReceiveSample(id);
			InputStream input = new FileInputStream(tempFile);
			if (fileSuffix.equals(".xlsx")) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			// 将数据写入流中
			receiveSampleService.generateExcel(wb, receiveSample);
			if (type.equals("excel")) {
				// 如果是excel，则提供下载功能，需设置头信息
				response.reset();
				response.setContentType("application/octet-stream;charset=UTF-8");
				response.setHeader("Content-disposition",
						"attachment;filename=" + URLEncoder.encode(tempFileName, "UTF-8"));
				out = response.getOutputStream();
				wb.write(out);
				input.close();
			} else {
				logger.info("Begin export PDF");
				String tempPdfName = ShortUUID.getInstance().generateShortID() + ".pdf";
				tempPdf = "/var/lib/docs/gzjy/temp/" + tempPdfName;
				String os = System.getProperty("os.name");
				if (os.toLowerCase().startsWith("win")) {
					ExcelToPdf.xlsToPdf(tempFile, tempPdf);
				} else {
					ExcelToPdf.xlsToPdfForLinux(tempFile, tempPdf);
				}
				return Response.success(tempPdf);
			}
			// 删除缓存模板文件
			FileOperate.deleteFile(tempFile);
		} catch (Exception e) {
			logger.error(e + "");
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sample/export", method = RequestMethod.GET)
	public Response exportExcel(HttpServletResponse response, @RequestParam(required = false) String templateFileName) {
		ReceiveSample receiveSample = new ReceiveSample();
		List<HashMap<String, String>> data = receiveSampleService.selectAllItem(receiveSample);
		// 建立服务器缓存模板文件
		String tempFileName = ShortUUID.getInstance().generateShortID() + ".xlsx";
		String tempFile = "/var/lib/docs/gzjy/temp/" + tempFileName;
		OutputStream out = null;
		Workbook wb = null;
		try {
			File file = new File(tempFile);
			if(!file.exists()) {
				file.createNewFile();
			}			
			wb = new XSSFWorkbook();
			// 将数据写入流中
			receiveSampleService.generateExcelReport(wb, data);
			// 如果是excel，则提供下载功能，需设置头信息
			response.reset();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLEncoder.encode(tempFileName, "UTF-8"));
			out = response.getOutputStream();
			wb.write(out);			
			// 删除缓存模板文件
			FileOperate.deleteFile(tempFile);
		} catch (Exception e) {
			logger.error(e + "");
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return Response.success(data);
	}	
}
