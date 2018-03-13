package com.welab.hsj.helloword;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
/**
 * 该列子因为是写死的申请流程里面的人,写死的流程自己手动走一遍
 * @author hsj
 *
 */
public class HelloWord {
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	
	//流程定义的key-相当于流程的id
	public final static String proDedfKey = "helloWord";
	
	//张三-申请者
	public final static String zhangsan = "张三";
	//李四-部门经理
	public final static String lisi = "李四";
	//王五-总经理
	public final static String wangwu = "王五";

	/**
	 * springboot会自动部署resources下面的processes文件直接目录下面的流程定义文件【只会第一子集，下面嵌套不会部署】
	 * -这里练习自己部署
	 */
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
	
	/**
	 * 流程启动-当前的流程是写死的->张三【申请】-李四【部门经理】-王五【总经理】
	 * 相当于->创建一个申请实例，会创建申请的id
	 */
	@Test
	public void startProcessesInstance(){
		ProcessInstance pi = pe.getRuntimeService().startProcessInstanceByKey(proDedfKey);
		System.out.println("流程实例id="+pi.getId());
		System.out.println("流程定义id="+pi.getProcessDefinitionId());
	}
	
	/**
	 * 查找个人的当前任务
	 * 相当于->查询该创建好的申请实例的id做准备填写申请
	 */
	@Test
	public void findMyPersonTask(){
		List<Task> lt = pe.getTaskService()
		   .createTaskQuery()
		   .taskAssignee(wangwu)
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
	 * 根据申请的id填写申请提交
	 */
	@Test
	public void completeMyPersonTask(){
		List<Task> lt = pe.getTaskService()
		   .createTaskQuery()
		   .taskAssignee(wangwu)
		   .list();
		TaskService ts = pe.getTaskService();
		if(lt != null && lt.size() > 0){
			for(Task at : lt){
				ts.complete(at.getId());
				System.out.println("完成任务id="+at.getId());
			}
		}
	}
}
