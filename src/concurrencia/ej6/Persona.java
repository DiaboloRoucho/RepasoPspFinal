package concurrencia.ej6;

public class Persona extends Thread{

	Parque p;
	String name;
	
	public Persona(String name, Parque p) {
		super(name);
		this.p = p;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(getName() + " esta paseando");
				Thread.sleep((long)(Math.random()*2000)+1000);
				if (p.banco.availablePermits() == 0)
					System.out.println("El banco esta lleno, " + getName() + " espera");
				p.banco.acquire();
				System.out.println(getName() + " se sienta");
				Thread.sleep((long)(Math.random()*600)+100);
				System.out.println(getName() + " se levanta");
				p.banco.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
