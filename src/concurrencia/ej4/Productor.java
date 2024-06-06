package concurrencia.ej4;

public class Productor extends Thread{

	private Almacen almacen;
	private String name;
	
	public Productor(String name, Almacen almacen) {
		super(name);
		this.almacen = almacen;
	}
	
	@Override
	public void run() {
		while (true) {
			almacen.almacenar();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
