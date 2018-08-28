package com.gzjy.contract.model;

import java.util.List;

public class ContractSample{
	private Contract contract;
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	private List<Sample> sampleList;

	public List<Sample> getSampleList() {
		return sampleList;
	}

	public void setSampleList(List<Sample> sampleList) {
		this.sampleList = sampleList;
	}
	
}
