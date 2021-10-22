package com.dcbox.api.service;

import org.springframework.stereotype.Service;

import com.dcbox.api.vo.ProcessVO;

@Service
public interface IProcessService {
	
	public boolean isProcessDuplicate(ProcessVO processVO);
	public boolean isMatched(ProcessVO processVO);

}
