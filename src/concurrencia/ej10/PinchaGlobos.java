package concurrencia.ej10;

import java.util.Random;
import static concurrencia.ej10Consola.Main.actualizar;

public class PinchaGlobos extends Thread{

	 private final AlmacenGlobos almacen;
	    private final String nombre;
	    private final Random random;

	    public PinchaGlobos(AlmacenGlobos almacen, String nombre) {
	        this.almacen = almacen;
	        this.nombre = nombre;
	        this.random = new Random();
	    }

	    @Override
	    public void run() {
	        try {
	            while (!isInterrupted()) {
	                Thread.sleep((random.nextInt(10) + 1) * 1000);
	                Globos globo = almacen.obtenerGloboHinchando();
	                if (globo != null) {
	                    actualizar("GLOBO " + globo.getId() + " PINCHADO POR " + nombre + "\n");
	                    almacen.terminarHinchando(globo);
	                    almacen.quitarGlobo(globo);
	                }
	            }
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }
}
