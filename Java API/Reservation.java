
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class Reservation {

    // public static final String CreateReservation = "CreateReservation";
    public static final String getReservationList = "getReservationList";
    public static final String getReservationInfo = "getReservationInfo";
    public static final String updatePayment = "updatePayment";

    private String reservationNum;
    private int numberOfGuest;
    private String paymentType;
    private double amount;
    private Timestamp createdTime;
    private String guestNum;
    private String staffNum;

    public Reservation(String reservationNum, int numberOfGuest, String paymentType, double amount,
            Timestamp createdTime, String guestNum, String staffNum) {
        this.reservationNum = reservationNum;
        this.numberOfGuest = numberOfGuest;
        this.paymentType = paymentType;
        this.amount = amount;
        this.createdTime = createdTime;
        this.guestNum = guestNum;
        this.staffNum = staffNum;
    }

    /*
    * getReservationList method
    * Method to retrieve the list of guests within the hotel.
    * 
    * @param params The input parameters (not used in the current implementation).
    * @author Andy Hoang
    */
    public static void getReservationList(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getReservationList - Return list of all reservations within hotel");
            System.out.println("COMMAND: getReservationList");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {});
            if(apiParams != null) {
                try {
                    HotelDB.getReservationList(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * updatePayment method
    * Updates payment whether it is paid or not on a reservation
    * 
    * @param params Payment Date and Payment Method
    * @author Andy Hoang
    */
    public static void updatePayment(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("updatePayment - Updates payment whether it is paid or not on a reservation");
            System.out.println("COMMAND: updatePayment COMMAND: ReservationNum COMMAND: PaymentType COMMAND: " +
                                    "PaymentDate COMMAND: OriginalCreationDate COMMAND: PaymentDate");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"ReservationNum", "PaymentType (XX)", 
                                                                                        "OriginalCreationDate", "PaymentDate"});
            if(apiParams != null) {
                try {
                    HotelDB.updatePayment(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * getReservationInfo method
    * Method to retrieve the list of guests within the hotel.
    * 
    * @param params The input parameters guestNum.
    * @author Andy Hoang
    */
    public static void getReservationInfo(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getReservationInfo - Return list of all reservations related to the guestNum");
            System.out.println("COMMAND: getReservationInfo COMMAND: GuestNum");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"guestNum"});
            if(apiParams != null) {
                try {
                    HotelDB.getReservationInfo(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
