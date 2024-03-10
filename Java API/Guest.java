/*
 * JUST DRAFT of Guest
 */

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Guest {
    public static final String getPaymentList = "getPaymentList";
    public static final String getGuestList = "getGuestList";
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

    // Getters and setters go here
}
