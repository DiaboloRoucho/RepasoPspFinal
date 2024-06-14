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
        this.plantaActual = random.nextInt(20); // Planta inicial aleatoria
        do {
            this.plantaDestino = random.nextInt(20); // Planta destino aleatoria
        } while (this.plantaActual == this.plantaDestino); // Asegurarse de que el destino sea diferente
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
            // Solicitar ascensor
            edificio.solicitarAscensor(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

