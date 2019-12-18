package kata5p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailListReader {
    
    public static List <String> read(String fileName) {
        List<String> list = new ArrayList<>();
                
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
            
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                if (isMail(line))
                    list.add(new String(line));
            }
            
        } catch (FileNotFoundException exception) {
            System.out.println("Error MailListReader::read (File Not Found) " + exception.getMessage());
        }
        catch (IOException exception) {
            System.out.println("Error MailListReader::read (IO" + exception.getMessage());
        }
        return list;
    }
    
    // Método para comprobar que los registros del fichero correspondan a 
    // emails válidos
    public static boolean isMail (String line){
        return line.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }
}