package com.dcbox.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dcbox.api.dao.IProcessDAO;
import com.dcbox.api.vo.ProcessVO;

@Service
public class ProcessServiceImpl implements IProcessService {
	@Autowired
	IProcessDAO processDAO;

	public boolean isProcessDuplicate(ProcessVO processVO) {
        System.out.println("********ProcessServiceImpl[isProcessDuplicate]*********"+processVO.getProcessId());
		return processDAO.isProcessDuplicate(processVO);
	}

	@Override
	public boolean isMatched(ProcessVO processVO) {
	      System.out.println("********ProcessServiceImpl[isMatched]*********"+processVO.getProcessId());
	      return processDAO.isMatched(processVO);
	}

}
