package jdbcExersice;

import java.util.Random;

public class WkFourteenPtThree {

	// Custom thread to handle summing a specific half of the array
	static class ArraySumThread extends Thread {
		private int[] arr;
		private int start, end;
		private long partialSum = 0;
		
		public ArraySumThread(int[] arr, int start, int end) {
			this.arr = arr;
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {
			for (int i = start; i < end; i++) {
				partialSum += (long) arr[i] * arr[i];
			}
		}
		
		// Gets partialSum
		public long getPartialSum() {
			return partialSum;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		if (args.length < 1) {
			System.out.println("Please provide n as a command line argument");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		int[] array = new int[n];
		Random rand = new Random();
		
		// Populate the array with small integers
		for (int i = 0; i < n; i++) {
			array[i] = rand.nextInt(100) + 1;
		}

		// Single threaded timing
		long startTimeSeq = System.nanoTime();
		long seqSum = 0;
		for (int i = 0; i < n; i++) {
			seqSum += (long) array[i] * array[i];
		}
		long endTimeSeq = System.nanoTime();
		long durationSeq = (endTimeSeq - startTimeSeq);
		
		// Multi threaded timing
		long startTimeThread = System.nanoTime();
		
		ArraySumThread t1 = new ArraySumThread(array, 0, n / 2);
		ArraySumThread t2 = new ArraySumThread(array, n / 2, n);
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		long threadSum = t1.getPartialSum() + t2.getPartialSum();
		long endTimeThread = System.nanoTime();
		long durationThread = (endTimeThread - startTimeThread);
		
		// Output results
		System.out.println("N = " + n);
		System.out.println("Sequential Time: " + durationSeq / 1_000_000.0 + " ms");
		System.out.println("Threaded Time: " + durationThread / 1_000_000.0 + " ms");
	}
	
	// By increasing the size of the array up to 100,000,000, for smaller numbers the sequential loop is faster, but once the size goes up
	// the threaded time is faster.

}
