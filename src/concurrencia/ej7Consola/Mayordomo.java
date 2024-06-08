package concurrencia.ej7Consola;

import java.util.concurrent.Semaphore;

class Mayordomo {
    private final Semaphore semaphore;

    public Mayordomo(int numFilosofos) {
        this.semaphore = new Semaphore(numFilosofos - 1);
    }

    public void solicitarPermiso() throws InterruptedException {
        semaphore.acquire();
    }

    public void liberarPermiso() {
        semaphore.release();
    }
}
