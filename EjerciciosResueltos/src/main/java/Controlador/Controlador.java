package Controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import Modelo.Modelo;
import Modelo.ClasesJaxb.Provincia;
import Vista.Vista;

public class Controlador {
	private Modelo modelo;
	private Vista vista;
	
	public Controlador( final Modelo modelo,  final Vista vista) {
		super();
		this.modelo = modelo;
		this.vista = vista;
		
		vista.anadirBD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modelo.insertarEnBD();
				
			}
			
		});
		
		vista.conectarBD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modelo.establecerConexionMySQL();
				
			}
			
		});
		
		vista.conectarExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modelo.establecerConexionExcel();
				
			}
			
		});
		vista.Listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rellenaTabla();
				
			}
			
		});
		vista.Limpiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarFila();
				
				
			}
			
		});
		
	}
	
	protected void rellenaTabla() {
		
		HashSet<Provincia> provincias = modelo.obtenerProvincias();
		for(Provincia provs: provincias) {
			String[] fila = {provs.getNombre()};
			vista.modeloTbl.addRow(fila);
		}
		
	}
	protected void limpiarFila() {
		int selectedRow = vista.table.getSelectedRow(); // Índice de la fila seleccionada
        if (selectedRow != -1) {
            vista.modeloTbl.removeRow(selectedRow); // Eliminar la fila del modelo
        } else {
        }
	}

}
