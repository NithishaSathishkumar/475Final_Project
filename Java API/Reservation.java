
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;


public class Reservation {

    public static final String CreateReservation = "CreateReservation";

    private String reservationNum;
    private int numberOfGuest;
    private String paymentType;
    private double amount;
    private Timestamp createdTime;
    private String guestNum;
    private String staffNum;

    public Reservation(String reservationNum, int numberOfGuest, String paymentType, double amount, Timestamp createdTime, String guestNum, String staffNum) {
        this.reservationNum = reservationNum;
        this.numberOfGuest = numberOfGuest;
        this.paymentType = paymentType;
        this.amount = amount;
        this.createdTime = createdTime;
        this.guestNum = guestNum;
        this.staffNum = staffNum;
    }

    static class Booking {
        String roomNum;
        java.util.Date checkInDate;
        java.util.Date checkOutDate;
        public Booking(String roomNum, java.util.Date checkInDate, java.util.Date checkOutDate){
            this.roomNum=roomNum;
            this.checkInDate=checkInDate;
            this.checkOutDate=checkOutDate;
        }
    }
    static class Room {
        int roomId;
        String roomNum;
        double price;
        public Room(int roomId, String roomNum, double price){
            this.roomId=roomId;
            this.roomNum=roomNum;
            this.price=price;
        }
    }

   

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public static boolean Create_Reservation(String[] params){

        if(params == null || params.length == 0){
            System.out.println("");
            System.out.println("CreateReservation: Create Reservation");
            System.out.println("COMMAND: Create_Reservation NumberOfRoom:xx NumberOfGuest:xx GuestNum:xx staffNum:xx paymentType");

        }else{
            System.out.println("");
            HashMap<String, String> apiParams = input.ParseInputParams(new String[] {"number_of_guest","number_of_room", "payment_type", "guest_number",  "staff_number"});
            
            if(apiParams == null){
                System.out.println("Please Input Correct Data.");
                return false;
            }

            int numberOfRoom=Integer.parseInt(apiParams.get("number_of_room"));
            ArrayList<Booking> bookings = getBookings(numberOfRoom);
            HashMap<String, Room> rooms = getRooms(bookings);

            int numberOfGuest = Integer.parseInt(apiParams.get("number_of_guest"));
            
            int guestId = getGuestId(apiParams.get("guest_number"));

            int staffId = getStaffId(apiParams.get("staff_number"));

            String paymentTypeId = getPaymentTypeId(apiParams.get("payment_type"));
            int paymentId = createPayment(paymentTypeId);

            double amout=getTotalAmount(bookings, rooms);
            String reservationNumber=getReservationNumber();

            int reservationId = createReservation(reservationNumber, numberOfGuest, paymentId, amout, guestId, staffId);

            for(int i=0;i<bookings.size();i++){
                String no=bookings.get(i).roomNum;
                createBooking(reservationId, rooms.get(no).roomId, bookings.get(i).checkInDate, bookings.get(i).checkOutDate);
            }
        
            return true;
        }
        return false;
    
    }

    private static ArrayList<Booking> getBookings(int rooms){
        ArrayList<Booking> bookings=new ArrayList<>();
        for(int i=0;i<rooms;i++){
            HashMap<String, String> ps = input.ParseInputParams(new String[] { "room_number", "checkin_date", "checkout_date"});
            String roomNum=ps.get("room_number");
            try{
                java.util.Date checkInDate=dateFormat.parse(ps.get("checkin_date"));
                java.util.Date checkOutDate=dateFormat.parse(ps.get("checkout_date"));
                bookings.add(new Reservation.Booking(roomNum, checkInDate, checkOutDate));
            }catch(Exception ex){
                //error
            }   
        }
        return bookings;
    }

    private static HashMap<String, Room> getRooms(ArrayList<Booking> bookings){
        // get rooms detail from db 
        return new HashMap<>();
    }

    private static int getGuestId(String guestNumber){
        return 0;
    }


    private static int getStaffId(String staffNumber){
        return 0;
    }


    private static String getPaymentTypeId(String paymentType){
        return "";
    }
    
    private static int createPayment(String paymentTypeId){
        return 0;
    }

    private static String getReservationNumber(){
        return "";
    }

    private static int getStayNights(java.util.Date date1, java.util.Date date2){
        long diffInMillies = date2.getTime() - date1.getTime();
        return (int)(diffInMillies / (1000 * 60 * 60 * 24));
    }

    

    private static double getTotalAmount(ArrayList<Booking> bookings, HashMap<String, Room> rooms){
        double amount=0;
        for(int i=0;i<bookings.size();i++){
            int nights = getStayNights(bookings.get(i).checkInDate, bookings.get(i).checkOutDate);
            double price = rooms.get(bookings.get(i).roomNum).price;
            amount+=(price*nights);
        }
        return amount;
    }


    private static int createReservation(String reservationNumber, int numberOfGuest, int paymentId, double amout, int guestId, int staffId){
        return 0;
    }

    private static int createBooking(int reservationId, int roomId, java.util.Date checkInDate, java.util.Date checkOutDate){
        return 0;
    }
   
}
