package com.welab.hsj.alldemo2;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 演示receivetask
 * @author hsj
 *
 */
public class ReceiveTask {
	   ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	   private String proKey = "receiveTask";
	   
		/**
		 * 部署
		 */
		@Test
		public void deployementProcessDifined(){
			Deployment deployment = pe.getRepositoryService()
			  .createDeployment()
			  .name("接受活动任务")
			  .addClasspathResource("processes/hsj/ReceiveTask.bpmn")
			  .addClasspathResource("processes/hsj/ReceiveTask.png")
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
		 * 一步一步的走
		 */
		@Test
		public void step2step(){
			/**
			 *  流程实例id=110001
				流程定义id=receiveTask:1:107504
			 */
			
			//查询执行对象的id
			Execution excution1 = pe.getRuntimeService().createExecutionQuery().processInstanceId("110001")
			    .activityId("receivetask1")//当前活动id
			    .singleResult();
			
			//使用流程变量设置当前的销售额，传递业务参数
			pe.getRuntimeService()
			   .setVariable(excution1.getId(), "汇总当日销售额", 2000);
			
			//向后执行一步，如果流程在等待状态，使得流程向后执行
			pe.getRuntimeService().signal(excution1.getId());
			
			//查询执行对象的id
			Execution excution2 = pe.getRuntimeService().createExecutionQuery().processInstanceId("110001")
				    .activityId("receivetask2")//当前活动id
				    .singleResult();
			
			//从流程变量中获取当日销售额的值
			int value = (int) pe.getRuntimeService().getVariable(excution2.getId(), "汇总当日销售额");
		
		   System.out.println("给老板发送短信，金额是="+value);
		   
		   //向后执行一步，如果流程在等待状态，使得流程向后执行
			pe.getRuntimeService().signal(excution2.getId());
		}
}
