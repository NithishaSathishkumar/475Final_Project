/*
 * JUST DRAFT of Booking
 */


import java.sql.*;
import java.util.HashMap;

public class Booking {
    public static final String getAvailableRooms = "getAvailableRooms";
    public static final String getBookingsOnRoom = "getBookingsOnRoom";
    public static final String listAllBookings = "listAllBookings";
    public static final String getRoomList = "getRoomList";
    public static final String updateCheckoutTime = "updateCheckoutTime";
    public static final String getRoomInfo = "getRoomInfo";
    public static final String getBookingInfo = "getBookingInfo";

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

    /*
     * getAvailableRooms for a date range method
     * @author Andy Hoang
     * 
     * @params params StartDate and EndDate
     */
    public static void getAvailableRooms(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getAvailableRooms - Return list of available rooms/bookings for listed time");
            System.out.println("COMMAND: getAvailableRooms COMMAND: StartDate (yyyy-mm-dd) COMMAND: EndDate (yyyy-mm-dd)");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"StartDate (yyyy-mm-dd)", "EndDate (yyyy-mm-dd)"});
            if(apiParams != null) {
                try {
                    HotelDB.getAvailableRooms(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * getBookingsOnRoom method
     * @author Andy Hoang
     * 
     * @params params RoomNumber to find bookings of
     */
    public static void getBookingsOnRoom(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getBookingsOnRoom - Return list of checked out/in times of specific room");
            System.out.println("COMMAND: getBookingsOnRoom COMMAND: RoomNumber");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"RoomNumber"});
            if(apiParams != null) {
                try {
                    HotelDB.getBookingsOnRoom(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

/*
     * listAllBookings method
     * @author Andy Hoang
     */
    public static void listAllBookings(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("listAllBookings - Return list bookings that have been made with rooms");
            System.out.println("COMMAND: listAllBookings");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {});
            if(apiParams != null) {
                try {
                    HotelDB.listAllBookings(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * getBookingInfo method
     * @author Andy Hoang
     */
    public static void getBookingInfo(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getBookingInfo - Return list of all bookings within a reservation");
            System.out.println("COMMAND: getBookingInfo COMMAND: reservationNum");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"reservationNum"});
            if(apiParams != null) {
                try {
                    HotelDB.getBookingInfo(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * getRoomList method
     * @author Andy Hoang
     */
    public static void getRoomList(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getRoomList - Return list of all rooms within hotel");
            System.out.println("COMMAND: getRoomList");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {});
            if(apiParams != null) {
                try {
                    HotelDB.getRoomList(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * getRoomInfo method
     * @author Andy Hoang
     */
    public static void getRoomInfo(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("getRoomInfo - Return list of all rooms within hotel");
            System.out.println("COMMAND: getRoomInfo COMMAND: roomNumber");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"roomNumber"});
            if(apiParams != null) {
                try {
                    HotelDB.getRoomInfo(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * updateCheckoutTime method
     * @author Andy Hoang
     * 
     * @params params RoomNumber to find bookings of
     */
    public static void updateCheckoutTime(String[] params) {
        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("updateCheckoutTime - Updates Checkout time with new input");
            System.out.println("COMMAND: updateCheckoutTime COMMAND: RoomNumber COMMAND: CheckoutTime");
        } else {
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"RoomNumber", "CheckOutTime"});
            if(apiParams != null) {
                try {
                    HotelDB.updateCheckoutTime(apiParams);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Getters and setters go here (Potentially not used)
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
