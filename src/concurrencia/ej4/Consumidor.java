package concurrencia.ej4;

public class Consumidor extends Thread{

	private Almacen almacen;
	private String name;
	
	public Consumidor(String name, Almacen almacen) {
		super(name);
		this.almacen = almacen;
	}
	
	@Override
	public void run() {
		while (true) {
			almacen.retirar();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
