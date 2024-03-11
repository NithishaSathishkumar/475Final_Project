//////////////////////////////////////////////////////////////
//                          IMPORTS                         //
//////////////////////////////////////////////////////////////
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Guest {
    //////////////////////////////////////////////////////////////
    //                      GLOBAL VARIABLES                    //
    //////////////////////////////////////////////////////////////
    public static final String getPaymentList = "getPaymentList";
    public static final String createGuest = "createGuest";
    public static final String getGuestList = "getGuestList";
    public static final String updateGuestPhoneNumber = "updateGuestPhoneNumber";
    public static final String updateGuestEmail = "updateGuestEmail";
    public static final String updateGuestAddress = "updateGuestAddress";
    public static final String getGuestInfoByGuestNum = "getGuestInfoByGuestNum";
    public static final String getGuestByGuestNum = "getGuestByGuestNum";
    public static final String getGuestInfoWithoutGuestNum = "getGuestInfoWithoutGuestNum";

    private String guestNum;
    private String firstName;
    private String lastName;
    private String email;
    private String address1;
    private String address2;
    private String phoneType;
    private String city;
    private String zipcode;
    private String state;

    /*
     * Constructor to initialize a Guest object.
     * 
     * @param guestNum The guest number.
     * @param firstName The first name of the guest.
     * @param lastName The last name of the guest.
     * @param email The email address of the guest.
     * @param address1 The first line of the guest's address.
     * @param address2 The second line of the guest's address.
     * @param phoneType The type of phone used by the guest.
     * @param city The city of the guest's address.
     * @param zipcode The ZIP code of the guest's address.
     * @param state The state of the guest's address.
     * 
     * @author Andy Hoang
     */
    public Guest(String guestNum, String firstName, String lastName, String email, String address1, String address2, String phoneType, String city, String zipcode, String state) {
        this.guestNum = guestNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.phoneType = phoneType;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
    }

    /*
    * getPaymentList method
    * Method to retrieve the list of payments for a guest.
    * 
    * @param params The input parameters (not used in the current implementation).
    * @author Andy Hoang
    */
    public static void getPaymentList(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getPaymentList - Return list of all payments within hotel");
            System.out.println("COMMAND: getPaymentList");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {});
            if(apiParams != null) {
                try {
                    HotelDB.getPaymentList(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * getGuestList method
    * Method to retrieve the list of guests within the hotel.
    * 
    * @param params The input parameters (not used in the current implementation).
    * @author Andy Hoang
    */
    public static void getGuestList(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getGuestList - Return list of all guests within hotel");
            System.out.println("COMMAND: getGuestList");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {});
            if(apiParams != null) {
                try {
                    HotelDB.getGuestList(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
    * updateGuestPhoneNumber method
    * Method to update a guest's phone number.
    * 
    * @param params GuestNum and PhoneNumber to be updated.
    * @author Andy Hoang
    */
    public static void updateGuestPhoneNumber(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("updateGuestPhoneNumber - Updates Guest's phone number");
            System.out.println("COMMAND: updateGuestPhoneNumber COMMAND: GuestNum COMMAND: PhoneNumber");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"GuestNum", "PhoneNumber"});
            if(apiParams != null) {
                try {
                    HotelDB.updateGuestPhoneNumber(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /*
    * updateGuestEmail method
    * Method to update a guest's email address.
    * 
    * @param params GuestNum and Email to be updated.
    * @author Andy Hoang
    */  
    public static void updateGuestEmail(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("updateGuestEmail - Updates Guest's email");
            System.out.println("COMMAND: updateGuestEmail COMMAND: Email COMMAND: GuestNum");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"guestNum", "email"});
            if(apiParams != null) {
                try {
                    HotelDB.updateGuestEmail(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
     * updateGuestAdress method
     * @author Andy Hoang    
     * @params params GuestNum, Address1 Address2
     */ 
   
    public static void updateGuestAddress(String[] params){
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("updateGuestAddress - Updates Guest's address");
            System.out.println("COMMAND: updateGuestAddress COMMAND: GuestNum COMMAND: Address1 COMMAND: Address2 " +
                "COMMAND: City COMMAND: Zipcode COMMAND: State");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"GuestNum", "NewAddress1", 
                "NewAddress2 (nullable)", "City", "Zipcode (nullable)", "State (XX)"});
            if(apiParams != null) {
                try {
                    HotelDB.updateGuestAddress(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    } 

    /*
    * getGuestByGuestNum method
    * Method to retrieve guest information based on the guest number.
    * 
    * @param params GuestNum for retrieving guest information.
    * @author Nithisha Sathishkumar
    */
    public static void getGuestByGuestNum(String[] params){
        System.out.println("");

        if(params == null || params.length == 0)
        {
            System.out.println("GetGuestByGuestNum - Return list of staff filtered by staff Number");
            System.out.println("COMMAND: getGuestByGuestNum COMMAND: GuestNum");
        }
        else
        {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"GuestNum"});
            if(apiParams != null)
            {
                try {
                    HotelDB.getGuestByGuestNum(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    } 

   /*
    * GetGuestInfoWithoutGuestNum Method
    * Method to retrieve guest information without specifying the guest number.
    * 
    * @param params FirstName, LastName, and Email for retrieving guest information.
    * @author Nithisha Sathishkumar
    */
    public static void getGuestInfoWithoutGuestNum(String[] params){
        System.out.println("");
        
        if(params == null || params.length == 0)
        {
            System.out.println("GetGuestInfoWithoutGuestNum - Return list of Guest filtered by Email ");
            System.out.println("COMMAND: getGuestInfoWithoutGuestNum Command:LastName Command:Email");
        }
        else
        {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"LastName", "Email" });

            if(apiParams != null){
                try {
                    HotelDB.getGuestInfoWithoutGuestNum(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }            
        }
    } 

    /*
     * createGuest Method
     * @author Andy Hoang
     * 
     * Creates a new guest.
     * 
     * @params params FirstName, LastName, GuestNum, Email, Address1, Address2, City, Zipcode, StateID
     * @return boolean Indicates if the creation was successful
     */
    public static boolean createGuest(String[] params) {
        if(params == null || params.length == 0){
            System.out.println("createGuest - Create Guest");
            System.out.println("COMMAND: createGuest COMMAND: FirstName COMMAND: LastName COMMAND: GuestNum"
            + "\nCOMMAND: Email COMMAND: Address1 COMMAND: Address2 COMMAND: City \nCOMMAND: Zipcode COMMAND: StateID");
        } else {

            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "GuestNum", "FirstName (nullable)", 
            "LastName", "PhoneNumber", "PhoneType (X)", "Email", "Address1", "Address2 (nullable)", "City", "Zipcode (nullable)", "State (XX)"});

            if(apiParams != null){
                try {
                    return HotelDB.createGuest(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
