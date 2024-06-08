package concurrencia.ej7Consola;

import java.util.concurrent.locks.ReentrantLock;

class Palillo {
    private final ReentrantLock lock = new ReentrantLock();

    public void tomar() {
        lock.lock();
    }

    public void soltar() {
        lock.unlock();
    }

    public boolean isHeld() {
        return lock.isLocked();
    }
}
