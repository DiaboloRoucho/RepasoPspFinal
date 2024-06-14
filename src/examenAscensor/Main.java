package examenAscensor;

public class Main {

	public static void main(String[] args) {
        Edificio edificio = new Edificio();

        Ascensor ascensor1 = new Ascensor(1, edificio);
        Ascensor ascensor2 = new Ascensor(2, edificio);
        edificio.agregarAscensor(ascensor1);
        edificio.agregarAscensor(ascensor2);

        ascensor1.start();

        for (int i = 0; i < 50; i++) {
            Persona persona = new Persona(i, edificio);
            edificio.agregarPersona(persona);
            persona.start();
        }
    }

}
