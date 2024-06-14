package examenAscensor;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Ascensor extends Thread {
    public enum Estado {
        SUBIENDO, BAJANDO, PARADO
    }

    private int id;
    private int plantaActual;
    private List<Persona> personas;
    private final int capacidad = 8;
    private Edificio edificio;
    private List<Integer> destinos;
    private Estado estado;

    public Ascensor(int id, Edificio edificio) {
        this.id = id;
        this.edificio = edificio;
        this.plantaActual = 0;
        this.personas = new ArrayList<>();
        this.destinos = new ArrayList<>();
        this.estado = Estado.PARADO;
        }

    @Override
    public void run() {
        while (true) {
            try {
                edificio.moverAscensor(this);
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPlantaActual() {
        return plantaActual;
    }

    public void setPlantaActual(int plantaActual) {
        this.plantaActual = plantaActual;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public boolean agregarPersona(Persona persona) {
        if (personas.size() < capacidad) {
            personas.add(persona);
            destinos.add(persona.getPlantaDestino());
            return true;
        }
        return false;
    }

    public void removerPersona(Persona persona) {
        personas.remove(persona);
        destinos.remove((Integer) persona.getPlantaDestino());
    }

    public List<Integer> getDestinos() {
        return destinos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    @Override
    public long getId() {
    	return id;
    }
}


