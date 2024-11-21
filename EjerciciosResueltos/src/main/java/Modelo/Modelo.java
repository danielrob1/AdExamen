package Modelo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import utilidades.Utilidades;
import Modelo.ClasesJaxb.CastillayLeon;
import Modelo.ClasesJaxb.Poblacion;
import Modelo.ClasesJaxb.Provincia;

public class Modelo {
    public static Connection con;
    public static Workbook wb;
    public static String DOCUMENTO="provincias.xml";
    private static JAXBContext jC;
    private static CastillayLeon Ejercicio; 
	private static HashSet<Provincia> provincias;
    

    public static void main(String[] args) {
        try {
        	establecerConexionExcel();
            provincias=obtenerProvincias();
            Ejercicio = new CastillayLeon(new ArrayList<>(provincias));
            marshalling();
            //con = establecerConexionMySQL();
            //insertarEnBD();
            wb.close();
            //con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static HashSet<Provincia> obtenerProvincias() {
        // HashSet para almacenar provincias con sus poblaciones
        HashSet<Provincia> provincias = new HashSet<>();

        // HashMap para almacenar las poblaciones asociadas a una provincia
        HashMap<String, ArrayList<Poblacion>> provinciasYPoblaciones = new HashMap<>();
        
        // Obtener la hoja de Excel
        Sheet hoja = wb.getSheetAt(0);
        int numFila =0;
        Row fila = hoja.getRow(numFila);
        
        while (fila != null) {
            Cell celdaPoblacion = fila.getCell(0);
            Cell celdaProvincia = fila.getCell(1);
            
            if (celdaPoblacion != null && celdaProvincia != null) {
                // Obtener los valores de la población y la provincia
                String nombrePoblacion = celdaPoblacion.getStringCellValue();
                String nombreProvincia = celdaProvincia.getStringCellValue();

                // Si la provincia no está en el mapa, agregarla con una nueva lista de poblaciones
                if (!provinciasYPoblaciones.containsKey(nombreProvincia)) {
                    provinciasYPoblaciones.put(nombreProvincia, new ArrayList<Poblacion>());
                }
                
                // Añadir la población a la lista de la provincia correspondiente
                provinciasYPoblaciones.get(nombreProvincia).add(new Poblacion(nombrePoblacion));
            }

            // Avanzar a la siguiente fila
            fila = hoja.getRow(++numFila); 
        }

        // Ahora, crear las provincias con sus poblaciones y agregarlas al HashSet
        for (String nombreProvincia : provinciasYPoblaciones.keySet()) {
            ArrayList<Poblacion> poblaciones = provinciasYPoblaciones.get(nombreProvincia);
            Provincia provincia = new Provincia(nombreProvincia, poblaciones);
            provincias.add(provincia);
        }
        
        return provincias;
    }


	public static void marshalling() {
		try {
			jC = JAXBContext.newInstance(CastillayLeon.class);
			Marshaller m = jC.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(Ejercicio, new File(Utilidades.RUTA+DOCUMENTO));
			System.out.println("Se ha creado el archivo");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

    public static void insertarEnBD() {
        Sheet hoja = wb.getSheetAt(0);
        int numFila = 1;
        Row fila = hoja.getRow(numFila);
        while (fila != null) {
            Cell celdaPoblacion = fila.getCell(0);
            Cell celdaProvincia = fila.getCell(1);

            if (celdaPoblacion != null && celdaProvincia != null) {
                String nombrePoblacion = celdaPoblacion.getStringCellValue();
                String nombreProvincia = celdaProvincia.getStringCellValue();

                // Si la provincia no existe, agregarla
                if (buscarProvincia(nombreProvincia) == 0) {
                    anadirProvincia(nombreProvincia);
                }

                // Agregar la población
                anadirPoblacion(nombrePoblacion, nombreProvincia);
            }

            fila = hoja.getRow(++numFila); // Avanzar a la siguiente fila
        }
    }

    public static void anadirPoblacion(String nombrePoblacion, String nombreProvincia) {
        try {
            int idProvincia = buscarProvincia(nombreProvincia);
            if (idProvincia > 0) {
                PreparedStatement sentencia = con.prepareStatement("INSERT INTO poblacion (nombre, id_provincia) VALUES (?, ?)");
                sentencia.setString(1, nombrePoblacion);
                sentencia.setInt(2, idProvincia);
                sentencia.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void anadirProvincia(String nombreProvincia) {
        try {
            PreparedStatement sentencia = con.prepareStatement("INSERT INTO provincia (nombre) VALUES (?)");
            sentencia.setString(1, nombreProvincia);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int buscarProvincia(String provincia) {
        try {
            PreparedStatement sentencia = con.prepareStatement("SELECT id_provincia from provincia where nombre = ?");
            sentencia.setString(1, provincia);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;  // Retorna 0 si no encuentra la provincia
    }
    
    
    
    public static void borrarFilaPorPoblacion(String nombrePoblacion) {
        Sheet hoja = wb.getSheetAt(0);  // Obtenemos la primera hoja
        int filaEliminada = -1;

        // Recorremos todas las filas para encontrar la que contiene el nombre de la población
        for (int i = 1; i <= hoja.getLastRowNum(); i++) {
            Row fila = hoja.getRow(i);
            if (fila != null) {
                Cell celdaPoblacion = fila.getCell(0);  // Primera columna (población)
                if (celdaPoblacion != null && celdaPoblacion.getStringCellValue().equals(nombrePoblacion)) {
                    filaEliminada = i;  // Guardamos la posición de la fila encontrada
                    break;
                }
            }
        }

        // Si encontramos la fila con el nombre de la población, la borramos
        if (filaEliminada != -1) {
            Row fila = hoja.getRow(filaEliminada);
            hoja.removeRow(fila);
            // Después de borrar la fila, movemos las filas siguientes hacia arriba
            for (int i = filaEliminada + 1; i <= hoja.getLastRowNum(); i++) {
                Row filaSiguiente = hoja.getRow(i);
                hoja.shiftRows(i, hoja.getLastRowNum(), -1);
            }
        }
    }

    public static void modificarFilaPorPoblacion(String nombrePoblacion, String nuevaPoblacion, String nuevaProvincia) {
        Sheet hoja = wb.getSheetAt(0);  // Obtenemos la primera hoja

        // Recorremos todas las filas para encontrar la que contiene el nombre de la población
        for (int i = 1; i <= hoja.getLastRowNum(); i++) {
            Row fila = hoja.getRow(i);
            if (fila != null) {
                Cell celdaPoblacion = fila.getCell(0);  // Primera columna (población)
                if (celdaPoblacion != null && celdaPoblacion.getStringCellValue().equals(nombrePoblacion)) {
                    // Si encontramos la fila, modificamos las celdas
                    Cell celdaProvincia = fila.getCell(1);  // Segunda columna (provincia)
                    celdaPoblacion.setCellValue(nuevaPoblacion);  // Modificamos la población
                    if (celdaProvincia != null) {
                        celdaProvincia.setCellValue(nuevaProvincia);  // Modificamos la provincia
                    } else {
                        // Si la celda de la provincia no existe, la creamos
                        celdaProvincia = fila.createCell(1);
                        celdaProvincia.setCellValue(nuevaProvincia);
                    }
                    break;  // Terminamos una vez que modificamos la fila
                }
            }
        }
    }

    

    public static void establecerConexionMySQL() {
        con= ConexionMySQL.getInstance().getCon();
    }

    public static void establecerConexionExcel() {
        try {
        	wb= ConexionExcel.getInstance().getWorkbook();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
