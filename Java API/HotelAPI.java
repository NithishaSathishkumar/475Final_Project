

public class HotelAPI {
    /*
     * Main Method
     * @author Nithisha Sathishkumar
     */
    public static void main(String[] args){
        System.out.println("Welcome to the Hotel System!");
        System.out.println("Type '?' or 'help' to list all available APIs.");
        System.out.println("Type 'exit' to quit the program");

        String cmb = "";

        while(!cmb.equalsIgnoreCase("exit")){
            System.out.println("HotelDB-# ");
            cmb = System.console().readLine();

            String[] mainCmdArr = cmb.split(" ", 2);

            if(mainCmdArr.length > 1)
            {
                System.out.println("Providing more than one command not supported");
                System.out.println("");
                continue;
            }

            switch(mainCmdArr[0]){
                case "?":
                case "help":
                    System.out.println("help needed!!");
                    // listOfAPI();
                    break;
                case "exit":
                    break;
                
                default:
                    System.out.println("Command not recognized, please try again");
            }
            System.out.println("");
        }

        HotelDB.disconnect();
       

    }
    
}
