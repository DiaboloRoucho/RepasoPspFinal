package concurrencia.ej5;

import static concurrencia.ej5.Main.actualizar;

import java.util.HashSet;
import java.util.stream.Collectors;



public class Mesa {

	HashSet<Ingrediente> ingredientes = new HashSet<>();
	private boolean pausa = false;
	public synchronized void depositar(Ingrediente i1, Ingrediente i2) {
		if (!pausa) {
			while (!ingredientes.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			ingredientes.add(i1);
			ingredientes.add(i2);
			actualizar("El agente depositó " + i1 + " y " + i2 + "\n");
			notifyAll();
		}else {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	
	public synchronized void retirar(Ingrediente i) {
		String fumador = Thread.currentThread().getName();
		if (!pausa) {
			while (ingredientes.isEmpty() || ingredientes.contains(i)) {
				actualizar(fumador + " tiene que esperar\n");
				try {
					wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			actualizar(ingredientes.stream().map(j -> j.toString())
					.collect(Collectors.joining(" y ", fumador + " retira ", "\n")));
			ingredientes.clear();
			notifyAll();
		} else
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
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
