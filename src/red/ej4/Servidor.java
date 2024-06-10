package red.ej4;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor de operaciones matemáticas iniciado en el puerto " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String request = in.readLine();
                    String[] parts = request.split(" ");
                    String operation = parts[0];
                    double result = 0;

                    switch (operation) {
                        case "ADD":
                            double sumando1 = Double.parseDouble(parts[1]);
                            double sumando2 = Double.parseDouble(parts[2]);
                            result = sumando1 + sumando2;
                            break;
                        case "RES":
                            double minuendo = Double.parseDouble(parts[1]);
                            double substraendo = Double.parseDouble(parts[2]);
                            result = minuendo - substraendo;
                            break;
                        case "MUL":
                            double factor1 = Double.parseDouble(parts[1]);
                            double factor2 = Double.parseDouble(parts[2]);
                            result = factor1 * factor2;
                            break;
                        case "DIV":
                            double dividendo = Double.parseDouble(parts[1]);
                            double divisor = Double.parseDouble(parts[2]);
                            if (divisor != 0) {
                                result = dividendo / divisor;
                            } else {
                                out.println("Error: División por cero");
                                continue;
                            }
                            break;
                        case "SQRT":
                            double operando = Double.parseDouble(parts[1]);
                            if (operando >= 0) {
                                result = Math.sqrt(operando);
                            } else {
                                out.println("Error: Raíz cuadrada de número negativo");
                                continue;
                            }
                            break;
                        default:
                            out.println("Error: Operación no válida");
                            continue;
                    }

                    out.println(result);
                } catch (IOException | NumberFormatException e) {
                    System.err.println("Error al manejar la conexión del cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}

