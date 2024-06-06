package concurrencia.ej1;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner in = new Scanner(new InputStreamReader(System.in));
		System.out.println("Introduced el valor de N");
		int x=in.nextInt();
		Hilo[] h = new Hilo[x];
		for (int i = 0; i < x; i++) {
			h[i] = new Hilo(""+i);
			h[i].start();
		}
		for (int i = 0; i < h.length; i++) {
			try {
				h[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Fin de programa");
	}

}
