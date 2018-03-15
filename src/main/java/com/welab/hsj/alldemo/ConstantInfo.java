package com.welab.hsj.alldemo;

public interface ConstantInfo {
      public interface User{
    		//张三-申请者
    		public final static String zhangsan = "张三";
    		//李四-部门经理
    		public final static String lisi = "李四";
    		//王五-总经理
    		public final static String wangwu = "王五";
    		
    		public abstract void userMethod();
      }
      
      public interface ProcessesInfo{
    	//流程定义的key-相当于流程的id
  		public final static String proDedfKey = "helloWord";
  		
  		public abstract void processesInfoMethod();
      }
}
