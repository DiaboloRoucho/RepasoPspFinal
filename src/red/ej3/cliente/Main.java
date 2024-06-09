package red.ej3.cliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private Conector con = new Conector(this);
	private JTextArea texto = new JTextArea();
	private JTextArea echo = new JTextArea();
	private JButton enviar = new JButton("Enviar");
	private Border borde1 = BorderFactory.createLineBorder(Color.DARK_GRAY, 3);
	private Border borde2 = BorderFactory.createLineBorder(Color.GREEN, 3);
	private Border borde3 = BorderFactory.createLineBorder(Color.RED, 3);
	

	public Main() {
		super("Cliente ECHO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(con, BorderLayout.NORTH);
		
		texto.setFont(texto.getFont().deriveFont(30f));
		texto.setColumns(35);
		texto.setRows(20);
		texto.setEditable(false);
		texto.setBorder(borde1);
		JScrollPane sp = new JScrollPane(texto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(sp, BorderLayout.WEST);
		echo.setFont(texto.getFont().deriveFont(30f));
		echo.setColumns(35);
		echo.setRows(20);
		echo.setEditable(false);
		echo.setBorder(borde1);
		sp = new JScrollPane(echo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(sp, BorderLayout.EAST);
		enviar = new JButton("enviar");
		enviar.setEnabled(false);
		enviar.addActionListener(this::enviar);
		add(enviar, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void conexionOn() {
		texto.setEditable(true);
		texto.setBorder(borde2);
		echo.setBorder(borde3);
		enviar.setEnabled(true);
		texto.requestFocus();
	}
	
	public void conexionOff() {
		echo.setText("");
		texto.setEditable(false);
		texto.setBorder(borde1);
		echo.setBorder(borde1);
		enviar.setEnabled(false);
	}
	
	private void enviar(ActionEvent e) {
		con.enviar(texto.getText());
		texto.setText("");
		texto.requestFocus();
	}
	
	public JTextArea getEcho() {
		return echo;
	}
	
	private void iniciar() {
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main()::iniciar);
	}
	
	
}
