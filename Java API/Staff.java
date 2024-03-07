/*
 * JUST DRAFT of Staff
 */

import java.sql.SQLException;
import java.util.HashMap;

public class Staff {
    private String staffNum;
    private String firstName;
    private String lastName;
    private Integer positionID;
    private String phoneNumber;
    private String email;
    
    public Staff(String staffNum, String firstName, String lastName, Integer positionId, String phoneNumber, String email) {
        this.staffNum = staffNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionID = positionId;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and setters go here

}
