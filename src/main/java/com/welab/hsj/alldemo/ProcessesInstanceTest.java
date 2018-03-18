package com.welab.hsj.alldemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 流程实例相关
 * @author hsj
 *
 */
public class ProcessesInstanceTest {
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	
	//流程实例id
	private String processesInstanceId="25001";
	
	/**
	 * 启动一个流程实例
	 */
	@Test
	public void startProcessesInstance(){
		ProcessInstance pi = pe.getRuntimeService().startProcessInstanceByKey(ConstantInfo.ProcessesInfo.proDedfKey);
		System.out.println("流程实例id="+pi.getId());
		System.out.println("流程定义id="+pi.getProcessDefinitionId());
		
		processesInstanceId = pi.getId();
	}
	
	/**
	 * 流程实例是不是正在执行
	 * 有没有执行完毕？
	 * 流程执行完毕之后会被从act_ru_execute表中删除，查询得到null值就表示结束了。
	 */
	@Test
	public void isRunProcessesInstance(){
		ProcessInstance pi = pe.getRuntimeService()
		  .createProcessInstanceQuery()
		  .processInstanceId(processesInstanceId)
		  .singleResult();
		
		/**
		 * 流程执行完毕之后会被从act_ru_execute表中删除，查询得到null值就表示结束了。
		 */
		if(pi!=null){
			if(pi.isEnded()){
				System.out.println(String.format("流程实例执行完成，实例id=%s", processesInstanceId));
			}else{
				System.out.println(String.format("流程实例正在执行，实例id=%s", processesInstanceId));
			}
		}else{
			System.out.println(String.format("流程实例执行完成，实例id=%s", processesInstanceId));
		}
	}
}
