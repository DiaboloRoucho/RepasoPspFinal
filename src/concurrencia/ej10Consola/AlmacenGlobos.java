package concurrencia.ej10Consola;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlmacenGlobos {

	private final List<Globos> globos;
    private int siguienteId;
    private final Lock lock;
    private final Condition noVacio;
    private final Condition noLleno;
    private final Condition maxHinchando;
    private int hinchandoCount;

    public AlmacenGlobos() {
        globos = new ArrayList<>();
        siguienteId = 1;
        lock = new ReentrantLock();
        noVacio = lock.newCondition();
        noLleno = lock.newCondition();
        maxHinchando = lock.newCondition();
        hinchandoCount = 0;
    }

    public Globos entregarGlobo() throws InterruptedException {
        lock.lock();
        try {
            while (globos.size() == 10) {
                noLleno.await();
            }
            Globos globo = new Globos(siguienteId++);
            globos.add(globo);
            noVacio.signalAll();
            System.out.println("GLOBO " + globo.getId() + " ENTREGADO");
            return globo;
        } finally {
            lock.unlock();
        }
    }

    public void empezarHinchando(Globos globo) throws InterruptedException {
        lock.lock();
        try {
            while (hinchandoCount == 3) {
                maxHinchando.await();
            }
            globo.setHinchando(true);
            hinchandoCount++;
            System.out.println("GLOBO " + globo.getId() + " COMIENZA A HINCHAR");
        } finally {
            lock.unlock();
        }
    }

    public void terminarHinchando(Globos globo) {
        lock.lock();
        try {
            globo.setHinchando(false);
            hinchandoCount--;
            maxHinchando.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Globos obtenerGloboHinchando() throws InterruptedException {
        lock.lock();
        try {
            while (hinchandoCount == 0) {
                noVacio.await();
            }
            for (Globos globo : globos) {
                if (globo.isHinchando()) {
                    return globo;
                }
            }
            return null; // No debería suceder
        } finally {
            lock.unlock();
        }
    }

    public void quitarGlobo(Globos globo) {
        lock.lock();
        try {
            globos.remove(globo);
            noLleno.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
