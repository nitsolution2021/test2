package com.dcbox.api.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.dcbox.api.util.DBConnection;
import com.dcbox.api.vo.ProcessVO;

@Component
public class ProcessDAOImpl implements IProcessDAO {
	@Autowired
	DBConnection dbConnection;

	@Value("${PROCESS_DUPLICATE_CHECK_QUERY}")
	private String processDuplicateCheckQuery;

	    @Autowired
	    private NamedParameterJdbcTemplate jdbcTemplate;

	    public NamedParameterJdbcTemplate getNetezzaNamedTemplate() 
	    {
	        return jdbcTemplate;
	    }

	    public void setNetezzaNamedTemplate(NamedParameterJdbcTemplate netezzaNamedTemplate) 
	    {
	        this.jdbcTemplate = netezzaNamedTemplate;
	    }

	public boolean isProcessDuplicate(ProcessVO processVO) {
		System.out.println("Test jdbcTemplate::"+getNetezzaNamedTemplate() );
		boolean isDuplicate = false;
		try {
			//processDuplicateCheckQuery
		    Map<String, Object> map = new HashMap<String,Object>();
		   // map.put("processId", new Object());
		    List<Map<String,Object>> list =getNetezzaNamedTemplate().queryForList(processDuplicateCheckQuery, map);
		    if(null!=list && list.size()>0) {
		    	isDuplicate=true;
		    }else {
		    	addRecords(processVO);
		    }
		    System.out.println("Database-row count:"+list.size()+", isDuplicate:"+isDuplicate);
		
		} catch (Exception e) {
			System.out.println("Exception ProcessDAOImpl[isProcessDuplicate]" + e.getMessage());
			e.printStackTrace();
		} finally {

		}

		return isDuplicate;
	}
	
	public boolean addRecords(ProcessVO processVO) {
		boolean isDuplicate = false;
		try {
			//processDuplicateCheckQuery
		    Map<String, Object> map = new HashMap<String,Object>();
		   // map.put("processId", new Object());
		    List<Map<String,Object>> list =null;//getNetezzaNamedTemplate().queryForList(processDuplicateCheckQuery, map);
		    if(null!=list && list.size()>0) {
		    	isDuplicate=true;
		    }
		
		} catch (Exception e) {
			System.out.println("Exception ProcessDAOImpl[addRecords]" + e.getMessage());
			e.printStackTrace();
		} finally {

		}

		return isDuplicate;
	}
	
	@Override
	public boolean isMatched(ProcessVO processVO) {
		return false;
	}

}
