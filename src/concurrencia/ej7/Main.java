package concurrencia.ej7;



public class Main {

	public static void main(String[] args) {
		Filosofo[] filosofos = new Filosofo[5];
		Palillo[] palillos = new Palillo[5];
		String[] x = {"CHANCHUN", "CHENCHON", "CHINCHON", "CHONCHIN", "CHUNCHAN"};
		Mesa m = new Mesa(5);
		
		for (int i = 0; i < palillos.length; i++) {
			palillos[i] = new Palillo();
		}
		for (int i = 0; i < palillos.length; i++) {
			Palillo pizq = palillos[i];
			Palillo pder = palillos[(i+1) % 5];
			filosofos[i] = new Filosofo(x[i], pizq, pder, m);
			filosofos[i].start();
		}
	}
	
	
}
