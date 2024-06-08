package concurrencia.ej8Consola;

public class Colocador extends Thread{

	Cadena cadena;
	
	public Colocador(Cadena cadena) {
		this.cadena = cadena;
	}
	
	
	@Override
	public void run() {
		while (true) {
			cadena.colocar();
		}
	}
}
