package com.welab.hsj.helloword;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class HelloWord {
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("helloword入门程序")
		  .addClasspathResource("processes/hsj/helloword.bpmn")
		  .addClasspathResource("processes/hsj/helloword.png")
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
	}
}
