package concurrencia.ej10;

import static concurrencia.ej10Consola.Main.actualizar;

public class HinchaGlobos extends Thread{

	 private final AlmacenGlobos almacen;
	    private final String nombre;

	    public HinchaGlobos(AlmacenGlobos almacen, String nombre) {
	        this.almacen = almacen;
	        this.nombre = nombre;
	    }

	    @Override
	    public void run() {
	        try {
	            while (true) {
	                Globos globo = almacen.entregarGlobo();
	                almacen.empezarHinchando(globo);
	                while (globo.getVolumen() < 5) {
	                    Thread.sleep(1000);
	                    globo.hinchar();
	                    actualizar("GLOBO " + globo.getId() + " VOLUMEN " + globo.getVolumen() + "\n");
	                    if (globo.getVolumen() >= 5) {
	                        actualizar("GLOBO " + globo.getId() + " ESTALLA\n");
	                        break;
	                    }
	                }
	                almacen.terminarHinchando(globo);
	                almacen.quitarGlobo(globo);
	            }
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }
}
