package concurrencia.ej8;

import java.util.concurrent.Semaphore;

public class Cadena {

	Semaphore s = new Semaphore(1, true);
	int[] cinta = new int[3];
	int contador = 0, x;
	
	public Cadena() {
		for (int i = 0; i < cinta.length; i++) {
			cinta[i] = 0;
		}
	}
	
	public void colocar() {
		try {
			s.acquire();
			for (int i = 0; i < cinta.length; i++) {
				if (cinta[i] == 0) {
					while (cinta[i] == 0) {
						x = (int) Math.round(Math.random() * 3);
						cinta[i] = x;
					}
					System.out.println("El colocador ha metido un " + x);
				}
				
			}
			s.release();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public void retirar(int tipo) {
		try {
			s.acquire();
			for (int i = 0; i < cinta.length; i++) {
				if(cinta[i] == tipo) {
					System.out.println(Thread.currentThread().getId() + " ha retirado " + cinta[i]);
					cinta[i] = 0;
					contador++;
					System.out.println("Se han retirado " + contador + " productos en total");
				}
			}
			s.release();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
