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

            String query = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " + 
                            "FROM Staff " +
                            "JOIN Position ON Position.ID = Staff.PositionID " +
                            "ORDER BY FirstName ASC";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            boolean gotRecords = false;

            System.out.println("List of Employees: ");

            System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name" );

            System.out.println("----------------------------------------------------------------------------------------------");

            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                    resultSet.getString("StaffNum"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getString("Email"),
                    resultSet.getString("Name")
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
    * GetJobPositionList Method
    * @author Nithisha Sathishkumar
    */

    public static void getJobPositionList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            Connection connection = HotelDB.getConnection();

            String query = "SELECT Position.Name " + 
                            "FROM Position " +
                            "ORDER BY Name ASC";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            boolean gotRecords = false;

            System.out.println("List of Job Position: ");

            System.out.format("%-10s%n",
                "Position Name" );

            System.out.println("-------------------");

            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%n",
                    resultSet.getString("Name")
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
    * GetStaffListByPosition Method
    * @author Nithisha Sathishkumar
    */

    public static void getStaffListByPosition(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println("");

        try {
            Connection connection = getConnection();

            // SQL PreparedStatement
            String query = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                        "FROM Staff " +
                        "JOIN Position ON Position.ID = Staff.PositionID " +
                        "WHERE Staff.PositionID = (SELECT Position.ID " + " From Position WHERE Name = ?)" +
                        "ORDER BY FirstName ASC";                 

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("Position"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.println("List of Employees by Specific Position: ");

            System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name");

            System.out.println("----------------------------------------------------------------------------------------------");

            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                    resultSet.getString("StaffNum"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getString("Email"),
                    resultSet.getString("Name")
                );
            }

            if(!gotRecords)
            {
                System.out.println("No results found !");
                System.out.println("");
            }             
        } catch (SQLException e) {
            
            e.printStackTrace();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }   
            
            if (resultSet != null) {
                resultSet.close();
            }             
        }
    }

   /*
    * GetStaffInfoByStaffNum Method
    * @author Nithisha Sathishkumar
    */

    public static void getStaffInfoByStaffNum(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println("");

        try {
            Connection connection = getConnection();

            String query = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                        "FROM Staff " +
                        "JOIN Position ON Position.ID = Staff.PositionID " +
                        "WHERE Staff.StaffNum = ? " +
                        "ORDER BY FirstName ASC";                 

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("StaffNum"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.println("Staff Info By StaffNum: ");

            System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name");

            System.out.println("----------------------------------------------------------------------------------------------");

            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                    resultSet.getString("StaffNum"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getString("Email"),
                    resultSet.getString("Name")
                );
            }

            if(!gotRecords){
                System.out.println("No results found !");
                System.out.println("");
            }            

        } catch (SQLException e) {
            
            e.printStackTrace();

        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }   
            
            if (resultSet != null) {
                resultSet.close();
            }             
        }
    }

   /*
    * getStaff_Staffnum Method
    * @author Nithisha Sathishkumar
    */

    public static void getStaff_Staffnum(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println("");

        try {
            Connection connection = getConnection();

            String sql = "SELECT Staff.StaffNum " +
                         "FROM Staff " +
                         "WHERE FirstName ILIKE ? AND LastName ILIKE ? AND Email ILIKE ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, apiParams.get("FirstName"));
            preparedStatement.setString(2, apiParams.get("LastName"));
            preparedStatement.setString(3, apiParams.get("Email"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.println("The StaffNum: ");
            System.out.format("%-10s%n","StaffNum");

            System.out.println("------------------");

            while(resultSet != null && resultSet.next()){
                gotRecords = true;

                System.out.format("%-10s%n",
                    resultSet.getString("StaffNum")
                );
            }

            if(!gotRecords)
            {
                System.out.println("No results found !");
                System.out.println("");
            }             
        } catch (SQLException e) {
            
            e.printStackTrace();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }   
            
            if (resultSet != null) {
                resultSet.close();
            }             
        }
    }

   /*
    * updateStaffPhoneNumber Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffPhoneNumber(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();
    
            // SQL PreparedStatement
            String updateSql = "UPDATE Staff SET PhoneNumber = ? WHERE StaffNum = ?";
    
            String selectSql = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                    "FROM Staff " +
                    "JOIN Position ON Position.ID = Staff.PositionID " +
                    "WHERE Staff.StaffNum = ? " +
                    "ORDER BY FirstName ASC";
    
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, apiParams.get("PhoneNumber"));
            preparedStatement.setString(2, apiParams.get("StaffNum"));
    
            int rows = preparedStatement.executeUpdate();
    
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Staff's PhoneNumber updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Staff Information: ");
    
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("StaffNum"));
                resultSet = preparedStatement.executeQuery();
    
                boolean gotRecords = false;
    
                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                        "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name");
    
                System.out.println("----------------------------------------------------------------------------------------------");
    
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                            resultSet.getString("StaffNum"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("Email"),
                            resultSet.getString("Name")
                    );
                }
    
                if (!gotRecords) {
                    System.out.println("No results found for the updated Staff PhoneNumber!");
                    System.out.println("");
                }
    
                return true;
    
            } else {
                System.out.println("Staff's PhoneNumber update failed! StaffNum not found.");
                System.out.println("");
                return false;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
    
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }    

    /*
    * updateStaffEmail Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffEmail(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();
    
            // SQL PreparedStatement
            String updateSql = "UPDATE Staff SET Email = ? WHERE StaffNum = ?";
    
            String selectSql = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                    "FROM Staff " +
                    "JOIN Position ON Position.ID = Staff.PositionID " +
                    "WHERE Staff.StaffNum = ? " +
                    "ORDER BY FirstName ASC";
    
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, apiParams.get("Email"));
            preparedStatement.setString(2, apiParams.get("StaffNum"));
    
            int rows = preparedStatement.executeUpdate();
    
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Staff's Email updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Staff Email Information: ");
    
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("StaffNum"));
                resultSet = preparedStatement.executeQuery();
    
                boolean gotRecords = false;
    
                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                        "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name");
    
                System.out.println("----------------------------------------------------------------------------------------------");
    
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                            resultSet.getString("StaffNum"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("Email"),
                            resultSet.getString("Name")
                    );
                }
    
                if (!gotRecords) {
                    System.out.println("No results found for the updated Email!");
                    System.out.println("");
                }
    
                return true;
    
            } else {
                System.out.println("Staff's Email update failed! StaffNum not found.");
                System.out.println("");
                return false;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
    
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }    

    /*
    * updateStaffFirstName Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffFirstName(HashMap<String, String> apiParams) throws SQLException {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        // Get DB connection
        Connection connection = getConnection();

        // Fetch existing Email
        String selectEmailSql = "SELECT Email FROM Staff WHERE StaffNum = ?";
        preparedStatement = connection.prepareStatement(selectEmailSql);
        preparedStatement.setString(1, apiParams.get("StaffNum"));
        resultSet = preparedStatement.executeQuery();

        String existingEmail = null;
        if (resultSet.next()) {
            existingEmail = resultSet.getString("Email");
        }

        // SQL PreparedStatement
        String updateSql = "UPDATE Staff SET FirstName = ?, Email = ? WHERE StaffNum = ?";
        preparedStatement = connection.prepareStatement(updateSql);
        preparedStatement.setString(1, apiParams.get("FirstName"));
        preparedStatement.setString(2, existingEmail); // Use existing Email
        preparedStatement.setString(3, apiParams.get("StaffNum"));

        int rows = preparedStatement.executeUpdate();

        preparedStatement.close();  // Close the update statement

        if (rows > 0) {
            System.out.println("Staff's FirstName updated successfully!");
            System.out.println("");

            System.out.println("Updated FirstName Email Information: ");

            // Retrieve updated information
            String selectSql = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                    "FROM Staff " +
                    "JOIN Position ON Position.ID = Staff.PositionID " +
                    "WHERE Staff.StaffNum = ? " +
                    "ORDER BY FirstName ASC";

            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setString(1, apiParams.get("StaffNum"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                    "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name");

            System.out.println("----------------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                        resultSet.getString("StaffNum"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Email"),
                        resultSet.getString("Name")
                );
            }

            if (!gotRecords) {
                System.out.println("No results found for the updated FirstName!");
                System.out.println("");
            }

            return true;

        } else {
            System.out.println("Staff's FirstName update failed! StaffNum not found.");
            System.out.println("");
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;

    } finally {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    /*
    * updateStaffFirstName Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffLastName(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();
    
            // SQL PreparedStatement
            String updateSql = "UPDATE Staff SET LastName = ? WHERE StaffNum = ?";
    
            String selectSql = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                    "FROM Staff " +
                    "JOIN Position ON Position.ID = Staff.PositionID " +
                    "WHERE Staff.StaffNum = ? " +
                    "ORDER BY FirstName ASC";
    
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, apiParams.get("LastName"));
            preparedStatement.setString(2, apiParams.get("StaffNum"));
    
            int rows = preparedStatement.executeUpdate();
    
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Staff's LastName updated successfully!");
                System.out.println("");
    
                System.out.println("Updated LastName Email Information: ");
    
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("StaffNum"));
                resultSet = preparedStatement.executeQuery();
    
                boolean gotRecords = false;
    
                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                        "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name");
    
                System.out.println("----------------------------------------------------------------------------------------------");
    
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                            resultSet.getString("StaffNum"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("Email"),
                            resultSet.getString("Name")
                    );
                }
    
                if (!gotRecords) {
                    System.out.println("No results found for the updated LastName!");
                    System.out.println("");
                }
    
                return true;
    
            } else {
                System.out.println("Staff's LastName update failed! StaffNum not found.");
                System.out.println("");
                return false;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
    
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * updateStaffFirstName Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffPosition(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();
    
            // SQL PreparedStatement
            String updateSql = "UPDATE Staff SET PositionID = (SELECT ID FROM Position WHERE Name = ?) WHERE StaffNum = ?";
    
            String selectSql = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                    "FROM Staff " +
                    "JOIN Position ON Position.ID = Staff.PositionID " +
                    "WHERE Staff.StaffNum = ? " +
                    "ORDER BY FirstName ASC";
    
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, apiParams.get("Name"));
            preparedStatement.setString(2, apiParams.get("StaffNum"));
    
            int rows = preparedStatement.executeUpdate();
    
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Staff's Position updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Position Information: ");
    
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("StaffNum"));
                resultSet = preparedStatement.executeQuery();
    
                boolean gotRecords = false;
    
                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                        "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name");
    
                System.out.println("----------------------------------------------------------------------------------------------");
    
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                            resultSet.getString("StaffNum"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("Email"),
                            resultSet.getString("Name")
                    );
                }
    
                if (!gotRecords) {
                    System.out.println("No results found for the updated Position!");
                    System.out.println("");
                }
    
                return true;
    
            } else {
                System.out.println("Staff's Position update failed! StaffNum not found.");
                System.out.println("");
                return false;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
    
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




   /*
    * create Method
    * @author Nithisha Sathishkumar
    */

    public static boolean createStaff(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Get DB connection
            Connection connection = getConnection();

            // SQL PreparedStatement
            String insertSql = "INSERT INTO Staff (FirstName, LastName, Email, PhoneNumber, PositionID) " +
                    "VALUES (?, ?, ?, ?, (SELECT ID FROM Position WHERE Name = ? ))";

            String selectSql = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                    "FROM Staff " +
                    "JOIN Position ON Position.ID = Staff.PositionID " +
                    "WHERE Staff.Email = ? " +
                    "ORDER BY FirstName ASC";

            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, apiParams.get("FirstName"));
            preparedStatement.setString(2, apiParams.get("LastName"));
            preparedStatement.setString(3, apiParams.get("Email"));
            preparedStatement.setString(4, apiParams.get("PhoneNumber"));
            preparedStatement.setString(5, apiParams.get("PositionName"));

            int rows = preparedStatement.executeUpdate();

            preparedStatement.close();

            if (rows > 0) {
                System.out.println("Staff created successfully!");
                System.out.println("");

                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("Email"));
                resultSet = preparedStatement.executeQuery();
    
                boolean gotRecords = false;
    
                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                        "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name");
    
                System.out.println("----------------------------------------------------------------------------------------------");
    
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                            resultSet.getString("StaffNum"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("Email"),
                            resultSet.getString("Name")
                    );
                }
    
                if (!gotRecords) {
                    System.out.println("No results found for the updated StaffNum!");
                    System.out.println("");
                }

                preparedStatement.close();
                return true;

            } else {
                System.out.println("Failed to create staff!");
                System.out.println("");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * With explicitly listed date, rooms that are not occupied 
    * during the date are listed and returned
    * @author Andy Hoang
    *
    *    UNTESTED WIP
    *    UNTESTED WIP
    *    UNTESTED WIP
    *
    */
    public static void getAvailableRooms(HashMap<String, String> apiParams) throws SQLException {
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = ""; 
            //Thinking of Logic to handle all various NULL dates of Rooms and CoDate

            preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setString(1, apiParams.get("Date"));
            resultSet = preparedStatement.executeQuery(query);
            
            boolean gotRecords = false;

            System.out.println("List of Rooms:");

            System.out.format("%-20s%-15s%-25s%n", "Room Number", "Capacity", "Price Per Day");
           
            System.out.println("--------------------------------------------------------------------------------");

            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-20s%-10s%-25s%n",
                    resultSet.getString("RoomNumber"),
                    resultSet.getInt("Capacity"),
                    resultSet.getDouble("PricePerDay"));
            }

            if(!gotRecords) {
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }

            if(resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static void getGuestList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = HotelDB.getConnection();

            String query = "SELECT Guest.GuestNum, Guest.FirstName, Guest.LastName, Phone.PhoneNumber, Guest.Email, Guest.Address1, Guest.Address2, Guest.City, Gest " + 
                            "FROM Staff " +
                            "JOIN Position ON Position.ID = Staff.PositionID " +
                            "ORDER BY FirstName ASC";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            boolean gotRecords = false;

            System.out.println("List of Employees: ");

            System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name" );

            System.out.println("----------------------------------------------------------------------------------------------");

            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                    resultSet.getString("StaffNum"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getString("Email"),
                    resultSet.getString("Name")
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


}





