package examenAscensor;

public class Main {

	public static void main(String[] args) {
        Edificio edificio = new Edificio();

        // Crear y agregar dos ascensores
        Ascensor ascensor1 = new Ascensor(1, edificio);
        Ascensor ascensor2 = new Ascensor(2, edificio);
        edificio.agregarAscensor(ascensor1);
        edificio.agregarAscensor(ascensor2);

        // Iniciar los ascensores
        ascensor1.start();
        ascensor2.start();

        // Crear y agregar personas (número indefinido para la simulación)
        for (int i = 0; i < 50; i++) { // Puedes ajustar este número para la simulación
            Persona persona = new Persona(i, edificio);
            edificio.agregarPersona(persona);
            persona.start();
        }
    }

}
