package concurrencia.ej7;

import java.util.concurrent.Semaphore;

public class Mesa {

	private Semaphore s;
	
	public Mesa(int nfilosofos) {
		this.s = new Semaphore(nfilosofos - 1);
	}
	
	public void intentarCoger() throws InterruptedException {
		if (s.availablePermits()==0)
			System.out.println(Thread.currentThread().getName() + " esta esperando para comer");
		s.acquire();
	}
	
	public void soltar() {
		s.release();
	}
}
