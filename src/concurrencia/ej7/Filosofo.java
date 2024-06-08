package concurrencia.ej7;



public class Filosofo extends Thread{

	Palillo izq, der;
	Mesa m;
	
	
	public Filosofo(String name, Palillo izq, Palillo der, Mesa m) {
		super(name);
		this.izq = izq;
		this.der = der;
		this.m = m;
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			pensar();
			try {
				m.intentarCoger();
				izq.coger();
				der.coger();
				comer();
				izq.dejar();
				der.dejar();
				m.soltar();
				
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}
	
	public long getRandomPensar() {
		long x =(long) (Math.random()*2000)+1000; 
		return x;
	}
	
	public long getRandomComer() {
		long x =(long) (Math.random()*3000)+2000; 
		return x;
	}
	
	private void pensar() {
		System.out.println("El filosofo " + getName() + " está pensando");
		try {
			Thread.sleep(getRandomPensar());
		} catch (InterruptedException e) {
			interrupt();
		}
	}
	
	private void comer() {
		System.out.println("El Filosofo " + getName() + " está comiendo");
		try {
			Thread.sleep(getRandomComer());
		} catch (InterruptedException e) {
			interrupt();
		}
	}
}
