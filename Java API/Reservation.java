
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class Reservation {

    // public static final String CreateReservation = "CreateReservation";

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

}
