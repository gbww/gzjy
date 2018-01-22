package com.gzjy.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.contract.mapper.SampleMapper;
import com.gzjy.contract.model.Sample;
import com.gzjy.contract.service.SampleService;

@Service
public class SampleServiceImpl implements SampleService {
	
	@Autowired
	private SampleMapper sampleMapper;
	
	public int insert(Sample sample) {
		return sampleMapper.insert(sample);
	}
	public void updateSampleById(Sample sample) {
		sampleMapper.updateByPrimaryKeySelective(sample);		
	}
	
	public int deleteByIds(List<String> idList) {
		return sampleMapper.deleteByIds(idList);		
	}
	
	public int deleteByContractId(String contractId) {
		return sampleMapper.deleteByContractId(contractId);
	}

}
