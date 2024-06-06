package concurrencia.ej4;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner in = new Scanner(new InputStreamReader(System.in));
		System.out.println("Introduce el tamaño del almacen");
		Almacen a = new Almacen(in.nextInt());
		Productor p1 = new Productor("p1", a);
		Productor p2 = new Productor("p2", a);
		Consumidor c1 = new Consumidor("c1", a);
		Consumidor c2 = new Consumidor("c2", a);
		p1.start();
		p2.start();
		c1.start();
		c2.start();
	}

}
