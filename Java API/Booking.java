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
    private Timestamp CoDate; //Did we not need a CoDate, I did not see one listed beforehand

    public Booking() {
        this.bookingNum = null;
        this.reservationNum = null;
        this.roomNum = null;
        this.CIDate = null;
        this.CoExpectDate = null;
        this.CoDate = null;
    }

    public Booking(String bookingNum, String reservationNum, String roomNum, Timestamp CIDate, 
        Timestamp CoExpectDate, Timestamp CoDate) {
        this.bookingNum = bookingNum;
        this.reservationNum = reservationNum;
        this.roomNum = roomNum;
        this.CIDate = CIDate;
        this.CoExpectDate = CoExpectDate;
        this.CoDate = CoDate;
    }
    
    //if CoDate not listed or null
    public Booking(String bookingNum, String reservationNum, String roomNum, Timestamp CIDate, 
        Timestamp CoExpectDate) {
        this.bookingNum = bookingNum;
        this.reservationNum = reservationNum;
        this.roomNum = roomNum;
        this.CIDate = CIDate;
        this.CoExpectDate = CoExpectDate;
        this.CoDate = null;
    }

    // Getters and setters go here
    public String getBookingNum() {
        return bookingNum;
    }

    public void setBookingNum(String bookingNum) {
        this.bookingNum = bookingNum;
    }

    public String getReservationNum() {
        return reservationNum;
    }
    
    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public Timestamp getCIDate() {
        return CIDate;
    }

    public void setCIDate(Timestamp CIDate) {
        this.CIDate = CIDate;
    } 

    public Timestamp getCoExpectDate() {
        return CoExpectDate;
    }

    public void setCoExpectDate(Timestamp CoExpectDate) {
        this.CoExpectDate = CoExpectDate;
    }

    public Timestamp getCoDate() {
        return CoDate;
    }   
    
    public void setCoDate(Timestamp CoDate) {
        this.CoDate = CoDate;
    }

    //toString
    public String toString() {
        String temp = "BookingNum: " + bookingNum + "\nReservationNum: " + reservationNum 
            + "\nRoomNum: " + roomNum + "\nCIDate: " + CIDate + "\nCoExpectDate: " + CoExpectDate;
        
        if(CoDate != null)
            temp += "\nCoDate: " + CoDate;
        
        return temp;
    }
    
    //equals
    public boolean equals(Booking toCompare) {
        if(toCompare == null || toCompare.getClass() != getClass()) 
            return false;

        Booking that = (Booking) toCompare;
        if(this.CoDate == null && that.CoDate == null) {
            return this.bookingNum.equals(that.bookingNum)
                && this.reservationNum.equals(that.reservationNum)
                && this.roomNum.equals(that.roomNum)
                && this.CIDate.equals(that.CIDate)
                && this.CoExpectDate.equals(that.CoExpectDate);

        } else if(this.CoDate != null && that.CoDate != null) { //if CoDate not null
            return this.bookingNum.equals(that.bookingNum)
                && this.reservationNum.equals(that.reservationNum)
                && this.roomNum.equals(that.roomNum)
                && this.CIDate.equals(that.CIDate)
                && this.CoExpectDate.equals(that.CoExpectDate)
                && this.CoDate.equals(that.CoDate);
        } else { //Covers != null && == null combination
            return false;
        }
    }


    //simple testing
    /*public static void main(String[] args) { 
        Booking a = new Booking("testBNum", "TestRNum", "TestRoomNum", new Timestamp(0, 10, 25, 0, 0, 0, 0), new Timestamp(2002, 10, 25, 0, 0, 0, 0));
        System.out.println(a);
        Booking b = new Booking("testBNum", "TestRNum", "TestRoomNum", new Timestamp(0, 10, 25, 0, 0, 0, 0), new Timestamp(2002, 10, 25, 0, 0, 0, 0));
        System.out.println(a.equals(b));
        System.out.println("\n" + b);
    } //Timestamp(int year, int month, int date, int hour, int minute, int second, int nano)
    */
    
}
