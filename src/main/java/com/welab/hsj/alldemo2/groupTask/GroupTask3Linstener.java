package com.welab.hsj.alldemo2.groupTask;

import java.util.ArrayList;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.welab.hsj.alldemo.ConstantInfo;

public class GroupTask3Linstener implements TaskListener{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 指定任务的办理人
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		// 指定个人任务的办理人，也可以指定组任务的办理人
		//个人任务：通过类去查询数据库，将下一个任务的办理人查询获取，然后通过setAssignee()的方法指定办理人
//		delegateTask.setAssignee(ConstantInfo.User.mayun);
		delegateTask.addCandidateUsers(new ArrayList<String>(){
			{
				add(ConstantInfo.User.guojin);
				add(ConstantInfo.User.huangrong);
			}
		});
	}

}
