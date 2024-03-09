/*
* HotelAsciiArt Method
* @author Nithisha Sathishkumar
*/


public class HotelAsciiArt {

    public static void main(String[] args) {
        printHotel();
    }

    public static void intro(){
        System.out.println();
        System.out.println("🌟🌈 Welcome to the UWB Hotel System! 🌟🌈");
        System.out.println("Type 'help' or '?' to explore our adorable APIs. 🐾");
        System.out.println("Type 'exit' to QUIT the program. 😢");
        System.out.println("Type 'clear' to clear the Terminal. 🧹");
        System.out.println();

    }

    public static void printHotel() {
        String hotel = 
            "      .          .            .        . \n"+
            "             UUUUUUUUUUUUUUUUUUUUUUU   \n" +
            "        .    UUUUUUUUUUUUUUUUUUUUUUU   .\n " +
            "              |_|_|_|_|_|_|_|_|_|_|    \n" +
            "      UUUUUUUU_|_|H|_|_|H|_|_|H|_|_UUUUUUUU () \n" +
            "   () UUUUUUUU|_|_|_|_|_|_|_|_|_|_|UUUUUUUU(()) \n" +
            "  ((() |_\\I/_UUUUUUUUUUUUUUUUUUUUUUU_\\I/_| ()() \n" +
            "  ())( |_|_|_||_\\I/_||_\\I/_||_\\I/_||_|_|_| ))() \n" +
            "  ((())||H|H|||_|_|_||/_o_\\||_|_|_|||H|H||((()) \n" +
            "  (()))|_|_|_|||H|H|||=xxx=|||H|H|||_|_|_|(())) \n" +
            "  @@@@@@@@@@@||_|_|_||=xxx=||_|_|_||@@@@@@@@@@@ \n" +
            "              @@@@@@@@@|   |@@@@@@@@@          \n" +
            "       ";

        System.out.println(hotel);
    }
}
