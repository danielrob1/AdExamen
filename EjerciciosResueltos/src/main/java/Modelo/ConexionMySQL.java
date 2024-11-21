package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
	private static ConexionMySQL instance;
	private static Connection con;
	
	private ConexionMySQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/castilla_leon", "root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ConexionMySQL getInstance() {
		if (instance==null) {
			instance = new ConexionMySQL();
		}
		return instance;
	}

	public static Connection getCon() {
		return con;
	}
	
	public static void cerrarCon() {
		try {
			instance.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
