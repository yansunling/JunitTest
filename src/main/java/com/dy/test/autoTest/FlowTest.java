package com.dy.test.autoTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FlowTest {
	public static void main(String[] args) throws Exception {
		
		
		ExecutorService excutorService = Executors.newFixedThreadPool(8);
		List<Future<Integer>> futures=new ArrayList<Future<Integer>>();
		for(int i=0;i<10;i++) {
			Future<Integer> future = excutorService.submit(()-> {
				
				
				
				
				
				return 0;
			});
			futures.add(future);
		}
		for(Future<Integer> future:futures) {
			future.get();
		}
		excutorService.shutdown();
		
	}
	
	
	
	
	
	
	
}
