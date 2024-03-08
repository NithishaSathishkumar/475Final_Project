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

    public static void getStaffList(String[] params){
        System.out.println("");
        if(params == null || params.length == 0){
            System.out.println("GetStaffList: List of Staff Information");
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
}
    


