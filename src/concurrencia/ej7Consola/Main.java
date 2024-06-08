package concurrencia.ej7Consola;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;



enum EstadoFilosofo {
    PENSANDO, ESPERANDO, COMIENDO
}


public class Main extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Mesa mesa;
    private final Filosofo[] filosofos;
    private Timer timer = null;

    public Main(int numFilosofos) {
        Palillo[] palillos = new Palillo[numFilosofos];
        for (int i = 0; i < numFilosofos; i++) {
            palillos[i] = new Palillo();
        }

        Mayordomo mayordomo = new Mayordomo(numFilosofos);
        filosofos = new Filosofo[numFilosofos];
        mesa = new Mesa(filosofos, palillos);
        for (int i = 0; i < numFilosofos; i++) {
            Palillo palilloIzquierdo = palillos[i];
            Palillo palilloDerecho = palillos[(i + 1) % numFilosofos];
            filosofos[i] = new Filosofo(i, palilloIzquierdo, palilloDerecho, mayordomo, mesa);
        }

        for (Filosofo filosofo : filosofos) {
            filosofo.start();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Filosofos Comensales");
        setSize(800, 800);
        setLayout(new BorderLayout());
        add(mesa, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton pauseButton = new JButton("Pausar");
        JButton resumeButton = new JButton("Reanudar");

        pauseButton.addActionListener(e -> {
            for (Filosofo filosofo : filosofos) {
                filosofo.pausar();
            }
            timer.stop();
        });

        resumeButton.addActionListener(e -> {
            for (Filosofo filosofo : filosofos) {
                filosofo.reanudar();
            }
            timer.start();
        });

        controls.add(pauseButton);
        controls.add(resumeButton);
        add(controls, BorderLayout.SOUTH);

        timer = new Timer(100, e -> mesa.repaint());
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main(5);
            frame.setVisible(true);
        });
    }
}
