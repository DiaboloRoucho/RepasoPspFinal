package concurrencia.ej4Consola;

import java.util.concurrent.Semaphore;
import static concurrencia.ej4Consola.Main.actualizar;

public class Almacen {

	Semaphore semaforo;
	int ocupados, maximo;
	private boolean pausa = false;

	public Almacen(int maximo) {
		this.maximo = maximo;
		semaforo = new Semaphore(maximo);
	}

	public synchronized void almacenar() {
		if (!pausa) {
			try {
				semaforo.acquire();
				if (ocupados < maximo) {
					ocupados++;
					actualizar("Articulo almacenado, hay " + ocupados + " articulos almacenados\n");
					semaforo.release();
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} 
		}
	}

	public synchronized void retirar() {
		if (!pausa) {
			try {
				semaforo.acquire();
				if (ocupados != 0) {
					ocupados--;
					actualizar("Articulo retirado, hay " + ocupados + " articulos almacenados\n");
					semaforo.release();
				}
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
