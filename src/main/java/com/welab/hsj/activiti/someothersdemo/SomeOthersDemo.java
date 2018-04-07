package com.welab.hsj.activiti.someothersdemo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.welab.hsj.alldemo.ConstantInfo;
import com.welab.hsj.alldemo.bean.PersonBean;

/**
 * 使用了b/s流程设计器-使用${someothersdemo}方式练习
 * 
 * setVariable -->setVariableLocal[不会传递变量到下一个任务！]
 * 
 * @author hsj
 *
 */
public class SomeOthersDemo {

	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	
	public final static String APPLLYDAYS = "请假天数";
	public final static String APPLLYSTARTTIME = "请假开始时间";
	public final static String APPLYRESON = "请假理由";
	private final static String PERSONBEANINFO = "人员信息";

	/**
	 * 通过B/S方式已经部署
	 */
	@Test
	public void deployementProcessDifined() {

	}
	
	/**
	 * 查询所有流程部署
	 * 
	 *  流程部署ID:8
		流程部署名称:一条审批流程
		################################
	 */
	@Test
	public void queryAllProcessDeployement() {
		List<Deployment> list = pe.getRepositoryService()
				.createDeploymentQuery()
				.orderByDeploymentId().asc()
				.list();
	
		if (list != null && list.size() > 0) {
			for (Deployment deployment : list) {
				System.out.println("流程部署ID:" + deployment.getId());
				System.out.println("流程部署名称:" + deployment.getName());
				
				System.out.println("################################");
			}
		}

	}

	/**
	 * 查询所有流程定义
	 * 
	 *  流程定义ID:流程测试:1:11
		流程定义ID:一条审批流程:1:11
		流程定义名称:一条审批流程
		流程定义的key:一条审批流程
		流程定义的版本:1
		资源名称bpmn文件:一条审批流程.bpmn20.xml
		资源名称png文件:一条审批流程.一条审批流程.png
		部署对象ID:8
		################################
	 */
	@Test
	public void queryAllProcessDifined() {
		List<ProcessDefinition> list = pe.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createProcessDefinitionQuery()// 创建一个流程定义查询
				/* 指定查询条件,where条件 */
				 .deploymentId("8")//使用部署对象ID查询
				// .processDefinitionId(processDefinitionId)//使用流程定义ID查询
				// .processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
				// .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

				/* 排序 */
//				.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
				// .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

				.list();// 返回一个集合列表，封装流程定义
		// .singleResult();//返回唯一结果集
		// .count();//返回结果集数量
		// .listPage(firstResult, maxResults)//分页查询

		if (list != null && list.size() > 0) {
			for (ProcessDefinition processDefinition : list) {
				System.out.println("流程定义ID:" + processDefinition.getId());// 流程定义的key+版本+随机生成数
				System.out.println("流程定义名称:" + processDefinition.getName());// 对应HelloWorld.bpmn文件中的name属性值
				System.out.println("流程定义的key:" + processDefinition.getKey());// 对应HelloWorld.bpmn文件中的id属性值
				System.out.println("流程定义的版本:" + processDefinition.getVersion());// 当流程定义的key值相同的情况下，版本升级，默认从1开始
				System.out.println("资源名称bpmn文件:"
						+ processDefinition.getResourceName());
				System.out.println("资源名称png文件:"
						+ processDefinition.getDiagramResourceName());
				System.out.println("部署对象ID:"
						+ processDefinition.getDeploymentId());
				System.out.println("################################");
			}
		}

	}

	/**
	 * 启动流程实例根据流程定义的key:一条审批流程
	 * 
	 *  流程实例id=2501
		流程定义id=一条审批流程:1:11
		
		指定办理人${applyperson}
	 */
	@Test
	public void startProcessesInstance() {
		Map<String,Object>  variable = new HashMap<>();
		variable.put("applyperson", ConstantInfo.User.zhoujielun);
		ProcessInstance processInstance = pe.getRuntimeService()
				.startProcessInstanceByKey("一条审批流程",variable);
		System.out.println("流程实例id=" + processInstance.getId());
		System.out.println("流程定义id=" + processInstance.getProcessDefinitionId());
	}
	

	/**
	 * 查找个人的当前任务 相当于->查询该创建好的申请实例的id做准备填写申请
	 * 
	 * 任务id=2505
		任务名称=申请人
		任务创建时间=Sat Apr 07 16:54:06 CST 2018
		任务办理人=周杰伦
		任务创建时间=Sat Apr 07 16:54:06 CST 2018
		流程实例id=2501
		执行对象id=2501
		流程定义id=一条审批流程:1:11
	 */
	@Test
	public void findMyPersonTask() {
		List<Task> lt = pe.getTaskService().createTaskQuery()
				.taskAssignee(ConstantInfo.User.mahuateng).list();
		if (lt != null && lt.size() > 0) {
			for (Task at : lt) {
				System.out.println("任务id=" + at.getId());
				System.out.println("任务名称=" + at.getName());
				System.out.println("任务创建时间=" + at.getCreateTime());
				System.out.println("任务办理人=" + at.getAssignee());
				System.out.println("任务创建时间=" + at.getCreateTime());
				System.out.println("流程实例id=" + at.getProcessInstanceId());
				System.out.println("执行对象id=" + at.getExecutionId());
				// 会使用最新的一次的流程定义——流程定义一样的话就进行升级version
				System.out.println("流程定义id=" + at.getProcessDefinitionId());
			}
		}
	}

	
	/**
	 * 设置流程变量
	 * 根据taskId"2505"设置流程变量
	 * 不仅仅是taskService
	 * runtimeService也可以
	 */
	@Test
	public void setVariable(){
		TaskService ts = pe.getTaskService();
		
		//Local只是与当前的taskid绑定，尽量使用local方式不会覆盖前面的task的信息
		ts.setVariable("12505", APPLLYDAYS, 3);
		ts.setVariable("12505", APPLLYSTARTTIME, new Date());
		ts.setVariable("12505", APPLYRESON, "回家探亲");
		
		//设置流程变量的方式2
//		ts.setVariable(processesTaskId, PERSONBEANINFO, new PersonBean(10,"翠花",18));
		
		System.out.println("流程变量设置完成");
	}
	
	/**
	 * 根据taskId完成任务"2505"
	 */
	@Test
	public void completeMyPersonTask() {
		pe.getTaskService().complete("17502");
		System.out.println("完成任务id="+"17502");
	}
	
	
	/**
	 * 获取流程变量
	 * 不仅仅是taskService
	 * runtimeService也可以
	 */
	@Test
	public void getVariable(){
		TaskService ts = pe.getTaskService();
		
		//Local只是与当前的taskid绑定，尽量使用local方式不会覆盖前面的task的信息
		System.out.println(APPLLYDAYS+"="+ts.getVariableLocal("17502", APPLLYDAYS));
		System.out.println(APPLLYSTARTTIME+"="+ts.getVariable("17502", APPLLYSTARTTIME));
		System.out.println(APPLYRESON+"="+ts.getVariable("17502", APPLYRESON));
		
		//设置流程变量的方式2读取
//		PersonBean pb = (PersonBean) ts.getVariable(processesTaskId, PERSONBEANINFO);
//		System.out.println(PERSONBEANINFO+"="+pb.toString());
	}
	
}
