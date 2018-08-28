package com.gzjy.checkitems.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.checkitems.mapper.CheckItemMapper;
import com.gzjy.checkitems.mapper.CheckItemsCatalogMappingMapper;
import com.gzjy.checkitems.model.CheckItem;
import com.gzjy.checkitems.model.CheckItemsCatalogMapping;
import com.gzjy.checkitems.service.CheckItemService;
import com.gzjy.common.ShortUUID;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.FileOperate;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;

@Service
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private EpicNFSService epicNFSService;

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Autowired
    private CheckItemsCatalogMappingMapper checkItemsCatalogMappingMapper;

    private static Logger logger = LoggerFactory.getLogger(CheckItemServiceImpl.class);


    public CheckItem selectByPrimaryKey(String id) {
        return checkItemMapper.selectByPrimaryKey(id);
    }

    public int insert(CheckItem checkItem) {
        return checkItemMapper.insert(checkItem);
    }

    public int updateByPrimaryKeySelective(CheckItem checkItem) {
        return checkItemMapper.updateByPrimaryKeySelective(checkItem);
    }

    public int deleteByPrimaryKey(String id) {
        return checkItemMapper.deleteByPrimaryKey(id);
    }

    public PageInfo<CheckItem> getPageList(Integer pageNum, Integer pageSize, String name, String method) {
        List<CheckItem> list = new ArrayList<CheckItem>();
        PageInfo<CheckItem> pages = new PageInfo<CheckItem>(list);
        pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                checkItemMapper.selectAll(name, method);
            }
        });
        return pages;
    }

    public boolean validateCheckItem(CheckItem checkItem) {
        int result = checkItemMapper.validateCheckItem(checkItem);
        return result > 0;
    }

    public void importFile(MultipartFile file) {
        EpicNFSClient client = epicNFSService.getClient("gzjy");
        // 建立远程存放excel模板文件目录
        if (!client.hasRemoteDir("temp")) {
            client.createRemoteDir("temp");
        }
        String fileSuffix = file.getOriginalFilename().endsWith("xlsx") ? ".xlsx" : ".xls";
        //存放在服务器的模板文件是随机生成的，避免重复
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
            List<CheckItem> dataList = new ArrayList<CheckItem>();
            for (int rowNum = 1; rowNum < sheet.getLastRowNum() + 1; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row.getCell(0) == null) {
                    //excel中有下拉框选择列，故要做出判断
                    break;
                }
                CheckItem item = new CheckItem();
                item.setId(ShortUUID.getInstance().generateShortID());
                item.setName(row.getCell(0).toString().trim());
                item.setMethod(row.getCell(1).toString().trim());
                item.setUnit(row.getCell(2).toString().trim());
                item.setStandardValue(row.getCell(3).toString().trim());
                item.setDetectionLimit(row.getCell(4) != null ? row.getCell(4).toString().trim() : "/");
                item.setQuantitationLimit(row.getCell(5) != null ? row.getCell(5).toString().trim() : "/");
                item.setDevice(row.getCell(6).toString().trim());
                item.setDefaultPrice(Double.parseDouble(row.getCell(7).toString().trim()));
                item.setDepartment(row.getCell(8).toString().trim());
                item.setSubpackage(row.getCell(9).toString().trim().substring(0, 1));
                item.setLaw(row.getCell(10).toString().trim());
                item.setCreatedAt(new Date());
                dataList.add(item);
            }
            checkItemMapper.importData(dataList);
//			removeRepeatData();
            wb.close();
        } catch (Exception e) {
            logger.info("文件导入异常:" + e);
            throw new BizException("文件导入异常" + e);
        } finally {
            //删除文件
            try {
                FileOperate.deleteFile(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeRepeatData() {
        List<String> idList = checkItemMapper.selectDistinctIds();
        if (idList != null && idList.size() > 0) {
            checkItemMapper.deleteByIds(idList);
        }
    }
}
