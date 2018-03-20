package com.welab.hsj;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * 脱离springboot学习创建表
 * @author hsj
 * 
 * 摘要: 1.Spring Boot基于“习惯优于配置”的原则，为大量第三方的库提供自动配置的功能。
 *      2.由Spring专家Josh Long主导开发的spring-boot-starters为我们在Spring Boot下使用Activiti做了自动配置。
 *      3.其中主要自动配置包括： * 自动创建Activiti ProcessEngine的Bean；
 *      4.所有的Activiti Service都自动注册为Spring的Bean； 
 *      5. 创建一个Spring Job Executor；
 *      6. 自动扫描并部署位于src/main/resources/processes目录下的流程处理文件【只会第一子集，下面嵌套不会部署】。
 *
 *   act_ru_activiti表表示每一个task就是一个activiti，可以理解成activiti包含task，包含开始，结束，排他网关，并行网关等
 *
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
