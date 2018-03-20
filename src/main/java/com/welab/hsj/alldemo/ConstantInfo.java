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
    		
    		public final static String zhaolu = "赵六";
    		
    		public final static String tianqi = "田七";
    		
    		
    		public final static String wangxiaowu = "王小五";
    		public final static String zhaoxiaolu = "赵小六";
    		public final static String tianxiaoqi = "田小七";
    		
    		
    		public final static String zhangsanfeng = "张三丰";
    		public final static String zhoujielun = "周杰伦";
    		
    		public final static String mayun = "马云";
    		public final static String mahuateng = "马化腾";
    		public final static String xidada = "习大大";
      }
      
      public interface ProcessesInfo{
    	//流程定义的key-相当于流程的id
  		public final static String proDedfKey = "helloWord";
  		
  		public abstract void processesInfoMethod();
      }
}
