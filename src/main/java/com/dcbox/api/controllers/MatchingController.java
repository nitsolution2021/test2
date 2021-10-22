package com.dcbox.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcbox.api.service.IProcessService;
import com.dcbox.api.vo.ProcessVO;

@Controller
public class MatchingController {
	@Autowired
	IProcessService processService;

	
	@RequestMapping(method = RequestMethod.GET, value="/process/matching/{processId}")
	@ResponseBody
	public boolean isMatched(@PathVariable String processId) {
		ProcessVO processVO = new ProcessVO();
		processVO.setProcessId(processId);
		processVO.getProcessId();
		System.out.println("*********TEST MatchingController*******processID:*******"+processVO.getProcessId());
		return processService.isMatched(processVO);
	}

}
