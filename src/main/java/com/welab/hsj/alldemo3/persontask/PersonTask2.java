package com.welab.hsj.alldemo3.persontask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.welab.hsj.alldemo.ConstantInfo;

/**
 * 流程变量方式${userId}
 * @author hsj
 *
 */
public class PersonTask2 {

    private String proKey = "personTask2";
	
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("个人任务2-流程变量")
		  .addClasspathResource("processes/hsj/PersonTask2.bpmn")
		  .addClasspathResource("processes/hsj/PersonTask2.png")
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
	}
	
	
	/**
	 * 流程实例-启动流程实例的时候设置变量userId
	 */
	@Test
	public void startProInstance(){
		//流程变量指定办理人-${userId}
		Map<String,Object>  variable = new HashMap<>();
		variable.put("userId", ConstantInfo.User.zhoujielun);
		ProcessInstance processInstance = pe.getRuntimeService()
				.startProcessInstanceByKey(proKey,variable);
		
		System.out.println("流程实例id="+processInstance.getId());
		System.out.println("流程定义id="+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查找个人的当前任务
	 * 相
	 */
	@Test
	public void findMyPersonTask(){
		List<Task> lt = pe.getTaskService()
		   .createTaskQuery()
		   .taskAssignee(ConstantInfo.User.zhoujielun)
//		   .taskName("财务")
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
		//完成任务的同时设置流程变量，决定下一步的连线，对应ExclusiveGateWay.bpmn文件的${money>100}等
		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("message", "我要去昆明开演唱会!");
		pe.getTaskService().complete("125005",variables);
		
		System.out.println("完成任务id="+125005);
	}
	
	
}
