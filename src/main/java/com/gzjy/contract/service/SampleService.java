package com.gzjy.contract.service;

import java.util.List;

import com.gzjy.contract.model.Sample;

public interface SampleService {
	public int insert(Sample sample);
	
	public void updateSampleById(Sample sample);
	
	int deleteByContractId(String contractId);
	
	int deleteByIds(List<String> idList);
}
