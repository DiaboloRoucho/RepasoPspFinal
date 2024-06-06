package concurrencia.ej2;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Hilo tic = new Hilo("TIC");
		Hilo tac = new Hilo("TAC");
		tic.start();
		try {
			Thread.currentThread().sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tac.start();
	}

}
