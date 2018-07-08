package com.gzjy.review.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.ShortUUID;
import com.gzjy.review.mapper.ComInforMapper;
import com.gzjy.review.modle.ComInfor;
import com.gzjy.review.service.ComInforService;

/**
 * @Description:
 * @Auther: wuyongfa
 * @Date: 2018/6/3009:33
 */
@Service
public class ComInforServiceImpl implements ComInforService {

    @Autowired
    private ComInforMapper comInforMapper;

    @Override
    public PageInfo<ComInfor> selectByPages(Integer pageCount, Integer pageSize) {

        Map<String ,List<String>> filterMaps = new HashMap<>();

        PageInfo<ComInfor> comInfors = PageHelper.startPage(pageCount, pageSize).doSelectPageInfo(
            () -> {
                comInforMapper.selectByFilters(filterMaps);
            }
        );

        return comInfors;
    }

    @Override
    public ComInfor selectByPrimaryKey(String id) {
        return comInforMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addComInfor(ComInfor comInfor) {

        comInfor.setId(ShortUUID.getInstance().generateShortID());

        return comInforMapper.insertSelective(comInfor);
    }

    @Override
    public int updateComInfor(ComInfor comInfor) {
        return comInforMapper.updateByPrimaryKeySelective(comInfor);
    }

    @Override
    public int deleteCominfors(List<String> ids) {
        return comInforMapper.deleteByKeys(ids);
    }
    
}
