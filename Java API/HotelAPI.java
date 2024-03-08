/*
* Main Method
* @author Nithisha Sathishkumar
*/

public class HotelAPI {
    public static void main(String[] args){
        System.out.println();
        HotelAsciiArt.printHotel();
        System.out.println();
        System.out.println("🌟🌈 Welcome to the UWB Hotel System! 🌟🌈");
        System.out.println("Type '?' or 'help' to explore our adorable APIs. 🐾");
        System.out.println("Type 'exit' to QUIT the program. 😢");
        System.out.println("Type 'clear' to clear the Terminal. 🧹");
        System.out.println();

        String cmb = "";

        while(!cmb.equalsIgnoreCase("exit")){
            System.out.print("HotelDB-# ");
            cmb = System.console().readLine();

            String[] mainCmdArr = cmb.split(" ", 2);

            if(mainCmdArr.length > 1)
            {
                System.out.println("Providing more than one command not supported");
                System.out.println("");
                continue;
            }

            switch(mainCmdArr[0]){
                case "Help":
                case "help":
                    Menu();
                    break;
                case "exit":
                case "Exit":
                    break;

                case Staff.ListOfStaff:
                    Staff.getStaffList(mainCmdArr);
                    break;

                case Staff.ListOfStaffByPosition:
                    Staff.getStaffListByPosition(mainCmdArr);
                    break;
                
                case Staff.StaffInfoByStaffNum:
                    Staff.getStaffInfoByStaffNum(mainCmdArr);
                    break;
                
                case Staff.ListOfJobPosition:
                    Staff.getJobPositionList(mainCmdArr);
                    break;

                case Staff.GetStaffStaffNum:
                    Staff.getStaff_Staffnum(mainCmdArr);
                    break;

                case Staff.UpdateStaffPhoneNumber:
                    Staff.updateStaffPhoneNumber(mainCmdArr);
                    break;

                case Reservation.CreateReservation:
                    Reservation.Create_Reservation(mainCmdArr);
                    break;

                case "clear":
                case "Clear":
                    clearTerminal();
                    System.out.println();
                    HotelAsciiArt.printHotel();
                    System.out.println();
                    System.out.println("🌟🌈 Welcome to the UWB Hotel System! 🌟🌈");
                    System.out.println("Type 'help' to explore our adorable APIs. 🐾");
                    System.out.println("Type 'exit' to QUIT the program. 😢");
                    System.out.println("Type 'clear' to clear the Terminal. 🧹");
                    System.out.println();
                    break;

                default:
                    System.out.println("Command not recognized, please try again");
            }
            System.out.println("");
        }

        HotelDB.disconnect();

    }

    /*
     * Clears The Terminal Method
     * @author Nithisha Sathishkumar
     */
    public static void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /*
     * Menu Method
     * @author Team
     */

    private static void Menu(){
        System.out.println("\nMENU: ");
        Staff.getStaffList(null);
        Staff.getStaffListByPosition(null);
        Staff.getStaffInfoByStaffNum(null);
        Staff.getJobPositionList(null);
        Staff.getStaff_Staffnum(null);
        Staff.updateStaffPhoneNumber(null);
        Reservation.Create_Reservation(null);

    }
    
}
