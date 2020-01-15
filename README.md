# Kata5P1

Kata 5: Primera parte

## Representación gráfica de un Histograma leyendo los datos desde una base de datos


**Objetivo**: 
* dado un conjunto de direcciones de email almacenadas en una base de datos mostrar el histograma de los dominios y visualizarlo. 

### Introducción

Vamos a familiarizarnos con el uso de los gestores de Base de Datos (BD) haciendo unos ejercicios introductorios:

1. Con la Aplicación _DB Browser for SQLite_.
2. Desde aplicaciones Java

#### A) Ejercicios introductorios con la aplicación _DB Browser for SQLite_

Pasos:

1. Abrimos la aplicación _DB Browser for SQLite_
2. Creamos la BD, por ejemplo, **Prueba**
3. Creamos la tabla **PEOPLE** con la siguiente estructura:

```sqlite
CREATE TABLE "PEOPLE" (
	"Id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"Name"	TEXT NOT NULL,
	"Apellidos"	TEXT NOT NULL,
	"Departamento"	TEXT NOT NULL
);
```

Añadimos al menos tres registros a la tabla PEOPLE:

```Sqli
select * from PEOPLE;
```

| Id   | Name    | Apellidos | Departamento |
| ---- | ------- | --------- | ------------ |
| 1    | Alberto | García    | DIS          |
| 2    | María   | Montes    | DUI          |
| 3    | Luisa   | Cabello   | DMA          |



#### B) Interacción de JAVA con _SQLite_ utilizando la API JDBC (_Java Database Connectivity_)

Usaremos un controlador JDBC moderno que se llama paquete _SQLiteJDBC_. Este paquete contiene tanto clases de Java como bibliotecas nativas de SQLite para Windows, Mac OS X y Linux. 

Pasos:

1. Creamos un nuevo proyecto NetBeans.

2. Descargarnos el último driver de sqlite-jdbc-3.23.1.jar

   https://bitbucket.org/xerial/sqlite-jdbc/src/default/

3. Importamos la librería a nuestro proyecto.

4. Realizaremos diferentes ejemplos de operaciones para aprender la interacción Java-SQLite.



##### Ejemplo 1: Cadena (String) de conexión SQLite.

El controlador de JDBC de _SQLite_ nos permite cargar una base de datos _SQLite_ desde el sistema de archivos utilizando la siguiente cadena de conexión:

```Java
String url = jdbc:sqlite:RUTA_A_BD;
```

**Posteriormente, establecemos la conexión a través de un objeto con la clase** `Connection` **y el método** `DriverManager.getConnection(url)`:

```Java
Connection con = null;
// ...
con = DriverManager.getConnection(url);
```

En el siguiente ejemplo se usará el controlador JDBC de _SQLite_ para cargar la base de datos Prueba.db

```Java
package kata5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Kata5 {

    public static void main(String[] args) {
        connect(); 
    }
    
    private static void connect () {
        Connection conn = null;
        
        try {
            // parámetros de la BD
            String url = "jdbc:sqlite:Prueba.db";
            //Creamos la conexión a la BD
            conn = DriverManager.getConnection(url);
            
            System.out.println("Conexión a SQLite establecida");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
}
```



##### Ejemplo 2. Seleccionar registros de una tabla existente.

Seguiremos los siguientes pasos:

1. Creamos un objeto `Connection` para conectarnos a la BD _SQLite_.
2. Creamos una instancia de `Statement` a partir del objeto `Connection`.
3. Se crea una instancia de la clase `ResultSet` invocando el método `executeQuery` del objeto `Statement`. Este método acepte una instrucción `Select`.
4. Se hace un bucle usando el método `next()` del objeto `ResultSet`.
5. Finalmente, se usa un método get de `Resulset`, como por ejemplo, `getInt( )`, `getString( )`, `getDouble( )`, etc. para obtener en cada iteración los datos. 

En el siguiente ejemplo, se usará el controlador JDBC de _SQLite_ para cargar la base de datos Kata5.db y seleccionar todos los registros de la tabla PEOPLE.

```Java
package kata5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectApp {
    
    // Se conecta a la BD y se devuelve un objeto Connection
    private Connection connect () {
        // SQLite connection string
        String url = new String("jdbc:sqlite:Kata5.db");
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn; 
    }
    
    // Se seleccionan todos los registros de la tabla PEOPLE
    public void selectAll() {
        String sql = "SELECT * FROM PEOPLE";
        
        try (Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            // Bucle sobre el conjunto de registros
            while(rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("Name") + "\t" +
                        rs.getString("Apellidos") + "\t" +
                        rs.getString("Departamento") + "\t");
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

```Java
package kata5;

public class Kata5 {
    
    public static void main (String[] args) {
        SelectApp app = new SelectApp();
        app.selectAll();
    }
}
```

Salida:

```
1 Alberto	García 		DIS
2 María 	Montes 		DUI
3 Luisa	 	Cabello 	DMA
```



##### Ejemplo 3. Crear una nueva BD.

Seguiremos los siguientes pasos:

* Se especifica el nombre y la ruta.
* Se conecta a la BD a través del driver _SQLite_ JDBC: al conectar a una BD inexistente, _SQLite_ la crea automáticamente. 

En el siguiente ejemplo se usará el controlador JDBC de _SQLite_ para crear la base de datos `mail.db`.

```java
package kata5;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CrearBD {
    
    public static void createNewDatabase (String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData ();
                System.out.println("El driver es " + meta.getDriverName());
                System.out.println("Se ha creado una nueva BD.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main (String[] args) {
        createNewDatabase ("mail.db");
    }
}
```

Salida:

```
El driver es SQLite JDBC
Se ha creado una nueva BD.
```



##### Ejemplo 4. Crear una tabla en una BD.

Seguiremos los siguientes pasos:

* Se define una cadena que contenga la instrucción `CREATE TABLE`.
* Se conecta a la BD que contenga dicha tabla.
* Se crea una instancia de la clase `Statement` a partir del objeto `Connection`
* Se ejecuta la instrucción `CREATE TABLE` invocando al método `executeUpdate( )` del objeto `Statement`.

En el siguiente ejemplo se usará el controlador JDBC de _SQLite_ para crear la tabla `direcc_email` en la base de datos mail.bd

```Java
package kata5;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearTabla {
    
    public static void createNewTable () {
        // Cadena de conexión SQLite
        String url = "jdbc:sqlite:mail.db";
        
        // Instrucción SQL para crar una nueva tabla
        String sql = "CREATE TABLE IF NOT EXISTS direcc_email (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT, \n"
                + " direccion text NOT NULL);";
        
        try (Connection conn = DriverManager.getConnection(url);
            	Statement stmt = conn.createStatement()) {
            // Se crea la nueva tabla
            stmt.execute(sql);
            System.out.println("Tabla creada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main (String [] args) {
        createNewTable();
    }
}
```

Salida:

```
Tabla creada
```

Podemos comprobar la creación de la tabla en la BD a través de _DB Browser for SQLite_



##### Ejemplo 5. Insertar datos en una tabla de una BD.

* Nos conectamos a la BD _SQLite_
* Se define una cadena que contenga la instrucción `INSERT`. Si se usan parámetros, debemos usar un carácter ? por cada parámetro.
* Se crea una instancia de la clase `Preparedstatement` a partir del objeto `Connection`.
* Se establecen los correspondientes valores para cada marcador de posición usando un método set del objeto `Preparedstatement`, como por ejemplo, `setInit()`, `setString()`, etc.
* Finalmente se invoca el método `executeUpdate()` del objeto `Preparedstatement` para ejecutar la instrucción `Insert`.

En el siguiente ejemplo se insertan tres valores en el campo `direccion` de la tabla `direcc_email` de la base de datos `mail.db` que creamos en el ejemplo anterior. 

```java
package kata5;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertarDatosTabla {
    
    private Connection connect () {
        // Cadena de conexión SQLite
        String url = "jdbc:sqlite:mail.db";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    // Método para insertar datos en la tabla direcc_email
    public void insert (String email) {
        String sql = "INSERT INTO direcc_email(direccion) VALUES (?)";
        
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main (String[] args){
        InsertarDatosTabla idt = new InsertarDatosTabla();
        // Se insertan tres nuevas líneas
        idt.insert("abd@ulpgc.es");
        idt.insert("xyz@ull.es");
        idt.insert("def123@gmail.com");
    }
}
```
