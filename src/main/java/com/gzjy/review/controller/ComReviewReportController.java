package com.gzjy.review.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gzjy.common.Response;
import com.gzjy.common.exception.BizException;
import com.gzjy.review.modle.ComReviewReport;
import com.gzjy.review.service.ComReviewReportService;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;


/**
 * @Description: 企业评审报告信息控制层
 * @Auther: wuyongfa
 * @Date: 2018/6/30 09:36
 */
@RestController
@RequestMapping({ "/v1/ahgz/company/review/report" })
public class ComReviewReportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComReviewReportController.class);

	@Autowired
	private ComReviewReportService comReviewReportService;
	@Autowired
	private DataSource dataSource;

	/**
	 *  根据主键查询企业评审报告信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/selectById/{reviewReportId}", method = RequestMethod.GET)
	public Response selectByPrimaryKey(@PathVariable(name = "reviewReportId",required = true) String reviewReportId){
		ComReviewReport comReviewReport=comReviewReportService.selectByPrimaryKey(reviewReportId);
		return Response.success(comReviewReport);
	}



	/**
	 *  根据企业id查询企业评审报告信息
	 * @param CompanyId 企业id
	 * @return
	 */
	@RequestMapping(value = "/selectByCompanyId/{companyId}", method = RequestMethod.GET)
	public Response selectByCompanyId(
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@PathVariable(name = "companyId",required = true) String companyId){

		PageInfo<ComReviewReport> comReviewReport = comReviewReportService.selectByCompanyId(pageNum,pageSize,companyId);
		return Response.success(comReviewReport);

	} 



	/**
	 * 通过companyId  企业ID 初始化企业评审报告信息
	 * 企业审核项目信息，并向前端返回创建的报告信息编号
	 * @param companyId 企业ID
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response add(@RequestParam(name="companyId",required = true) String companyId){
		try {

			String reviewReportId=comReviewReportService.insertComReviewReport(companyId);
			return Response.success(reviewReportId);
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	/**
	 * 修改企业评审报告信息
	 * @param comReviewReport
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.PUT)
	public Response update(@RequestBody ComReviewReport comReviewReport){
		try {

			comReviewReportService.update(comReviewReport);
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
	}

	/**
	 *完善企业评审报告信息
	 * @param reviewReportId
	 * @return
	 */
	@RequestMapping(value = "/PerfectReport",method = RequestMethod.PUT)
	public Response perfectReport(@RequestParam(name="reviewReportId",required = true)String reviewReportId){
		try {

			comReviewReportService.perfectReport(reviewReportId);
			return Response.success("success");
		}
		catch (Exception e) {
			throw new BizException(e.toString());
		}
	}


	/**
	 * 批量预览pdf报告
	 * 
	 * @return
	 */
	@RequestMapping(value = "/preview", method = RequestMethod.GET)
	//@Privileges(name = "REPORT-MUTI-PREVIEW", scope = { 1 })
	public Response batchviewReport(@RequestParam(name = "reviewReportId", required = true) String reviewReportId, HttpServletResponse response) {
		List<JasperPrint> prints = new ArrayList<JasperPrint>();
		OutputStream out = null;
		for (int i = 0; i <6; i++) {
			System.out.println("111111111111111111111111111111111111111111111111111111111111111");
			ComReviewReport node =comReviewReportService.selectByPrimaryKey(reviewReportId);
			if (node == null) {

			} else {


				//String templateDir = "/var/lib/docs/gzjy/template/" + templateName;
				String templateDir = "C:/Users/Administrator/Desktop/psmb/comReview" + i+".jasper";
				//String templateDir = "C:/Users/Administrator/Desktop/psmb/wugonghai.jasper";
				Map<String, Object> rptParameters = new HashMap<String, Object>();

				rptParameters.put("reviewReportId", reviewReportId);
				// 传入报表源文件绝对路径，外部参数对象，DB连接，得到JasperPring对象
				JasperPrint jasperPrint = new JasperPrint();
				try {
					jasperPrint = JasperFillManager.fillReport(templateDir, rptParameters,
							dataSource.getConnection());
				} catch (Exception e) {
					e.printStackTrace();
					return Response.fail(e.getMessage());
				}
				prints.add(jasperPrint);

			}
		}
		if (prints.size() == 0) {

			return Response.fail("没有打印的报告");
		} else {

			try {
				response.reset();
				response.setContentType("application/pdf;charset=UTF-8");
				// response.setContentType("arraybuffer;charset=UTF-8");
				// response.setDateHeader("Expires", 0); // 清除页面缓存
				response.setHeader("Content-disposition",
						"inline;filename=" + URLEncoder.encode(UUID.randomUUID() + ".pdf", "UTF-8"));
				out = response.getOutputStream();
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setExporterInput(SimpleExporterInput.getInstance(prints));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
				exporter.exportReport();
				out.flush();


				return Response.success("success");
			} catch (Exception e) {

				return Response.fail(e.getMessage());
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}try {
	                if(!dataSource.getConnection().isClosed()) {
	                    dataSource.getConnection().close();
	                }
	            } catch (SQLException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
			}

		}
	}
}
