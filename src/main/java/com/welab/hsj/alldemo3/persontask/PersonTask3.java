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
 * 含移交任务
 * 使用类指定办理人-实现org.activiti.engine.delegate.TaskListener.TaskListener
 * @author hsj
 *
 */
public class PersonTask3 {

	
    private String proKey = "PersonTask3";
	
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("个人任务3-使用类指定办理人-实现org.activiti.engine.delegate.TaskListener.TaskListener")
		  .addClasspathResource("processes/hsj/PersonTask3.bpmn")
		  .addClasspathResource("processes/hsj/PersonTask3.png")
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
	}
	
	
	/**
	 * 流程实例-启动流程实例的时候设置变量userId
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
		   .taskAssignee(ConstantInfo.User.mahuateng)
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
		variables.put("message", "主席说的都对！");
		pe.getTaskService().complete("162503",variables);
		
		System.out.println("完成任务id="+162503);
	}
	
	
	/**
	 * 移交
	 * 可以分配个人任务从一个到另外一个
	 */
	@Test
	public void move2otherTask(){
		pe.getTaskService().setAssignee("157504", ConstantInfo.User.xidada);
		
		System.out.println("完成任务移交id="+157504);
	}
	

}




