package concurrencia.ej11;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EscribeLetras extends Thread {
    private final char letra;
    private final int repeticiones;
    private final ControlTurno controlTurno;

    public EscribeLetras(char letra, int repeticiones, ControlTurno controlTurno) {
        this.letra = Character.toUpperCase(letra);
        this.repeticiones = repeticiones;
        this.controlTurno = controlTurno;
    }

    @Override
    public void run() {
        try {
            while (true) {
                controlTurno.esperarTurno(letra);
                for (int i = 0; i < repeticiones; i++) {
                    System.out.print(letra);
                    Thread.sleep(500);
                }
                controlTurno.siguienteTurno();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ControlTurno {
    private final List<Character> letras;
    private int turno;
    private final Lock lock;
    private final Condition condition;

    public ControlTurno(List<Character> letras) {
        this.letras = letras;
        this.turno = 0;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public void esperarTurno(char letra) throws InterruptedException {
        lock.lock();
        try {
            while (letras.get(turno) != letra) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void siguienteTurno() {
        lock.lock();
        try {
            turno = (turno + 1) % letras.size();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el patrón:");
        String patron = scanner.nextLine().toUpperCase();

        Map<Character, Integer> mapaLetras = new LinkedHashMap<>();

        try {
            for (int i = 0; i < patron.length(); i += 2) {
                char letra = patron.charAt(i);
                if (!Character.isLetter(letra)) {
                    throw new IllegalArgumentException("Patrón incorrecto.");
                }
                int repeticiones = Character.getNumericValue(patron.charAt(i + 1));
                if (repeticiones <= 0) {
                    throw new IllegalArgumentException("Patrón incorrecto.");
                }
                mapaLetras.put(letra, mapaLetras.getOrDefault(letra, 0) + repeticiones);
            }
        } catch (Exception e) {
            System.err.println("Error: Patrón incorrecto.");
            return;
        }

        List<Character> ordenLetras = new ArrayList<>(mapaLetras.keySet());
        ControlTurno controlTurno = new ControlTurno(ordenLetras);

        for (Map.Entry<Character, Integer> entry : mapaLetras.entrySet()) {
            new EscribeLetras(entry.getKey(), entry.getValue(), controlTurno).start();
        }
    }
}

