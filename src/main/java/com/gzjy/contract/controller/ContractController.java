package com.gzjy.contract.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.contract.mapper.ContractMapper;
import com.gzjy.contract.model.Contract;

@RestController
@RequestMapping({ "/v1/ahgz" })
public class ContractController {
	@Autowired
	ContractMapper contractMapper;

	@RequestMapping(value = "/contract/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getContractById(@PathVariable(name="id") String id) {
		Contract contract = contractMapper.getById("111");
		return contract.toString();
	}
	
	
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delContractById(@PathVariable(name="id") String id) {
		Contract contract = contractMapper.getById("111");
		return contract.toString();
	}
	
	@RequestMapping(value = "/contract/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String updateContract(@PathVariable(name="id") String id) {
		Contract contract = contractMapper.getById("111");
		return contract.toString();
	}
	
	@RequestMapping(value = "/contracts", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject getPageList() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ArrayList<Contract> list = contractMapper.getPageList("111", 1, 10);
		result.put("total", list.size());
		result.put("list", list);		
		return null;
	}
}