package App;

import Controlador.Controlador;
import Modelo.Modelo;
import Vista.Vista;

public class App {

	public static void main(String[] args) {
		Controlador controlador = new Controlador(new Modelo(), new Vista());

	}

}
