package Modelo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilidades.Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConexionExcel {
    private static ConexionExcel instance;
    private Workbook wb;
    private String doctrabajo = "Poblaciones.xlsx";


    private ConexionExcel() {
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(Utilidades.RUTA+doctrabajo)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    // Método público para obtener la instancia única
    public static ConexionExcel getInstance() throws IOException {
                if (instance == null) {
                    instance = new ConexionExcel();
                }
        return instance;
    }

    // Método para obtener el Workbook
    public Workbook getWorkbook() {
        return wb;
    }
    
    public static void cerrarCon() {
		try {
			instance.wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    
}
