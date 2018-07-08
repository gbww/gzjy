package com.gzjy.review.service;

import com.github.pagehelper.PageInfo;
import com.gzjy.review.modle.ComInfor;

import java.util.List;

/**
 * @Description:
 * @Auther: wuyongfa
 * @Date: 2018/6/3009:35
 */
public interface ComInforService {
    /**
     * 分页查询企业信息
     * @param pageNum 当前页数
     * @param pageSize 每页数据条数
     * @return
     */
    PageInfo<ComInfor> selectByPages(Integer pageNum, Integer pageSize);

    /**
     * 根据主键查询企业信息
     * @param id
     * @return
     */
    ComInfor selectByPrimaryKey(String id);

    /**
     * 添加企业信息
     * @param comInfor
     * @return
     */
    int addComInfor(ComInfor comInfor);

    /**
     * 更新企业信息
     * @param comInfor
     * @return
     */
    int updateComInfor(ComInfor comInfor);

    /**
     * 批量删除企业信息
     * @param ids
     * @return
     */
    int deleteCominfors(String ids);
}
