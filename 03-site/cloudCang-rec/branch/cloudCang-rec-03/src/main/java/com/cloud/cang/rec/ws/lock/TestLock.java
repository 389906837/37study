package com.cloud.cang.rec.ws.lock;

import com.cloud.cang.cache.redis.JedisClusterFactory;
import com.cloud.cang.lock.JedisLock;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestLock {

	static ExecutorService exec = Executors.newFixedThreadPool(50);
	static ExecutorService exec1 = Executors.newFixedThreadPool(50);
	
	static int v=0;
	
	
	
	    public static void main(String[] args)throws Exception {
	    	
	    	GenericObjectPoolConfig conf=new GenericObjectPoolConfig();
	    	conf.setMaxWaitMillis(-1);
	    	
	    	
	    	
	    	
	    	final JedisClusterFactory ff=new JedisClusterFactory();
	    	ff.setConnStr("192.168.1.31:7000");
	    	ff.setGenericObjectPoolConfig(conf);
	    	ff.afterPropertiesSet();
	    	
	    	 for (int i = 0; i <500; i++)
	    		 exec.execute(new Runnable() {
					
					@Override
					public void run() {
						try{
							com.cloud.cang.lock.JedisLock lock=new com.cloud.cang.lock.JedisLock(ff.getObject(), "t011");
					    	if(lock.acquire()){
					    		System.out.println((v++));
					    	}
					    	lock.release();
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				});
	    	 for (int i = 0; i <500; i++)
	    		 exec1.execute(new Runnable() {

					@Override
					public void run() {
						try{
							com.cloud.cang.lock.JedisLock lock=new JedisLock(ff.getObject(), "t011");
					    	if(lock.acquire()){
					    		System.out.println((v++));
					    	}
					    	lock.release();
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				});
	    	 exec.shutdown();
	    	 exec1.shutdown();
	    	//JedisLock lock=new JedisLock(j, "t01");
	    	//if(lock.acquire()){
	    	//	System.out.println("-----------------------");
	    	//}
	    	//lock.release();
		}
	    

}
