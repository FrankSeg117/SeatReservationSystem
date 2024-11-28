package ProyectMain;

import java.util.InputMismatchException;
/* This class handles large menu prints and inputs that classes use.
 * It was made with the intent to reduce clutter in other classes so they can focus more on the logic while these
 * manage inputs and prints said classes will use.
 */
public class Menu{

    public static void mainMenu(){
            while (true) {
            Stadium.sPrint("\n\n===UPRM Baseball Stadium Seat Manager===");
            Stadium.sPrint("\nPlease Select an Option: ");
            Stadium.sPrint("""
                   1. Reserve Seat
                   2. Cancel a Reservation
                   3. Reservation History
                   4. Undo Previous Transaction
                   5. Show Wait List
                   6. Close App
                   """);

            try {
                Stadium.sPrint("Enter Option Number: ");
                int input = Stadium.scanner.nextInt();
                Stadium.scanner.nextLine();
                switch (input) {
                    case 1 -> {
                        // Reserve Seat Client
                        Stadium.client = Client.addClient(Stadium.scanner);
                        Stadium.reserveSeat();
                    }
                    case 2 -> {        
                        Stadium.cancelReservation();
                    }
                    case 3 -> // Show Reservation History
                        Transaction.printTransactionLinkedList(Stadium.transactionRegister, Stadium.scanner);
                    case 4 -> {
                        Transaction.undoLastTransaction();
                    }
                    case 5 -> // WaitList
                        WaitingList.WaitList();
                    case 6 -> {
                        Stadium.sPrint("Closing Program...");
                        Stadium.waitTime(3000);
                        return;
                    }
                    default -> {
                        continue;
                    }
                }
            } catch (InputMismatchException e) {
                Stadium.sPrint("Invalid Input");
                Stadium.scanner.nextLine();
            }
        }
    }

    public static void handleSeatOption(){
        Stadium.sPrint("\nPlease select an option: ");
        Stadium.sPrint("""
                   1. Select Seats
                   2. Buy Remaining Seats
                   3. Return""");
    }

    public static void reservationMenu(String level){
        if(level.equals("FieldLevel") && !Stadium.fieldLevel.isEmpty()){
            Stadium.lastSeatField = false;
            Stadium.sPrint("\n===UPRM Baseball Stadium Seat Manager===");
            Stadium.sPrint("\nPlease Select a Section: ");
            Stadium.sPrint( "1. Section A (Available Seats: " + (100 - Stadium.secFL.get('A').size()) + ")"
                        + "\n2. Section B (Available Seats: " + (100 - Stadium.secFL.get('B').size()) + ")"
                        + "\n3. Section C (Available Seats: " + (100 - Stadium.secFL.get('C').size()) + ")"
                        + "\n4. Section D (Available Seats: " + (100 - Stadium.secFL.get('D').size()) + ")"
                        + "\n5. Section E (Available Seats: " + (100 - Stadium.secFL.get('E').size()) + ")"
                        + "\n6. Return");
            
                    
                    
        } else if (Stadium.fieldLevel.isEmpty() && level.equals("FieldLevel")){

            if(!Stadium.lastSeatField){ 
                Stadium.lastSeatField = true;
                return;
            }

            Stadium.sPrint("\nNo seats available in Field Level\nWould you like to enter the Waiting List for this level? (Y/N)");
            String wList = Stadium.scanner.next();
            if(wList.equals("n")){
                Stadium.sPrint("\nCancelling operation...");
                Stadium.waitTime(1500);
                Stadium.sPrint("Enter 6 to go back");
                return;
            } else if(wList.equals("y")) {
                WaitingList.fieldWaitListAdd(Stadium.client);
                Stadium.sPrint("Enter 6 to go back");
                return;
            }
        } 
        if(level.equals("MainLevel") && !Stadium.mainLevel.isEmpty()){
            Stadium.lastSeatMain = false;
            Stadium.sPrint("\n===UPRM Baseball Stadium Seat Manager===");
            Stadium.sPrint("\nPlease Select a Section: ");
            Stadium.sPrint( "1. Section A (Available Seats: " + (100 - Stadium.secML.get('A').size()) + ")"
            + "\n2. Section B (Available Seats: " + (100 - Stadium.secML.get('B').size()) + ")"
            + "\n3. Section C (Available Seats: " + (100 - Stadium.secML.get('C').size()) + ")"
            + "\n4. Section D (Available Seats: " + (100 - Stadium.secML.get('D').size()) + ")"
            + "\n5. Section E (Available Seats: " + (100 - Stadium.secML.get('E').size()) + ")"
            + "\n6. Section F (Available Seats: " + (100 - Stadium.secML.get('F').size()) + ")"
            + "\n7. Section G (Available Seats: " + (100 - Stadium.secML.get('G').size()) + ")"
            + "\n8. Section H (Available Seats: " + (100 - Stadium.secML.get('H').size()) + ")"
            + "\n9. Section I (Available Seats: " + (100 - Stadium.secML.get('I').size()) + ")"
            + "\n10. Section J (Available Seats: "+ (100 - Stadium.secML.get('J').size()) + ")"
            + "\n11. Return"
            + "\n12. Buy everything");
        } else if(Stadium.mainLevel.isEmpty() && level.equals("MainLevel")) {
            if(!Stadium.lastSeatMain){ 
                Stadium.lastSeatMain = true;
                return;
            }
            Stadium.sPrint("\nNo seats available in Main Level\nWould you like to enter the Waiting List for this level? (Y/N)");
            String wList = Stadium.scanner.next();
            if(wList.equals("n")){
                Stadium.sPrint("\nCancelling operation...");
                Stadium.waitTime(1500);
                Stadium.sPrint("Enter 11 to go back");
                return;
            } else if(wList.equals("y")) {
                WaitingList.mainWaitListAdd(Stadium.client);
                Stadium.sPrint("Enter 11 to go back");
                return;
            }
        }
    
        if(level.equals("GrandStandLevel") && !Stadium.grandStandLevel.isEmpty()){
            Stadium.lastSeatGrandStand = false;
            Stadium.sPrint("\n===UPRM Baseball Stadium Seat Manager===");
            Stadium.sPrint("\nPlease Select a Section: ");
            Stadium.sPrint( "1. Section A (Available Seats: " + (250 - Stadium.secGSL.get('A').size()) + ")"
            + "\n2. Section B (Available Seats: " + (250 - Stadium.secGSL.get('B').size()) + ")"
            + "\n3. Section C (Available Seats: " + (250 - Stadium.secGSL.get('C').size()) + ")"
            + "\n4. Section D (Available Seats: " + (250 - Stadium.secGSL.get('D').size()) + ")"
            + "\n5. Section E (Available Seats: " + (250 - Stadium.secGSL.get('E').size()) + ")"
            + "\n6. Section F (Available Seats: " + (250 - Stadium.secGSL.get('F').size()) + ")"
            + "\n7. Section G (Available Seats: " + (250 - Stadium.secGSL.get('G').size()) + ")"
            + "\n8. Section H (Available Seats: " + (250 - Stadium.secGSL.get('H').size()) + ")"
            + "\n9. Return");
        } else if(Stadium.grandStandLevel.isEmpty() && level.equals("GrandStandLevel")){
            if(!Stadium.lastSeatGrandStand){ 
                Stadium.lastSeatGrandStand = true;
                return;
            }
            Stadium.sPrint("\nNo seats available in Grand Stand Level\nWould you like to enter the Waiting List for this level? (Y/N)");
            String wList = Stadium.scanner.next();
            if(wList.equals("n")){
                Stadium.sPrint("\nCancelling operation...");
                Stadium.waitTime(1500);
                Stadium.sPrint("Enter 9 to go back");
                return;
            } else if(wList.equals("y")) {
                WaitingList.grandWaitListAdd(Stadium.client);
                Stadium.sPrint("Enter 9 to go back");
                return;
            }
        }
    }

    public static void levelSelectionMenu(){
        Stadium.sPrint("\n===UPRM Baseball Stadium Seat Manager===");
            Stadium.sPrint("\nPlease Select a Seat Level: ");
            if(Stadium.fieldLevel.isEmpty()){
                Stadium.sPrint("1. Field Level" + " (Currently Full: WaitList Available)");
            }
            else{
                Stadium.sPrint("1. Field Level" + " (Available Seats: " + Stadium.fieldLevel.size() + ")");
            }
            if(Stadium.mainLevel.isEmpty()){
                Stadium.sPrint("\n2. Main Level" + " (Currently Full: WaitList Available)");
            }
            else{
                Stadium.sPrint("\n2. Main Level" + " (Available Seats: " + Stadium.mainLevel.size() + ")");
            }
            if(Stadium.grandStandLevel.isEmpty()){
                Stadium.sPrint("\n3. Grandstand Level" + " (Currently Full: WaitList Available)");
            }
            else{
                Stadium.sPrint("\n3. Grandstand Level" + " (Available Seats: " + Stadium.grandStandLevel.size() + ")");
            }
            Stadium.sPrint("\n4. Return");
    }

    public static void cancelMenu(){
        Stadium.sPrint("""
                \nEnter Seats in the format SectionNumber, Example: A24, B100.
                - Enter 1 when finished to continue  
                - Enter 2 to cancel ALL reservations in this level. 
                - Enter 0 to leave without saving. 
                """);
    }

    public static void waitingListMenu(){
        Stadium.sPrint("\\*Waiting list menu*\\\n");
        Stadium.sPrint("Please select an option:"
        + "\n1. Show Waiting List"
        + "\n2. Quit Waiting List"
        + "\n3. Exit");
    }

    public static void waitingListLevelMenu(){
        Stadium.sPrint("\\*Select Waiting list to display*\\\n");
        Stadium.sPrint("Please select an option:"
        + "\n1. Main level Waiting List"
        + "\n2. Field level Waiting List"
        + "\n3. Grand Stand level Waiting List"
        + "\n4. Exit");
    }
}