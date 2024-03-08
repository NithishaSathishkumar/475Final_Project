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
            System.out.println("command: getStaff_Staffnum Command:FirstName Command:LastName Command:Email");
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
            System.out.println("updateStaffContact - Update Staff Contact");
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
}
    


