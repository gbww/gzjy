package com.gzjy.template.service.impl;

import java.net.URLEncoder;
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
import com.gzjy.template.mapper.TemplateMapper;
import com.gzjy.template.model.Template;
import com.gzjy.template.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateMapper templateMapper;

	@Autowired
	private EpicNFSService epicNFSService;
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveSampleService.class);

	public Template selectByName(String name) {
		return templateMapper.selectByName(name);
	}

	public int insert(Template record) {
		return templateMapper.insert(record);
	}

	public void uploadFile(MultipartFile file, String type, String name, String description) {
		EpicNFSClient client = epicNFSService.getClient("gzjy");
		// 建立远程存放excel模板文件目录
		if (!client.hasRemoteDir("template")) {
			client.createRemoteDir("template");
		}
		//存放在服务器的模板文件是随机生成的，避免重复
		String excelName = ShortUUID.getInstance().generateShortID()+".xls";
		try {
			client.upload(file.getInputStream(), "template/" + excelName);
			client.close();	
			logger.info("文件上传到服务器成功");
			Template record=new Template();
			record.setId(ShortUUID.getInstance().generateShortID());
			record.setName(name);
			record.setExcelName(excelName);
			record.setCreatedAt(new Date());
			record.setDescription(description);
			record.setType(type);
			templateMapper.insert(record);
		} catch (Exception e) {
			logger.info("文件上传失败:"+e);
			throw new BizException("文件上传失败");
		}
	}
	
	public PageInfo<Template> getPageList(Integer pageNum, Integer pageSize, String name, String type){
		List<Template> list = new ArrayList<Template>();
	    PageInfo<Template> pages = new PageInfo<Template>(list);
	    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
	        @Override
	        public void doSelect() {
	        	templateMapper.selectAll(name, type);
	        }
	    });
	    return pages;
	}

	@Override
	public int updateByPrimaryKeySelective(Template record) {
		// TODO Auto-generated method stub
		return templateMapper.updateByPrimaryKeySelective(record);
	}
}
