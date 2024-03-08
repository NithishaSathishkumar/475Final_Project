import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class input {
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String CheckStringForNull(String value)
    {
        if(value == null || value.equals("null"))
        {
            return "";
        }
        return value;
    }

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
