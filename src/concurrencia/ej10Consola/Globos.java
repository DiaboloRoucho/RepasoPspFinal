package concurrencia.ej10Consola;

public class Globos {

	private final int id;
    private int volumen;
    private boolean hinchando;

    public Globos(int id) {
        this.id = id;
        this.volumen = 0;
        this.hinchando = false;
    }

    public int getId() {
        return id;
    }

    public int getVolumen() {
        return volumen;
    }

    public void hinchar() {
        volumen++;
    }

    public boolean isHinchando() {
        return hinchando;
    }

    public void setHinchando(boolean hinchando) {
        this.hinchando = hinchando;
    }
	
}
