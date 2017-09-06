/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
package com.gzjy.receive.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.receive.service.ReceiveSampleService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
@RestController
@RequestMapping(value = "v1")
public class ReceiveSampleController {
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveSampleService.class);
	
	@Autowired
	private ReceiveSampleService receiveSampleService;

	// 添加接样基本信息
	@RequestMapping(value = "/receiveSample", method = RequestMethod.POST)
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
	@RequestMapping(value = "/receivesampleitem/{receivesampleid}", method = RequestMethod.POST)
	public Response addItem(@PathVariable("receivesampleid") String receiveSample,
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

		return Response.success("添加成功：" + flag);
	}

	// 删除接样单（包括接样单中的检验项）
	@RequestMapping(value = "/receivesampleitem/", method = RequestMethod.DELETE)
	public Response deleteSample(@Validated(value = { Add.class }) @RequestBody List<String> ids,
			BindingResult result) {
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
	@RequestMapping(value = "/receivesampleitem/{receivesampleid}", method = RequestMethod.DELETE)
	public Response deleteItem(@PathVariable("receivesampleid") String receiveSample, @RequestBody List<String> items,
			BindingResult result) {
		if (result.hasErrors()) {
			return Response.fail(result.getFieldError().getDefaultMessage());
		}

		return Response.success(receiveSampleService.deleteReceiveSampleItems(items));
	}

	// 更新接样基本信息
	@RequestMapping(value = "/receiveSample", method = RequestMethod.PUT)
	public Response updateSample(@Validated(value = { Add.class }) @RequestBody ReceiveSample receiveSample,
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
	@RequestMapping(value = "/receiveSample", method = RequestMethod.GET)
	public Response list(@RequestParam(name = "receivesampleid", required = false) String id,
			@RequestParam(name = "entrustedunit", required = false) String entrustedUnit,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		Map<String, Object> filter = new HashMap<String, Object>();
		String orderby = new String();
		if (StringUtils.isBlank(id)) {
			filter.put("receive_sample_id", id);
		}
		if (StringUtils.isBlank(entrustedUnit)) {
			filter.put("entrusted_unit", entrustedUnit);
		}
		if (StringUtils.isBlank(order)) {
			orderby = "created_at desc";
		}

		return Response.success(receiveSampleService.select(pageNum, pageSize, orderby, filter));
	}

	// 根据ID获取接样信息
	@RequestMapping(value = "/receiveSample/{id}", method = RequestMethod.GET)
	public Response getReceiveSample(@PathVariable(name = "id") String id) {

		return Response.success(receiveSampleService.getReceiveSample(id));
	}

	// 根据接样ID获得接样对应的检验项信息
	@RequestMapping(value = "/receiveSample/sampleItems/{id}", method = RequestMethod.GET)
	public Response getItems(@PathVariable(name = "id") String id) {

		return Response.success(receiveSampleService.getItemsByReceiveSampleId(id));
	}

	// 根据ID获得单个检验项信息
	@RequestMapping(value = "/receiveSample/sampleItems/item/{id}", method = RequestMethod.GET)
	public Response getItem(@PathVariable(name = "id") String itemId) {

		return Response.success(receiveSampleService.getItem(itemId));
	}

	// 导出excel
	@RequestMapping(value = "/receiveSample/sampleItems/test", method = RequestMethod.GET)
	public Response getExcel(HttpServletResponse response) {
		receiveSampleService.copyFile("D://test1.xls", "E://test1.xls");
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("reportId", "XXXXXXXX");
		data.put("sampleType", "AAAAAAAA");
		data.put("receiveDate", "SSSSSSS");
		data.put("entrustedUnit", "QQQQQQQQ");
		data.put("entrustedUnitAddress", "ZZZZZZZ");
		data.put("sampleName", "WWWWWWW");
		data.put("sampleNames", "EEEEEEE");
		data.put("sampleNumber", "RRRRRRR");
		data.put("sampleWay", "DDDDDDD");
		data.put("specificationModel", "CCCCCCC");
		data.put("sampleTrademark", "RRRRRRR");
		data.put("sampleBasenumber", "FFFFFFFF");
		data.put("processingTechnology", "VVVVVVVV");
		data.put("sampleStatus", "BBBBBBB");
		data.put("executeStandard", "GGGGGGG");
		data.put("sampleAddress", "RRTTTT");
		data.put("productionUnit", "YYYYYY");
		data.put("inspectedUnit", "HHHHHHH");
		data.put("inspectedUnitAddress", "UUUUUUU");
		data.put("finishDate", "MMMMMM");
		try {
			InputStream input = new FileInputStream("E://test1.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(input);
			receiveSampleService.generateExcel(workbook, data);
			response.reset();
			response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode("1.xls", "UTF-8"));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			input.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e+"");
			return Response.fail(e.getMessage());
		}		
		return Response.success("success");
	}
}
