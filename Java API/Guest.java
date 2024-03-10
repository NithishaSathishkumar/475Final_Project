/*
 * JUST DRAFT of Guest
 */

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Guest {
    public static final String getPaymentList = "getPaymentList";
    public static final String getGuestList = "getGuestList";
    public static final String updateGuestPhoneNumber = "updateGuestPhoneNumber";
    public static final String updateGuestEmail = "updateGuestEmail";
    public static final String updateGuestAddress = "updateGuestAddress";
    public static final String GuestInfoByGuestNum = "getGuestInfoByGuestNum";
    public static final String GetGuestByGuestNum = "getGuestByGuestNum";

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
     * @author Andy Hoang
     * @params params GuestNum and PhoneNumber
     */
    public static void updateGuestPhoneNumber(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("updateGuestPhoneNumber - Updates Guest's phone number");
            System.out.println("COMMAND: updateGuestPhoneNumber COMMAND: GuestNum COMMAND: PhoneNumber");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"GuestNum", "New PhoneNumber"});
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
         * @author Andy Hoang    
         * @params params GuestNum and Email
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
     * updateGuestAddress method
     * @author Andy Hoang
     * @params params GuestNum, Address1, & Address2
     */
    // public static void updateGuestAddress(String[] params) {
    //     System.out.println("");
    //     if(params == null || params.length == 0) {
    //         System.out.println("updateGuestAddress - Updates Guest's address");
    //         System.out.println("COMMAND: updateGuestAddress COMMAND: GuestNum COMMAND: Address1 COMMAND: Address2");
    //     } else {
    //         HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"GuestNum", "NewAddress1", "NewAddress2"});
    //         if(apiParams != null) {
    //             try {
    //                 HotelDB.updateGuestAddress(apiParams);
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    // }

    public static void updateGuestAddress(String[] params){
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("updateGuestAddress - Updates Guest's address");
            System.out.println("COMMAND: updateGuestAddress COMMAND: GuestNum COMMAND: Address1 COMMAND: Address2");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"GuestNum", "NewAddress1", 
                "NewAddress2 (nullable)", "City", "ZipCode (nullable)", "State (XX)"});
            if(apiParams != null) {
                try {
                    HotelDB.updateGuestAddress(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    } 

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

}
