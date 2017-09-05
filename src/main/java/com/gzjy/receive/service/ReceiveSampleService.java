/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
package com.gzjy.receive.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.gzjy.receive.mapper.ReceiveSampleItemMapper;
import com.gzjy.receive.mapper.ReceiveSampleMapper;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.user.model.User;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年9月3日
 */
@Service
public class ReceiveSampleService {
    private static Logger logger = LoggerFactory
            .getLogger(ReceiveSampleService.class);

    @Autowired
    private ReceiveSampleMapper receiveSampleMapper;
    @Autowired
    private ReceiveSampleItemMapper receiveSampleItemMapper;

    @Transactional
    public Boolean addReceiveSample(ReceiveSample record) {
        if (StringUtils.isBlank(record.getReceiveSampleId())) {
            return false;
        } else {
            receiveSampleMapper.insert(record);
        }

        return true;
    }

    @Transactional
    public Boolean addReceiveSampleItems(List<ReceiveSampleItem> items) {
        if (items.size() == 0) {
            return false;
        }

        for (ReceiveSampleItem item : items) {
            if (StringUtils.isBlank(item.getName())) {
                continue;
            }
            if (StringUtils.isBlank(item.getId())) {
                item.setId(UUID.random());
                receiveSampleItemMapper.insert(item);
            } else {
                ReceiveSampleItem exitItem = receiveSampleItemMapper
                        .selectByPrimaryKey(item.getId());
                if (exitItem != null) {
                    receiveSampleItemMapper.updateByPrimaryKey(item);
                } else {
                    System.out.println("接样单中不存在此检验项ID");
                    logger.error("接样单中不存在此检验项ID");
                }
            }

        }
        return true;
    }

    @Transactional
    public Boolean deleteReceiveSampleItems(List<String> itemIds) {

        for (String item : itemIds) {

            receiveSampleItemMapper.deleteByPrimaryKey(item);
        }
        return true;
    }

    @Transactional
    public int delete(String recordId) {
        int i = 1;
        try {
            receiveSampleMapper.deleteByPrimaryKey(recordId);
            receiveSampleItemMapper.deleteByReceiveSampleId(recordId);
        } catch (Exception e) {
            i = 0;
            e.printStackTrace();
            throw new BizException("删除失败");
        }
        return i;
    }

    @Transactional
    public ReceiveSample updateReceiveSample(ReceiveSample record) {
        ReceiveSample exitrecord = receiveSampleMapper
                .selectByPrimaryKey(record.getReceiveSampleId());
        if (exitrecord != null) {
            receiveSampleMapper.updateByPrimaryKeySelective(record);
        }

        return record;
    }

    @Transactional
    public PageInfo<ReceiveSample> select(Integer pageNum, Integer pageSize,
            String order, Map<String, Object> filter) {

        List<ReceiveSample> list = new ArrayList<ReceiveSample>();
        PageInfo<ReceiveSample> pages = new PageInfo<ReceiveSample>(list);
        ;
        pages = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(new ISelect() {
                    @Override
                    public void doSelect() {
                        receiveSampleMapper.selectAll(filter, order);
                    }
                });
        return pages;
    }

    public ReceiveSample getReceiveSample(String id) {

        ReceiveSample record = receiveSampleMapper.selectByPrimaryKey(id);
        if (record != null) {
            return record;
        } else {
            throw new BizException("查询失败，id输入有误");
        }

    }
    public List<ReceiveSampleItem> getItemsByReceiveSampleId(String ReceiveSampleId) {
        if(StringUtils.isBlank(ReceiveSampleId)) {
            throw new BizException("接样ID参数是空值");
        }
        List<ReceiveSampleItem> record = receiveSampleItemMapper.selectByReceiveSampleId(ReceiveSampleId);
        return record;

    }
    public ReceiveSampleItem getItem(String itemId) {

        ReceiveSampleItem record = receiveSampleItemMapper.selectByPrimaryKey(itemId);
        if (record != null) {
            return record;
        } else {
            throw new BizException("查询失败，id输入有误");
        }

    }

}
