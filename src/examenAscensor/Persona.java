package examenAscensor;

import java.util.Random;

public class Persona extends Thread {
    private String id;
    private int plantaActual;
    private int plantaDestino;
    private Edificio edificio;
    private Random random = new Random();

    public Persona(int n, Edificio edificio) {
        this.id = "P"+n;
        this.edificio = edificio;
        this.plantaActual = random.nextInt(20);
        do {
            this.plantaDestino = random.nextInt(20);
        } while (this.plantaActual == this.plantaDestino);
    }

    public int getPlantaActual() {
        return plantaActual;
    }
    public String getid() {
    	return id;
    }

    public int getPlantaDestino() {
        return plantaDestino;
    }

    public void setPlantaActual(int planta) {
        this.plantaActual = planta;
    }

    @Override
    public void run() {
        try {
            edificio.solicitarAscensor(this);
        } catch (InterruptedException e) {
        	interrupt();
        }
    }
}

