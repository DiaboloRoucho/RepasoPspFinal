package examenAscensor;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Edificio {
    private List<Ascensor> ascensores;
    private List<Persona> personas;
    private final Object lock = new Object(); // Objeto de sincronización

    public Edificio() {
        ascensores = new ArrayList<>();
        personas = new ArrayList<>();
    }

    public void agregarAscensor(Ascensor ascensor) {
        ascensores.add(ascensor);
    }

    public void agregarPersona(Persona persona) {
        personas.add(persona);
    }

    public void solicitarAscensor(Persona persona) throws InterruptedException {
        synchronized (lock) {
            // Seleccionar el ascensor más cercano o disponible
            Ascensor ascensorSeleccionado = seleccionarAscensor(persona.getPlantaActual());
            // Agregar persona al ascensor seleccionado
            while (!ascensorSeleccionado.agregarPersona(persona)) {
                lock.wait(); // Esperar hasta que haya espacio en el ascensor
            }
            System.out.println(persona.getid() + " se ha subido al ascensor " + ascensorSeleccionado.getId());
            lock.notifyAll();
        }
    }

    public void moverAscensor(Ascensor ascensor) {
        synchronized (lock) {
            if (!ascensor.getDestinos().isEmpty()) {
                int siguienteDestino = ascensor.getDestinos().get(0);

                if (ascensor.getPlantaActual() < siguienteDestino) {
                    ascensor.setEstado(Ascensor.Estado.SUBIENDO);
                    System.out.println("El ascensor "+ascensor.getId()+" esta subiendo");
                } else if (ascensor.getPlantaActual() > siguienteDestino) {
                    ascensor.setEstado(Ascensor.Estado.BAJANDO);
                    System.out.println("El ascensor "+ascensor.getId()+" esta bajando");
                } else {
                    ascensor.setEstado(Ascensor.Estado.PARADO);
                    System.out.println("El ascensor "+ascensor.getId()+" esta parado");
                }

                // Mover ascensor a la siguiente planta destino
                ascensor.setPlantaActual(siguienteDestino);
                System.out.println("El ascensor "+ascensor.getId()+" ha llegado a la planta " + siguienteDestino);

                // Dejar a las personas en su destino
                List<Persona> personasParaDejar = new ArrayList<>();
                for (Persona p : ascensor.getPersonas()) {
                    if (p.getPlantaDestino() == siguienteDestino) {
                        personasParaDejar.add(p);
                    }
                }
                for (Persona p : personasParaDejar) {
                    ascensor.removerPersona(p);
                    p.setPlantaActual(siguienteDestino);
                    System.out.println("Persona " + p.getId() + " ha llegado a su destino en planta " + siguienteDestino);
                    p.interrupt(); // Finalizar ejecución del hilo persona
                }

                // Determinar nuevo estado del ascensor
                if (ascensor.getDestinos().isEmpty()) {
                    ascensor.setEstado(Ascensor.Estado.PARADO);
                    System.out.println("El ascensor "+ascensor.getId()+" esta parado");
                } else if (ascensor.getPlantaActual() < ascensor.getDestinos().get(0)) {
                    ascensor.setEstado(Ascensor.Estado.SUBIENDO);
                    System.out.println("El ascensor "+ascensor.getId()+" esta subiendo");
                } else {
                    ascensor.setEstado(Ascensor.Estado.BAJANDO);
                    System.out.println("El ascensor "+ascensor.getId()+" esta bajando");
                }
            }
            lock.notifyAll();
        }
    }

    private Ascensor seleccionarAscensor(int plantaActual) {
        // Selección del ascensor más cercano o disponible
        Ascensor ascensorSeleccionado = null;
        int distanciaMinima = Integer.MAX_VALUE;

        for (Ascensor ascensor : ascensores) {
            int distancia = Math.abs(ascensor.getPlantaActual() - plantaActual);
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                ascensorSeleccionado = ascensor;
            }
        }
        return ascensorSeleccionado;
    }

    
}


