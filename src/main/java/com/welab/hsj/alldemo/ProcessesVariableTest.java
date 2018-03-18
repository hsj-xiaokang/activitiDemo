package com.welab.hsj.alldemo;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

import com.welab.hsj.alldemo.bean.PersonBean;

/**
 * 流程变量设置一般方式和通过Javabean的方式【需要序列化】
 * @author hsj
 *
 */
public class ProcessesVariableTest {
	
	    ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	    //流程任务id
		private String processesTaskId="42504";
		
		public final static String APPLLYDAYS = "请假天数";
		public final static String APPLLYSTARTTIME = "请假开始时间";
		public final static String APPLYRESON = "请假理由";
		
		private final static String PERSONBEANINFO = "人员信息";
		
		//流程实例id
		private String processesInstanceId="25001";
		
		
		/**
		 * 设置流程变量
		 * 不仅仅是taskService
		 * runtimeService也可以
		 */
		@Test
		public void setVariable(){
			TaskService ts = pe.getTaskService();
			
			//Local只是与当前的taskid绑定，尽量使用local方式不会覆盖前面的task的信息
			/*ts.setVariableLocal(processesTaskId, APPLLYDAYS, 3);
			ts.setVariable(processesTaskId, APPLLYSTARTTIME, new Date());
			ts.setVariable(processesTaskId, APPLYRESON, "回家探亲");*/
			
			//设置流程变量的方式2
			ts.setVariable(processesTaskId, PERSONBEANINFO, new PersonBean(10,"翠花",18));
			
			System.out.println("流程变量设置完成");
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
			/*System.out.println(APPLLYDAYS+"="+ts.getVariableLocal(processesTaskId, APPLLYDAYS));
			System.out.println(APPLLYSTARTTIME+"="+ts.getVariable(processesTaskId, APPLLYSTARTTIME));
			System.out.println(APPLYRESON+"="+ts.getVariable(processesTaskId, APPLYRESON));*/
			
			//设置流程变量的方式2读取
			PersonBean pb = (PersonBean) ts.getVariable(processesTaskId, PERSONBEANINFO);
			System.out.println(PERSONBEANINFO+"="+pb.toString());
		}
		
		
		/**
		 * 完成任务
		 */
		@Test
		public void completeTask(){
			pe.getTaskService().complete(processesTaskId);
			System.out.println("完成任务id="+processesTaskId);
		}
		
		/**
		 * 流程变量历史查询
		 */
		@Test
		public void queryVariableHis(){
			List<HistoricVariableInstance> lhv = pe.getHistoryService().createHistoricVariableInstanceQuery()
			     .processInstanceId(processesInstanceId).list();
			if(lhv != null && lhv.size() > 0){
				for(HistoricVariableInstance hvi:lhv){
					System.out.println("任务id="+hvi.getTaskId());
					//再根据任务id查询下面流程变量值
				}
			}
		}

}
