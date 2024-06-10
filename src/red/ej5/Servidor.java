package red.ej5;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    private static final int PORT = 12345;
    private static List<GameHandler> waitingPlayers = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("Servidor de Tres en Raya iniciado...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                GameHandler handler = new GameHandler(clientSocket);
                synchronized (waitingPlayers) {
                    if (waitingPlayers.isEmpty()) {
                        waitingPlayers.add(handler);
                    } else {
                        GameHandler opponent = waitingPlayers.remove(0);
                        handler.setOpponent(opponent);
                        opponent.setOpponent(handler);
                        new Thread(handler).start();
                        new Thread(opponent).start();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class GameHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GameHandler opponent;
    private char[] board = ".........".toCharArray();
    private boolean myTurn = false;

    public GameHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setOpponent(GameHandler opponent) {
        this.opponent = opponent;
    }

    @Override
    public void run() {
        try {
            out.println("WELCOME");
            if (opponent != null) {
                out.println("START OPPONENT");
                opponent.out.println("START YOU");
                opponent.myTurn = true;
            }

            String input;
            while ((input = in.readLine()) != null) {
                if (input.startsWith("MOVE")) {
                    int position = Integer.parseInt(input.substring(5));
                    if (myTurn && board[position] == '.') {
                        board[position] = myTurn ? 'X' : 'O';
                        myTurn = !myTurn;
                        opponent.myTurn = !opponent.myTurn;
                        out.println("VALID_MOVE");
                        opponent.out.println("OPPONENT_MOVED " + position);
                        if (checkWin()) {
                            out.println("VICTORY");
                            opponent.out.println("DEFEAT");
                            break;
                        } else if (boardFull()) {
                            out.println("TIE");
                            opponent.out.println("TIE");
                            break;
                        }
                    } else {
                        out.println("INVALID_MOVE");
                    }
                } else if (input.startsWith("QUIT")) {
                    opponent.out.println("OPPONENT_QUIT");
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
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals
        int[][] winPatterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}              // diagonals
        };

        for (int[] pattern : winPatterns) {
            if (board[pattern[0]] == board[pattern[1]] &&
                board[pattern[1]] == board[pattern[2]] &&
                board[pattern[0]] != '.') {
                return true;
            }
        }
        return false;
    }

    private boolean boardFull() {
        for (char c : board) {
            if (c == '.') {
                return false;
            }
        }
        return true;
    }
}
