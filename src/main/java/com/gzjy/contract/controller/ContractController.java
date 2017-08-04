package com.gzjy.contract.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.contract.model.Contract;
import com.gzjy.contract.service.ContractService;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class ContractController {
	
	@Autowired
	ContractService contractService;
	
	/** 
	 * 通过ID获取合同信息
	 * @param id
	 * @return JSON对象(包含查询到的Contract实体信息)
	 */
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.GET)
	public Response getContractById(@PathVariable String id) {
		Contract contract = contractService.selectByPrimaryKey(id);
		return Response.success(contract);
	}
	
	/**
	 * 通过ID删除合同信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.DELETE)
	public Response delContractById(@PathVariable String id) {
		int result = contractService.deleteByPrimaryKey(id);
		return Response.success("success");
	}
	
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.PUT)
	public String updateContract(@PathVariable String id) {
		Contract contract = contractService.selectByPrimaryKey("111");
		return contract.toString();
	}	
	
}