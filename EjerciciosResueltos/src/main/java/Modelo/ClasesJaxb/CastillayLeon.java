package Modelo.ClasesJaxb;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="castillayleon")
public class CastillayLeon {
	private ArrayList<Provincia> listaProvincia;
	
	public CastillayLeon() {
		
	}
	public CastillayLeon(ArrayList<Provincia> listaProvincia) {
		this.listaProvincia= listaProvincia;
	}
	@XmlElementWrapper(name="provincias")
	@XmlElement(name="provincia")
	public ArrayList<Provincia> getListaProvincia() {
		return listaProvincia;
	}
	public void setListaProvincia(ArrayList<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

}
