package com.gzjy.contract.model;

import java.util.ArrayList;

public class ContractProcess {
	/*合同里面多个审批人*/
	private ArrayList<String> approveUsers;
	/*修改合同人*/
	private String updateContractUser;

	private String contractId;
	
	public ArrayList<String> getApproveUsers() {
		return approveUsers;
	}

	public void setApproveUsers(ArrayList<String> approveUsers) {
		this.approveUsers = approveUsers;
	}

	public String getUpdateContractUser() {
		return updateContractUser;
	}

	public void setUpdateContractUser(String updateContractUser) {
		this.updateContractUser = updateContractUser;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	

}
