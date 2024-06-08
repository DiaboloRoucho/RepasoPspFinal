package concurrencia.ej7;

import java.util.concurrent.locks.ReentrantLock;

public class Palillo {

	private ReentrantLock lock = new ReentrantLock();
	
	public void coger() {
		lock.lock();
	}
	
	public void dejar() {
		lock.unlock();
	}
}
