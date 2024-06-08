package concurrencia.ej10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {

		 AlmacenGlobos almacen = new AlmacenGlobos();
	        ExecutorService executor = Executors.newFixedThreadPool(10);

	        for (int i = 1; i <= 5; i++) {
	            executor.execute(new HinchaGlobos(almacen, "HG" + i));
	            executor.execute(new PinchaGlobos(almacen, "PG" + i));
	        }

	        executor.shutdown();
	}

}
