package concurrencia.ej5;

import static concurrencia.ej5.Main.actualizar;


public class Agente extends Thread{

private Mesa mesa;
	
	public Agente(Mesa mesa) {
		super("Agente");
		this.mesa = mesa;
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			Ingrediente i1 = Ingrediente.get();
			Ingrediente i2 = Ingrediente.get();
			while(i1==i2)
				i2 = Ingrediente.get();
			mesa.depositar(i1, i2);
		}
		actualizar ("El agente finaliza su tarea");
	}
}
