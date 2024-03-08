/*
 * JUST DRAFT of create methods
 */


import java.sql.*;

public class Create {

    // Create Reservation GuestNum?? ReservationNum??
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


    // Create Booking
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

    // Create Guest
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

    // Create Staff
    // public static Staff createStaff(String firstName, String lastName, Integer positionId, String phoneNumber, String email) {
    //     try (Connection connection = HotelDB.getConnection()) {
    //         String query = "INSERT INTO Staff (firstName, lastName, positionId, phoneNumber, email) VALUES (?, ?, ?, ?, ?) RETURNING *";
    //         try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    //             preparedStatement.setString(1, firstName);
    //             preparedStatement.setString(2, lastName);
    //             preparedStatement.setInt(3, positionId);
    //             preparedStatement.setString(4, phoneNumber);
    //             preparedStatement.setString(5, email);

    //             try (ResultSet resultSet = preparedStatement.executeQuery()) {
    //                 if (resultSet.next()) {
    //                     return new Staff(
    //                             resultSet.getString("staffNum"),
    //                             resultSet.getString("firstName"),
    //                             resultSet.getString("lastName"),
    //                             resultSet.getInt("positionId"),
    //                             resultSet.getString("phoneNumber"),
    //                             resultSet.getString("email")
    //                     );
    //                 }
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
}

