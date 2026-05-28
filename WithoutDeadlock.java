public class WithoutDeadlock {
	// Recursos compartilhados (locks)
	private static final Object lockA = new Object();
	private static final Object lockB = new Object();

	public static void main(String[] args) {
		// Ambos os threads seguem a mesma ordem: lockA -> lockB.
		// Isso elimina a espera circular e evita deadlock.
		Thread thread1 = new Thread(() -> doWork("Thread 1"), "Thread-1");
		Thread thread2 = new Thread(() -> doWork("Thread 2"), "Thread-2");

		thread1.start();
		thread2.start();
	}

	private static void doWork(String name) {
		synchronized (lockA) {
			System.out.println(name + ": Holding Lock A...");

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}

			System.out.println(name + ": Waiting for Lock B...");
			synchronized (lockB) {
				System.out.println(name + ": Acquired Lock B!");
			}
		}
	}
}
