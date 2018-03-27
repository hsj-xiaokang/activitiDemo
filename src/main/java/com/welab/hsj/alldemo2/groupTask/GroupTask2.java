package com.welab.hsj.alldemo2.groupTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.welab.hsj.alldemo.ConstantInfo;

/**
 * 流程变量指定-不生效！！！
 * @author hsj
 *
 */
public class GroupTask2 {
	 private String proKey = "groupTask2";
		
		ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
		
		
		/**
		 * 部署
		 */
		@Test
		public void deployementProcessDifined(){
			Deployment deployment = pe.getRepositoryService()
			  .createDeployment()
			  .name("组任务2-流程变量方式")
			  .addClasspathResource("processes/hsj/GroupTask2.bpmn")
			  .addClasspathResource("processes/hsj/GroupTask2.png")
			  .deploy();
			
			System.out.println("部署id="+deployment.getId());
			System.out.println("部署名字="+deployment.getName());
		}
		
		
		/**
		 * 流程实例
		 */
		@Test
		public void startGroup2Instance(){
			//流程变量指定办理人-#{userIDs}
			Map<String,Object>  variable = new HashMap<>();
			variable.put("userIDs", "aa,bb,cc");
			ProcessInstance processInstance = pe.getRuntimeService()
					.startProcessInstanceByKey(proKey,variable);
			
			System.out.println("流程实例id="+processInstance.getId());//190001
			System.out.println("流程定义id="+processInstance.getProcessDefinitionId());
		}
		
		/**
		 * 查找当前人的组任务
		 *
		 */
		@Test
		public void findMyGroupTask(){
			List<Task> lt = pe.getTaskService()
			   .createTaskQuery()
//			   .taskAssignee(ConstantInfo.User.F)
			   .taskCandidateUser(ConstantInfo.User.xiaoxiao)
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
		
		/**查询正在执行任务的办理人表**/
		@Test
		public void findRunPersonTableTask(){
			//查询的是候选者的人员table表
			 List<IdentityLink> li = pe.getTaskService().getIdentityLinksForTask("190005");
			 if(li != null && li.size() > 0){
				 for(IdentityLink il:li){
					 System.out.println("任务id="+il.getTaskId());
					 System.out.println("任务GroupId="+il.getGroupId());
					 System.out.println("任务userid="+il.getUserId());
					 System.out.println("任务流程实例id="+il.getProcessInstanceId());
					 System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
				 }
			 }   
		}
		
		/**查询历史任务任务的办理人表**/
		@Test
		public void findHisPersonTableTask(){
			//查询的是参与者的人员table表
			List<HistoricIdentityLink> lh =pe.getHistoryService().getHistoricIdentityLinksForProcessInstance("182501");
		    if(lh != null && lh.size() > 0){
		    	for(HistoricIdentityLink hi : lh){
		    		 System.out.println("任务id="+hi.getTaskId());
					 System.out.println("任务GroupId="+hi.getGroupId());
					 System.out.println("任务userid="+hi.getUserId());
					 System.out.println("任务流程实例id="+hi.getProcessInstanceId());
					 System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
		    	}
		    }
		}
}
