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

import com.gzjy.client.mapper.ClientSchedulerMapper;
import com.gzjy.client.mapper.SchedulerReportMapper;
import com.gzjy.client.model.ClientScheduler;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
@Service
public class ClientSchedulerService {
    @Autowired
    private ClientSchedulerMapper schedulerMapper;

    @Autowired
    private SchedulerReportMapper schedulerReportMapper;

    @Autowired
    private ClientService clientService;

    private static Logger logger = LoggerFactory
            .getLogger(ClientSchedulerService.class);

    @Transactional
    public ClientScheduler add(ClientScheduler record) {
        Boolean flag = clientService.checkClient(record.getClientNum());
        if (!flag) {
            throw new BizException("客户信息不存在");
        }
        try {
            record.setId(UUID.random());
            record.setCreatedAt(new Date());
            schedulerMapper.insertSelective(record);

        } catch (Exception e) {
            logger.error("插入日程失败");
            e.printStackTrace();
        }
        return record;
    }

    @Transactional
    public int delete(String id) {

        int result = 0;
        try {
            schedulerReportMapper.deleteBySchedulerId(id);
            result = schedulerMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除日程失败");
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public ClientScheduler update(ClientScheduler record) {
        Boolean flag = clientService.checkClient(record.getClientNum());
        if (!flag) {
            throw new BizException("客户信息不存在");
        }
        try {
            record.setCreatedAt(new Date());
            schedulerMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新日程失败");
            e.printStackTrace();
        }
        return record;
    }

    public Boolean checkScheduler(String schedulerId) {
        ClientScheduler record = schedulerMapper
                .selectByPrimaryKey(schedulerId);
        if (record != null)
            return true;
        else
            return false;
    }

    public List<ClientScheduler> selectByclientNum(Integer clientNum,
            String order, Map<String, Object> filter) {

        return schedulerMapper.selectByClientNum(clientNum, filter, order);
    }

}
