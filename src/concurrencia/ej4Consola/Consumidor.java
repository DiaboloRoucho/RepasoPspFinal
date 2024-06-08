package concurrencia.ej4Consola;

public class Consumidor extends Thread{

	private Almacen almacen;
	private String name;
	
	public Consumidor(String name, Almacen almacen) {
		super(name);
		this.almacen = almacen;
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			almacen.retirar();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}
}
