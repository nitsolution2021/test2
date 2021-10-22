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
public class ProcessDuplicateCheckController {
	@Autowired
	IProcessService processService;

	
	@RequestMapping(method = RequestMethod.GET, value="/process/duplicate/{processId}")
	@ResponseBody
	public boolean isProcessDuplicate(@PathVariable String processId) {
		ProcessVO processVO = new ProcessVO();
		processVO.setProcessId(processId);
		processVO.getProcessId();
		System.out.println("*********TEST ProcessDuplicateCheckController*******processID:*******"+processVO.getProcessId());
		return processService.isProcessDuplicate(processVO);
	}

}
