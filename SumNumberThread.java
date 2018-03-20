package oop6z2;

import java.util.concurrent.Callable;

public class SumNumberThread implements Callable<Long> {

	private int [] arrayInt;
	private int from;
	private int to;
	
	public SumNumberThread(int[] arrayInt, int from, int to) {
		super();
		this.arrayInt = arrayInt;
		this.from = from;
		this.to = to;
	}

	@Override
	public Long call() throws Exception {
		long result = 0;
		
		for (int i = from; i < to; i++) {
			result+=arrayInt[i];
			
		}
		return result;
	}

	
	
	
	
	
	
}
