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

import com.welab.hsj.alldemo.ConstantInfo;

/**
 * 连线
 * 点击连线配置流程变量条件：main config :Condition ${message=='不重要'}
 * @author hsj
 *
 */
public class Sequence {
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	
	private String proKey = "sequenceFlow";

	/**
	 * 部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("sequenceProcesees")
		  .addClasspathResource("processes/hsj/sequence.bpmn")
		  .addClasspathResource("processes/hsj/sequence.png")
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
	}
	
	
	/**
	 * 流程实例
	 */
	@Test
	public void startProInstance(){
		ProcessInstance processInstance = pe.getRuntimeService()
				.startProcessInstanceByKey(proKey);
		
		System.out.println("流程实例id="+processInstance.getId());
		System.out.println("流程定义id="+processInstance.getProcessDefinitionId());
	}
	
	
	/**
	 * 查找个人的当前任务
	 * 
	 */
	@Test
	public void findMyPersonTask(){
		List<Task> lt = pe.getTaskService()
		   .createTaskQuery()
		   .taskAssignee(ConstantInfo.User.tianqi)
		   .list();
		if(lt != null && lt.size() > 0){
			for(Task at : lt){
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
		//完成任务的同时设置流程变量，决定下一步的连线，对应sequenceFlow.bpmn文件的${message='不重要'}或者${message='重要'}
		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("message", "重要");
		pe.getTaskService().complete("67503"/*,variables*/);
		
		System.out.println("完成任务");
	}
}
