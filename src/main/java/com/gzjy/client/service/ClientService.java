/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月24日
 */
package com.gzjy.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.client.mapper.GzClientMapper;
import com.gzjy.client.model.ClientScheduler;
import com.gzjy.client.model.GzClient;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月24日
 */
@Service
public class ClientService {
    private static Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private GzClientMapper clientMapper;

    @Autowired
    ClientSchedulerService clientSchedulerService;

    @Transactional
    public GzClient add(GzClient record) {
        record.setCreatedAt(new Date());
        try {
            clientMapper.insertSelective(record);

        } catch (Exception e) {
            logger.error("插入客户失败");
            e.printStackTrace();
        }
        return record;
    }

    @Transactional
    public int delete(Integer id) {
        int result = 0;
        Map<String, Object> filter = new HashMap<String, Object>();
        try {
            List<ClientScheduler> schedulers = clientSchedulerService
                    .selectByclientNum(id, "created_at", filter);
            for (ClientScheduler scheduler : schedulers) {
                clientSchedulerService.delete(scheduler.getId());
            }

            result = clientMapper.deleteLogicByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除客户失败");
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public GzClient update(GzClient record) {
        record.setCreatedAt(new Date());
        try {
            clientMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新客户失败");
            e.printStackTrace();
        }
        return record;
    }

    public PageInfo<GzClient> select(Integer pageNum, Integer pageSize,
            String order, Map<String, Object> filter) {

        List<GzClient> records = new ArrayList<GzClient>();
        PageInfo<GzClient> pages = new PageInfo<GzClient>(records);
        pages = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(new ISelect() {

                    @Override
                    public void doSelect() {
                        clientMapper.selectAll(filter, order);

                    }
                });
        return pages;
    }

    public Boolean checkClient(Integer clientNum) {
        GzClient client = clientMapper.selectByPrimaryKey(clientNum);
        if (client != null)
            return true;
        else
            return false;
    }

}
