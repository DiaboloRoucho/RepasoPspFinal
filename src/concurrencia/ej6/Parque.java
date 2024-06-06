package concurrencia.ej6;

import java.util.concurrent.Semaphore;

public class Parque {

	Semaphore banco;
	int plazas;
	
	public Parque(int plazas) {
		this.plazas = plazas;
		banco = new Semaphore(plazas);
	}
	
	public Semaphore getBanco() {
		return banco;
	}
}
