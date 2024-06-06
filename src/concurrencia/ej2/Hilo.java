package concurrencia.ej2;

public class Hilo extends Thread{

	
	
	public Hilo(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		while (true) {
			System.out.println(this.getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
