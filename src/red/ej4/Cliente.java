package red.ej4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Cliente extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField displayField, serverField;
    private StringBuilder input = new StringBuilder();
    private String operation = "";
    private double firstNumber = 0;
    private boolean newOperation = true;

    public Cliente() {
        setTitle("Calculadora Cliente/Servidor");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.PLAIN, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);

        serverField = new JTextField("localhost");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(displayField, BorderLayout.NORTH);
        panel.add(createButtonPanel(), BorderLayout.CENTER);
        panel.add(createServerPanel(), BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.addActionListener(e -> onButtonClick(text));
            panel.add(button);
        }

        return panel;
    }

    private JPanel createServerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Servidor:"), BorderLayout.WEST);
        panel.add(serverField, BorderLayout.CENTER);

        return panel;
    }

    private void onButtonClick(String text) {
        if ("0123456789.".contains(text)) {
            if (newOperation) {
                displayField.setText("");
                newOperation = false;
            }
            input.append(text);
            displayField.setText(input.toString());
        } else if ("+-*/".contains(text)) {
            firstNumber = Double.parseDouble(displayField.getText());
            operation = text;
            input.setLength(0);
        } else if ("=".equals(text)) {
            double secondNumber = Double.parseDouble(displayField.getText());
            performOperation(firstNumber, secondNumber);
        }
    }

    private void performOperation(double firstNumber, double secondNumber) {
        String serverAddress = serverField.getText().trim();
        if (serverAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce la dirección del servidor.");
            return;
        }

        String request = "";
        switch (operation) {
            case "+":
                request = "ADD " + firstNumber + " " + secondNumber;
                break;
            case "-":
                request = "SUB " + firstNumber + " " + secondNumber;
                break;
            case "*":
                request = "MUL " + firstNumber + " " + secondNumber;
                break;
            case "/":
                request = "DIV " + firstNumber + " " + secondNumber;
                break;
        }

        try (Socket socket = new Socket(serverAddress, 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(request);
            String response = in.readLine();
            displayField.setText(response);
            newOperation = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cliente::new);
    }
}


