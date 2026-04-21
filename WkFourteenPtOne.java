package jdbcExersice;

public class WkFourteenPtOne {
	// 1a. Implementing the Runnable interface
	static class RunnableSum implements Runnable {
		int sum = 0;
		
		@Override
		public void run() {
			for (int i = 1; i <= 100; i++) {
				sum += i;
			}
		}
	}
	
	// 1b. Extending the thread class
	static class ThreadSum extends Thread {
		int sum = 0;
		
		@Override
		public void run() {
			for (int i=1; i <= 100; i++) {
				sum += i;
			}
		}
	}
	
	public static void main(String [] args) throws InterruptedException {
		// Testing 1a
		RunnableSum myRunnable = new RunnableSum();
		Thread thread1 = new Thread(myRunnable);
		thread1.start();
		thread1.join(); // Main thread waits
		System.out.println("Runnable sum : " + myRunnable.sum);
		
		// Testing 1b
		ThreadSum thread2 = new ThreadSum();
		thread2.start();
		thread1.join();
		System.out.println("Thread Class sum: " + thread2.sum);
	}
}
