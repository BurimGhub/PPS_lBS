package jdbcExersice;

public class WkFourteenPtTwo {

	// Counter Class
	static class Counter {
		private int count = 0;
		
		// Synchronized prevents race condition
		public synchronized void increment() {
			count++;
		}
		
		// Gets count
		public int getCount() {
			return count;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		// 2a
		Counter sharedCounter = new Counter();
		Thread[] threads = new Thread[10];
		
		// Start 10 threads
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread(() -> {
				for (int j = 0; j < 1000; j++) {
					sharedCounter.increment();
				}
			});
			threads[i].start();
		}
		
		// 2b. Main thread joins the created threads
		for (int i = 0; i < 10; i++) {
			threads[i].join();
		}
		
		System.out.println("Final Counter Value: " + sharedCounter.getCount());

	}

}
