/*
 * JUST DRAFT of Guest
 */

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Guest {
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



    // Getters and setters go here
}
