import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

/*
 * Postgres Connector Method
 * @author Nithisha Sathishkumar
*/

public class HotelDB {
    private static final String URL = "jdbc:postgresql://localhost/hotel";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Hoang2317"; //Enter your postgres password //Gayathri@27
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
    * PhoneNumber Method
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
    * With explicitly listed RoomNumber, list of all CiDate/CoDates are returned
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
    * Lists every single booking made in this hotel
    * @author Andy Hoang
    */
    public static void listAllBookings(HashMap<String, String> apiParams) throws SQLException {
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
    * Specific Booking info is given with specified param (reservationNum) 
    * @author Andy Hoang
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
     * updateCheckoutTime method
     * @authors Andy Hoang
     * 
     *  NOT DONE
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

            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, apiParams.get(""));
            preparedStatement.setString(2, apiParams.get(""));

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement

            if (rows > 0) {
                System.out.println("Guest's checkouttime updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Booking Information: ");
    
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
    * Lists every single room in this hotel
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
    
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, apiParams.get("GuestNum").toUpperCase()); 
            resultSet = preparedStatement.executeQuery();
    
            boolean gotRecords = false;
    
            System.out.println("Guest Info By GuestNum: ");
    
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
    
            if (!gotRecords) {
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
    * Specific Room info is given with specified param (roomNumber) 
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
     * getGuestList
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
     * getPaymentList of all payments that have been made
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
     * updateGuestAddress method
     * @author Andy Hoang
     * 
     * somehow broke in one of the last merges/commits
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
                
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, apiParams.get("GuestNum"));
                resultSet = preparedStatement.executeQuery();
                System.out.println(resultSet);

                System.out.format("%-10s%-15s%-15s%-15s%-15s%-15s%-10s%-5s%n",
                        "GuestNum", "FirstName", "LastName", "Address1", "Address2", "City", "Zipcode", "State");
                System.out.println("----------------------------------------------------------------------------------------------");
                
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
     * updateGuestEmail method
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

            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, apiParams.get("email"));
            preparedStatement.setString(2, apiParams.get("guestNum"));

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement

            if (rows > 0) {
                System.out.println("Guest's email updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Guest Information: ");
    
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
     * updateGuestPhoneNumber method
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
    
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, apiParams.get("PhoneNumber"));
            preparedStatement.setString(2, apiParams.get("GuestNum"));

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();  // Close the update statement
    
            if (rows > 0) {
                System.out.println("Guest's phonenumber updated successfully!");
                System.out.println("");
    
                System.out.println("Updated Guest Information: ");
    
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, apiParams.get("GuestNum"));
                resultSet = preparedStatement.executeQuery();
    
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
    * GetGuestInfoWithoutGuestNum Method
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
                    "WHERE FirstName ILIKE ? AND LastName ILIKE ? AND Email ILIKE ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, apiParams.get("FirstName"));
            preparedStatement.setString(2, apiParams.get("LastName"));
            preparedStatement.setString(3, apiParams.get("Email"));
            resultSet = preparedStatement.executeQuery();

            boolean gotRecords = false;

            System.out.println("Guest Info By Firstname, Lastname and Email: ");
    
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

}





