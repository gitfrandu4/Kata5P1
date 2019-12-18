package Kata5P1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class kata5p1 {
    
    public static void main(String[] args) {
        
        // Cadena de conexión SQLite
        String url = "jdbc:sqlite:C:\\Users\\jdelh\\Dropbox\\40822 - Ingeniería de Software II\\Prácticas en Laboratorio\\Kata5\\Kata5P1\\src\\Kata5P1\\kata5.db";
        Connection conn = null;
        
        // Instrucción para crear una nueva tabla
        String sql = "CREATE TABLE IF NOT EXISTS Mail (\n" 
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" 
                + "Mail	TEXT NOT NULL);";
                        
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión a SQLite establecida");
            
            Statement stmt  = conn.createStatement();
            
            // Se crea la nueva tabla
            stmt.execute(sql);
            System.out.println("Tabla Creada");
                    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }
    
}
