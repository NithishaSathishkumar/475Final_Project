/*
 * JUST DRAFT of Booking
 */


import java.sql.*;

public class Booking {
    private String bookingNum;
    private String reservationNum;
    private String roomNum;
    private Timestamp CIDate;
    private Timestamp CoExpectDate;

    public Booking(String bookingNum, String reservationNum, String roomNum, Timestamp CIDate, Timestamp CoExpectDate) {
        this.bookingNum = bookingNum;
        this.reservationNum = reservationNum;
        this.roomNum = roomNum;
        this.CIDate = CIDate;
        this.CoExpectDate = CoExpectDate;
    }

    // Getters and setters go here
}