package Modelo.ClasesJaxb;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


public class Provincia {
	private String nombre;
	private ArrayList<Poblacion> listaPoblacion;
	
	
	public Provincia() {
		
	}
	public Provincia(String nombre, ArrayList<Poblacion> arrayList) {
		this.nombre = nombre;
		this.listaPoblacion = arrayList;
	}
	@XmlAttribute()
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@XmlElement(name="poblacion")
	public ArrayList<Poblacion> getListaPoblacion() {
		return listaPoblacion;
	}
	public void setListaPoblacion(ArrayList<Poblacion> listaPoblacion) {
		this.listaPoblacion = listaPoblacion;
	}
	
	
	
	

}
