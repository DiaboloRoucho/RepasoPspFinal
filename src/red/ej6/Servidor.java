package red.ej6;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PORT = 12345;
    private static Map<String, Set<String>> contacts = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Servidor de contactos iniciado...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String request = in.readLine();
                if (request != null) {
                    String response = handleRequest(request);
                    out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String handleRequest(String request) {
            try {
                if (request.startsWith("añadir:")) {
                    return handleAdd(request.substring(7).trim());
                } else if (request.startsWith("buscar:")) {
                    return handleSearch(request.substring(7).trim());
                } else if (request.startsWith("eliminar:")) {
                    return handleRemove(request.substring(9).trim());
                } else if (request.equals("contactos")) {
                    return handleList();
                } else {
                    return generateSyntaxError(request);
                }
            } catch (IndexOutOfBoundsException e) {
                return generateSyntaxError(request);
            }
        }

        private String handleAdd(String data) {
            String[] parts = data.split(":");
            if (parts.length != 2 || !parts[1].matches("\\d+")) {
                return generateSyntaxError("añadir:" + data);
            }

            String name = parts[0].trim();
            String phone = parts[1].trim();

            contacts.putIfAbsent(name, ConcurrentHashMap.newKeySet());

            Set<String> phones = contacts.get(name);

            synchronized (phones) {
                if (phones.contains(phone)) {
                    return "ERR01";
                }
                phones.add(phone);
            }

            return "OK";
        }

        private String handleSearch(String name) {
            Set<String> phones = contacts.get(name);

            if (phones == null) {
                return "ERR02";
            }

            StringBuilder response = new StringBuilder("OK");
            for (String phone : phones) {
                response.append("\n").append(phone);
            }

            return response.toString();
        }

        private String handleRemove(String name) {
            if (contacts.remove(name) == null) {
                return "ERR02";
            }
            return "OK";
        }

        private String handleList() {
            StringBuilder response = new StringBuilder("OK");

            List<String> sortedNames = new ArrayList<>(contacts.keySet());
            Collections.sort(sortedNames);

            for (String name : sortedNames) {
                response.append("\n").append(name);
            }

            return response.toString();
        }

        private String generateSyntaxError(String request) {
            StringBuilder response = new StringBuilder("ERR03\n");
            response.append(request).append("\n");

            // Marking the position of the error
            int errorPos = request.indexOf(':');
            if (errorPos == -1) {
                errorPos = request.length();
            }
            for (int i = 0; i < errorPos; i++) {
                response.append(" ");
            }
            response.append("^");

            return response.toString();
        }
    }
}