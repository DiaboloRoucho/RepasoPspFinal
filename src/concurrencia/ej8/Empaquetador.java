package concurrencia.ej8;

public class Empaquetador extends Thread{

	int tipo;
	long id;
	Cadena cadena;
	
	public Empaquetador(int tipo, Cadena cadena) {
		this.id = tipo;
		this.tipo = tipo;
		this.cadena = cadena;
	}
	
	@Override
	public void run() {
		while (true) {
			cadena.retirar(tipo);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}
	
	@Override
	public long getId() {
		return id;
	}
}
