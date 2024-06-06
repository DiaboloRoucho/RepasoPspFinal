package concurrencia.ej6;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner in = new Scanner(new InputStreamReader(System.in));
		System.out.println("Introduce el numero de plazas en el banco");
		Parque p = new Parque(in.nextInt());
		System.out.println("Introduce el numero de personas");
		int np = in.nextInt();
		Persona[] personas = new Persona[np];
		for (int i = 0; i < np; i++) {
			personas[i] = new Persona("p"+i, p);
		}
		for (int i = 0; i < np; i++) {
			personas[i].start();
		}
	}

}
