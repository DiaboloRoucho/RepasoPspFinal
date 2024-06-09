package concurrencia.ej13;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

class SortingPanel extends JPanel {
    private static final int NUM_ELEMENTS = 50;
    private int[] array;
    private String sortingMethod;

    public SortingPanel(String sortingMethod) {
        this.sortingMethod = sortingMethod;
        array = new int[NUM_ELEMENTS];
        Random random = new Random();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            array[i] = random.nextInt(300);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int barWidth = width / NUM_ELEMENTS;
        for (int i = 0; i < array.length; i++) {
            int barHeight = array[i];
            g.fillRect(i * barWidth, height - barHeight, barWidth, barHeight);
        }
        g.drawString(sortingMethod, 10, 20);
    }

    public void updateArray(int[] newArray) {
        array = Arrays.copyOf(newArray, newArray.length);
        repaint();
    }

    public int[] getArray() {
        return array;
    }
}
