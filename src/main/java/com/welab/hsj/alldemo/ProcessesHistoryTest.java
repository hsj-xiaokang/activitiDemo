package com.welab.hsj.alldemo;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

/**
 * 流程历史的查询
 * @author hsj
 *
 */
public class ProcessesHistoryTest {
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	//流程实例id
	private String processesInstanceIdGlobale="25001";
	
	/**
	 * 历史任务实例查询
	 */
	@Test
	public void findHistoryTask(){
		List<HistoricTaskInstance> lh = pe.getHistoryService().createHistoricTaskInstanceQuery()
		    .taskAssignee(ConstantInfo.User.zhangsan)
		    .list();
		
		if(lh != null && lh.size() > 0){
			for(HistoricTaskInstance ht:lh){
				System.out.println(String.format("历史任务实例的id=%s", ht.getId()));
				System.out.println(String.format("历史任务实例的ProcessInstanceId=%s", ht.getProcessInstanceId()));
				System.out.println(String.format("历史任务实例的ExecuteId=%s", ht.getExecutionId()));
				System.out.println(String.format("历史任务实例的task的name=%s", ht.getName()));
				System.out.println("===========================================");
			}
		}
	}
	
	/**
	 * 历史流程实例
	 */
	@Test
	public void findHistoryProcessesInstance(){
		  //流程实例id
		  String processesInstanceId="15001";
		    HistoricProcessInstance hp =  pe.getHistoryService().createHistoricProcessInstanceQuery()
		                                         .processInstanceId(processesInstanceId)
		                                         .singleResult();
		    
		    if(hp != null){
		    	System.out.println(String.format("历史流程实例的id=%s", hp.getId()));
		    	System.out.println(String.format("历史流程实例的ProcessesInstanceId=%s", hp.getProcessDefinitionId()));
		    	System.out.println(String.format("历史流程实例的开始时间=%s", hp.getStartTime().toLocaleString()));
		    	System.out.println(String.format("历史流程实例的结束时间=%s", hp.getEndTime().toLocaleString()));
		    }		    
	}
	
	/**
	 * 查询历史活动
	 */
	@Test
	public void findHistoryActiviti(){
		  //流程实例id
		  String processesInstanceId="25001";
		    List<HistoricActivityInstance> lha =  pe.getHistoryService().createHistoricActivityInstanceQuery()
		    		                        .processInstanceId(processesInstanceId)
		    		                        .orderByHistoricActivityInstanceStartTime()
		    		                        .asc()
		    		                        .list();
		    
		    if(lha != null && lha.size() > 0){
		    	for(HistoricActivityInstance hai:lha){
		    		System.out.println(String.format("历史活动的id=%s", hai.getId()));
			    	System.out.println(String.format("历史活动的流程实例的ProcessesInstanceId=%s", hai.getProcessDefinitionId()));
			    	System.out.println(String.format("历史活动的流程实例的开始时间=%s", hai.getStartTime().toLocaleString()));
			    	System.out.println(String.format("历史活动的流程实例的结束时间=%s", hai.getEndTime().toLocaleString()));
		    	}
		    }		    
	}
	
	
	/**
	 * 流程变量历史查询
	 */
	@Test
	public void queryVariableHis(){
		List<HistoricVariableInstance> lhv = pe.getHistoryService().createHistoricVariableInstanceQuery()
		     .processInstanceId(processesInstanceIdGlobale).list();
		if(lhv != null && lhv.size() > 0){
			for(HistoricVariableInstance hvi:lhv){
				System.out.println("任务id="+hvi.getTaskId());
				//再根据任务id查询下面流程变量值
			}
		}
	}
}
