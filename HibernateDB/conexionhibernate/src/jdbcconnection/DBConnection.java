package jdbcconnection;

import java.sql.DriverManager;
import java.sql.Connection;


public class DBConnection {
    
    public static void main(String[] args) {
        
        String url = "jdbc:mysql://localhost:3306/clientes";
        String user = "root";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
