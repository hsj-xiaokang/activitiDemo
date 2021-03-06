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
 * 个人任务-直接分配-写死
 * @author hsj
 *
 */
public class PersonTask1 {
	
    private String proKey = "personTask1";
	
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	
	
	/**
	 * 部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("个人任务1-直接写死")
		  .addClasspathResource("processes/hsj/PersonTask1.bpmn")
		  .addClasspathResource("processes/hsj/PersonTask1.png")
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
	 * 相
	 */
	@Test
	public void findMyPersonTask(){
		List<Task> lt = pe.getTaskService()
		   .createTaskQuery()
		   .taskAssignee(ConstantInfo.User.zhangsanfeng)
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
		variables.put("pass", "pass ok!");
		pe.getTaskService().complete("117504",variables);
		
		System.out.println("完成任务id="+117504);
	}
	

}
