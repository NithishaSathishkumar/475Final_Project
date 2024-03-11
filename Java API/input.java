import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class input {
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /*
     * Checks if a string is null or equals "null" and returns an empty string in such cases.
     * @author Nithisha Sathishkumar
     * @param value The input string to check.
     * @return An empty string if the input is null or equals "null"; otherwise, the input string.
     */
    public static String CheckStringForNull(String value)
    {
        if(value == null || value.equals("null"))
        {
            return "";
        }
        return value;
    }

    /*
     * Parses input parameters based on the provided parameter names.
     * @author Nithisha Sathishkumar
     * @param params An array of parameter names.
     * @return A HashMap containing the parameter names and their corresponding values.
     */
    public static HashMap<String, String> ParseInputParams(String[] params)
    {
        HashMap<String, String> map = new HashMap<>();

        for(int i = 0; i < params.length; i++)
        {
            String paramName = params[i];

            System.out.print(paramName + ": ");
            String paramValue = System.console().readLine();

            if(CheckKnownParamValues(paramName, paramValue))
            {
                map.put(paramName, paramValue);
            }
            else
            {
                return null;
            }
        }

        return map;
    }

    /*
     * Checks if the provided parameter values match the expected values for certain parameters.
     * Prints error messages if unexpected values are encountered.
     * @author Nithisha Sathishkumar
     * @param paramName  The name of the parameter to check.
     * @param paramValue The value of the parameter to check.
     * @return True if the parameter values are as expected; otherwise, false.
     */
    private static boolean CheckKnownParamValues(String paramName, String paramValue)
    {
        boolean valueOk = true;

        switch(paramName) {
            case "Position":
                if(!paramValue.equals("Manager") && !paramValue.equals("Receptionist"))
                {
                    System.out.println(" - Unexpected value, expecting [Manager|Receptionist]");
                    valueOk = false;
                }
                break;

            case "ReservationNum":
            case "FirstName":
            case "LastName":
            case "ClassName":
            case "StaffNum":
            case "GuestNum":
                if(paramValue == null || paramValue.length() == 0)
                {
                    System.out.println(" - Unexpected value, value can not be empty");
                    valueOk = false;
                }             
                break;
            case "CreateTime":
                try {
                    LocalDate.parse(paramValue, dtf);
                } catch (DateTimeParseException e) {
                    valueOk = false;
                    System.out.println(" - Unexpected value, expecting DateTime format yyyy-MM-dd HH:mm");
                }  
                break;                   
            case "CoDate":
            case "CiDate":
            case "CoExpectDate":
                try {
                    LocalDate.parse(paramValue, df);
                } catch (DateTimeParseException e) {
                    valueOk = false;
                    System.out.println(" - Unexpected value, expecting DateTime format yyyy-MM-dd");
                }  
                break;    
                               
            default:
          }        

        return valueOk;
    }
    
}
