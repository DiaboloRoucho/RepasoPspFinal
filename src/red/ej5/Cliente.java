package red.ej5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Cliente extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JButton[] buttons = new JButton[9];
    private JLabel statusLabel;
    private boolean myTurn = false;

    public Cliente(String serverAddress) throws IOException {
        socket = new Socket(serverAddress, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        setTitle("Tres en Raya");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 60));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            boardPanel.add(buttons[i]);
        }
        add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Conectando...");
        add(statusLabel, BorderLayout.SOUTH);

        new Thread(() -> {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    if (response.startsWith("WELCOME")) {
                        statusLabel.setText("Esperando oponente...");
                    } else if (response.startsWith("START YOU")) {
                        myTurn = true;
                        statusLabel.setText("Tu turno");
                    } else if (response.startsWith("START OPPONENT")) {
                        myTurn = false;
                        statusLabel.setText("Turno del oponente");
                    } else if (response.startsWith("VALID_MOVE")) {
                        statusLabel.setText("Turno del oponente");
                    } else if (response.startsWith("OPPONENT_MOVED")) {
                        int loc = Integer.parseInt(response.substring(15));
                        buttons[loc].setText(myTurn ? "O" : "X");
                        myTurn = true;
                        statusLabel.setText("Tu turno");
                    } else if (response.startsWith("VICTORY")) {
                        statusLabel.setText("¡Ganaste!");
                        break;
                    } else if (response.startsWith("DEFEAT")) {
                        statusLabel.setText("Perdiste");
                        break;
                    } else if (response.startsWith("TIE")) {
                        statusLabel.setText("Empate");
                        break;
                    } else if (response.startsWith("OPPONENT_QUIT")) {
                        statusLabel.setText("El oponente se rindió");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!myTurn) {
            return;
        }
        JButton buttonClicked = (JButton) e.getSource();
        int position = -1;
        for (int i = 0; i < 9; i++) {
            if (buttons[i] == buttonClicked) {
                position = i;
                break;
            }
        }
        if (position != -1 && buttonClicked.getText().equals("")) {
            out.println("MOVE " + position);
            buttonClicked.setText(myTurn ? "X" : "O");
            myTurn = false;
            statusLabel.setText("Turno del oponente");
        }
    }

    public static void main(String[] args) throws IOException {
        String serverAddress = JOptionPane.showInputDialog(
                "Introduce la dirección del servidor:");
        Cliente client = new Cliente(serverAddress);
        client.setVisible(true);
    }
}

