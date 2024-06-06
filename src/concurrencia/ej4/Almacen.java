package concurrencia.ej4;

import java.util.concurrent.Semaphore;

public class Almacen {

	Semaphore semaforo;
	int ocupados, maximo;

	public Almacen(int maximo) {
		this.maximo = maximo;
		semaforo = new Semaphore(maximo);
	}

	public synchronized void almacenar() {
		try {
			semaforo.acquire();
			if (ocupados < maximo) {
				ocupados++;
				System.out.println("Articulo almacenado, hay " + ocupados + " articulos almacenados");
			}
			semaforo.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void retirar() {
		try {
			semaforo.acquire();
			if (ocupados != 0) {
				ocupados--;
				System.out.println("Articulo retirado, hay " + ocupados + " articulos almacenados");
			}
			semaforo.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
