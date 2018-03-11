package com.welab.hsj;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * 脱离springboot学习创建表
 * @author hsj
 *
 */
public class TestActiviti {
   
	/**
	 * 使用代码初始化表
	 * 创建23张表
	 * 实际上发现是25张表->5.19.0.2-25张表
	 */
	@Test
	public void createTableWithCode(){
		ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		pec.setJdbcDriver("com.mysql.jdbc.Driver");
		pec.setJdbcUrl("jdbc:mysql://localhost:3306/activitidemo?useUnicode=true&characterEncoding=utf8");
		pec.setJdbcUsername("root");
		pec.setJdbcPassword("HSJissmart");
		
		pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		//创建引擎对象
		ProcessEngine pe= pec.buildProcessEngine();
		
		System.out.println("流程引擎对象="+pe.toString());
	}
	
	/** 
	 * 使用xml配置 简化 
	 */  
	@Test  
	public void CreateTableWithXml(){  
	    // 引擎配置  
	    ProcessEngineConfiguration pec=ProcessEngineConfiguration.
	    		createProcessEngineConfigurationFromResource("activiti.cfg.xml");  
	    // 获取流程引擎对象  
	    ProcessEngine pe=pec.buildProcessEngine();  
	    System.out.println("流程引擎对象="+pe.toString());
	} 
}
