/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月7日
 */
package com.gzjy.samplemanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.samplemanager.mapper.SampleExchangeMapper;
import com.gzjy.samplemanager.mapper.SampleManagerMapper;
import com.gzjy.samplemanager.model.SampleExchange;
import com.gzjy.samplemanager.model.SampleManager;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月7日
 */
@Service
public class SampleManagerService {
    @Autowired
    private SampleManagerMapper sampleManagerMapper;
    @Autowired
    private SampleExchangeMapper sampleExchangeMapper;
    
public List<SampleManager>autoAddSameReportIdSamples(List<SampleManager> records) {
    String reportId;
    String sampleId="";
    int j=0;
    if(records.size()>0) {
        reportId=records.get(0).getReportId();
        for(int i=0;i<records.size();i++) {
            if(reportId.equals(records.get(i).getReportId()))
                 j=i+1;
                 sampleId = reportId+":"+j;
                 while(sampleManagerMapper.selectByPrimaryKey(sampleId)!=null) {
                     j++;
                     sampleId=reportId+":"+j;
                 }                            
                records.get(i).setSampleId(sampleId);
                sampleManagerMapper.insertSelective(records.get(i));
            
        }
    }
    else {
        throw new BizException("样品信息数量为0");
    }
    
    return records;
}

public int deleteSampleManager(List<String> records) {
   int i=0;
   for(String s:records) {
       i+=sampleManagerMapper.deleteByPrimaryKey(s);
   }                
    return i;
}

public SampleManager updateSampleManager(SampleManager record) {
   
      sampleManagerMapper.updateByPrimaryKeySelective(record);
                   
     return record;
 }

public PageInfo<SampleManager> selectSampleManagerList(Integer pageNum, Integer pageSize,
        String order, Map<String, Object> filter) {
    List<SampleManager> records = new ArrayList<SampleManager>();
    PageInfo<SampleManager> pages = new PageInfo<SampleManager>(records);
    pages = PageHelper.startPage(pageNum, pageSize)
            .doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    sampleManagerMapper.select(filter, order);
                }
            });
    return pages;
}



public List<SampleExchange>addSampleExchange(List<SampleExchange> records) {
  
        for(int i=0;i<records.size();i++) {
            records.get(i).setId(UUID.random());
            sampleExchangeMapper.insertSelective(records.get(i));
            
        }
 
    return records;
}

public int deleteSampleExchange(List<String> records) {
   int i=0;
   for(String s:records) {
       i+=sampleExchangeMapper.deleteByPrimaryKey(s);
   }                
    return i;
}

public SampleExchange updateSampleExchange(SampleExchange record) {
   
    sampleExchangeMapper.updateByPrimaryKeySelective(record);
                   
     return record;
 }



public PageInfo<SampleExchange> selectSampleExchangeList(Integer pageNum, Integer pageSize,
        String order, Map<String, Object> filter) {
    List<SampleExchange> records = new ArrayList<SampleExchange>();
    PageInfo<SampleExchange> pages = new PageInfo<SampleExchange>(records);
    pages = PageHelper.startPage(pageNum, pageSize)
            .doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    sampleExchangeMapper.select(filter, order);
                }
            });
    return pages;
}







}
