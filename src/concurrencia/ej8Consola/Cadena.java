package concurrencia.ej8Consola;

import java.util.concurrent.Semaphore;

import static concurrencia.ej8Consola.Main.actualizar;

public class Cadena {

	Semaphore s = new Semaphore(1, true);
	int[] cinta = new int[3];
	int contador = 0, x;
	private boolean pausa = false;

	public Cadena() {
		for (int i = 0; i < cinta.length; i++) {
			cinta[i] = 0;
		}
	}

	public void colocar() {
		try {
			if (!pausa) {
				s.acquire();
				for (int i = 0; i < cinta.length; i++) {
					if (cinta[i] == 0) {
						while (cinta[i] == 0) {
							x = (int) Math.round(Math.random() * 3);
							cinta[i] = x;
						}
						actualizar("El colocador ha metido un " + x + "\n");
					}

				}
				s.release();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void retirar(int tipo) {
		try {
			if (!pausa) {
				s.acquire();
				for (int i = 0; i < cinta.length; i++) {
					if (cinta[i] == tipo) {
						actualizar(Thread.currentThread().getId() + " ha retirado " + cinta[i] + "\n");
						cinta[i] = 0;
						contador++;
						actualizar("Se han retirado " + contador + " productos en total\n");
					}
				}
				s.release();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void parar() {
		pausa = true;
	}

	public void reanudar() {
		pausa = false;
		notifyAll();
	}
}
