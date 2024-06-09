package red.ej3.servidor;

import java.io.DataOutputStream;
import java.util.LinkedList;

public class Almacen {

	private LinkedList<Item> cola = new LinkedList<>();
	private static final int MAX = 1000;
	
	public synchronized void almacenar(String s, DataOutputStream out) {
		while (cola.size() == MAX)
			try {
				wait();
			} catch (InterruptedException e) {}
		cola.offer(new Item(s, out));
		notify();
	}
	
	public synchronized Item retirar() {
		try {
			while (cola.isEmpty())
				wait();
			Item i = cola.poll();
			notify();
			return i;
		} catch (InterruptedException e) {
			return null;
		}
	}
}
