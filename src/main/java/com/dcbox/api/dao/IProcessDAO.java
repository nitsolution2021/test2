package com.dcbox.api.dao;

import org.springframework.stereotype.Component;

import com.dcbox.api.vo.ProcessVO;
@Component
public interface IProcessDAO {
	
	public boolean isProcessDuplicate(ProcessVO processVO);
	public boolean isMatched(ProcessVO processVO);

}
