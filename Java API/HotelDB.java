import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class HotelDB{
    private static final String URL = "jdbc:postgresql://localhost/hotel";
    private static final String USER = "postgres";
    private static final String PASSWORD = "net1net2";
    private static Connection connection = null;


    public static Connection getConnection() throws SQLException{
        if(connection != null && !connection.isValid(0000)){
            connection = null;
        }

        int connectionAttempts = 0;
        while(connection == null || connection.isClosed()){
            try{
                connectionAttempts++;
                System.err.println("Creating connection to HotelDB...");

                Properties connectionProps = new Properties();
                connectionProps.put("user", USER);
                connectionProps.put("password", PASSWORD);

                connection = DriverManager.getConnection(URL, connectionProps);

                if(!connection.isValid(100)){
                    connection = null;
                    throw new SQLException("Connection not Valid");
                }
            }catch(SQLException e){
                System.out.println("Failed connecting to DB");
                if(connectionAttempts >= 10){
                    connection = null;
                    throw e;
                }

                try{
                    Thread.sleep(10000);
                }catch(InterruptedException e1){
                    e1.printStackTrace();
                }
            }
            System.out.println("");
        }
        return connection;
    }
    
    public static void disconnect(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

