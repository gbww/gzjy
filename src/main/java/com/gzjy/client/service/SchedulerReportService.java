/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
package com.gzjy.client.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzjy.client.mapper.SchedulerReportMapper;
import com.gzjy.client.model.ClientScheduler;
import com.gzjy.client.model.SchedulerReport;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
@Service
public class SchedulerReportService {
    
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientSchedulerService clientSchedulerService;
    @Autowired
    private SchedulerReportMapper schedulerReportMapper;
    private static Logger logger=LoggerFactory.getLogger(SchedulerReportService.class);
    
    @Transactional
    public SchedulerReport add(SchedulerReport record) {
        Boolean flag=clientSchedulerService.checkScheduler(record.getClientSchedulerId());
        if(!flag) {
            throw new BizException("日程信息不存在");
        }
        try {
            record.setId(UUID.random());
            record.setCreatedAt(new Date());
            schedulerReportMapper.insertSelective(record);
            
        } catch (Exception e) {
            logger.error("插入日程报告失败");
            e.printStackTrace();
        }
        return record;
    }
    
    @Transactional
    public int delete(String id) {
        int result=0;
        try {
            result = schedulerReportMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除日程报告失败");
            e.printStackTrace();
        }
        return result;
    }
    
    @Transactional
    public SchedulerReport update(SchedulerReport record) {
        Boolean flag=clientSchedulerService.checkScheduler(record.getClientSchedulerId());
        if(!flag) {
            throw new BizException("日程信息不存在");
        }
        try {
            record.setCreatedAt(new Date());
            schedulerReportMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新日程报告失败");
            e.printStackTrace();
        }
        return record;
    }
    
    public List<SchedulerReport> selectBySchedulerId(String schedulerId,String order, Map<String, Object> filter) {
        
        return schedulerReportMapper.selectBySchedulerId(schedulerId,filter, order);
}
}
