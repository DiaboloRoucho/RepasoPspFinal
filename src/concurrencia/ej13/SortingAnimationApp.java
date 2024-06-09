package concurrencia.ej13;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SortingAnimationApp extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton startButton;
    private JCheckBox insertionCheckBox;
    private JCheckBox selectionCheckBox;
    private JCheckBox bubbleCheckBox;
    private JPanel animationPanel;

    public SortingAnimationApp() {
        setTitle("Sorting Animation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        insertionCheckBox = new JCheckBox("Insertion Sort");
        selectionCheckBox = new JCheckBox("Selection Sort");
        bubbleCheckBox = new JCheckBox("Bubble Sort");
        startButton = new JButton("Start");

        controlPanel.add(insertionCheckBox);
        controlPanel.add(selectionCheckBox);
        controlPanel.add(bubbleCheckBox);
        controlPanel.add(startButton);

        animationPanel = new JPanel();
        animationPanel.setLayout(new GridLayout(1, 3));

        add(controlPanel, BorderLayout.NORTH);
        add(animationPanel, BorderLayout.CENTER);

        startButton.addActionListener(new Main());
    }

    private class Main implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            animationPanel.removeAll();

            if (insertionCheckBox.isSelected()) {
                SortingPanel insertionPanel = new SortingPanel("Insertion Sort");
                animationPanel.add(insertionPanel);
                new Thread(new InsertionSortRunnable(insertionPanel)).start();
            }
            if (selectionCheckBox.isSelected()) {
                SortingPanel selectionPanel = new SortingPanel("Selection Sort");
                animationPanel.add(selectionPanel);
                new Thread(new SelectionSortRunnable(selectionPanel)).start();
            }
            if (bubbleCheckBox.isSelected()) {
                SortingPanel bubblePanel = new SortingPanel("Bubble Sort");
                animationPanel.add(bubblePanel);
                new Thread(new BubbleSortRunnable(bubblePanel)).start();
            }

            animationPanel.revalidate();
            animationPanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingAnimationApp app = new SortingAnimationApp();
            app.setVisible(true);
        });
    }
}

