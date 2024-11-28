package ProyectMain;
import java.util.*;

/* This class handles the WaitLing Object, which allows for easier track of different functions to do
 * IF a client is on the waiting list due to there being a level full, if a space opens up, this class
 * handles the deque of the client.
 * There will be 3 queues, for each of the 3 levels (field, main and grand stand)
 */

public class WaitingList {

    /* Main menu for the waiting list option */
    public static void WaitList(){
        Stadium.sPrint("\\*Waiting list menu*\\\n");
        Stadium.sPrint("""
                       Please select an option:
                       1. Show Waiting List
                       2. Cancel Waiting List Spot
                       3. Exit""");

        try{
            Stadium.sPrint("Enter Option Number: ");
            int option = Stadium.scanner.nextInt();

            switch (option){
                case 1:
                    showWaitingList();
                    break;
                case 2:
                    WLDequeHelper();
                    break;
                case 3:
                    return;
            }

        } catch(InputMismatchException e){
            Stadium.sPrint("Invalid input");
            Stadium.scanner.nextLine();
        }
    }
    
    /* This method allows the operator to view each of the waiting lists for each level */
    public static void showWaitingList() {
        Stadium.sPrint("\\*Select Waiting list to display*\\\n");
        Stadium.sPrint("""
                       Please select an option:
                       1. Main level Waiting List
                       2. Field level Waiting List
                       3. Grand Stand level Waiting List
                       4. Exit""");

        try{
            Stadium.sPrint("Enter Option Number: ");
            int option = Stadium.scanner.nextInt();

            switch (option){
                case 1 -> {
                    Stadium.sPrint("\n\\*Showing: Main Level WL*\\");
                    int i = 1;
                    for(Client c : Stadium.mainWaitList){
                        System.out.println("Position " + i + " -> " + c.getName());
                        i++;
                    }
                    Stadium.sPrint("");
                    Stadium.waitTime(5000);
                }
                    case 2 -> {
                        Stadium.sPrint("\n\\*Showing: Field Level WL*\\");
                        int j = 1;
                        for(Client c : Stadium.fieldWaitList){
                            System.out.println("Position " + j + " -> " + c.getName());
                            j++;
                        }
                        Stadium.sPrint("");
                        Stadium.waitTime(5000);
                }
                    case 3 -> {
                        Stadium.sPrint("\n\\*Showing: Grand Stand Level WL*\\");
                        int k = 1;
                        for(Client c : Stadium.grandWaitList){
                            System.out.println("Position " + k + " -> " + c.getName());
                            k++;
                        }
                        Stadium.sPrint("");
                        Stadium.waitTime(5000);
                }
                case 4 -> {
                    return;
                }
            }

        } catch(InputMismatchException e){
            Stadium.sPrint("Invalid input");
            Stadium.scanner.nextLine();
        }
    }

    //TODO EXPLAIN WHAT THIS DOES
    public static void WLDequeHelper(){

        Stadium.sPrint("\nSelect WL to deque from");
        Stadium.sPrint("""
                       1. Main Level WL
                       2. Field level WL
                       3. Grand stand level WL
                       4. Cancel""");
        
        try{
            Stadium.sPrint("Enter Option Number: ");
            int option = Stadium.scanner.nextInt();

            switch (option){
                case 1 -> WaitingListDeque(Stadium.client, Stadium.mainWaitList);
                case 2 -> WaitingListDeque(Stadium.client, Stadium.fieldWaitList);
                case 3 -> WaitingListDeque(Stadium.client, Stadium.grandWaitList);
                case 4 -> {
                    return;
                }
            }

        } catch(InputMismatchException e){
            Stadium.sPrint("Invalid input");
            Stadium.scanner.nextLine();
        }
    }

    public static void WaitingListDeque(Client c, Queue<Client> levelWL){
        Queue<Client> temp = new LinkedList<>();

        while(!levelWL.isEmpty()){ // This traverses the queue to delete indicated client
            if(!levelWL.peek().equals(c)){
                temp.add(levelWL.poll());
            } else {
                levelWL.poll();
            }
        }

        if(!temp.contains(c)){Stadium.sPrint("Client deleted from Waiting List succesfully");}
        Stadium.waitTime(1500);

        // while(!temp.isEmpty()){ // This makes the original queue return to its original state
        //     levelWL.add(temp.poll());
        // }
        return;
    }

    public static void mainWaitListAdd(Client c){
        if(!Stadium.mainWaitList.contains(c)){
            Stadium.sPrint("Added client to waitList.");
            Stadium.mainWaitList.add(c);
            Stadium.waitTime(2000);
        } else{
            Stadium.sPrint("Client already in Waiting List");
            Stadium.waitTime(2000);
        }
    }

    public static void fieldWaitListAdd(Client c){
        if(!Stadium.fieldWaitList.contains(c)){
            Stadium.sPrint("Added client to waitList.");
            Stadium.fieldWaitList.add(c);
            Stadium.waitTime(2000);
        } else{
            Stadium.sPrint("Client already in Waiting List");
            Stadium.waitTime(2000);
        }
    }

    public static void grandWaitListAdd(Client c){
        if(!Stadium.grandWaitList.contains(c)){
            Stadium.sPrint("Added client to waitlist.");
            Stadium.grandWaitList.add(c);
            Stadium.waitTime(2000);
        } else{
            Stadium.sPrint("Client already in Waiting List");
            Stadium.waitTime(2000);
        }
    }

    public static void printQueue(Queue<Client> queue) {
        for (Client c : queue) {
            System.out.println(c.getName());
        } 
    }

    public static void SpaceAvailable(Client c, String level){
        /// This method manipulates the waitlists so they are processed whenever there are seats available after a cancellation or an undo transaction
        /// By level
        if(Stadium.mainWaitList.isEmpty() && Stadium.fieldWaitList.isEmpty() && Stadium.grandWaitList.isEmpty()){
            return;
        }

        ArrayList<Seat> seatForWL = new ArrayList<>();
        
        switch (level) {
            case "FieldLevel": /// Switch is based on the level passed as the parameter
                while(!Stadium.fieldLevel.isEmpty() && !Stadium.fieldWaitList.isEmpty()){
                    /// While there are available seats and and the waitlist is not empty we continue to process it until either of them gets emptied
                    ///This exact process is repeated for the cases of different levels below
                        Seat seat = null;
                        for(Seat s : Stadium.fieldLevel){
                            seat = s;
                            seatForWL.add(s);
                            Stadium.fieldLevel.remove(s);
                            break;
                        }
                        Stadium.FLseats.put(c, seatForWL);
                        Stadium.secFL.get(seat.getSection()).add(seat);
                        Transaction.undoStack.add(new Transaction(c, seatForWL, Seat.seatsTotalPrice(seatForWL, level) , "Reservation", level));
                        Stadium.transactionRegister.add(new Transaction(c, seatForWL, Seat.seatsTotalPrice(seatForWL, level), "Reservation", level));
                        WaitingListDeque(c, Stadium.fieldWaitList);
                    }
                
                Stadium.sPrint("\nWaitlist has been updated.");
                break;
            case "MainLevel":
                while(!Stadium.mainWaitList.isEmpty() && !Stadium.mainLevel.isEmpty()){
                        Seat seat = null;
                        for(Seat s : Stadium.mainLevel){
                            seat = s;
                            seatForWL.add(s);
                            Stadium.mainLevel.remove(s);
                            break;
                        }
                        Stadium.MLseats.put(c, seatForWL);
                        Stadium.secML.get(seat.getSection()).add(seat);
                        Transaction.undoStack.add(new Transaction(c, seatForWL, Seat.seatsTotalPrice(seatForWL, level) , "Reservation", level));
                        Stadium.transactionRegister.add(new Transaction(c, seatForWL, Seat.seatsTotalPrice(seatForWL, level), "Reservation", level));
                        WaitingListDeque(c, Stadium.mainWaitList);
                }
                Stadium.sPrint("\nWaitlist has been updated.");
                break;
            case "GrandStandLevel":
                while(!Stadium.grandStandLevel.isEmpty() && !Stadium.grandWaitList.isEmpty()){
                        Seat seat = null;
                        for(Seat s : Stadium.grandStandLevel){
                            seat = s;
                            seatForWL.add(s);
                            Stadium.grandStandLevel.remove(s);
                            break;
                        }
                        Stadium.GSLseats.put(c, seatForWL);
                        Stadium.secGSL.get(seat.getSection()).add(seat);
                        Transaction.undoStack.add(new Transaction(c, seatForWL, Seat.seatsTotalPrice(seatForWL, level) , "Reservation", level));
                        Stadium.transactionRegister.add(new Transaction(c, seatForWL, Seat.seatsTotalPrice(seatForWL, level), "Reservation", level));
                        WaitingListDeque(c, Stadium.grandWaitList);
                }
                Stadium.sPrint("\nWaitlist has been updated.");
                break;
            default:
                break;
        }
        return;
    }
}
