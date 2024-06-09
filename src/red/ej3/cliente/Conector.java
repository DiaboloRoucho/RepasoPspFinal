package red.ej3.cliente;

import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Conector extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Main frame;
	private JTextField dir = new JTextField("localhost");
	private Socket socket;
	private DataOutputStream out;

	public Conector(Main frame) {
		this.frame = frame;
		add(new JLabel("Dirección:"));
		dir.setColumns(15);
		add(dir);
		JButton con = new JButton("Conectar");
		con.setActionCommand("conectar");
		con.addActionListener(this::conectarDesconectar);
		add(con);
	}
	
	private Thread t;
	private void conectarDesconectar(ActionEvent cmd) {
		JButton src = (JButton) cmd.getSource();
		if (cmd.getActionCommand().equals("conectar")) {
			src.setText("Desconectar");
			src.setActionCommand("desconectar");
			dir.setEditable(false);
			try {
				socket = new Socket(dir.getText(), 9999);
				t = new Receptor(socket, frame.getEcho());
				out = new DataOutputStream(socket.getOutputStream());
				t.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.conexionOn();
		}
		else {
			src.setText("Conectar");
			src.setActionCommand("conectar");
			dir.setEditable(true);
			try {
				socket.shutdownOutput();
				t.join();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			out = null;
			frame.conexionOff();
		}
	}
	
	
	public boolean enviar(String texto) {
		if (out != null) {
			try {
				out.writeUTF(texto);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
		
	}
	
}
