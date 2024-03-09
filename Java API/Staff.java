/*
* Method for inserting, updated and getting staff information
* @author Nithisha Sathishkumar
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Staff {

    public static final String ListOfStaff = "getStaffList";
    public static final String ListOfStaffByPosition = "getStaffListByPosition";
    public static final String StaffInfoByStaffNum = "getStaffInfoByStaffNum";
    public static final String ListOfJobPosition = "getJobPositionList";
    public static final String GetStaffStaffNum = "getStaff_Staffnum";
    public static final String UpdateStaffPhoneNumber = "updateStaffPhoneNumber";
    public static final String UpdateStaffEmail = "updateStaffEmail";
    public static final String UpdateStaffFirstName = "updateStaffFirstName";
    public static final String UpdateStaffLastName = "updateStaffLastName";
    public static final String UpdateStaffPosition = "updateStaffPosition";
    public static final String CreateStaff = "createStaff";  

    /*
    * GetStaffList Method
    * @author Nithisha Sathishkumar
    */ 

    public static void getStaffList(String[] params){
        System.out.println("");
        
        if(params == null || params.length == 0){
            System.out.println("ListOfStaff: List of Staff Information");
            System.out.println("COMMAND: getStaffList");

        }else{
            HashMap<String, String> apiParms = input.ParseInputParams(new String[] {});
            if(apiParms != null){
                try{
                    HotelDB.getStaffList(apiParms);
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * GetStaffListByPosition Method
    * @author Nithisha Sathishkumar
    */ 

    public static void getStaffListByPosition(String[] params){
        System.out.println("");

        if(params == null || params.length == 0)
        {
            System.out.println("ListOfStaffByPosition - Return list of students filtered by Job Position");
            System.out.println("COMMAND: getStaffListByPosition DifficultyLevel: [Manager|Receptionist]");
        }
        else
        {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"Position"});
            if(apiParams != null)
            {
                try {
                    HotelDB.getStaffListByPosition(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    } 

    /*
    * GetStaffInfoByStaffNum Method
    * @author Nithisha Sathishkumar
    */  

    public static void getStaffInfoByStaffNum(String[] params){
        System.out.println("");

        if(params == null || params.length == 0)
        {
            System.out.println("StaffInfoByStaffNum - Return list of staff filtered by staff Number");
            System.out.println("COMMAND: getStaffInfoByStaffNum COMMAND: StaffNum");
        }
        else
        {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"StaffNum"});
            if(apiParams != null)
            {
                try {
                    HotelDB.getStaffInfoByStaffNum(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    } 

   /*
    * GetJobPositionList Method
    * @author Nithisha Sathishkumar
    */   

    public static void getJobPositionList(String[] params){
        System.out.println("");

        if(params == null || params.length == 0){
            System.out.println("ListOfJobPosition: List of Job Position Information");
            System.out.println("COMMAND: getJobPositionList");

        }else{
            HashMap<String, String> apiParms = input.ParseInputParams(new String[] {});
            if(apiParms != null){
                try{
                    HotelDB.getJobPositionList(apiParms);
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * GetStaff_Staffnum Method
    * @author Nithisha Sathishkumar
    */

    public static void getStaff_Staffnum(String[] params){
        System.out.println("");
        
        if(params == null || params.length == 0)
        {
            System.out.println("GetStaffStaffNum - Staff's StaffNum");
            System.out.println("COMMAND: getStaff_Staffnum Command:FirstName Command:LastName Command:Email");
        }
        else
        {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "FirstName", "LastName", "Email" });

            if(apiParams != null){
                try {
                    HotelDB.getStaff_Staffnum(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }            
        }
    } 

   /*
    * UpdateStaffPhoneNumber Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffPhoneNumber(String[] params) {
        System.out.println("");

        if(params == null || params.length == 0){
            System.out.println("UpdateStaffContact - Update Staff Contact");
            System.out.println("COMMAND: updateStaffPhoneNumber COMMAND:StaffNum COMMAND:PhoneNumber");

        }else{

            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "StaffNum", "PhoneNumber" });

            if(apiParams != null){
                try {
                    return HotelDB.updateStaffPhoneNumber(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    /*
    * UpdateStaffEmail Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffEmail(String[] params) {
        System.out.println("");

        if(params == null || params.length == 0){
            System.out.println("UpdateStaffEmail - Update Staff Email");
            System.out.println("COMMAND: updateStaffEmail COMMAND:StaffNum COMMAND:Email");

        }else{

            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "StaffNum", "Email" });

            if(apiParams != null){
                try {
                    return HotelDB.updateStaffEmail(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /*
    * UpdateStaffFirstName Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffFirstName(String[] params) {
        System.out.println("");

        if(params == null || params.length == 0){
            System.out.println("UpdateStaffFirstName - Update Staff First Name");
            System.out.println("COMMAND: updateStaffFirstName COMMAND:StaffNum COMMAND:FirstName");

        }else{

            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "StaffNum", "FirstName" });

            if(apiParams != null){
                try {
                    return HotelDB.updateStaffFirstName(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /*
    * UpdateStaffLastName Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffLastName(String[] params) {
        System.out.println("");

        if(params == null || params.length == 0){
            System.out.println("UpdateStaffLastName - Update Staff Last Name");
            System.out.println("COMMAND: updateStaffLastName COMMAND:StaffNum COMMAND:LastName");

        }else{

            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "StaffNum", "LastName" });

            if(apiParams != null){
                try {
                    return HotelDB.updateStaffLastName(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /*
    * UpdateStaffFirstName Method
    * @author Nithisha Sathishkumar
    */

    public static boolean updateStaffPosition(String[] params) {
        System.out.println("");

        if(params == null || params.length == 0){
            System.out.println("UpdateStaffPosition - Update Staff Position");
            System.out.println("COMMAND: updateStaffPosition COMMAND:StaffNum COMMAND:Position");

        }else{

            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "StaffNum", "Name" });

            if(apiParams != null){
                try {
                    return HotelDB.updateStaffPosition(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /*
    * CreateStaff Method
    * @author Nithisha Sathishkumar
    */


    public static boolean createStaff(String[] params){
        if(params == null || params.length == 0){
            System.out.println("CreateStaff - Create Staff");
            System.out.println("COMMAND: createStaff COMMAND:StaffNum COMMAND:PhoneNumber");

        }else{

            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "FirstName", "LastName", "Email", "PhoneNumber", "PositionName" });

            if(apiParams != null){
                try {
                    return HotelDB.createStaff(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


}
    


