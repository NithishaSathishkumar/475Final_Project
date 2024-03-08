import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

/*
 * Postgres Connector Method
 * @author Nithisha Sathishkumar
*/

public class HotelDB{
    private static final String URL = "jdbc:postgresql://localhost/hotel";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Gayathri@27"; //Enter your postgres password
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
                if(connectionAttempts >= 1){
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

    /*
    * GetStaffList Method
    * @author Nithisha Sathishkumar
    */
    
    public static void getStaffList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            Connection connection = HotelDB.getConnection();

            String query = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Staff.PositionID " + 
                            "FROM Staff " + "ORDER BY FirstName ASC";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            boolean gotRecords = false;

            System.out.println("List of Employees: ");

            System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position ID");

            System.out.println("------------------------------------------------------------------------------");

            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                    resultSet.getString("StaffNum"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getString("Email"),
                    //resultSet.getInt("PositionID")
                    getPositionName(resultSet.getInt("PositionID"))
                );
            }

            if(!gotRecords){
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(statement != null){
                statement.close();
            }

            if(resultSet != null){
                resultSet.close();
            }
        }
    }

    /*
    * GetPositionName Method
    * @author Nithisha Sathishkumar
    */

    private static String getPositionName(int positionID) {
        // Map position IDs to position names
        switch (positionID) {
            case 1:
                return "Manager";
            case 2:
                return "Receptionist";
            default:
                return "Incorrect Job tittle";
        }
    }
}





