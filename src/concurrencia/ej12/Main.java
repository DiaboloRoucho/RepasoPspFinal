package concurrencia.ej12;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Piscina {
    private final Semaphore calles;
    private int hombres, mujeres, ninos, ninas, submarinistas;

    public Piscina(int maxCalles) {
        this.calles = new Semaphore(maxCalles);
        this.hombres = 0;
        this.mujeres = 0;
        this.ninos = 0;
        this.ninas = 0;
        this.submarinistas = 0;
    }

    public void entrar(String tipo, String nombre) throws InterruptedException {
        int callesNecesarias = tipo.equals("Submarinista") ? 2 : 1;
        calles.acquire(callesNecesarias);
        synchronized (this) {
            if (tipo.equals("Hombre")) hombres++;
            else if (tipo.equals("Mujer")) mujeres++;
            else if (tipo.equals("Niño")) ninos++;
            else if (tipo.equals("Niña")) ninas++;
            else if (tipo.equals("Submarinista")) submarinistas++;
            mostrarEstado(nombre, "entra");
        }
    }

    public void salir(String tipo, String nombre) {
        int callesNecesarias = tipo.equals("Submarinista") ? 2 : 1;
        synchronized (this) {
            actualizarContador(tipo, -1);
            mostrarEstado(nombre, "sale");
        }
        calles.release(callesNecesarias);
    }

    private void actualizarContador(String tipo, int delta) {
        switch (tipo) {
            case "Hombre": hombres += delta; break;
            case "Mujer": mujeres += delta; break;
            case "Niño": ninos += delta; break;
            case "Niña": ninas += delta; break;
            case "Submarinista": submarinistas += delta; break;
        }
    }

    private synchronized void mostrarEstado(String nombre, String accion) {
        System.out.printf("%s %s. Hombres: %d, Mujeres: %d, Niños: %d, Niñas: %d, Submarinistas: %d\n",
                nombre, accion, hombres, mujeres, ninos, ninas, submarinistas);
    }

}

class Usuario implements Runnable {
    private final String tipo;
    private final String nombre;
    private final Piscina piscina;

    public Usuario(String tipo, String nombre, Piscina piscina) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.piscina = piscina;
    }

    @Override
    public void run() {
        try {
            piscina.entrar(tipo, nombre);
            Thread.sleep(new Random().nextInt(31) + 50);
            piscina.salir(tipo, nombre);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Piscina piscina = new Piscina(8);
        ExecutorService executor = Executors.newFixedThreadPool(20);

        String[] tipos = {"Hombre", "Mujer", "Niño", "Niña", "Submarinista"};
        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            String tipo = tipos[random.nextInt(tipos.length)];
            String nombre = tipo + i;
            executor.execute(new Usuario(tipo, nombre, piscina));
        }

        executor.shutdown();
    }
}

