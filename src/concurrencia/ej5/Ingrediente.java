package concurrencia.ej5;


public enum Ingrediente {

TABACO, CERILLAS, PAPEL;
	
	public static Ingrediente get() {
		return values()[(int)(Math.random()*1000)%values().length];
	}
}
