package com.welab.hsj.alldemo2.groupTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
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
 * 组任务-直接分配-写死
 * 组内都是参与者与候选者人员
 * 但是执行任务的只会有一个人真正的去执行任务
 * 
 * Candidate=后选着
 * Participant=参与者
 * @author hsj
 *
 */
public class GroupTask1 {
	
    private String proKey = "groupTask1";
	
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	
	
	/**
	 * 部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("组任务1-直接写死")
		  .addClasspathResource("processes/hsj/GroupTask1.bpmn")
		  .addClasspathResource("processes/hsj/GroupTask1.png")
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
	 * 查找当前人的组任务
	 *
	 */
	@Test
	public void findMyGroupTask(){
		List<Task> lt = pe.getTaskService()
		   .createTaskQuery()
//		   .taskAssignee(ConstantInfo.User.F)
		   .taskCandidateUser(ConstantInfo.User.xiaoD)
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
	
	/**拾取任务，将组任务分配给个人任务，指定任务的办理人字段**/
    @Test
	public void testClaim(){
    	try{
    		//将组任务分配给个人任务，分配个人任务可以是组内里面的成员，也可以是非组内的成员
        	pe.getTaskService().claim("207504", ConstantInfo.User.xiaoD);	
    	}catch(ActivitiTaskAlreadyClaimedException e){
    		System.out.println("该组该任务节点已经指定的执行人，不能重复指定！");
    	}
     }
    
    /**
     * 将个人任务回退到组任务
     */
    @Test
	public void setAssigee2Group(){
        	pe.getTaskService().setAssignee("202504", null);	
     }
	/**
	 * 完成任务
	 */
	@Test
	public void completeCurrentTask(){
		//完成任务的同时设置流程变量，决定下一步的连线，对应ExclusiveGateWay.bpmn文件的${money>100}等
		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("pass", "pass ok!");
		pe.getTaskService().complete("207504",variables);
		
		System.out.println("完成任务id="+207504);
	}
	
	
	/**查询正在执行任务的办理人表**/
	@Test
	public void findRunPersonTableTask(){
		//查询的是候选者的人员table表
		 List<IdentityLink> li = pe.getTaskService().getIdentityLinksForTask("202504");
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
		List<HistoricIdentityLink> lh =pe.getHistoryService().getHistoricIdentityLinksForProcessInstance("202501");
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
	 
	/**
	 * 组任务中添加成员
	 */
	@Test
	public void addPerson2Group(){
        	pe.getTaskService().addCandidateUser("182504", ConstantInfo.User.H);	
     }
	
	/**
	 * 组任务中删除成员
	 */
	@Test
	public void deletePersonFromGroup(){
		pe.getTaskService().deleteCandidateUser("182504", ConstantInfo.User.xiaoB);
     }
	    
}
