/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月2日
 */
package com.gzjy.unit.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.common.exception.BizException;
import com.gzjy.unit.mapper.GzUnitMapper;
import com.gzjy.unit.model.GzUnit;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月2日
 */
@Service
public class GzUnitService {
    @Autowired
    private GzUnitMapper unitMapper;
    
    public Boolean add(GzUnit record) {
        if(StringUtils.isBlank(record.getUnitName())) {
            throw new BizException("单位名称为空");
        }
        if(isExist(record.getUnitName())) {
            throw new BizException("存在相同的单位名称");
        }
        unitMapper.insertSelective(record);
        
        return true;
    }
    
    
    public Boolean isExist(String unitName) {       
       return  unitMapper.selectByPrimaryKey(unitName) == null ? false : true;
             
    }
    
    public Boolean delete (String unitName) {
        unitMapper.deleteByPrimaryKey(unitName);
        return true;
    }
    public Boolean update(GzUnit record) {
        if(StringUtils.isBlank(record.getUnitName())) {
            throw new BizException("单位名称为空");
        }
        if(isExist(record.getUnitName())) {
            throw new BizException("单位名称不存在");
        }
        unitMapper.updateByPrimaryKeySelective(record);
        return true;
    }
    
    public List<GzUnit>findAll(Map<String, Object> filter,String order){
        if(StringUtils.isBlank(order))
            order="unit_name desc";
        return unitMapper.selectAll(filter, order);     
    }
}
