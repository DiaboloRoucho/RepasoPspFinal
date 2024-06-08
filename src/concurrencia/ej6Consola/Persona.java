package concurrencia.ej6Consola;

import static concurrencia.ej6Consola.Main.actualizar;

public class Persona extends Thread {

	Parque p;
	String name;

	public Persona( Parque p) {
		this.p = p;
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				actualizar(getName() + " esta paseando\n");
				Thread.sleep((long) (Math.random() * 2000) + 1000);
			} catch (InterruptedException e) {
				interrupt();
			}
			p.sentarse();
		}
	}

	
}
