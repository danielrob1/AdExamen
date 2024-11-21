package Modelo.ClasesJaxb;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Poblacion {
	private String nombre;

	public Poblacion() {
	}

	public Poblacion(String nombre) {
		this.nombre = nombre;
	}

	@XmlAttribute()
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	

}
