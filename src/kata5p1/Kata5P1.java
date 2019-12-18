package Kata5P1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Kata5P1 {
    
    public static void main(String[] args) {
        
        // Cadena de conexión SQLite
        String url = "jdbc:sqlite:C:\\Users\\jdelh\\Dropbox\\40822 - Ingeniería de Software II\\Prácticas en Laboratorio\\Kata5\\Kata5P1\\src\\Kata5P1\\kata5.db";
        Connection conn = null;
        
        // Sentencia sql consulta registros
        String sql = "SELECT * FROM PEOPLE;";
                        
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión a SQLite establecida");
            
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            
            // Bucle sobre el conjunto de registros
            while(rs.next()){
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("Name") + "\t" +
                        rs.getString("Apellidos") + "\t" +
                        rs.getString("Departamento") + "\t");
            }
                    
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
