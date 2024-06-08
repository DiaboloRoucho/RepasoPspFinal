package concurrencia.ej7Consola;

class Filosofo extends Thread {
    private final int id;
    private final Palillo palilloIzquierdo;
    private final Palillo palilloDerecho;
    private final Mayordomo mayordomo;
    private EstadoFilosofo estado;
    private final Mesa mesa;
    private volatile boolean pausado;

    public Filosofo(int id, Palillo palilloIzquierdo, Palillo palilloDerecho, Mayordomo mayordomo, Mesa mesa) {
        this.id = id;
        this.palilloIzquierdo = palilloIzquierdo;
        this.palilloDerecho = palilloDerecho;
        this.mayordomo = mayordomo;
        this.mesa = mesa;
        this.estado = EstadoFilosofo.PENSANDO;
    }

    private void pensar() {
        estado = EstadoFilosofo.PENSANDO;
        mesa.repaint();
        try {
            Thread.sleep(((int) ((Math.random() * 5000)+2000)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void comer() {
        estado = EstadoFilosofo.COMIENDO;
        mesa.repaint();
        try {
            Thread.sleep(((int) ((Math.random() * 10000)+2000)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public EstadoFilosofo getEstado() {
        return estado;
    }

    public void pausar() {
        pausado = true;
    }

    public void reanudar() {
        pausado = false;
        synchronized (this) {
            notify();
        }
    }

    private void comprobarPausa() throws InterruptedException {
        synchronized (this) {
            while (pausado) {
                wait();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                pensar();
                estado = EstadoFilosofo.ESPERANDO;
                mesa.repaint();
                mayordomo.solicitarPermiso();
                palilloIzquierdo.tomar();
                palilloDerecho.tomar();
                comer();
                palilloIzquierdo.soltar();
                palilloDerecho.soltar();
                mayordomo.liberarPermiso();
                comprobarPausa();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}