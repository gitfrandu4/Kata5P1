package Kata5P1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import kata5p1.MailListReader;

public class kata5p1 {
    
    public static void main(String[] args) {
        
        // Cadena de conexión SQLite
        String url = "jdbc:sqlite:kata5.db";
        Connection conn = null;
        
        // Instrucción sql INSERT
        String sql = "INSERT INTO MAIL(Mail) VALUES (?)";
        
        // Leemos el fich. email.txt y cargamos los emails válidos en una lista
        String fileName = "email.txt";
        List<String> mailList = MailListReader.read(fileName);
        
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión a SQLite establecida");
            
            // Bucle recorre la lista y realiza los INSERT
            for (String email : mailList) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, email);
                pstmt.executeUpdate();
            }
            System.out.println("Tabla Mail actualizada.");
                                  
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
