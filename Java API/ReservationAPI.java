//////////////////////////////////////////////////////////////
//                          IMPORTS                         //
//////////////////////////////////////////////////////////////
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.time.LocalDate;

public class ReservationAPI {

    static class Guest {
        int id;
        String num;
        String firstName;
        String lastName;

        public Guest(int id, String num, String firstName, String lastName) {
            this.id = id;
            this.num = num;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    static class Booking {
        String roomNum;
        java.util.Date checkInDate;
        java.util.Date checkOutDate;

        public Booking(String roomNum, java.util.Date checkInDate, java.util.Date checkOutDate) {
            this.roomNum = roomNum;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }
    }

    static class Room {
        int roomId;
        String roomNum;
        double price;

        public Room(int roomId, String roomNum, double price) {
            this.roomId = roomId;
            this.roomNum = roomNum;
            this.price = price;
        }
    }

    public static final String CreateReservation = "CreateReservation";
    
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    //////////////////////////////////////////////////////////////
    //                      METHODS                             //
    //////////////////////////////////////////////////////////////

    /*
     * Creates a reservation based on user input or provided parameters.
     * Displays usage information if no parameters are provided.
     * @author Song Sahong
     * @param params Optional parameters for creating a reservation interactively
     * @return boolean Indicates whether the reservation creation was successful
     */
    public static boolean Create_Reservation(String[] params) {

        System.out.println("");
        if(params == null || params.length == 0) {
            System.out.println("CreateReservation - Make the reservation");
            System.out.println("COMMAND: CreateReservation (Detail input will be asked interactively)");
            return true;
        }
        try {

            connection = HotelDB.getConnection();

            HashMap<String, String> apiParams = input.ParseInputParams(new String[] { "number_of_guest",
                    "number_of_room", "payment_type", "guest_email", "staff_number" });

            if (apiParams == null) {
                System.out.println("Please input correct data.");
                return false;
            }

            int numberOfRoom = Integer.parseInt(apiParams.get("number_of_room"));
            ArrayList<Booking> bookings = getBookings(numberOfRoom);// to get the room info from guest 
            HashMap<String, Room> rooms = getRooms(bookings);// to get the info from the room obj by using the roomnum

            int numberOfGuest = Integer.parseInt(apiParams.get("number_of_guest"));

            Guest guest = getGuest(apiParams.get("guest_email"));
            int staffId = getStaffId(apiParams.get("staff_number"));
            String paymentType = apiParams.get("payment_type");
            String paymentTypeId = getPaymentTypeId(paymentType);
            int paymentId = createPayment(paymentTypeId);

            double amout = getTotalAmount(bookings, rooms);
            String reservationNumber = getReservationNumber();

            int reservationId = createReservation(reservationNumber, numberOfGuest, paymentId, amout, guest.id,
                    staffId);

            for (int i = 0; i < bookings.size(); i++) {
                String no = bookings.get(i).roomNum;
                createBooking(reservationId, rooms.get(no).roomId, bookings.get(i).checkInDate,
                        bookings.get(i).checkOutDate);
            }

            connection.close();

            System.out.println(String.format("Great! Here is your booking information."));
            System.out.println(
                    "----------------------------------------------------------------------------------------------");

            System.out.println(String.format(
                    "Reservation No.: %s, Name: %s %s, Guests: %d, Payment Type: %s, Rooms: %d, Amount: %.2f", reservationNumber, guest.firstName,
                    guest.lastName, numberOfGuest, paymentType, numberOfRoom, amout));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to book. An unexpected error has happened!");
            return false;
        }

    }

    /*
    * Private method to interactively gather booking information for a specified number of rooms.
    * 
    * @param rooms The number of rooms for which booking information needs to be entered.
    * @return ArrayList<Booking> List of Booking objects containing room number, check-in, and check-out dates.
    * 
    * @author Song Sahong
    */
    private static ArrayList<Booking> getBookings(int rooms) {
        System.out.println("Start enter the booking info:");
        ArrayList<Booking> bookings = new ArrayList<>();
        for (int i = 0; i < rooms; i++) {
            System.out.println(String.format("--- Room %d ----", i + 1));
            HashMap<String, String> ps = input
                    .ParseInputParams(new String[] { "room_number", "checkin_date", "checkout_date" });
            String roomNum = ps.get("room_number");
            try {

                java.util.Date checkInDate = dateFormat.parse(ps.get("checkin_date"));
                java.util.Date checkOutDate = dateFormat.parse(ps.get("checkout_date"));
                java.util.Date today = dateFormat.parse(LocalDate.now().toString());
                if (checkInDate.getTime() - today.getTime() < 0
                        || getStayNights(checkInDate, checkOutDate) <= 0) {
                    System.out.println("The check in or out date is invalid! Please reenter.");
                    i--;
                    continue;
                }
                bookings.add(new Booking(roomNum, checkInDate, checkOutDate));
            } catch (Exception ex) {
                // error
                System.out.println("Error to acccept this room info. Please enter again!");
                i--;
                continue;
            }
        }
        return bookings;
    }

    /*
    * Private method to retrieve room details from the database based on provided bookings.
    * 
    * @param bookings List of Booking objects containing room numbers.
    * @return HashMap<String, Room> Map of room numbers to Room objects containing room details.
    * 
    * @throws SQLException If a database access error occurs.
    * 
    * @author Song Sahong
    */
    private static HashMap<String, Room> getRooms(ArrayList<Booking> bookings) throws SQLException {
        // get rooms detail from db
        ArrayList<String> roomNums = new ArrayList<>();
        for (Booking booking : bookings) {
            roomNums.add(booking.roomNum);
        }

        StringBuffer query = new StringBuffer("SELECT id, roomnumber, priceperday " +
                "FROM room " +
                "Where roomnumber in (");

        for (int i = 0; i < bookings.size(); i++) {
            query.append("?");
            if (i < bookings.size() - 1) {
                query.append(",");
            }
        }
        query.append(")");

        preparedStatement = connection.prepareStatement(query.toString());

        for (int i = 0; i < bookings.size(); i++) {
            preparedStatement.setString(i + 1, bookings.get(i).roomNum);
        }
        resultSet = preparedStatement.executeQuery();

        HashMap<String, Room> rooms = new HashMap<>();
        while (resultSet != null && resultSet.next()) {

            int roomId = resultSet.getInt("id");
            String roomNum = resultSet.getString("roomnumber");
            double price = resultSet.getDouble("priceperday");

            Room r = new Room(roomId, roomNum, price);

            rooms.put(roomNum, r);
        }
        return rooms;
    }

    /*
    * Private method to retrieve guest information based on the provided guest email.
    * 
    * @param guestEmail The email address of the guest.
    * @return Guest Guest object containing guest details.
    * 
    * @throws SQLException If a database access error occurs.
    * 
    * @author Song Sahong
    */
    private static Guest getGuest(String guestEmail) throws SQLException {

        String query = "SELECT * FROM guest WHERE email = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, guestEmail);
        resultSet = preparedStatement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            int guestId = resultSet.getInt("id");
            String guestNum = resultSet.getString("guestnum");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");

            return new Guest(guestId, guestNum, firstName, lastName);
        }
        return null;
    }

    /*
    * Private method to retrieve the staff ID based on the staff number.
    * 
    * @param staffNumber The staff number to look up in the database.
    * @return int The staff ID if found, otherwise -1.
    * 
    * @throws SQLException If a database access error occurs.
    * 
    * @author Song Sahong
    */
    private static int getStaffId(String staffNumber) throws SQLException {
        int staffId = -1; // Default value if no staff id found
        String query = "SELECT id FROM staff WHERE staffnum = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, staffNumber);
        resultSet = preparedStatement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            staffId = resultSet.getInt("id");
        }

        return staffId;
    }

    /*
    * Private method to retrieve the payment type ID based on the payment type name.
    * 
    * @param paymentType The payment type to look up in the database.
    * @return String The payment type ID if found, otherwise an empty string.
    * 
    * @throws SQLException If a database access error occurs.
    * 
    * @author Song Sahong
    */
    private static String getPaymentTypeId(String paymentType) throws SQLException {
        String paymentTypeId = ""; // Default value if no payment type id found

        String query = "SELECT id FROM paymenttype WHERE methodname = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, paymentType);
        resultSet = preparedStatement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            paymentTypeId = resultSet.getString("id");
        }

        return paymentTypeId;
    }

    /*
    * Private method to create a payment record in the database.
    * 
    * @param paymentTypeId The payment type ID associated with the payment.
    * @return int The ID of the newly created payment record.
    * 
    * @throws SQLException If a database access error occurs.
    * 
    * @author Song Sahong
    */
    private static int createPayment(String paymentTypeId) throws SQLException {
        int paymentId = -1; // Default value if payment creation fails

        String query = "INSERT INTO Payment (PaymentTypeID, PaymentDate) VALUES (?, CURRENT_TIMESTAMP) RETURNING ID";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, paymentTypeId);
        resultSet = preparedStatement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            paymentId = resultSet.getInt(1); // Get the ID of the newly inserted payment
        }

        return paymentId;
    }

    /*
    * Private method to generate a reservation number based on the current timestamp.
    * 
    * @return String The generated reservation number.
    * 
    * @author Song Sahong
    */
    private static String getReservationNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyddMMHHmmss");

        // Format the current time
        String formattedTime = dateFormat.format(new java.util.Date());
        return formattedTime;
        // return String.format("%d-%s", guestId, formattedTime);
    }

    /*
    * Private method to calculate the number of stay nights between two dates.
    * 
    * @param date1 The check-in date.
    * @param date2 The check-out date.
    * @return int The number of stay nights.
    * 
    * @author Song Sahong
    */
    private static int getStayNights(java.util.Date date1, java.util.Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        long remaining = diffInMillies % (1000 * 60 * 60 * 24);

        // Check if there are any remaining milliseconds
        if (remaining > 0) {
            // If there are remaining milliseconds, add one day to round up
            diffInDays++;
        } else if (remaining < 0)
            diffInDays--;

        return (int) diffInDays;
    }

    /*
    * Private method to calculate the total amount for the given bookings and room details.
    * 
    * @param bookings List of Booking objects containing room number, check-in, and check-out dates.
    * @param rooms Map of room numbers to Room objects containing room details.
    * @return double The total amount for the reservation.
    * 
    * @author Song Sahong
    */
    private static double getTotalAmount(ArrayList<Booking> bookings, HashMap<String, Room> rooms) {
        double amount = 0;
        for (int i = 0; i < bookings.size(); i++) {
            int nights = getStayNights(bookings.get(i).checkInDate, bookings.get(i).checkOutDate);
            double price = rooms.get(bookings.get(i).roomNum).price;
            amount += (price * nights);
        }
        return amount;
    }

    /*
    * Private method to create a reservation record in the database.
    * 
    * @param reservationNumber The reservation number.
    * @param numberOfGuest The number of guests for the reservation.
    * @param paymentId The ID of the associated payment record.
    * @param amount The total amount for the reservation.
    * @param guestId The ID of the guest making the reservation.
    * @param staffId The ID of the staff member handling the reservation.
    * @return int The ID of the newly created reservation record.
    * 
    * @throws Exception If an unexpected error occurs.
    * 
    * @author Song Sahong
    */
    private static int createReservation(String reservationNumber, int numberOfGuest, int paymentId, double amount,
            int guestId, int staffId) throws Exception {
        int reservationId = -1; // Default value if payment creation fails

        String query = "INSERT INTO Reservation (reservationnum, numberOfGuest,paymentid, amount,guestid,staffId, createtime) VALUES (?,?,?,?,?,?, now()::timestamp(0)) RETURNING ID";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, reservationNumber);
        preparedStatement.setInt(2, numberOfGuest);
        preparedStatement.setInt(3, paymentId);
        preparedStatement.setDouble(4, amount);
        preparedStatement.setInt(5, guestId);
        preparedStatement.setInt(6, staffId);
        resultSet = preparedStatement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            reservationId = resultSet.getInt(1); // Get the ID of the newly inserted payment
        }

        return reservationId;
    }

    /*
    * Private method to create a booking record in the database.
    * 
    * @param reservationId The ID of the associated reservation record.
    * @param roomId The ID of the room for the booking.
    * @param checkInDate The check-in date for the booking.
    * @param checkOutDate The check-out date for the booking.
    * @return int The ID of the newly created booking record.
    * 
    * @throws Exception If an unexpected error occurs.
    * 
    * @author Song Sahong
    */
    private static int createBooking(int reservationId, int roomId, java.util.Date checkInDate,
            java.util.Date checkOutDate) throws Exception {

        int bookingId = -1; // Default value if payment creation fails

        String query = "INSERT INTO Booking (reservationId, roomId, ciDate, codate) VALUES (?, ?, ?, ?) RETURNING ID";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reservationId);
        preparedStatement.setInt(2, roomId);
        preparedStatement.setDate(3, new java.sql.Date(checkInDate.getTime()));
        preparedStatement.setDate(4, new java.sql.Date(checkOutDate.getTime()));

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            bookingId = resultSet.getInt(1); // Get the ID of the newly inserted booking
        }

        return bookingId;
    }

}
