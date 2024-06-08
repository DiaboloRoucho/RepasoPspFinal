package concurrencia.ej6Consola;

import static concurrencia.ej6Consola.Main.actualizar;

import java.util.concurrent.Semaphore;

public class Parque {

	private Semaphore banco;
	int plazas;
	private boolean pausa = false;
	
	
	public Parque(int plazas) {
		this.plazas = plazas;
		banco = new Semaphore(plazas);
	}
	
	public synchronized void sentarse() {
		if (!pausa) {
			try {
				if (banco.availablePermits() == 0)
					actualizar("El banco esta lleno, " + Thread.currentThread().getName() + " espera\n");
				banco.acquire();
				actualizar(Thread.currentThread().getName() + " se sienta\n");
				Thread.sleep((long) (Math.random() * 600) + 100);
				actualizar(Thread.currentThread().getName() + " se levanta\n");
				banco.release();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} 
		}
	}
	public void parar() {
		pausa = true;
	}

	public synchronized void reanudar() {
		pausa = false;
		notifyAll();
	}
}
