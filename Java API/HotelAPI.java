/*
* Main Method
* @author Nithisha Sathishkumar
*/

public class HotelAPI {
    public static void main(String[] args){
        System.out.println();
        HotelAsciiArt.printHotel();
        HotelAsciiArt.intro();
        
        String cmb = "";

        while(!cmb.equalsIgnoreCase("exit")){
            System.out.print("HotelDB-# ");
            cmb = System.console().readLine();

            String[] mainCmdArr = cmb.split(" ", 2);

            if(mainCmdArr.length > 1) {
                System.out.println("Providing more than one command not supported");
                System.out.println("");
                continue;
            }

            switch(mainCmdArr[0]) {
                case "Help":
                case "help":
                case "?":
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

                case Staff.UpdateStaffEmail:
                    Staff.updateStaffEmail(mainCmdArr);
                    break;

                case Staff.UpdateStaffFirstName:
                    Staff.updateStaffFirstName(mainCmdArr);
                    break;

                case Staff.UpdateStaffLastName:
                    Staff.updateStaffLastName(mainCmdArr);
                    break;

                case Staff.UpdateStaffPosition:
                    Staff.updateStaffPosition(mainCmdArr);
                    break;
                    
                case ReservationAPI.CreateReservation:
                    ReservationAPI.Create_Reservation(mainCmdArr);
                    break;

                case Staff.CreateStaff:
                    Staff.createStaff(mainCmdArr);
                    break;

                case Booking.getAvailableRooms: //UNFINISHED WIP
                    Booking.getAvailableRooms(mainCmdArr);
                    break;

                case Booking.getBookingsOnRoom:
                    Booking.getBookingsOnRoom(mainCmdArr);
                    break;

                case Booking.listAllBookings:
                    Booking.listAllBookings(mainCmdArr);
                    break;

                case Booking.listAllRooms:
                    Booking.listAllRooms(mainCmdArr);
                    break;

                case "clear":
                case "Clear":
                    clearTerminal();
                    System.out.println();
                    HotelAsciiArt.printHotel();
                    HotelAsciiArt.intro();
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
        Staff.updateStaffEmail(null);
        Staff.updateStaffFirstName(null);
        Staff.updateStaffLastName(null);
        Staff.updateStaffPosition(null);
        Staff.createStaff(null);
        Booking.getAvailableRooms(null);
        Booking.getBookingsOnRoom(null);
        Booking.listAllBookings(null);
        Booking.listAllRooms(null);
        ReservationAPI.Create_Reservation(null);
    }
}
