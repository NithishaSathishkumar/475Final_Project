//////////////////////////////////////////////////////////////
//                          IMPORTS                         //
//////////////////////////////////////////////////////////////
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

/*
 * Postgres Connector Method
 * @author Nithisha Sathishkumar
*/

public class HotelDB {
    //////////////////////////////////////////////////////////////
    //                      CONNECTOR VARIABLES                 //
    //////////////////////////////////////////////////////////////
    private static final String URL = "jdbc:postgresql://localhost/hotel";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Hoang2317"; 
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException{
        if(connection != null && !connection.isValid(0000)) {
            connection = null;
        }

        int connectionAttempts = 0;
        while(connection == null || connection.isClosed()) {
            try {

                connectionAttempts++;
                System.err.println("Creating connection to HotelDB...");

                Properties connectionProps = new Properties();
                connectionProps.put("user", USER);
                connectionProps.put("password", PASSWORD);

                connection = DriverManager.getConnection(URL, connectionProps);

                if(!connection.isValid(100)) {
                    connection = null;
                    throw new SQLException("Connection not Valid");
                }
            }catch(SQLException e){
                System.out.println("Failed connecting to DB");
                if(connectionAttempts >= 1) {
                    connection = null;
                    throw e;
                }

                try {
                    Thread.sleep(10000);
                } catch(InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println("");
        }
        return connection;
    }
    
    public static void disconnect() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

        //////////////////////////////////////////////////////////////
        //                      METHODS                             //
        //////////////////////////////////////////////////////////////

    /*
    * GetStaffList Method
    * @author Nithisha Sathishkumar
    * Retrieves a list of staff members along with their details from the database.
    *
    * @param apiParams HashMap containing API parameters (not used in this method).
    * @throws SQLException if a database access error occurs.
    */
    public static void getStaffList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Get a database connection
            Connection connection = HotelDB.getConnection();

            // SQL query to retrieve staff information
            String query = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " + 
                            "FROM Staff " +
                            "JOIN Position ON Position.ID = Staff.PositionID " +
                            "ORDER BY FirstName ASC";

            // Create a statement and execute the query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            boolean gotRecords = false;

            System.out.println("List of Employees: ");

            // Print header for the table
            System.out.format("%-10s%-15s%-15s%-15s%-25s%-15s%n",
                "StaffNum", "First Name", "Last Name", "Phone Number", "Email", "Position Name" );

            System.out.println("----------------------------------------------------------------------------------------------");

            // Iterate through the result set and print staff details
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

            // Display a message if no records are found
            if(!gotRecords){
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            // Close the statement and result set in the finally block
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
    *
    * Retrieves a list of job positions from the database.
    *
    * @param apiParams HashMap containing API parameters (not used in this method).
    * @throws SQLException if a database access error occurs.
    */
    public static void getJobPositionList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Get a database connection
            Connection connection = HotelDB.getConnection();

            // SQL query to retrieve job positions
            String query = "SELECT Position.Name " + 
                            "FROM Position " +
                            "ORDER BY Name ASC";

            // Create a statement and execute the query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            boolean gotRecords = false;

            System.out.println("List of Job Position: ");

            // Print header for the table
            System.out.format("%-10s%n",
                "Position Name" );

            System.out.println("-------------------");

            // Iterate through the result set and print job positions
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%n",
                    resultSet.getString("Name")
                );
            }

            // Display a message if no records are found
            if(!gotRecords){
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            // Close the statement and result set in the finally block
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
    * Retrieves a list of employees by a specific job position from the database.
    *
    * @param apiParams HashMap containing API parameters, specifically the job position.
    * @throws SQLException if a database access error occurs.
    */
    public static void getStaffListByPosition(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println("");

        try {
            // Get a database connection
            Connection connection = getConnection();

            String query = "SELECT Staff.StaffNum, Staff.FirstName, Staff.LastName, Staff.PhoneNumber, Staff.Email, Position.Name " +
                        "FROM Staff " +
                        "JOIN Position ON Position.ID = Staff.PositionID " +
                        "WHERE Staff.PositionID = (SELECT Position.ID " + " From Position WHERE Name = ?)" +
                        "ORDER BY FirstName ASC";                 

            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("Position"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.println("List of Employees by Specific Position: ");

            // Print header for the table
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

            // Display a message if no records are found
            if(!gotRecords)
            {
                System.out.println("No results found !");
                System.out.println("");
            }             
        } catch (SQLException e) {
            
            e.printStackTrace();

        } finally {
            // Close the prepared statement and result set in the finally block
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
    * Retrieves information about a staff member by staff number from the database.
    *
    * @param apiParams HashMap containing API parameters, specifically the staff number.
    * @throws SQLException if a database access error occurs.
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

            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("StaffNum"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.println("Staff Info By StaffNum: ");

            // Print header for the table
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

            // Display a message if no records are found
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
    * Retrieves the StaffNum based on matching FirstName, LastName, and Email from the database.
    *
    * @param apiParams HashMap containing API parameters, including FirstName, LastName, and Email.
    * @throws SQLException if a database access error occurs.
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

            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, apiParams.get("FirstName"));
            preparedStatement.setString(2, apiParams.get("LastName"));
            preparedStatement.setString(3, apiParams.get("Email"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.println("The StaffNum: ");

            // Print header for the table
            System.out.format("%-10s%n","StaffNum");

            System.out.println("------------------");

            while(resultSet != null && resultSet.next()){
                gotRecords = true;

                System.out.format("%-10s%n",
                    resultSet.getString("StaffNum")
                );
            }

            // Display a message if no records are found
            if(!gotRecords)
            {
                System.out.println("No results found !");
                System.out.println("");
            }             
        } catch (SQLException e) {
            
            e.printStackTrace();

        } finally {
            // Close the prepared statement and result set in the finally block
            if (preparedStatement != null) {
                preparedStatement.close();
            }   
            
            if (resultSet != null) {
                resultSet.close();
            }             
        }
    }

   /*
    * PhoneNumber Method
    * @author Nithisha Sathishkumar
    * Updates the PhoneNumber of a staff member in the database based on StaffNum.
    *
    * @param apiParams HashMap containing API parameters, including StaffNum and PhoneNumber.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
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
    
            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, apiParams.get("PhoneNumber"));
            preparedStatement.setString(2, apiParams.get("StaffNum"));
    
            int rows = preparedStatement.executeUpdate();
    
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Staff's PhoneNumber updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Staff Information: ");
    
                // Create a prepared statement and set the parameter
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("StaffNum"));
                resultSet = preparedStatement.executeQuery();
    
                boolean gotRecords = false;
    
                // Print header for the table
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
    
                // Display a message if no records are found
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
            // Close the prepared statement and result set in the finally block
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
    * Updates the Email of a staff member in the database based on StaffNum.
    *
    * @param apiParams HashMap containing API parameters, including StaffNum and Email.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
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
    
            // Create a prepared statement and set the parameter
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
    
                // Print header for the table
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
    
                // Display a message if no records are found
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

            // Close the prepared statement and result set in the finally block
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
    * Updates the FirstName of a staff member in the database based on StaffNum.
    *
    * @param apiParams HashMap containing API parameters, including StaffNum and FirstName.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
    */
    public static boolean updateStaffFirstName(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Get DB connection
            Connection connection = getConnection();

            // Fetch existing Email
            String selectEmailSql = "SELECT Email FROM Staff WHERE StaffNum = ?";

            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(selectEmailSql);
            preparedStatement.setString(1, apiParams.get("StaffNum"));
            resultSet = preparedStatement.executeQuery();

            String existingEmail = null;
            if (resultSet.next()) {
                existingEmail = resultSet.getString("Email");
            }

            // SQL PreparedStatement
            String updateSql = "UPDATE Staff SET FirstName = ?, Email = ? WHERE StaffNum = ?";

            // Create a prepared statement and set the parameter
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

                // Create a prepared statement and set the parameter
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("StaffNum"));
                resultSet = preparedStatement.executeQuery();

                boolean gotRecords = false;

                // Print header for the table
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

                // Display a message if no records are found
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
            // Close the prepared statement and result set in the finally block
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
    * Updates the LastName of a staff member in the database based on StaffNum.
    *
    * @param apiParams HashMap containing API parameters, including StaffNum and LastName.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
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
    
            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, apiParams.get("LastName"));
            preparedStatement.setString(2, apiParams.get("StaffNum"));
    
            int rows = preparedStatement.executeUpdate();
    
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Staff's LastName updated successfully!");
                System.out.println("");
    
                System.out.println("Updated LastName Email Information: ");
    
                // Create a prepared statement and set the parameter
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("StaffNum"));
                resultSet = preparedStatement.executeQuery();
    
                boolean gotRecords = false;
    
                // Print header for the table
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
    
                // Display a message if no records are found
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
            // Close the prepared statement and result set in the finally block
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
    * Updates the Position of a staff member in the database based on StaffNum.
    *
    * @param apiParams HashMap containing API parameters, including StaffNum and PositionName.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
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
    
            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, apiParams.get("Name"));
            preparedStatement.setString(2, apiParams.get("StaffNum"));
    
            int rows = preparedStatement.executeUpdate();
    
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Staff's Position updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Position Information: ");
    
                // Create a prepared statement and set the parameter
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, apiParams.get("StaffNum"));
                resultSet = preparedStatement.executeQuery();
    
                boolean gotRecords = false;
    
                // Print header for the table
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
    
                // Display a message if no records are found
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

            // Close the prepared statement and result set in the finally block
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
    * Creates a new staff member in the database with the specified details.
    *
    * @param apiParams HashMap containing API parameters, including FirstName, LastName, Email, PhoneNumber, and PositionName.
    * @return true if the creation is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
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

            // Create a prepared statement and set the parameter
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

                // Print header for the table
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
    
                // Display a message if no records are found
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

            // Close the prepared statement and result set in the finally block
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
    * getAvailableRooms methods
    * Retrieves a list of available rooms based on the specified start and end dates.
    *
    * @param apiParams HashMap containing API parameters, including StartDate and EndDate.
    * @throws SQLException if a database access error occurs.
    * @author Andy Hoang
    */
    public static void getAvailableRooms(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = "SELECT roomNumber, capacity, pricePerDay " 
                                + "FROM Room R " 
                                + "LEFT JOIN Booking B ON (R.ID = B.roomID) " 
                                + "WHERE NOT((?::timestamp, ?::timestamp) OVERLAPS (CiDate, CoDate)) " 
                                + "OR (CiDate IS NULL AND CoDate IS NULL) " 
                                + "ORDER BY roomNumber"; 

            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get( "StartDate (yyyy-mm-dd)"));
            preparedStatement.setString(2, apiParams.get("EndDate (yyyy-mm-dd)"));
            resultSet = preparedStatement.executeQuery();
            
            System.out.println("List of Rooms:");
            System.out.format("%-20s%-15s%-25s%n", "Room Number", "Capacity", "Price Per Day");
            System.out.println("--------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while(resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-20s%-15s%-25s%n",
                    resultSet.getString("RoomNumber"),
                    resultSet.getInt("Capacity"),
                    resultSet.getDouble("PricePerDay"));
            }

            // Display a message if no records are found
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

    /*
    *getBookingsOnRoom Method
    * Retrieves a list of CheckinDate and CheckoutDate for a specific room.
    *
    * @param apiParams HashMap containing API parameters, including RoomNumber.
    * @throws SQLException if a database access error occurs.
    * @author Andy Hoang
    */
    public static void getBookingsOnRoom(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = "SELECT roomNumber, CiDate \"CheckinDate\", CoDate \"CheckoutDate\" " +
                                "FROM Room R " +
                                "JOIN Booking B ON (R.ID = B.roomID) " +
                                "WHERE roomNumber = ? " +
                                "ORDER BY roomNumber";

            // Create a prepared statement and set the parameter                
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("RoomNumber"));
            resultSet = preparedStatement.executeQuery();
            
            System.out.println("List of Rooms and Dates:");
            System.out.format("%-15s%-25s%-25s%n", "Room Number", "CheckinDate", "CheckoutDate");
            System.out.println("--------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-15s%-25s%-25s%n",
                    resultSet.getString("RoomNumber"),
                    resultSet.getString("CheckinDate"),
                    resultSet.getString("CheckoutDate"));
            }

            // Display a message if no records are found
            if(!gotRecords){
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(preparedStatement != null){
                preparedStatement.close();
            }

            if(resultSet != null){
                resultSet.close();
            }
        }
    }

    /*
    * getBookingList Method
    * Lists every single booking made in the hotel.
    *
    * @param apiParams HashMap containing API parameters.
    * @throws SQLException if a database access error occurs.
    * @author Andy Hoang
    */
    public static void getBookingList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = "SELECT reservationNum, roomNumber, CiDate \"CheckinDate\", CoDate \"CheckoutDate\" " +
                                "FROM Reservation R " + 
                                "JOIN Booking B ON (R.ID = B.reservationID) " + 
                                "JOIN Room Ro ON (B.roomID = Ro.ID) " +
                                "ORDER BY reservationNum";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            System.out.println("List of Bookings:");
            System.out.format("%-20s%-15s%-25s%-25s%n", "reservationNum", "roomNumber", "CheckinDate", "CheckoutDate");
            System.out.println("--------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-20s%-15s%-25s%-25s%n",
                    resultSet.getString("reservationNum"),
                    resultSet.getString("roomNumber"),
                    resultSet.getString("CheckinDate"),
                    resultSet.getString("CheckoutDate"));
            }

            // Display a message if no records are found
            if(!gotRecords) {
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                statement.close();
            }

            if(resultSet != null) {
                resultSet.close();
            }
        }
    }

    /*
    * getBookingInfo Method
    * Retrieves information about a specific booking based on the reservation number.
    * @author Andy Hoang
    * @param apiParams HashMap containing API parameters, including reservationNum.
    * @throws SQLException if a database access error occurs.
    */
    public static void getBookingInfo(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = "SELECT reservationNum, roomNumber, capacity, pricePerDay, CiDate, CoDate " 
                                + "FROM Room R " 
                                + "JOIN Booking B ON (R.ID = B.roomID) "
                                + "JOIN Reservation Re ON (B.reservationID = Re.ID) " 
                                + "WHERE reservationNum = ?"; 
            
            // Create a prepared statement and set the parameter                    
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("reservationNum"));
            resultSet = preparedStatement.executeQuery();
            
            System.out.println("List of Rooms:");
            System.out.format("%-20s%-15s%-10s%-15s%-25s%-25s%n", "ReservationNum", "RoomNumber", "Capacity", "PricePerDay", 
                                "CheckinDate", "CheckoutDate");
            System.out.println("--------------------------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while(resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-20s%-15s%-10s%-15s%-25s%-25s%n",
                    resultSet.getString("ReservationNum"),
                    resultSet.getString("RoomNumber"),
                    resultSet.getInt("Capacity"),
                    resultSet.getDouble("PricePerDay"),
                    resultSet.getString("CiDate"),
                    resultSet.getString("CoDate"));
            }

            // Display a message if no records are found
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

    /*
    * updateCheckoutTime Method
    * Updates the checkout time for a guest.
    *
    * @param apiParams HashMap containing API parameters.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
    * @authors Andy Hoang (Note: Marked as "NOT DONE")
    */
    public static boolean updateCheckoutTime(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();
    
            // SQL PreparedStatement
            String updateQuery = "";
            String selectQuery = "";

            // Create a prepared statement and set the parameter
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, apiParams.get(""));
            preparedStatement.setString(2, apiParams.get(""));

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement

            if (rows > 0) {
                System.out.println("Guest's checkouttime updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Booking Information: ");
    
                // Create a prepared statement and set the parameter
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, apiParams.get(""));
                resultSet = preparedStatement.executeQuery();
    
                System.out.format("%-15s%-20s%-20s%n",
                        "RoomNum", "CheckinTime", "CheckoutTime");
                System.out.println("---------------------------------------------------------------------");
                
                boolean gotRecords = false;
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-15s%-20s%-20s%n",
                            resultSet.getString("RoomNum"),
                            resultSet.getString("CheckinTime"),
                            resultSet.getString("CheckoutTime")
                    );
                }

                // Display a message if no records are found
                if (!gotRecords) {
                    System.out.println("No results found for the updated checkouttime!");
                    System.out.println("");
                }
    
                return true;
            } else {
                System.out.println("Guest's Email update failed! RoomNum not found.");
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
            
            // Close the prepared statement and result set in the finally block
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
    * getRoomList Method
    * Lists every single room in the hotel.
    *
    * @param apiParams HashMap containing API parameters.
    * @throws SQLException if a database access error occurs.
    * @author Andy Hoang
    */
    public static void getRoomList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = "SELECT roomNumber, capacity, pricePerDay " +
                            "FROM Room " +
                            "ORDER BY roomNumber";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            System.out.println("List of Rooms:");
            System.out.format("%-20s%-15s%-25s%n", "roomNumber", "capacity", "pricePerDay");
            System.out.println("--------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-20s%-15s%-25s%n",
                    resultSet.getString("RoomNumber"),
                    resultSet.getString("Capacity"),
                    resultSet.getString("PricePerDay"));
            }

            // Display a message if no records are found
            if(!gotRecords) {
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                statement.close();
            }

            if(resultSet != null) {
                resultSet.close();
            }
        }
    }

    /*
    * getGuestByGuestNum Method
    * Retrieves information about a guest based on the guest number.
    * @ author Nithisha Sathishkumar
    * @param apiParams HashMap containing API parameters, including GuestNum.
    * @throws SQLException if a database access error occurs.
    */
    public static void getGuestByGuestNum(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        System.out.println("");
    
        try {
            Connection connection = getConnection();
    
            String query = "SELECT Guest.GuestNum, Guest.FirstName, Guest.LastName, Phone.PhoneNumber, Guest.Email, Guest.address1, Guest.city, State.Name " +
                    "FROM Guest " +
                    "JOIN State ON Guest.StateID = State.ID " +
                    "JOIN Phone ON Guest.ID = Phone.GuestID " +
                    "WHERE Guest.GuestNum = ? " + 
                    "ORDER BY FirstName ASC";
    
            // Create a prepared statement and set the parameter        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("GuestNum").toUpperCase()); 
            resultSet = preparedStatement.executeQuery();
    
            boolean gotRecords = false;
    
            System.out.println("Guest Info By GuestNum: ");
    
            // Print header for the table
            System.out.format("%-10s%-15s%-15s%-15s%-25s%-25s%-15s%-15s%n",
                    "GuestNum", "First Name", "Last Name", "Phone Number", "Email", " Address", "City", "State Name");
    
            System.out.println("-------------------------------------------------------------------------------------------------------------");
    
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;
    
                System.out.format("%-10s%-15s%-15s%-15s%-25s%-25s%-15s%-15s%n",
                        resultSet.getString("GuestNum"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Email"),
                        resultSet.getString("address1"),
                        resultSet.getString("city"),
                        resultSet.getString("Name")
                );
            }
            
            // Display a message if no records are found
            if (!gotRecords) {
                System.out.println("No results found !");
                System.out.println("");
            }
    
        } catch (SQLException e) {
    
            e.printStackTrace();
    
        } finally {

            // Close the prepared statement and result set in the finally block
            if (preparedStatement != null) {
                preparedStatement.close();
            }
    
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
    

    /*
    * getRoomInfo Method
    * Retrieves information about a specific room based on the room number.
    *
    * @param apiParams HashMap containing API parameters, including roomNumber.
    * @throws SQLException if a database access error occurs.
    * @author Andy Hoang
    */
    public static void getRoomInfo(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = "SELECT roomNumber, capacity, pricePerDay " 
                                + "FROM Room R " 
                                + "LEFT JOIN Booking B ON (R.ID = B.roomID) " 
                                + "WHERE roomNumber = ?"; 
    
            // Create a prepared statement and set the parameter                    
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("roomNumber"));
            resultSet = preparedStatement.executeQuery();
            
            System.out.println("List of Rooms:");
            System.out.format("%-20s%-15s%-25s%n", "Room Number", "Capacity", "Price Per Day");
            System.out.println("--------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while(resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-20s%-15s%-25s%n",
                    resultSet.getString("RoomNumber"),
                    resultSet.getInt("Capacity"),
                    resultSet.getDouble("PricePerDay"));
            }

            // Display a message if no records are found
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

    /*
    * getGuestList Method
    * Retrieves a list of all guests in the hotel.
    *
    * @param apiParams HashMap containing API parameters.
    * @throws SQLException if a database access error occurs.
    * @authors Nithisha Sathishkumar & Andy Hoang
    */
    public static void getGuestList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = HotelDB.getConnection();

            String query = "SELECT guestNum, firstName, lastName, phoneNumber, email, " +
                            "address1, address2, city, zipcode, stateID " +
                            "FROM Guest G " +
                            "JOIN Phone P ON (G.ID = P.guestID) " +
                            "ORDER BY firstName";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            System.out.println("List of Guests:");

            // Print header for the table
            System.out.format("%-10s%-15s%-15s%-15s%-25s%-20s%-20s%-15s%-15s%-5s%n",
                "GuestNum", "FirstName", "LastName", "PhoneNumber", "Email", 
                "Address1", "Address2", "City", "Zipcode", "State");
            System.out.println("----------------------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-10s%-15s%-15s%-15s%-25s%-20s%-20s%-15s%-15s%-5s%n",
                    resultSet.getString("guestNum"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("email"),
                    resultSet.getString("address1"),
                    resultSet.getString("address2"),
                    resultSet.getString("city"),
                    resultSet.getString("zipcode"),
                    resultSet.getString("stateID")
                );
            }

            // Display a message if no records are found
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
    * getPaymentList Method
    * Retrieves a list of all payments that have been made.
    *
    * @param apiParams HashMap containing API parameters.
    * @throws SQLException if a database access error occurs.
    * @author Andy Hoang
    */
    public static void getPaymentList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = "SELECT reservationNum, amount, methodName, paymentDate " +
            "FROM Reservation R " +
            "JOIN Payment P ON (R.paymentID = P.ID) " +
            "JOIN PaymentType PT ON (P.paymentTypeID = PT.ID) " +
            "ORDER BY methodName";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            System.out.println("List of Payments:");
            System.out.format("%-20s%-15s%-25s%-25s%n", "reservationNum", "amount", "methodName", "paymentDate");
            System.out.println("--------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-20s%-15s%-25s%-25s%n",
                    resultSet.getString("reservationNum"),
                    resultSet.getString("amount"),
                    resultSet.getString("methodName"),
                    resultSet.getString("paymentDate"));
            }

            // Display a message if no records are found
            if(!gotRecords) {
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                statement.close();
            }

            if(resultSet != null) {
                resultSet.close();
            }
        }
    }

    /*
    * updateGuestAddress Method
    * Updates the address information for a guest.
    *
    * @param apiParams HashMap containing API parameters.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
    * @author Andy Hoang
    */
    public static boolean updateGuestAddress(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();
    
            // SQL PreparedStatement
            String updateQuery = "UPDATE Guest SET address1 = ?, address2 = ?, city = ?, zipcode = ?, " +
                                "stateID = ? WHERE GuestNum = ?";
            String selectQuery = "SELECT GuestNum, FirstName, LastName, Address1, address2, City, ZipCode, StateID " +
                                "FROM Guest " +
                                "WHERE guestNum = ?";
    
            // Create a prepared statement and set the parameter                    
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, apiParams.get("NewAddress1"));
            preparedStatement.setString(2, apiParams.get("NewAddress2 (nullable)"));
            preparedStatement.setString(3, apiParams.get("City"));
            preparedStatement.setString(4, apiParams.get("Zipcode (nullable)"));
            preparedStatement.setString(5, apiParams.get("State (XX)"));
            preparedStatement.setString(6, apiParams.get("GuestNum"));

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement

            if (rows > 0) {
                System.out.println("Guest's Address updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Guest Information: ");
                
                // Create a prepared statement and set the parameter
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, apiParams.get("GuestNum"));
                resultSet = preparedStatement.executeQuery();

                // Print header for the table
                System.out.format("%-10s%-15s%-15s%-15s%-15s%-15s%-10s%-5s%n",
                        "GuestNum", "FirstName", "LastName", "Address1", "Address2", "City", "Zipcode", "State");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                
                boolean gotRecords = false;
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-10s%-15s%-15s%-15s%-15s%-15s%-10s%-5s%n",
                            resultSet.getString("GuestNum"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Address1"),
                            resultSet.getString("Address2"),
                            resultSet.getString("City"),
                            resultSet.getString("Zipcode"),
                            resultSet.getString("StateID")
                    );
                }
                if (!gotRecords) {
                    System.out.println("No results found for the updated Address!");
                    System.out.println("");
                }
    
                return true;
            } else {
                System.out.println("Guest's address update failed! GuestNum not found.");
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
            
            // Close the prepared statement and result set in the finally block
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
    * updateGuestEmail Method
    * Updates the email address for a guest.
    *
    * @param apiParams HashMap containing API parameters.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
    * @authors Andy Hoang
    */
    public static boolean updateGuestEmail(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();
    
            // SQL PreparedStatement
            String updateQuery = "UPDATE Guest SET email = ? WHERE GuestNum = ?";
            String selectQuery = "SELECT GuestNum, FirstName, LastName, email " +
                                "FROM Guest " +
                                "WHERE guestNum = ?";

            // Create a prepared statement and set the parameter                    
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, apiParams.get("email"));
            preparedStatement.setString(2, apiParams.get("guestNum"));

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement

            if (rows > 0) {
                System.out.println("Guest's email updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Guest Information: ");
    
                // Create a prepared statement and set the parameter
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, apiParams.get("guestNum"));
                resultSet = preparedStatement.executeQuery();
    
                System.out.format("%-15s%-15s%-15s%-20s%n",
                        "GuestNum", "FirstName", "LastName", "Email");
                System.out.println("---------------------------------------------------------------------");
                
                boolean gotRecords = false;
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-15s%-15s%-15s%-20s%n",
                            resultSet.getString("GuestNum"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email")
                    );
                }
                if (!gotRecords) {
                    System.out.println("No results found for the updated Email!");
                    System.out.println("");
                }
    
                return true;
            } else {
                System.out.println("Guest's Email update failed! GuestNum not found.");
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
            
            // Close the prepared statement and result set in the finally block
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
    * updateGuestPhoneNumber Method
    * Updates the phone number for a guest.
    *
    * @param apiParams HashMap containing API parameters.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
    * @authors Andy Hoang
    */
    public static boolean updateGuestPhoneNumber(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();
    
            // SQL PreparedStatement
            String updateQuery = "UPDATE Phone SET phoneNumber = ? WHERE " +
                                "ID = (SELECT ID FROM Guest WHERE guestNum = ?)";
            String selectQuery = "SELECT guestNum, firstName, LastName, phoneNUmber FROM Guest G " +
                "JOIN Phone P ON (G.ID = P.guestID) WHERE guestNum = ? " +
                "ORDER BY firstName";
    
            // Create a prepared statement and set the parameter    
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, apiParams.get("PhoneNumber"));
            preparedStatement.setString(2, apiParams.get("GuestNum"));

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Guest's phonenumber updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Guest Information: ");
    
                // Create a prepared statement and set the parameter
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, apiParams.get("GuestNum"));
                resultSet = preparedStatement.executeQuery();
    
                // Print header for the table
                System.out.format("%-10s%-15s%-15s%-15s%n",
                        "GuestNum", "FirstName", "LastName", "PhoneNumber");
                System.out.println("----------------------------------------------------------------------------------------------");
                
                boolean gotRecords = false;
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-10s%-15s%-15s%-15s%n",
                            resultSet.getString("GuestNum"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("PhoneNumber")
                    );
                }
                if (!gotRecords) {
                    System.out.println("No results found for the updated phonenumber!");
                    System.out.println("");
                }
    
                return true;
            } else {
                System.out.println("Guest's phonenumber update failed! GuestNum not found.");
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
            
            // Close the prepared statement and result set in the finally block
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
    * getGuestInfoWithoutGuestNum Method
    * Retrieves guest information without specifying the guest number.
    *
    * @param apiParams HashMap containing API parameters.
    * @throws SQLException if a database access error occurs.
    * @author Nithisha Sathishkumar
    */
    public static void getGuestInfoWithoutGuestNum(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println("");

        try {
            Connection connection = getConnection();

            String sql = "SELECT Guest.GuestNum, Guest.FirstName, Guest.LastName, Phone.PhoneNumber, Guest.Email, Guest.address1, Guest.city, State.Name " +
                    "FROM Guest " +
                    "JOIN State ON Guest.StateID = State.ID " +
                    "JOIN Phone ON Guest.ID = Phone.GuestID " +
                    "WHERE LastName ILIKE ? AND Email ILIKE ? ";

            // Create a prepared statement and set the parameter        
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, apiParams.get("LastName"));
            preparedStatement.setString(2, apiParams.get("Email"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.println("Guest Info By Lastname and Email: ");
    
            // Print header for the table
            System.out.format("%-10s%-15s%-15s%-15s%-25s%-25s%-15s%-15s%n",
                    "GuestNum", "First Name", "Last Name", "Phone Number", "Email", " Address", "City", "State Name");
    
            System.out.println("-------------------------------------------------------------------------------------------------------------");
    
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;
    
                System.out.format("%-10s%-15s%-15s%-15s%-25s%-25s%-15s%-15s%n",
                        resultSet.getString("GuestNum"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Email"),
                        resultSet.getString("address1"),
                        resultSet.getString("city"),
                        resultSet.getString("Name")
                );
            }

            // Display a message if no records are found
            if(!gotRecords)
            {
                System.out.println("No results found !");
                System.out.println("");
            }             
        } catch (SQLException e) {
            
            e.printStackTrace();

        } finally {

            // Close the prepared statement and result set in the finally block
            if (preparedStatement != null) {
                preparedStatement.close();
            }   
            
            if (resultSet != null) {
                resultSet.close();
            }             
        }
    }

    /*
    * getReservationList Method
    * Lists every single reservation in the hotel.
    *
    * @param apiParams HashMap containing API parameters.
    * @throws SQLException if a database access error occurs.
    * @author Andy Hoang
    */
    public static void getReservationList(HashMap<String, String> apiParams) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            String query = "SELECT ReservationNum, GuestNum, RoomNumber, NumberOfGuest, " +
                                "PaymentTypeID, Amount, StaffNum " +
                                "FROM Guest G " +
                                "JOIN Reservation Re ON (G.ID = Re.guestID) " +
                                "JOIN Booking B ON (Re.ID = B.reservationID) " +
                                "JOIN Room R ON (B.roomID = R.ID) " +
                                "LEFT JOIN Payment P ON (Re.paymentID = P.ID) " +
                                "LEFT JOIN Staff S ON (Re.staffID = S.ID) " +
                                "ORDER BY reservationNum";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            System.out.println("List of Reservations:");
            System.out.format("%-20s%-15s%-15s%-20s%-15s%-15s%-15s%n", "ReservationNum", "GuestNum", "RoomNumber", "NumberOfGuests", 
                "PaymentType", "Amount", "StaffNum");
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            
            boolean gotRecords = false;
            while (resultSet != null && resultSet.next()) {
                gotRecords = true;

                System.out.format("%-20s%-15s%-15s%-20d%-15s%-15.2f%-15s%n",
                    resultSet.getString("ReservationNum"),
                    resultSet.getString("GuestNum"),
                    resultSet.getString("RoomNumber"),
                    resultSet.getInt("NumberOfGuest"),
                    resultSet.getString("PaymentTypeID"),
                    resultSet.getDouble("Amount"),
                    resultSet.getString("StaffNum"));
            }

            // Display a message if no records are found
            if(!gotRecords) {
                System.out.println("No Result Found!");
                System.out.println("");
            }
    
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                statement.close();
            }

            if(resultSet != null) {
                resultSet.close();
            }
        }
    }

    /*
    * updatePayment Method
    * Updates the payment for a reservation.
    *
    * @param apiParams HashMap containing API parameters.
    * @return true if the update is successful, false otherwise.
    * @throws SQLException if a database access error occurs.
    * @authors Andy Hoang
    */
    public static boolean updatePayment(HashMap<String, String> apiParams) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            // Get DB connection
            Connection connection = getConnection();

            // SQL PreparedStatement
            String insertQuery = "INSERT INTO Payment (paymentTypeID, paymentDate) " +
                                    "VALUES(?, ?::timestamp)";

            String updateQuery = "UPDATE Reservation SET paymentID = " +
                                    "(SELECT ID FROM Payment ORDER BY ID DESC LIMIT 1) " +
                                    "WHERE reservationNum = ? AND createTime = ?::timestamp";

            String selectQuery = "SELECT reservationNum, numberOfGuest, paymentTypeID, createTime " +
                                "FROM Reservation R " + 
                                "LEFT JOIN Payment P ON (R.paymentID = P.ID) " +
                                "WHERE reservationNum = ? AND createTime = ?::timestamp AND paymentTypeID = ?";
    
            // Create a prepared statement for the Insert and set the parameter    
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, apiParams.get("PaymentType (XX)"));
            preparedStatement.setString(2, apiParams.get("PaymentDate"));

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement

            // Create a prepared statement for the Update and set the parameter    
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, apiParams.get("ReservationNum"));
            preparedStatement.setString(2, apiParams.get("OriginalCreationDate"));

            rows += preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement

            if (rows > 0) {
                System.out.println("Payment updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Payment with Reservation Information: ");
    
                // Create a prepared statement for the select and set the parameter
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, apiParams.get("ReservationNum"));
                preparedStatement.setString(2, apiParams.get("OriginalCreationDate"));
                preparedStatement.setString(3, apiParams.get("PaymentType (XX)"));

                resultSet = preparedStatement.executeQuery();
    
                // Print header for the table
                System.out.format("%-20s%-15s%-15s%-20s%n",
                        "ReservationNum", "NumberOfGuests", "paymentMethod", "CreateTime");
                System.out.println("----------------------------------------------------------------------------------------------");
                
                boolean gotRecords = false;
                while (resultSet.next()) {
                    gotRecords = true;
    
                    System.out.format("%-20s%-15d%-15s%-20s%n",
                            resultSet.getString("ReservationNum"),
                            resultSet.getInt("numberOfGuest"),
                            resultSet.getString("paymentTypeID"),
                            resultSet.getString("createTime")
                    );
                }
                if (!gotRecords) {
                    System.out.println("No results found for the updated Payment!");
                    System.out.println("");
                }
    
                return true;
            } else {
                System.out.println("Payment update failed! ReservationNum/CreationTime not found.");
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
            
            // Close the prepared statement and result set in the finally block
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}





