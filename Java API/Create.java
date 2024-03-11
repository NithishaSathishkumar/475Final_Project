//////////////////////////////////////////////////////////////
//                          IMPORTS                         //
//////////////////////////////////////////////////////////////
import java.sql.*;

public class Create {
public static final String createGuest = "createGuest";

    /*
     * Create a new reservation in the database.
     * @author Song Sahong
     * @param numberOfGuest The number of guests for the reservation.
     * @param paymentType The payment type for the reservation.
     * @param amount The total amount for the reservation.
     * @param createdTime The timestamp of when the reservation was created.
     * @param guestNum The guest number associated with the reservation.
     * @param staffNum The staff number associated with the reservation.
     * @return Reservation The created Reservation object.
     */
    public static Reservation createReservation(Integer numberOfGuest, String paymentType, double amount, Timestamp createdTime, String guestNum, String staffNum) {
        try (Connection connection = HotelDB.getConnection()) {
            String query = "INSERT INTO Reservation (numberOfGuest, paymentType, amount, createdTime, guestNum, staffNum) VALUES (?, ?, ?, ?, ?, ?) RETURNING reservationNum, numberOfGuest, paymentType, amount, createdTime, guestNum, staffNum";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, numberOfGuest);
                preparedStatement.setString(2, paymentType);
                preparedStatement.setDouble(3, amount);
                preparedStatement.setTimestamp(4, createdTime);
                preparedStatement.setString(5, guestNum);
                preparedStatement.setString(6, staffNum);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Reservation(
                                resultSet.getString("reservationNum"),
                                resultSet.getInt("numberOfGuest"),
                                resultSet.getString("paymentType"),
                                resultSet.getDouble("amount"),
                                resultSet.getTimestamp("createdTime"),
                                resultSet.getString("guestNum"),
                                resultSet.getString("staffNum")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Create a new booking in the database.
     * @author Song Sahong
     * @param reservationNum The reservation number associated with the booking.
     * @param roomNum The room number associated with the booking.
     * @param CIDate The check-in date for the booking.
     * @param CoExpectDate The expected check-out date for the booking.
     * @return Booking The created Booking object.
     */
    public static Booking createBooking(String reservationNum, String roomNum, Timestamp CIDate, Timestamp CoExpectDate) {
        try (Connection connection = HotelDB.getConnection()) {
            String query = "INSERT INTO Booking (reservationNum, roomNum, CIDate, CoExpectDate) VALUES (?, ?, ?, ?) RETURNING *";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, reservationNum);
                preparedStatement.setString(2, roomNum);
                preparedStatement.setTimestamp(3, CIDate);
                preparedStatement.setTimestamp(4, CoExpectDate);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Booking(
                                resultSet.getString("bookingNum"),
                                resultSet.getString("reservationNum"),
                                resultSet.getString("roomNum"),
                                resultSet.getTimestamp("CIDate"),
                                resultSet.getTimestamp("CoExpectDate")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Create a new guest in the database.
     * 
     * @param firstName The first name of the guest.
     * @param lastName The last name of the guest.
     * @param email The email address of the guest.
     * @param address1 The first line of the guest's address.
     * @param address2 The second line of the guest's address.
     * @param phoneType The type of phone used by the guest.
     * @param city The city of the guest's address.
     * @param zipcode The ZIP code of the guest's address.
     * @param state The state of the guest's address.
     * @return Guest The created Guest object.
     */
    public static Guest createGuest(String firstName, String lastName, String email, String address1, String address2, String phoneType, String city, char zipcode, String state) {
        try (Connection connection = HotelDB.getConnection()) {
            String query = "INSERT INTO Guest (firstName, lastName, email, address1, address2, phoneType, city, zipcode, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, address1);
                preparedStatement.setString(5, address2);
                preparedStatement.setString(6, phoneType);
                preparedStatement.setString(7, city);
                preparedStatement.setString(8, String.valueOf(zipcode));
                preparedStatement.setString(9, state);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Guest(
                                resultSet.getString("guestNum"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getString("email"),
                                resultSet.getString("address1"),
                                resultSet.getString("address2"),
                                resultSet.getString("phoneType"),
                                resultSet.getString("city"),
                                resultSet.getString("zipcode"),
                                resultSet.getString("state")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

