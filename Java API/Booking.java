//////////////////////////////////////////////////////////////
//                          IMPORTS                         //
//////////////////////////////////////////////////////////////
import java.sql.*;
import java.util.HashMap;

public class Booking {

    //////////////////////////////////////////////////////////////
    //                      GLOBAL VARIABLES                    //
    //////////////////////////////////////////////////////////////
    public static final String getAvailableRooms = "getAvailableRooms";
    public static final String getBookingsOnRoom = "getBookingsOnRoom";
    public static final String getBookingList = "getBookingList";
    public static final String getRoomList = "getRoomList";
    public static final String updateCheckoutTime = "updateCheckoutTime";
    public static final String getRoomInfo = "getRoomInfo";
    public static final String getBookingInfo = "getBookingInfo";

    private String bookingNum;
    private String reservationNum;
    private String roomNum;
    private Timestamp CIDate;
    private Timestamp CoExpectDate;
    private Timestamp CoDate; 

    //////////////////////////////////////////////////////////////
    //                          METHODS                         //
    //////////////////////////////////////////////////////////////
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
     * Retrieves and prints a list of available rooms/bookings for the specified time range.
     * If no parameters are provided, displays usage information.
     * 
     * @params params StartDate and EndDate
     */
    public static void getAvailableRooms(String[] params) {
        System.out.println("");

        // Check if parameters are provided
        if(params == null || params.length == 0) {
            System.out.println("getAvailableRooms - Return list of available rooms/bookings for listed time");
            System.out.println("COMMAND: getAvailableRooms COMMAND: StartDate (yyyy-mm-dd) COMMAND: EndDate (yyyy-mm-dd)");

        } else {
            // Parse input parameters into a HashMap
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"StartDate (yyyy-mm-dd)", "EndDate (yyyy-mm-dd)"});

            // Check if parsing was successful
            if(apiParams != null) {
                try {
                    // Call HotelDB method to get available rooms
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
     * Retrieves and prints a list of checked out/in times for a specific room.
     * If no parameters are provided, displays usage information.
     * 
     * @params params RoomNumber
     */
    public static void getBookingsOnRoom(String[] params) {
        System.out.println("");

        // Check if parameters are provided
        if(params == null || params.length == 0) {
            System.out.println("getBookingsOnRoom - Return list of checked out/in times of specific room");
            System.out.println("COMMAND: getBookingsOnRoom COMMAND: RoomNumber");

        } else {
            // Parse input parameters into a HashMap
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"RoomNumber"});

            // Check if parsing was successful
            if(apiParams != null) {
                try {
                    // Call HotelDB method to get bookings for the specified room
                    HotelDB.getBookingsOnRoom(apiParams);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * getBookingList method
     * @author Andy Hoang
     * 
     * Lists all bookings that have been made with rooms.
     * 
     * @params Input parameter
     */
    public static void getBookingList(String[] params) {
        System.out.println("");

        // Check if parameters are provided
        if(params == null || params.length == 0) {
            System.out.println("getBookingList - Return list bookings that have been made with rooms");
            System.out.println("COMMAND: getBookingList");

        } else {
            // Parse input parameters into a HashMap
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {});

            // Check if parsing was successful
            if(apiParams != null) {
                try {

                    // Call HotelDB method to list all bookings
                    HotelDB.getBookingList(apiParams);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * getBookingInfo method
     * @author Andy Hoang
     * 
     * Returns a list of all bookings within a reservation.
     * 
     * @params ReservationNum
     */
    public static void getBookingInfo(String[] params) {
        System.out.println("");

        // Check if parameters are provided
        if(params == null || params.length == 0) {
            System.out.println("getBookingInfo - Return list of all bookings within a reservation");
            System.out.println("COMMAND: getBookingInfo COMMAND: reservationNum");

        } else {
            // Parse input parameters into a HashMap
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"reservationNum"});
            if(apiParams != null) {

                try {
                    // Call HotelDB method to get booking information for the specified reservation
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
     * 
     * Returns a list of all rooms within the hotel
     * 
     * @params Input parameter
     */
    public static void getRoomList(String[] params) {
        System.out.println("");

        // Check if parameters are provided
        if(params == null || params.length == 0) {
            System.out.println("getRoomList - Return list of all rooms within hotel");
            System.out.println("COMMAND: getRoomList");
        } else {

            // Parse input parameters into a HashMap
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {});

            // Check if parsing was successful
            if(apiParams != null) {
                try {
                    // Call HotelDB method to get the list of all rooms within the hotel
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
     * 
     * Returns information about a specific room within the hotel.
     * 
     * @params roomNumber
     */
    public static void getRoomInfo(String[] params) {
        System.out.println("");

        // Check if parameters are provided
        if(params == null || params.length == 0) {
            System.out.println("getRoomInfo - Return list of all rooms within hotel");
            System.out.println("COMMAND: getRoomInfo COMMAND: roomNumber");

        } else {
            // Parse input parameters into a HashMap
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"roomNumber"});

            // Check if parsing was successful
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
     * Updates the checkout time for a specific room.
     * 
     * @params params RoomNumber, checkouttime
     */
    public static void updateCheckoutTime(String[] params) {
        System.out.println("");

        // Check if parameters are provided
        if(params == null || params.length == 0) {
            System.out.println("updateCheckoutTime - Updates Checkout time with new input");
            System.out.println("COMMAND: updateCheckoutTime COMMAND: RoomNumber COMMAND: CheckoutTime");
        } else {

            // Parse input parameters into a HashMap
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"RoomNumber", "CheckOutTime"});

            // Check if parsing was successful
            if(apiParams != null) {
                try {
                    // Call HotelDB method to update checkout time for the specified room
                    HotelDB.updateCheckoutTime(apiParams);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////
    //                      GETTER & SETTER                     //
    //////////////////////////////////////////////////////////////
  
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

    /*
    * Returns a String representation of the Booking object.
    *
    * @return String representation of the Booking.
    */
  
    public String toString() {
        // Build the string with Booking information
        String temp = "BookingNum: " + bookingNum + "\nReservationNum: " + reservationNum 
            + "\nRoomNum: " + roomNum + "\nCIDate: " + CIDate + "\nCoExpectDate: " + CoExpectDate;
        
        // Add CoDate information if it's not null
        if(CoDate != null)
            temp += "\nCoDate: " + CoDate;
        
            // Return the constructed string
        return temp;
    }

    /*
    * Compares this Booking object with another Booking object for equality.
    *
    * @param toCompare The Booking object to compare with.
    * @return true if the objects are equal, false otherwise.
    */
    
    public boolean equals(Booking toCompare) {
        // Check if the object to compare is null or of a different class
        if(toCompare == null || toCompare.getClass() != getClass()) 
            return false;

        // Cast the object to a Booking for comparison
        Booking that = (Booking) toCompare;

        // Check equality for Booking attributes
        if(this.CoDate == null && that.CoDate == null) {

            // If both CoDate are null, compare other attributes
            return this.bookingNum.equals(that.bookingNum)
                && this.reservationNum.equals(that.reservationNum)
                && this.roomNum.equals(that.roomNum)
                && this.CIDate.equals(that.CIDate)
                && this.CoExpectDate.equals(that.CoExpectDate);

        } else if(this.CoDate != null && that.CoDate != null) { //if CoDate not null
            // If CoDate is not null for both, compare all attributes including CoDate
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
}
