package com.gzjy.contract.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.common.Response;
import com.gzjy.contract.mapper.ContractMapper;
import com.gzjy.contract.model.Contract;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class ContractController {
	@Autowired
	ContractMapper contractMapper;

	@RequestMapping(value = "/contract/{id}", method = RequestMethod.GET)
	public Response getContractById(@PathVariable String id) {
		Contract contract = contractMapper.selectByPrimaryKey(id);
		return Response.success(contract);
	}
	
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.DELETE)	
	public Response delContractById(@PathVariable String id) {
		Contract contract = contractMapper.selectByPrimaryKey(id);
		return Response.success("success");
	}
	
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.PUT)	
	public String updateContract(@PathVariable String id) {
		Contract contract = contractMapper.selectByPrimaryKey("111");
		return contract.toString();
	}
	
	/*@RequestMapping(value = "/contracts", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getPageList() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ArrayList<Contract> list = contractMapper.getPageList("111", 1, 10);
		result.put("total", list.size());
		result.put("list", list);		
		return null;
	}*/
}