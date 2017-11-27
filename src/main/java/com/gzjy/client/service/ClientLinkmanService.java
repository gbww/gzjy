/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年11月26日
 */
package com.gzjy.client.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzjy.client.mapper.ClientLinkmanMapper;
import com.gzjy.client.model.ClientLinkman;

@Service
public class ClientLinkmanService {
    @Autowired
    private ClientLinkmanMapper linkmanMapper;
    private static Logger logger = LoggerFactory.getLogger(ClientLinkmanService.class);
    
    @Transactional
    public ClientLinkman add(ClientLinkman record) {
        try {
            linkmanMapper.insert(record);
            
        } catch (Exception e) {
            logger.error("插入联系人失败");
            e.printStackTrace();
        }
        return record;
    }
    @Transactional
    public int delete(String id) {
        int result=0;
        try {
            result = linkmanMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除联系人失败");
            e.printStackTrace();
        }
        return result;
    }
    @Transactional
    public ClientLinkman update(ClientLinkman record) {
        try {
            linkmanMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新联系人失败");
            e.printStackTrace();
        }
        return record;
    }
    
    public List<ClientLinkman> selectByclientNum(Integer clientNum,String order, Map<String, Object> filter) {
      
              return linkmanMapper.selectByClientNum(clientNum,filter, order);
    }

}
