package oop6z2;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
	public static void main(String[] args) {
		int lengthArray = 20000000;
		int[] arrayElement = new int[lengthArray];
		for (int i = 0; i < arrayElement.length; i++) {
			arrayElement[i] = (int) (Math.random() * 10);
		}
		Long start = (long) 0;
		int partLength = 0;
		int maxThread = 4;
		if (lengthArray % maxThread == 0) {
			try {
				partLength = lengthArray / maxThread;
			} catch (ArithmeticException e) {
				e.printStackTrace();
			}
		}

		// Time 4 thread
		start = System.currentTimeMillis();

		ExecutorService es = Executors.newFixedThreadPool(maxThread);
		CompletionService<Long> cs = new ExecutorCompletionService<>(es);

		int offset = 0;
		int sum = 0;
		try {
			for (int i = 0; i < maxThread; i++) {
				SumNumberThread snt = new SumNumberThread(arrayElement, offset, offset + partLength);
				offset += partLength;
				cs.submit(snt);
			}
			for (int i = 0; i < maxThread; i++) {
				try {
					Future<Long> future = cs.take();
					sum += future.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			System.out.println(
					"Sum from four thread = " + sum + " counting time (ms)= " + (System.currentTimeMillis() - start));
		} finally {
			es.shutdownNow();
		}

		// Time 1 thread
		start = System.currentTimeMillis();
		sum = 0;
		for (int i = 0; i < arrayElement.length; i++) {
			sum = sum + arrayElement[i];
		}
		System.out.println(
				"Sum from one thread = " + sum + " counting time (ms)= " + (System.currentTimeMillis() - start));

	}

}
