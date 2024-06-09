package red.ej2;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cliente {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Dirección del servidor
        int port = 12345; // Puerto del servidor
        
        try (Socket socket = new Socket(serverAddress, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Tomamos nota del tiempo antes de recibir la hora del servidor
            long requestTime = System.currentTimeMillis();
            
            String serverTime = in.readLine();
            
            // Tomamos nota del tiempo después de recibir la hora del servidor
            long responseTime = System.currentTimeMillis();
            
            // Calculamos el tiempo de ida y vuelta (RTT)
            long rtt = responseTime - requestTime;
            long delay = rtt / 2; // Asumimos que el retraso es la mitad del RTT
            
            // Convertimos la hora recibida del servidor a LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime serverDateTime = LocalDateTime.parse(serverTime, formatter);
            
            // Ajustamos la hora del servidor sumando el retraso estimado
            LocalDateTime adjustedTime = serverDateTime.plusMinutes(delay);
            
            System.out.println("Hora recibida del servidor: " + serverTime);
            System.out.println("Hora ajustada: " + adjustedTime.format(formatter));
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}

