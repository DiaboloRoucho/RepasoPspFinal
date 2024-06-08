package concurrencia.ej7Consola;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

class Mesa extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Filosofo[] filosofos;
    private final Palillo[] palillos;

    public Mesa(Filosofo[] filosofos, Palillo[] palillos) {
        this.filosofos = filosofos;
        this.palillos = palillos;
        setPreferredSize(new Dimension(600, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;

        // Draw table
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        // Draw philosophers
        for (int i = 0; i < filosofos.length; i++) {
            double angle = 2 * Math.PI * i / filosofos.length;
            int philosopherX = centerX + (int) (radius * Math.cos(angle));
            int philosopherY = centerY + (int) (radius * Math.sin(angle));

            // Draw plate
            g.setColor(Color.WHITE);
            g.fillOval(philosopherX - 30, philosopherY - 30, 60, 60);

            // Draw action text
            g.setColor(Color.BLACK);
            String estado = switch (filosofos[i].getEstado()) {
                case PENSANDO -> "Pensando";
                case ESPERANDO -> "Esperando";
                case COMIENDO -> "Comiendo";
            };
            g.drawString(estado, philosopherX - 5, philosopherY + 5);
        }

        for (int i = 0; i < palillos.length; i++) {
            double angle = 2 * Math.PI * i / palillos.length;
            int chopstickStartX = centerX + (int) ((radius - 30) * Math.cos(angle));
            int chopstickStartY = centerY + (int) ((radius - 30) * Math.sin(angle));
            int chopstickEndX = centerX + (int) ((radius + 30) * Math.cos(angle));
            int chopstickEndY = centerY + (int) ((radius + 30) * Math.sin(angle));

            if (palillos[i].isHeld()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GRAY);
            }
            g.drawLine(chopstickStartX, chopstickStartY, chopstickEndX, chopstickEndY);
        }
    }
}
