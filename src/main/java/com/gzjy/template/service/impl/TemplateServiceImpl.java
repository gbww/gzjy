package com.gzjy.template.service.impl;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

	public void uploadFile(MultipartFile file) {
		EpicNFSClient client = epicNFSService.getClient("gzjy");
		// 建立远程存放excel模板文件目录
		if (!client.hasRemoteDir("template")) {
			client.createRemoteDir("template");
		}
		try {
			client.upload(file.getInputStream(), "var/lib/docs/gzjy/template/" + file.getOriginalFilename());
			client.close();			
//			OutputStream os = new FileOutputStream("var/lib/docs/gzjy/template/" + file.getOriginalFilename());
//			// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
//			InputStream is = file.getInputStream();
//			int temp;
//			// 一个一个字节的读取并写入
//			while ((temp = is.read()) != (-1)) {
//				os.write(temp);
//			}
//			os.flush();
//			os.close();
//			is.close();
		} catch (Exception e) {
			logger.info("文件上传失败:"+e);
			throw new BizException("文件上传失败");
		}
	}
	
	public PageInfo<Template> getPageList(Integer pageNum, Integer pageSize, String name){
		List<Template> list = new ArrayList<Template>();
	    PageInfo<Template> pages = new PageInfo<Template>(list);
	    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
	        @Override
	        public void doSelect() {
	        	templateMapper.selectAll(name);
	        }
	    });
	    return pages;
	}
}
