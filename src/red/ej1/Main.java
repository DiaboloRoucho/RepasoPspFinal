package red.ej1;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ImageDownloader <URL>");
            return;
        }

        String url = args[0];

        try {
            // Obtener el contenido HTML de la página web
            String html = fetchHTML(url);
            
            // Extraer las URLs de las imágenes
            Pattern pattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(html);
            
            // Crear un directorio para guardar las imágenes
            Files.createDirectories(Paths.get("images"));
            
            while (matcher.find()) {
                String imgSrc = matcher.group(1);
                
                // Si la URL es relativa, convertirla a absoluta
                if (!imgSrc.startsWith("http")) {
                    URL base = new URL(url);
                    imgSrc = new URL(base, imgSrc).toString();
                }
                
                // Descargar la imagen
                downloadImage(imgSrc);
            }
            
            System.out.println("Download completed.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String fetchHTML(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    private static void downloadImage(String src) {
        try (InputStream in = new URL(src).openStream()) {
            String fileName = "images/" + src.substring(src.lastIndexOf("/") + 1);
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Downloaded: " + fileName);
        } catch (IOException e) {
            System.err.println("Error downloading image: " + src);
            e.printStackTrace();
        }
    }
}

