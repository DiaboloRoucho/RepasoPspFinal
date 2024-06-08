package concurrencia.ej5;

import static concurrencia.ej5.Main.actualizar;

public class Fumador extends Thread{

	Ingrediente ingrediente;
	Mesa mesa;
	
	public Fumador(String nombre, Ingrediente ingrediente, Mesa mesa) {
		super(nombre);
		this.ingrediente = ingrediente;
		this.mesa = mesa;
	}

	@Override
	public void run() {
		while(!isInterrupted()) {
			try {
				sleep(100);
			} catch (InterruptedException e) {interrupt();}
			mesa.retirar(ingrediente);
			try {
				sleep(1000);
			} catch (InterruptedException e) { interrupt();}
			actualizar(getName() + " terminó de fumar\n");
		}
		actualizar (getName() + " finaliza su tarea");
	}
}
