package concurrencia.ej8Consola;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Main extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	private static final JTextArea textArea = new JTextArea();
	private JButton pausa = new JButton("PAUSA");
	private JButton reanudar = new JButton("REANUDAR");
	
//	static Parque p;
//	static int np;
//	static Scanner in = new Scanner(new InputStreamReader(System.in));
//	static ExecutorService personas;
	private static Cadena c = new Cadena();
	private static Colocador colocador = new Colocador(c);
	private static Empaquetador e1 = new Empaquetador(1, c);
	private static Empaquetador e2 = new Empaquetador(2, c);
	private static Empaquetador e3 = new Empaquetador(3, c);
	
	
	public Main() {
		super("Cadena de montaje");
		this.addWindowListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setPreferredSize(new Dimension(900, 700));
		JPanel panel = new JPanel();
		pausa.addActionListener(this::pausa);
		panel.add(pausa, BorderLayout.WEST);
		reanudar.setEnabled(false);
		reanudar.addActionListener(this::reanudar);
		panel.add(reanudar, BorderLayout.EAST);
		contentPane.add(panel, BorderLayout.NORTH);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
	}
	
	public static void actualizar(String msg) {
		SwingUtilities.invokeLater(() -> textArea.append(msg));
	}
	
	private void pausa(ActionEvent e) {
		pausa.setEnabled(false);
		reanudar.setEnabled(true);
		textArea.append("PAUSADO\n");
		c.parar();
	}
	
	private void reanudar(ActionEvent e) {
		pausa.setEnabled(true);
		reanudar.setEnabled(false);
		textArea.append("REANUDADO\n");
		c.reanudar();
	}
	
	private void iniciar() {
		setVisible(true);
		colocador.start();
		e1.start();
		e2.start();
		e3.start();
	}
	
	private static void crear() {
		new Main().iniciar();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::crear);
	}
	
	
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		colocador.interrupt();
		e1.interrupt();
		e2.interrupt();
		e3.interrupt();
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
