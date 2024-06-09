package red.ej3.cliente;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Receptor extends Thread {
	
	private Socket socket;
	private JTextArea texto;
	
	public Receptor(Socket socket, JTextArea texto) {
		this.socket = socket;
		this.texto = texto;		
	}
	
	private void actualizar(String s) {
		SwingUtilities.invokeLater(() -> texto.append(s));
	}
	
	@Override
	public void run() {
		System.out.println("receptor para " + socket.getInetAddress());
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			while (true)
				actualizar(in.readUTF() + "\n");
		} catch (EOFException e) {
			System.out.println("Recibido EOF del servidor");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Receptor finalizado");
	}
	
}