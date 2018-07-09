package com.gzjy.review.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.ShortUUID;
import com.gzjy.review.mapper.ComInforMapper;
import com.gzjy.review.mapper.ComProjectReviewMapper;
import com.gzjy.review.mapper.ComReviewReportMapper;
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
	@Autowired
	private ComReviewReportMapper comReviewReportMapper;
	@Autowired
	private ComProjectReviewMapper comProjectReviewMapper;

    @Override
    public PageInfo<ComInfor> selectByPages(Integer pageNum, Integer pageSize) {

        Map<String ,List<String>> filterMaps = new HashMap<>();

        PageInfo<ComInfor> comInfors = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
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
        comInfor.setUpdatedAt(new Date());
        return comInforMapper.insertSelective(comInfor);
    }

    @Override
    public int updateComInfor(ComInfor comInfor) {
        return comInforMapper.updateByPrimaryKeySelective(comInfor);
    }

    @Override
    @Transactional
    public int deleteCominfors(String id) {
    	//根据企业id删除企业信息
    	int n=comInforMapper.deleteByPrimaryKey(id);
    	//根据企业id删除报告信息
    	comReviewReportMapper.deleteByCompanyId(id);
    	//根据企业id删除企业审核项信息
    	comProjectReviewMapper.deleteByCompanyId(id);
    	
        return n;
    }
    
}
