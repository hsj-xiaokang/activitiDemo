package com.welab.hsj.alldemo2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 并行网关
 * 并行网关不会解析条件！
 * @author hsj
 *
 */
public class ParallelGateWay {
   ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
   private String proKey = "parallelGateWay";
   
   /**
	 * 部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("parallelGateWay")
		  .addClasspathResource("processes/hsj/ParallelGateWay.bpmn")
		  .addClasspathResource("processes/hsj/ParallelGateWay.png")
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
	}
	
	
	/**
	 * 流程实例
	 * 
	 * act_ru_excution 三个：
	 *  95001	1	95001		NULL	parallelGateWay:1:92504		parallelgateway1	0	0	1	0	1	2			
		95004	1	95001		95001	parallelGateWay:1:92504		usertask1			1	1	0	0	1	7			
		95005	1	95001		95001	parallelGateWay:1:92504		usertask2			1	1	0	0	1	7			
	 */
	@Test
	public void startProInstance(){
		ProcessInstance processInstance = pe.getRuntimeService()
				.startProcessInstanceByKey(proKey);
		
		System.out.println("流程实例id="+processInstance.getId());
		System.out.println("流程定义id="+processInstance.getProcessDefinitionId());
	}
	
	
	/**
	 * 商家  买家都可以查询
	 */
	@Test
	public void findMyPersonTask(){
		List<Task> lt = pe.getTaskService()
		   .createTaskQuery()
		   .taskAssignee("买家")
		   .list();
		if(lt != null && lt.size() > 0){
			for(Task at : lt){
				System.out.println("*********************************");
				System.out.println("任务id="+at.getId());
				System.out.println("任务名称="+at.getName());
				System.out.println("任务创建时间="+at.getCreateTime());
				System.out.println("任务办理人="+at.getAssignee());
				System.out.println("任务创建时间="+at.getCreateTime());
				System.out.println("流程实例id="+at.getProcessInstanceId());
				System.out.println("执行对象id="+at.getExecutionId());
				//会使用最新的一次的流程定义——流程定义一样的话就进行升级version
				System.out.println("流程定义id="+at.getProcessDefinitionId());
			}
		}
	}
	

	/**
	 * 完成任务
	 */
	@Test
	public void completeCurrentTask(){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("goods", "收货");
		pe.getTaskService().complete("100003",variables);
		
		System.out.println("完成任务id="+100003);
	}
}
