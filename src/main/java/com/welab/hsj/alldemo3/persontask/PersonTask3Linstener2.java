package com.welab.hsj.alldemo3.persontask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.welab.hsj.alldemo.ConstantInfo;

/**
 * 放在一个单独的类
 * 
 * @author hsj
 *
 */
public  class PersonTask3Linstener2 implements TaskListener{

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
	delegateTask.setAssignee(ConstantInfo.User.mahuateng);
}
}
