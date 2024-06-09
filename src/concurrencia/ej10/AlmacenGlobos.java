package concurrencia.ej10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static concurrencia.ej10Consola.Main.actualizar;

public class AlmacenGlobos {

	private final List<Globos> globos;
	private int siguienteId;
	private final Lock lock;
	private final Condition noVacio;
	private final Condition noLleno;
	private final Condition maxHinchando;
	private int hinchandoCount;
	private boolean pausa = false;

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
			actualizar("GLOBO " + globo.getId() + " ENTREGADO\n");
			return globo;
		} finally {
			lock.unlock();
		}
	}

	public void empezarHinchando(Globos globo) throws InterruptedException {
		if (!pausa) {
			lock.lock();
			try {
				while (hinchandoCount == 3) {
					maxHinchando.await();
				}
				globo.setHinchando(true);
				hinchandoCount++;
				actualizar("GLOBO " + globo.getId() + " COMIENZA A HINCHAR");
			} finally {
				lock.unlock();
			}
		} else
			try {
				Thread.currentThread().wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
	}

	public void terminarHinchando(Globos globo) {
		if (!pausa) {
			lock.lock();
			try {
				globo.setHinchando(false);
				hinchandoCount--;
				maxHinchando.signalAll();
			} finally {
				lock.unlock();
			}
		} else
			try {
				Thread.currentThread().wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
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
		if (!pausa) {
			lock.lock();
			try {
				globos.remove(globo);
				noLleno.signalAll();
			} finally {
				lock.unlock();
			}
		} else
			try {
				Thread.currentThread().wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
	}

	public void parar() {
		pausa = true;
	}

	public void reanudar() {
		pausa = false;
		notifyAll();
	}
}
