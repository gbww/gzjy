/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月2日
 */
package com.gzjy.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.UUID;
import com.gzjy.user.mapper.UserTypeMapper;
import com.gzjy.user.model.UserType;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月2日
 */
@Service
public class UserTypeServiceImpl {

    private static Logger logger = LoggerFactory
            .getLogger(UserTypeServiceImpl.class);

    @Autowired
    private UserTypeMapper userTypeMapper;
    
    @Autowired
    private UserService UserClient;

    @Transactional
    public UserType add(UserType record) {
        record.setId(UUID.random());
        record.setCreatedAt(new Date());
        if(!UserClient.nameExist(record.getName())) {
            throw new BizException("用户表中无此用户的名字");
        }
        if(UserClient.getUser(record.getUserId())==null){
            throw new BizException("用户ID不存在");
        }
        if(!UserClient.getUser(record.getUserId()).getName().equals(record.getName())) {
            throw new BizException("用户ID与用户中文名称关系对应不上");
        }
        
        try {
            userTypeMapper.insertSelective(record);

        } catch (Exception e) {
            logger.error("插入失败");
            e.printStackTrace();
        }
        return record;
    }

    @Transactional
    public int delete(String id) {
        int result = 0;
        try {

            result = userTypeMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除失败");
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public UserType update(UserType record) {
        record.setCreatedAt(new Date());
        if(!UserClient.nameExist(record.getName())) {
            throw new BizException("用户表中无此用户的名字");
        }
        if(UserClient.getUser(record.getUserId())==null){
            throw new BizException("用户ID不存在");
        }
        if(!UserClient.getUser(record.getUserId()).getName().equals(record.getName())) {
            throw new BizException("用户ID与用户中文名称关系对应不上");
        }
        
        
        try {
            userTypeMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新失败");
            e.printStackTrace();
        }
        return record;
    }

    public PageInfo<UserType> select(Integer pageNum, Integer pageSize,
            String order, Map<String, Object> filter) {

        List<UserType> records = new ArrayList<UserType>();
        PageInfo<UserType> pages = new PageInfo<UserType>(records);
        pages = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(new ISelect() {

                    @Override
                    public void doSelect() {
                        userTypeMapper.selectAll(filter, order);

                    }
                });
        return pages;
    }

    public List<UserType> select(Map<String, Object> filter,String order ) {

        List<UserType> records = new ArrayList<UserType>();

        records = userTypeMapper.selectAll(filter, order);
   
        return records;

    }

}
