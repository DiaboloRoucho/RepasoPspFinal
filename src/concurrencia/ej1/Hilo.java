package concurrencia.ej1;

public class Hilo extends Thread{

	public Hilo(String name) {
		super(name);
	}
	@Override
	public void run() {
		System.out.println("Hola soy el hilo " + this.getName());
		try {
			Thread.sleep(5000);
			System.out.println("Soy " + this.getName() + " y voy a morir");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
