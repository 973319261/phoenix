package com.koi.util;

/**
 * 高并发场景下System.currentTimeMillis()的性能问题的优化
 * 时间戳打印建议使用
 */
public class SystemClockUtils {
	  private long rate = 0;// 频率  
	    private volatile long now = 0;// 当前时间  
	  
	    private SystemClockUtils(long rate) {  
	        this.rate = rate;  
	        this.now = System.currentTimeMillis();  
	        start();  
	    }  
	  
	    private void start() {  
	        new Thread(new Runnable() {  
	            @Override  
	            public void run() {  
	                try {  
	                    Thread.sleep(rate);  
	                } catch (InterruptedException e) {  
	                    e.printStackTrace();  
	                }  
	                now = System.currentTimeMillis();  
	            }  
	        }).start();  
	    }  
	  
	    public long now() {  
	        return now;  
	    }  
	  
	    public static final SystemClockUtils CLOCK = new SystemClockUtils(10);  
	}  