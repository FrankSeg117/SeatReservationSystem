package ProyectMain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/* The Stadium class is the main class that stores and manipulates the primary data structures for
 * managing clients and seats that are either unsold or purchased. Three sets were used: one for each
 * level of unsold seats, two dictionaries for clients and ArrayLists of seats, and another for Section and
 * seat for each level.
 *   - This class, as it processes transactions, also calls the Transaction class to properly register them.
 *   - Additionally, it calls the WaitingList class to handle any logic required to manage the waiting list.
 * 
 */

public class Stadium {
    /*
     * Sets
     */
    //Unreserved Seats - Here all unreserved seats are stored. this allows quick storage when canceling a reservation.
    public static Set<Seat> fieldLevel = new HashSet<>();
    public static Set<Seat> mainLevel = new HashSet<>(); 
    public static Set<Seat> grandStandLevel = new HashSet<>();
    
    //This sets are for the storage of future bought seats which will be stored on dictionaries below
    public static Set<Seat> FLA = new HashSet<>();
    public static Set<Seat> FLB = new HashSet<>();
    public static Set<Seat> FLC = new HashSet<>();
    public static Set<Seat> FLD = new HashSet<>();
    public static Set<Seat> FLE = new HashSet<>();

    public static Set<Seat> MLA = new HashSet<>();
    public static Set<Seat> MLB = new HashSet<>();
    public static Set<Seat> MLC = new HashSet<>();
    public static Set<Seat> MLD = new HashSet<>();
    public static Set<Seat> MLE = new HashSet<>();
    public static Set<Seat> MLF = new HashSet<>();
    public static Set<Seat> MLG = new HashSet<>();
    public static Set<Seat> MLH = new HashSet<>();
    public static Set<Seat> MLI = new HashSet<>();
    public static Set<Seat> MLJ = new HashSet<>();

    public static Set<Seat> GSLA = new HashSet<>();
    public static Set<Seat> GSLB = new HashSet<>();
    public static Set<Seat> GSLC = new HashSet<>();
    public static Set<Seat> GSLD = new HashSet<>();
    public static Set<Seat> GSLE = new HashSet<>();
    public static Set<Seat> GSLF = new HashSet<>();
    public static Set<Seat> GSLG = new HashSet<>();
    public static Set<Seat> GSLH = new HashSet<>();
    
    /*
     * Hash Maps
     */

    //Reserved Seats are stored in both dictionaries, where client has them and a set has them
    //Dictionaries where we pair client with the seats they reserved
    public static HashMap<Client, ArrayList<Seat>> FLseats = new HashMap<>();
    public static HashMap<Client, ArrayList<Seat>> MLseats = new HashMap<>();
    public static HashMap<Client, ArrayList<Seat>> GSLseats = new HashMap<>();
    
    //Dictionaries where we store bought seats with respect to their section
    public static HashMap<Character, Set<Seat>> secFL = new HashMap<>();
    public static HashMap<Character, Set<Seat>> secML = new HashMap<>();
    public static HashMap<Character, Set<Seat>> secGSL = new HashMap<>();

    /*These dictionaries allow us to quickly know which seats are reserved, with respect to client, level and section.
    We need this at the time of doing cancelations/Undos/Waitlists so we can easily know which seats we must transfer.*/

    /*
     * Linked Lists
     */
    //This linkedlist stores all types of transactions, if it is a reservation or a cancelation (including Undos)
    public static LinkedList<Transaction> transactionRegister = new LinkedList<>();

    /*
     * Stacks
     */

    //The main stack for undoing transactions is stored on the Transaction Class file
    //Since the stack stores transactions, its place is in that file.

    /*
     * Queues
     */

    public static Queue<Client> mainWaitList = new LinkedList<>();
    public static Queue<Client> fieldWaitList = new LinkedList<>();
    public static Queue<Client> grandWaitList = new LinkedList<>();

    /*
     * Variables
     */

    //Client global variable, which is created/replaced each time a new client is added to the system
    public static Client client;

    /* These flags are used when a level becomes full, so the waitlist logic can trigger correctly */
    public static boolean lastSeatField = false;
    public static boolean lastSeatMain = false;
    public static boolean lastSeatGrandStand = false;

    public static Scanner scanner = new Scanner(System.in); //Main Class scanner
    
    // Method to print faster like Python, making code more readable, so we do not have to use System.out every time.
    public static void sPrint(String a) {System.out.println(a);} 

    /*This a function in java that is used to make a brief pause, usefull for having a pause on screen before
    we change menus, so we give the operator a chance to read the final statements of certain functions*/
    public static void waitTime(int ms){
        try { 
            Thread.sleep(ms); // Pause for "ms" miliseconds
        }
        catch (InterruptedException e) {
                e.printStackTrace();
        }
    }

    //This method is used to verify if a Client exists on the system by passing an identifier (email or phone number).
    public static Boolean isInSystem(String identifier){
        return Client.checkClientIsInSystem(identifier, FLseats.keySet(), MLseats.keySet(), GSLseats.keySet());
    }
    //This method is used to get a client already inside the system with the use of an identifier (email or phone number).
    public static Client getInSystem(String identifier){
        return Client.getClientInSystem(identifier, FLseats.keySet(), MLseats.keySet(), GSLseats.keySet());
    }

    //This method will allow the operator to select seats a client wants
    public static void Select(Character sec, int NumberOfSeats, String Level) { 
        boolean available = false;
        int seatnum = 0;
        
        sPrint("Available Seats:");
        //These loops will save and sort all available seats on a certain level based on their numebr
        ArrayList<Seat> availableSeats = new ArrayList<>();
        if(Level.equals("FieldLevel")){
            for (Seat a : fieldLevel) {
                if (a.getSection() == sec) {
                    availableSeats.add(a);
                }
            }
        }
        if(Level.equals("MainLevel")){
            for (Seat a : mainLevel) {
                if (a.getSection() == sec) {
                    availableSeats.add(a);
                }
            }
        }
        if(Level.equals("GrandStandLevel")){
            for (Seat a : grandStandLevel) {
                if (a.getSection() == sec) {
                    availableSeats.add(a);
                }
            }
        }
        availableSeats.sort(Comparator.comparingInt(Seat::getSeatNumber));
        
        ArrayList<Seat> previousSeats = new ArrayList<>(); //Seats that the client already had if said client existed before; it will be empty if client had no previous reservations
        ArrayList<Seat> newSeats = new ArrayList<>(); //Here we will store the new seats a client will buy, without counting existing ones. Use full for registering new transactions correctly.

        //These loops will look for any previous seat a client had reserved previously on that level
        if(!FLseats.isEmpty()){ 
            for(Client a: FLseats.keySet()){
                if(client.equals(a)){
                    previousSeats = FLseats.get(a);
                    waitTime(2000);
                }
            }
        }if(!MLseats.isEmpty()){
            for(Client a: MLseats.keySet()){
                if(client.equals(a)){
                    previousSeats = MLseats.get(a);
                    waitTime(2000);
                }
            }
        }if(!GSLseats.isEmpty()){
            for(Client a: GSLseats.keySet()){
                if(client.equals(a)){
                    previousSeats = GSLseats.get(a);
                    waitTime(2000);
                }
            }
        }
        //This loop will print on screen all seats available on that section, and leave empty spaces on already reserved seats
        for(int k = 0; k<NumberOfSeats; k++){
            available = false;
            if(Level.equals("FieldLevel")){
                for(Seat a: secFL.get(sec)){
                    if(a.getSeatNumber() < availableSeats.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            if(Level.equals("MainLevel")){
                for(Seat a: secML.get(sec)){
                    if(a.getSeatNumber() < availableSeats.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            if(Level.equals("GrandStandLevel")){
                for(Seat a: secGSL.get(sec)){
                    if(a.getSeatNumber() < availableSeats.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            for (int i = 0; i < availableSeats.size(); i++) {
                if(previousSeats.size() == 0){
                    System.out.print(availableSeats.get(i).getSeatNumber() + " ");
                }else{
                    if(previousSeats.contains(availableSeats.get(i))){
                        System.out.print("   ");
                    }else{
                        System.out.print(availableSeats.get(i).getSeatNumber() + " ");
                    }
                }
                
                if(i != availableSeats.size()-1){
                    if(availableSeats.get(i+1).getSeatNumber() - availableSeats.get(i).getSeatNumber() > 1){
                        for(int j = 1; availableSeats.get(i+1).getSeatNumber() - availableSeats.get(i).getSeatNumber()>j; j++){
                            System.out.print("   ");
                        }
                    }
                }
                if (availableSeats.get(i).getSeatNumber() % 25 == 0) {
                    System.out.print("\n");
                    if(Level.equals("FieldLevel")){
                        for(Seat a: secFL.get(sec)){
                            if(availableSeats.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < availableSeats.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                    if(Level.equals("MainLevel")){
                        for(Seat a: secML.get(sec)){
                            if(availableSeats.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < availableSeats.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                    if(Level.equals("GrandStandLevel")){
                        for(Seat a: secGSL.get(sec)){
                            if(availableSeats.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < availableSeats.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                }
                
            }
            
            sPrint("\n==== Choose Seat ====");

            sPrint("\nSeat Number: ");
            //Here we allow the operator to select seats, and if the selected seat is not available, give a warning and another try.
            try {
                seatnum = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                sPrint("Please input the correct information");
            }
            if(Level.equals("FieldLevel")){
                for (Seat a : fieldLevel) {
                    if (a.getSection() == sec && a.getSeatNumber() == seatnum) {
                        if(!previousSeats.contains(a)){
                            available = true;
                        }
                    }
                }
            }
            if(Level.equals("MainLevel")){
                for (Seat a : mainLevel) {
                    if (a.getSection() == sec && a.getSeatNumber() == seatnum) {
                        if(!previousSeats.contains(a)){
                            available = true;
                        }
                    }
                }
            }
            if(Level.equals("GrandStandLevel")){
                for (Seat a : grandStandLevel) {
                    if (a.getSection() == sec && a.getSeatNumber() == seatnum) {
                        if(!previousSeats.contains(a)){
                            available = true;
                        }
                    }
                }
            }

            if(!available){
                sPrint("\nSeat is not available.");
                sPrint("\nTry again.");
                waitTime(2000);
                k--;
            }
            if(available){
                if(Level.equals("FieldLevel")){
                    for (Seat a : fieldLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == seatnum) {
                            previousSeats.add(a);
                            newSeats.add(a);
                        }
                    }
                }
                if(Level.equals("MainLevel")){
                    for (Seat a : mainLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == seatnum) {
                            previousSeats.add(a);
                            newSeats.add(a);
                        }
                    }
                }
                if(Level.equals("GrandStandLevel")){
                    for (Seat a : grandStandLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == seatnum) {
                            previousSeats.add(a);
                            newSeats.add(a);
                        }
                    } 
                }
            }
        }
        //Once we know which seats will be bought, we call the Buy method.
        Buy(previousSeats, sec, Level, newSeats,false);

    }

    /* This method will print available seats on screen, 
       and return whether the client wants to buy all remaining seats, select seats, or go back.  */
    public static int previewSeats(Character sectionCharacter, String section) {
        sPrint("\nAvailable Seats:");

        ArrayList<Seat> seatsToPreview = new ArrayList<>();

        //If any of the levels are full display no seats available
        if(section.equals("FieldLevel")){
            if(secFL.get(sectionCharacter).size() == 100){
                waitTime(2000);
                sPrint("\nNo seats available.");
                waitTime(2000);
                return 3;
            }
            for (Seat seat : fieldLevel) {
                if (seat.getSection() == sectionCharacter) {
                    seatsToPreview.add(seat);
                }
            }
        }
        if(section.equals("MainLevel")){
            if(secML.get(sectionCharacter).size() == 100){
                waitTime(2000);
                sPrint("\nNo seats available.");
                waitTime(2000);
                return 3;
            }
            for (Seat seat : mainLevel) {
                if (seat.getSection() == sectionCharacter) {
                    seatsToPreview.add(seat);
                }
            }
        }
        if(section.equals("GrandStandLevel")){
            if(secGSL.get(sectionCharacter).size() == 250){
                waitTime(2000);
                sPrint("\nNo seats available.");
                waitTime(2000);
                return 3;
            }
            for (Seat seat : grandStandLevel) {
                if (seat.getSection() == sectionCharacter) {
                    seatsToPreview.add(seat);
                }
            }
        }
        //Sort the seats to show on-screen
        seatsToPreview.sort(Comparator.comparingInt(Seat::getSeatNumber));
        
        ArrayList<Seat> clientA = new ArrayList<>();

            //If there's a taken seat at the start, we print a space on that location
            if(section.equals("FieldLevel")){
                for(Seat a: secFL.get(sectionCharacter)){
                    if(a.getSeatNumber() < seatsToPreview.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            if(section.equals("MainLevel")){
                for(Seat a: secML.get(sectionCharacter)){
                    if(a.getSeatNumber() < seatsToPreview.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            if(section.equals("GrandStandLevel")){
                for(Seat a: secGSL.get(sectionCharacter)){
                    if(a.getSeatNumber() < seatsToPreview.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            //
            for (int i = 0; i < seatsToPreview.size(); i++) {
                if(clientA.size() == 0){
                    System.out.print(seatsToPreview.get(i).getSeatNumber() + " ");
                }else{
                    if(clientA.contains(seatsToPreview.get(i))){
                        System.out.print("   ");
                    }else{
                        System.out.print(seatsToPreview.get(i).getSeatNumber() + " ");
                    }
                }
                
                if(i != seatsToPreview.size()-1){
                    if(seatsToPreview.get(i+1).getSeatNumber() - seatsToPreview.get(i).getSeatNumber() > 1){
                        for(int j = 1; seatsToPreview.get(i+1).getSeatNumber() - seatsToPreview.get(i).getSeatNumber()>j; j++){
                            System.out.print("   ");
                        }
                    }
                }
                if (seatsToPreview.get(i).getSeatNumber() % 25 == 0) {
                    System.out.print("\n");
                    if(section.equals("FieldLevel")){
                        for(Seat a: secFL.get(sectionCharacter)){
                            if(seatsToPreview.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < seatsToPreview.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                    if(section.equals("MainLevel")){
                        for(Seat a: secML.get(sectionCharacter)){
                            if(seatsToPreview.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < seatsToPreview.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                    if(section.equals("GrandStandLevel")){
                        for(Seat a: secGSL.get(sectionCharacter)){
                            if(seatsToPreview.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < seatsToPreview.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                }
            }
        
        waitTime(2000);
        boolean OPTION = true;
        while (OPTION) {
            Menu.handleSeatOption();
            try {
                sPrint("Enter Option Number: ");
                int input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1:
                        return 0;
                    case 2:
                        return 1;
                    case 3:
                        return 2;
                }
            } catch (InputMismatchException e) {
                sPrint("Invalid Input");
                waitTime(2000);
                scanner.nextLine();
            }
        }
        return 2; 
    }

    /* This method buys a certain amounts of seats specified by the arraylist  
     * and register the transaction in the name of the given client.
     * It takes as a paramter said ArrayList of previously owned seats, a section for the level passed, an Array of the new seats
     * and an undo flag to streamline the operation if we know its an Undo. */
    @SuppressWarnings({"Unnecessary return statement", "UnnecessaryReturnStatement"}) // This avoids a warning
    public static void Buy(ArrayList<Seat> Seat, Character sec, String Level, ArrayList<Seat> newSeats, boolean Undo) {
        int price = 0;
        String level = "";
        String conf = "";
        //We save and print the final price
        if(!Undo){
            try {
                sPrint("==== Payment ====");

                if(Level.equals("FieldLevel")){ 
                    price = 300*newSeats.size();
                    level = "FieldLevel";
                    sPrint("\nTotal Cost: $" + price);
                }
                if(Level.equals("MainLevel")){
                    price = 120*newSeats.size();
                    level = "MainLevel";
                    sPrint("\nTotal Cost: $" + price);
                }
                if(Level.equals("GrandStandLevel")){
                    price = 45*newSeats.size();
                    level = "GrandStandLevel";
                    sPrint("\nTotal Cost: $" + price);
                }
                //We confirm if client wants to finish this payment
                sPrint("\nDo you wish to confirm your payment?");

                sPrint("\nYes or No (Y/N):");

                conf = scanner.nextLine();

            } catch (InputMismatchException e) {
                sPrint("Please input the correct information");
            }
        }else{
            conf = "y";
        }

        if (conf.toLowerCase().equals("y")) {
            //Implementation for reservations in transaction history
            if(!Undo){
                Transaction.undoStack.add(new Transaction(client, newSeats, price, "Reservation", level));
            }
            transactionRegister.add(new Transaction(client, newSeats, price, "Reservation", level));
            //We handle the case in which it is an undo 
            if(Undo){
                if(Level.equals("FieldLevel")){ 
                    price = 300*newSeats.size();
                    sPrint("\nTotal Cost: $" + price);
                }
                if(Level.equals("MainLevel")){
                    price = 120*newSeats.size();
                    sPrint("\nTotal Cost: $" + price);
                }
                if(Level.equals("GrandStandLevel")){
                    price = 45*newSeats.size();
                    sPrint("\nTotal Cost: $" + price);
                }
                for(Seat b: newSeats){
                    if(Level.equals("FieldLevel")){
                        for (Seat a : fieldLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                                Seat.add(a);
                            }
                        }
                    }
                    if(Level.equals("MainLevel")){
                        for (Seat a : mainLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                                Seat.add(a);
                            }
                        } 
                    }
                    if(Level.equals("GrandStandLevel")){
                        for (Seat a : grandStandLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                                Seat.add(a);
                            }
                        } 
                    }
                }
            }
            sPrint("\nTransaction Completed.");

            sPrint("\nReturning Back...");
    
            //Finally, we add the seats to purchased structures and remove them from the unbought set
            for (Seat b : Seat) {
                if(Level.equals("FieldLevel")){
                    for(Seat a: fieldLevel){
                        if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                            FLseats.put(client, Seat);
                            fieldLevel.remove(a);
                            secFL.get(sec).add(a);
                            break;
                        }
                    }
                }
                if(Level.equals("MainLevel")){
                    for(Seat a: mainLevel){
                        if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                            MLseats.put(client, Seat);
                            mainLevel.remove(a);
                            secML.get(sec).add(a);
                            break;
                        }
                    } 
                }
                if(Level.equals("GrandStandLevel")){
                    for(Seat a: grandStandLevel){
                        if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                            GSLseats.put(client, Seat);
                            grandStandLevel.remove(a);
                            secGSL.get(sec).add(a);
                            break;
                        }
                    } 
                }
            }
            waitTime(2000);
            return;
        }
        sPrint("\nReturrning Back...");
        waitTime(2000);
        return;
    }

    //This method is used to buy all the seats in a given section inside a level
    //This is usefull for testing the Waiting List
    public static void BuyAll(Character sec, String A) {
        ArrayList<Seat> newSeats = new ArrayList<>();
        boolean avl = false;
        int SN = 0; 

        ArrayList<Seat> secA = new ArrayList<>();
        if(A.equals("FieldLevel")){
            for (Seat a : fieldLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("MainLevel")){
            for (Seat a : mainLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("GrandStandLevel")){
            for (Seat a : grandStandLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        secA.sort(Comparator.comparingInt(Seat::getSeatNumber));
        
        ArrayList<Seat> clientA = new ArrayList<>();

        if(!FLseats.isEmpty()){
            for(Client a: FLseats.keySet()){
                if(client.equals(a)){
                    clientA = FLseats.get(a);
                    waitTime(2000);
                }
            }
        }if(!MLseats.isEmpty()){
            for(Client a: MLseats.keySet()){
                if(client.equals(a)){
                    clientA = MLseats.get(a);
                    waitTime(2000);
                }
            }
        }if(!GSLseats.isEmpty()){
            for(Client a: GSLseats.keySet()){
                if(client.equals(a)){
                    clientA = GSLseats.get(a);
                    waitTime(2000);
                }
            }
        }

        for(int k = 0; k<secA.size(); k++){
            avl = false;
                SN = secA.get(k).getSeatNumber();

                if(A.equals("FieldLevel")){
                    for (Seat a : fieldLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            if(!clientA.contains(a)){
                                avl = true;
                            }
                        }
                    }
                }
                if(A.equals("MainLevel")){
                    for (Seat a : mainLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            if(!clientA.contains(a)){
                                avl = true;
                            }
                        }
                    }
                }
                if(A.equals("GrandStandLevel")){
                    for (Seat a : grandStandLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            if(!clientA.contains(a)){
                                avl = true;
                            }
                        }
                    }
                }
                if(avl){
                    if(A.equals("FieldLevel")){
                        for (Seat a : fieldLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                                clientA.add(a);
                                newSeats.add(a);
                            }
                        }
                    }
                    if(A.equals("MainLevel")){
                        for (Seat a : mainLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                                clientA.add(a);
                                newSeats.add(a);
                            }
                        } 
                    }
                    if(A.equals("GrandStandLevel")){
                        for (Seat a : grandStandLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                                clientA.add(a);
                                newSeats.add(a);
                            }
                        } 
                    }
                }
                if(!avl){
                    continue;
                }
        }
        Buy(clientA, sec, A, newSeats,false);
    }

    //This method asks the information from a client and returns 
    //a new client with the given attributes
    public static Client addClient() {
        // variables para crear y añadir cliente
        String Cname = ""; 
        String email = "";
        String num = "";
        
        try {
            sPrint("==== Client Information ====");

            sPrint("\nPlease enter the client's name: ");
            Cname = scanner.nextLine();
            
            sPrint("\nPlease enter the client's email: ");
            email = scanner.nextLine();

            while(!Client.validEmail(email)){
                sPrint("\nPlease enter the client's email: ");
                email = scanner.nextLine();
            }

            sPrint("\nPlease enter the client's phone number: ");
            num = scanner.nextLine();
            num = Client.removeHyphen(num);

            while(!Client.validPhoneNumber(num)){
                sPrint("\nPlease enter the client's phone number: ");
                num = scanner.nextLine();
                num = Client.removeHyphen(num);
            }


        } catch (InputMismatchException e) {
            sPrint("Please input the correct information");
        }
        if(!FLseats.isEmpty() || !MLseats.isEmpty() || !GSLseats.isEmpty()){
            if(isInSystem(email) || isInSystem(num)){
                sPrint("\nClient is already on the system.");
                waitTime(2000);
                return getInSystem(email);
            }
        }
        return new Client(Cname, email, num);
    }

    /*
    This method is the main for reservsastions. It shows available spaces on each section and allows the operator
    to select wether to enter a waitlist if full, buy all, or a certein amount of seats
    */ 
    public static void Reservation(String level) { 
        boolean MENU = true;
        while (MENU) {
            { // Verifies if there are seats still available
                Menu.reservationMenu(level);
                if(lastSeatField && level.equals("FieldLevel")){
                    return;
                }
                if(lastSeatMain && level.equals("MainLevel")){
                    return;
                }
                if(lastSeatGrandStand && level.equals("GrandStandLevel")){
                    return;
                }
                try {
                sPrint("Enter Option Number: ");
                int input = scanner.nextInt();
                scanner.nextLine();
                int number_of_seats = 3;
                int sel;
                boolean retry;
                switch (input) {
                    case 1:
                        // Aqui method para add client
                        sPrint("\nSeats Avaialble in this section:");
                        sel = previewSeats('A', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            retry = true;                         
                            while(retry){
                                try{
                                    number_of_seats = scanner.nextInt();
                                    scanner.nextLine();
                                }catch (InputMismatchException e) {
                                    sPrint("Invalid Input");
                                    scanner.nextLine();
                                }
                                if(number_of_seats<=100 && number_of_seats>=1 && (level.equals("FieldLevel") || level.equals("MainLevel"))){
                                    retry = false;
                                }else if(level.equals("FieldLevel")){
                                    sPrint("Number of seats need to be from 1 to " + secFL.get('A').size());
                                }else if(level.equals("MainLevel")){
                                    sPrint("Number of seats need to be from 1 to " + secML.get('A').size());
                                }else if(number_of_seats<=250 && number_of_seats>=1 && level.equals("GrandStandLevel")){
                                    retry = false;
                                }else if(level.equals("GrandStandLevel")){
                                    sPrint("Number of seats need to be from 1 to " + secGSL.get('A').size());
                                }
                            }
                            Select('A',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('A', level);
                            break;
                        }else{
                          break;  
                        }
                    case 2:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('B', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            number_of_seats = scanner.nextInt();
                            scanner.nextLine();
                            Select('B',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('B', level);
                            break;
                        }else{
                          break;  
                        }
                    case 3:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('C', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            number_of_seats = scanner.nextInt();
                            scanner.nextLine();
                            Select('C',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('C', level);
                            break;
                        }else{
                            break;  
                        }
                    case 4:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('D', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            number_of_seats = scanner.nextInt();
                            scanner.nextLine();
                            Select('D',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('D', level);
                            break;
                        }else{
                            break;  
                        }
                    case 5:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('E', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            number_of_seats = scanner.nextInt();
                            scanner.nextLine();
                            Select('E',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('E', level);
                            break;
                        }else{
                            break;  
                        }
                    case 6:
                        if(level.equals("MainLevel") || level.equals("GrandStandLevel")){
                            sPrint("\nHow many seats do you want? ");
                            sel = previewSeats('F', level);
                            if(sel == 0){
                                sPrint("\nHow many seats do you want? ");
                                number_of_seats = scanner.nextInt();
                                scanner.nextLine();
                                Select('F',number_of_seats,level);
                                break;
                            }else if (sel == 1){
                                BuyAll('F', level);
                                break;
                            }else{
                                break;  
                            }
                        }
                        return;
                    case 7:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('G', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            number_of_seats = scanner.nextInt();
                            scanner.nextLine();
                            Select('G',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('G', level);
                            break;
                        }else{
                            break;  
                        }
                    case 8:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('H', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            number_of_seats = scanner.nextInt();
                            scanner.nextLine();
                            Select('H',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('H', level);
                            break;
                        }else{
                            break;  
                        }
                    case 9:
                        if(level.equals("GrandStandLevel")){
                            return;
                        }
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('I', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            number_of_seats = scanner.nextInt();
                            scanner.nextLine();
                            Select('I',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('I', level);
                            break;
                        }else{
                            break;  
                        }
                    case 10:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('J', level);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            number_of_seats = scanner.nextInt();
                            scanner.nextLine();
                            Select('J',number_of_seats,level);
                            break;
                        }else if (sel == 1){
                            BuyAll('J', level);
                            break;
                        }else{
                            break;  
                        }
                    case 11:
                        return;
                    default:
                        continue;
                }
            } catch (InputMismatchException e) {
                sPrint("Invalid Input");
                scanner.nextLine();
            }
            }
        }
    }
    /*Method that lets the operator select which level to reserve seats from
    it will later call the method necessary to make reservations inside a particular level.
     */
    public static void reserveSeat() {
        boolean MENU = true;
        while (MENU) {
            
            Menu.levelSelectionMenu();
            
            try {
                sPrint("Enter Option Number: ");
                int input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1 -> Reservation("FieldLevel");
                    // Aqui method para add client
                    case 2 -> Reservation("MainLevel");
                    case 3 -> Reservation("GrandStandLevel");
                    case 4 -> {
                        return;
                    }
                    default -> {
                        continue;
                    }
                }
            } catch (InputMismatchException e) {
                sPrint("Invalid Input");
                scanner.nextLine();
            }
        }
    }

    /*This method creates all seats of the program, which will be added to the unbought seats
    set. If we dont create seats, we will not be able to reserve any positions*/ 
    public static void createSeats() {
        Seat seat;
        int row = 1;
        int sec = 0;
        char alpha = 'A';
        for (int i = 1; i < 501; i++) {
            alpha = (char) ((int) 'A' + sec);
            seat = new Seat(alpha, row, i);
            fieldLevel.add(seat);
            if (i % 25 == 0) {
                row++;
            }
            if (i % 100 == 0) {
                sec++;
                row = 1;
            }
        }
        
        row = 1;
        sec = 0;
        alpha = 'A';
        for (int i = 1; i < 1001; i++) {
            alpha = (char) ((int) 'A' + sec);
            seat = new Seat(alpha, row, i);
            mainLevel.add(seat);
            if (i % 25 == 0) {
                row++;
            }
            if (i % 100 == 0) {
                sec++;
                row = 1;
            }
        }

        row = 1;
        sec = 0;
        alpha = 'A';
        for (int i = 1; i < 2001; i++) {
            alpha = (char) ((int) 'A' + sec);
            seat = new Seat(alpha, row, i);
            grandStandLevel.add(seat);
            if (i % 25 == 0) {
                row++;
            }
            if (i % 250 == 0) {
                sec++;
                row = 1;
            }
        }
    }

/* This method takes care of the initial steps for a cancelation, asking for the client's details and checking
if said client exists in the system before proceeding*/ 
public static void cancelReservation(){
    sPrint("\n===UPRM Baseball Stadium Seat Manager===");
    sPrint("========Reservation Cancelation======== ");
    boolean menu1 = false;
    Client canceledClient = null;
    while(!menu1){
        try{
            sPrint("Enter client's email or phone number:    -Enter 0 to exit");
            String clientIdentifier = scanner.nextLine();
            if(clientIdentifier.equals("0")){
                return;
            }
            if(isInSystem(clientIdentifier)){
                canceledClient = getInSystem(clientIdentifier);
                menu1 = true;
                sPrint("Client found: " + canceledClient.getName());
            }
            else{
                sPrint("Client was not found.");
                continue;
            }
        } catch(InputMismatchException e) {
            sPrint("Invalid input.");
        }
    }
    
    boolean fieldflag = false;
    boolean mainflag = false;
    boolean grandflag = false;

    if(FLseats.keySet().contains(canceledClient)){
        fieldflag = true;
    }
    if(MLseats.keySet().contains(canceledClient)){
        mainflag = true;
    }
    if(GSLseats.keySet().contains(canceledClient)){
        grandflag = true;
    }
    
    boolean menu2 = false;
    
    while(!menu2){
        sPrint("Select the level to cancel:");
        if(fieldflag){ sPrint(" 1. Field Level");}
        if(mainflag){  sPrint(" 2. Main Level");}
        if(grandflag){ sPrint(" 3. Grand Stand Level");}
        sPrint("Enter 0 to return");
        try{
            int input = scanner.nextInt();
            scanner.nextLine();
            if(input == 0){
                break;
            }
            switch (input) {
                case 1:
                    if(fieldflag){
                        cancelContinuation("FieldLevel", canceledClient, false, null);
                        return;
                    }
                    break;
                case 2:
                    if(mainflag){
                        cancelContinuation("MainLevel", canceledClient, false, null);
                        return;
                    }
                    break;
                case 3:
                    if(grandflag){
                        cancelContinuation("GrandStandLevel",canceledClient, false, null);
                        return;
                    }
                    break;
                default:
                sPrint("Input not recognized");
                    continue;
            }
        } catch(InputMismatchException e){
            sPrint("Invalid input.");
        }
    }

}
/* This method is the next step of a cancelation, which will take care of showing the seats a client has
and allows to select which to cancel and return to the pool of available seats.
It also has additional parameters (Undo and optionalSeatList) which are used in the case of an undo, which already knows
which seats will be returned. */
public static void cancelContinuation(String level, Client client, boolean Undo, ArrayList<Seat> optionalSeatList){
    int count = 0;
    boolean menu = true;
    ArrayList<Seat> seatsToReturn = new ArrayList<>(); //Here we will store the seats to unreserve

    //If this is an undo, we will do a streamlined approach to the cancelation since we already know exactly which seats are to be returned.
    if(Undo){
        seatsToReturn = optionalSeatList; //Here we will store the seats to unreserve in the case of an Undo
        if(seatsToReturn.isEmpty()){
            sPrint("No reservations were cancelled.");
            waitTime(2000);
            return;
        }
        for(Seat returningSeat : seatsToReturn){
            if (level.equals("FieldLevel")) {
                Iterator<Seat> iterator = FLseats.get(client).iterator();
                while (iterator.hasNext()) {
                    Seat clientSeat = iterator.next();
                    if (clientSeat.getSection() == returningSeat.getSection() && clientSeat.getSeatNumber() == returningSeat.getSeatNumber()) {
                        iterator.remove(); // Safe removal using iterator
                        continue;
                    }
                }
                //If said client has no more seats assigned to his arraylist, we can safely remove said client from the system
                if(FLseats.get(client).isEmpty()){
                    FLseats.remove(client);
                }
            }
            if (level.equals("MainLevel")) {
                Iterator<Seat> iterator = MLseats.get(client).iterator();
                while (iterator.hasNext()) {
                    Seat clientSeat = iterator.next();
                    if (clientSeat.getSection() == returningSeat.getSection() && clientSeat.getSeatNumber() == returningSeat.getSeatNumber()) {
                        iterator.remove(); // Safe removal using iterator
                        continue;
                    }
                }
                //If said client has no more seats assigned to his arraylist, we can safely remove said client from the system
                if(MLseats.get(client).isEmpty()){
                    MLseats.remove(client);
                }
            }
            if (level.equals("GrandStandLevel")) {
                Iterator<Seat> iterator = GSLseats.get(client).iterator();
                while (iterator.hasNext()) {
                    Seat clientSeat = iterator.next();
                    if (clientSeat.getSection() == returningSeat.getSection() && clientSeat.getSeatNumber() == returningSeat.getSeatNumber()) {
                        iterator.remove(); // Safe removal using iterator
                        continue;
                    }
                }
                //If said client has no more seats assigned to his arraylist, we can safely remove said client from the system
                if(GSLseats.get(client).isEmpty()){
                    GSLseats.remove(client);
                }
            }
        }
        int price = Seat.seatsTotalPrice(seatsToReturn, level);
        sPrint("Due balance of $" + price +  " has been returned");
        returnSeats(seatsToReturn, level);
        transactionRegister.add(new Transaction(client, seatsToReturn, price, "Cancelation", level)); 
        //TODO ADD WAITLIST THING
        WaitingList.SpaceAvailable(client, level);
        waitTime(2000);
        return;
    }
    while(menu){
        sPrint("\nThe following seats from " + "level are assigned to this client: ");
        //Depending on the level selected (or previously available) we will show on screen
        // the corresponding seats assigned to that client in the respective level
        if(level.equals("FieldLevel")){ 
            sPrint("FieldLevel: ");
            for(Seat s : FLseats.get(client)){
                System.out.print(s.getSection() + "" + s.getSeatNumber() + " ");
                count++;
                if(count == 25){
                    System.out.println();
                    count = 0;
                }
            } count = 0;
        }
        if(level.equals("MainLevel")){
            sPrint("\nMain Level: ");
            for(Seat s : MLseats.get(client)){
                System.out.print(s.getSection() + "" + s.getSeatNumber() + " ");
                count++;
                if(count == 25){
                    System.out.println();
                    count = 0;
                }
            } count = 0;
        }
        if(level.equals("GrandStandLevel")){
            sPrint("\nGrand Stand Level: ");
            for(Seat s : GSLseats.get(client)){
                System.out.print(s.getSection()+ "" + s.getSeatNumber() + " ");
                count++;
                if(count == 25){
                    System.out.println();
                    count = 0;
                }
            } count = 0;
        }
        Menu.cancelMenu();
        char section;
        int seatNumber;
        try{
            String cancel;
            cancel = scanner.nextLine();
            if (cancel.matches("[A-Z][0-9]+")) { // Matches a capital letter followed by one or more digits
                section = Character.toUpperCase(cancel.charAt(0)); //We extract the section from the input
                seatNumber = Integer.parseInt(cancel.substring(1)); //We extract the seat number specified

                if (level.equals("FieldLevel")) {
                    Iterator<Seat> iterator = FLseats.get(client).iterator();
                    while (iterator.hasNext()) {
                        Seat s = iterator.next();
                        if (s.getSection() == section && s.getSeatNumber() == seatNumber) {
                            seatsToReturn.add(s);
                            iterator.remove(); // Safe removal using iterator
                            continue;
                        }
                    }

                }
                if (level.equals("MainLevel")) {
                    Iterator<Seat> iterator = MLseats.get(client).iterator();
                    while (iterator.hasNext()) {
                        Seat s = iterator.next();
                        if (s.getSection() == section && s.getSeatNumber() == seatNumber) {
                            seatsToReturn.add(s);
                            iterator.remove(); // Safe removal using iterator
                            continue;
                        }
                    }
                }
                if (level.equals("GrandStandLevel")) {
                    Iterator<Seat> iterator = GSLseats.get(client).iterator();
                    while (iterator.hasNext()) {
                        Seat s = iterator.next();
                        if (s.getSection() == section && s.getSeatNumber() == seatNumber) {
                            seatsToReturn.add(s);
                            iterator.remove(); // Safe removal using iterator
                            continue;
                        }
                    }
                }  
            //Client exited without saving so we must return canceled reservations       
            } else if (cancel.equals("0")) { 
                while(!seatsToReturn.isEmpty()){
                    if(level.equals("FieldLevel")){
                        FLseats.get(client).add(seatsToReturn.getFirst());
                        seatsToReturn.removeFirst();
                    }
                    if(level.equals("MainLevel")){
                        MLseats.get(client).add(seatsToReturn.getFirst());
                        seatsToReturn.removeFirst();
                    }
                    if(level.equals("GrandStandLevel")){
                        GSLseats.get(client).add(seatsToReturn.getFirst());
                        seatsToReturn.removeFirst();
                    }
                }
                sPrint("Exiting...");
                waitTime(2000);
                return;
            }
            //Client wants to cancel selected reservations
            else if(cancel.equals("1")){ 
                if(seatsToReturn.isEmpty()){
                    sPrint("No reservations were canceled.");
                    waitTime(2000);
                    return;
                }
                int price = Seat.seatsTotalPrice(seatsToReturn, level);
                sPrint("Due balance of $" + price +  " has been returned");
                returnSeats(seatsToReturn, level);
                waitTime(2000);
                transactionRegister.add(new Transaction(client, seatsToReturn, price, "Cancelation", level)); 
                Transaction.undoStack.add(new Transaction(client, seatsToReturn, price, "Cancelation", level));
                WaitingList.SpaceAvailable(client, level); //Check for availability on waitlist
                return;
            }
            //Client wants to cancel all reservations
            else if(cancel.equals("2")){ 
                if (level.equals("FieldLevel")) {
                    Iterator<Seat> iterator = FLseats.get(client).iterator();
                    while (iterator.hasNext()) {
                        Seat s = iterator.next();
                        seatsToReturn.add(s);
                        iterator.remove(); //Safe removal using iterator

                    }
                }
                if (level.equals("MainLevel")) {
                    Iterator<Seat> iterator = MLseats.get(client).iterator();
                    while (iterator.hasNext()) {
                        Seat s = iterator.next();
                        seatsToReturn.add(s);
                        iterator.remove(); //Safe removal using iterator
                    }
                }
                if (level.equals("GrandStandLevel")) {
                    Iterator<Seat> iterator = GSLseats.get(client).iterator();
                    while (iterator.hasNext()) {
                        Seat s = iterator.next();
                        seatsToReturn.add(s);
                        iterator.remove(); //Safe removal using iterator
                    }
                }
                int price = Seat.seatsTotalPrice(seatsToReturn, level);
                sPrint("Due balance of $" + price +  " has been returned");
                returnSeats(seatsToReturn, level);
                if(level.equals("FieldLevel")){
                    FLseats.remove(client);
                }
                if(level.equals("MainLevel")){
                    MLseats.remove(client);
                }
                if(level.equals("GrandStandLevel")){
                    GSLseats.remove(client);
                }
                transactionRegister.add(new Transaction(client, seatsToReturn, price, "Cancelation", level)); 
                Transaction.undoStack.add(new Transaction(client, seatsToReturn, price, "Cancelation", level));
                WaitingList.SpaceAvailable(client, level); //Check for availability on waitlist
                waitTime(2000);
                return;
            }
            else {
                sPrint("\nSeat not found or invalid input format. Please use the format SectionNumber, e.g., A24.");
                scanner.nextLine();
            }
        } catch(InputMismatchException e){
            sPrint("Invalid input");
        }
    }
}

/* This method returns an array of seats to the pool of available seats and removes them from
the sets that contain reserved seats which are inside the dictionaries of each level */
public static void returnSeats(ArrayList<Seat> seatsToReturn, String level) {
    for(Seat seat : seatsToReturn){
        if(level.equals("FieldLevel")){
            fieldLevel.add(seat);
            secFL.get(seat.getSection()).remove(seat);
        }
        if(level.equals("MainLevel")){
            mainLevel.add(seat);
            secML.get(seat.getSection()).remove(seat);
        }
        if(level.equals("GrandStandLevel")){
            grandStandLevel.add(seat);
            secGSL.get(seat.getSection()).remove(seat);
        }
    }
}

//TODO COMMENT THIS TOMAS PLEASE XOXO
    public static void WaitList(){
        // Scanner sc = new Scanner(System.in);

        Menu.waitingListMenu();

        try{
            sPrint("Enter Option Number: ");
            int option = scanner.nextInt();
            scanner.nextLine();

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
            sPrint("Invalid input");
            scanner.nextLine();
        }
    }
    //TODO COMMENT THIS TOMAS PLEASE XOXO
    public static void showWaitingList() {
        // Scanner sc = new Scanner(System.in);
        
        Menu.waitingListLevelMenu();

        try{
            sPrint("Enter Option Number: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1:
                    sPrint("\n\\*Showing: Main Level WL*\\");
                    int i = 1;
                    for(Client c : mainWaitList){
                        System.out.println("Position " + i + " -> " + c.getName());
                        i++;
                    }
                    sPrint("");
                    waitTime(5000);
                    break;
                    case 2:
                    sPrint("\n\\*Showing: Field Level WL*\\");
                    int j = 1;
                    for(Client c : fieldWaitList){
                        System.out.println("Position " + j + " -> " + c.getName());
                        j++;
                    }
                    sPrint("");
                    waitTime(5000);
                    break;
                    case 3:
                    sPrint("\n\\*Showing: Grand Stand Level WL*\\");
                    int k = 1;
                    for(Client c : grandWaitList){
                        System.out.println("Position " + k + " -> " + c.getName());
                        k++;
                    }
                    sPrint("");
                    waitTime(5000);
                    break;
                case 4:
                    return;
            }

        } catch(InputMismatchException e){
            sPrint("Invalid input");
            scanner.nextLine();
        }
    }
    //TODO COMMENT THIS TOMAS PLEASE XOXO
    public static void WLDequeHelper(){
        // Scanner sc = new Scanner(System.in);

        sPrint("\nSelect WL to deque from");
        sPrint("1. Main Level WL"
           + "\n2. Field level WL"
           + "\n3. Grand stand level WL"
           + "\n4. Cancel");
        
        try{
            sPrint("Enter Option Number: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1:
                    WaitingListDeque(client, mainWaitList);
                    break;
                case 2:
                    WaitingListDeque(client, fieldWaitList);
                    break;
                case 3:
                    WaitingListDeque(client, grandWaitList);
                    break;
                case 4:
                    return;
            }

        } catch(InputMismatchException e){
            sPrint("Invalid input");
            scanner.nextLine();
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

        sPrint("Client deleted from Waiting List succesfully");
        waitTime(3000);

        while(!temp.isEmpty()){ // This makes the original queue return to its original state
            levelWL.add(temp.poll());
        }
    }

    public static void mainWaitListAdd(Client c){
        mainWaitList.add(c);
    }

    public static void fieldWaitListAdd(Client c){
        fieldWaitList.add(c);
    }

    public static void grandWaitListAdd(Client c){
        grandWaitList.add(c);
    }

    public static void printQueue(Queue<Client> queue) {
        for (Client c : queue) {
            System.out.println(c.getName());
        }
    }


    /* These methods add the seats that will contain bought seats to dictionaries depending on the section they
    are apart of */
    /* Add grand stand level seats to dictionaries of bought seats*/
    public static void fLevelCreate(){
        // Field Level
        secFL.put('A', FLA);
        secFL.put('B', FLB);
        secFL.put('C', FLC);
        secFL.put('D', FLD);
        secFL.put('E', FLE);
    }
    /* Add grand stand level seats to dictionaries of bought seats*/
    public static void mLevelCreate(){
        // Main Level
        secML.put('A', MLA);
        secML.put('B', MLB);
        secML.put('C', MLC);
        secML.put('D', MLD);
        secML.put('E', MLE);
        secML.put('F', MLF);
        secML.put('G', MLG);
        secML.put('H', MLH);
        secML.put('I', MLI);
        secML.put('J', MLJ);
    }
    /* Add grand stand level seats to dictionaries of bought seats*/
    public static void gsLevelCreate(){
        // Grand Stand Level
        secGSL.put('A', GSLA);
        secGSL.put('B', GSLB);
        secGSL.put('C', GSLC);
        secGSL.put('D', GSLD);
        secGSL.put('E', GSLE);
        secGSL.put('F', GSLF);
        secGSL.put('G', GSLG);
        secGSL.put('H', GSLH);
    }
}
