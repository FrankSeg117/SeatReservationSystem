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
import java.util.Stack;


public class Stadium {
    /*
     * Sets
     */
    //Unreserved Seats - Here all unreserved seats are stored
    public static Set<Seat> mainLevel = new HashSet<>(); 
    public static Set<Seat> fieldLevel = new HashSet<>();
    public static Set<Seat> grandStandLevel = new HashSet<>();
    
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
     * Linked Lists
     */

    public static LinkedList<Transaction> transactionRegister = new LinkedList<>(); // placeholder, may need to create a class for transactions

    /*
     * Hash Maps
     */

    public static HashMap<Client, ArrayList<Seat>> FLseats = new HashMap<>(); // Structure to store Client in field level seats
    public static HashMap<Client, ArrayList<Seat>> MLseats = new HashMap<>(); // Structure to store Client in main level seats
    public static HashMap<Client, ArrayList<Seat>> GSLseats = new HashMap<>(); // Structure to store Client in Grand Stand level seats
    
    //Reserved Seats
    public static HashMap<Character, Set<Seat>> secFL = new HashMap<>();
    public static HashMap<Character, Set<Seat>> secML = new HashMap<>();
    public static HashMap<Character, Set<Seat>> secGSL = new HashMap<>();

    /*
     * Stacks
     */

    public Stack<String> actions = new Stack<>();
    // Para implementar la funcionalidad de deshacer, en la que se puede
    // deshacer la última acción de reservación/cancelación

    /*
     * Queues
     */

    public static Queue<Client> mainWaitList = new LinkedList<>();
    public static Queue<Client> fieldWaitList = new LinkedList<>();
    public static Queue<Client> grandWaitList = new LinkedList<>();

    /*
     * Variables
     */

    public static boolean buy = false;

    public static Client client;

    public static Scanner scanner = new Scanner(System.in); //TODO MAKE THE CODE USE THIS SCANNER ONLY AND ONLY IF

    public static void sPrint(String a) {System.out.println(a);} // Method to print faster like Python

    public static void waitTime(int ms){
        // Esto es parte de java para crear una breve pausa en pantalla para que se
        // pueda mostrar un aviso de transaccion completada antes de volver al inicio
        try { 
            Thread.sleep(ms); // Pausa por "ms" milisegundos
        }
        catch (InterruptedException e) {
                e.printStackTrace();
        }
    }

    public static Boolean isInSystem(String identifier){
        return Client.checkClientIsInSystem(identifier, FLseats.keySet(), MLseats.keySet(), GSLseats.keySet());
    }

    public static Client getInSystem(String identifier){
        return Client.getClientInSystem(identifier, FLseats.keySet(), MLseats.keySet(), GSLseats.keySet());
    }

    public static void Select(Character sec, int NumberOfSeats, String A) {  //NofS is NUMBER OF SEATS 
        boolean avl = false;
        int SN = 0;

        sPrint("Available Seats:");

        ArrayList<Seat> secA = new ArrayList<>();
        if(A.equals("FL")){
            for (Seat a : fieldLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("ML")){
            for (Seat a : mainLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("GSL")){
            for (Seat a : grandStandLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        secA.sort(Comparator.comparingInt(Seat::getSeatNumber));
        
        ArrayList<Seat> clientA = new ArrayList<>(); //Sillas que estaban en el cliente anteriormente (Si es que existia anteriormente)
        ArrayList<Seat> newSeats = new ArrayList<>(); //Sillas que se añaden nuevas al cliente sin contar las anteriores (Esto nos sirve para registrar las transacciones de forma precisa)

        //En estos metodos se busca las sillas que el cliente tenia anteriormente en el diccionario
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

        for(int k = 0; k<NumberOfSeats; k++){
            avl = false;
            if(A.equals("FL")){
                for(Seat a: secFL.get(sec)){
                    if(a.getSeatNumber() < secA.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            if(A.equals("ML")){
                for(Seat a: secML.get(sec)){
                    if(a.getSeatNumber() < secA.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            if(A.equals("GSL")){
                for(Seat a: secGSL.get(sec)){
                    if(a.getSeatNumber() < secA.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            for (int i = 0; i < secA.size(); i++) {
                if(clientA.size() == 0){
                    System.out.print(secA.get(i).getSeatNumber() + " ");
                }else{
                    if(clientA.contains(secA.get(i))){
                        System.out.print("   ");
                    }else{
                        System.out.print(secA.get(i).getSeatNumber() + " ");
                    }
                }
                
                if(i != secA.size()-1){
                    if(secA.get(i+1).getSeatNumber() - secA.get(i).getSeatNumber() > 1){
                        for(int j = 1; secA.get(i+1).getSeatNumber() - secA.get(i).getSeatNumber()>j; j++){
                            System.out.print("   ");
                        }
                    }
                }
                if (secA.get(i).getSeatNumber() % 25 == 0) {
                    System.out.print("\n");
                    if(A.equals("FL")){
                        for(Seat a: secFL.get(sec)){
                            if(secA.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < secA.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                    if(A.equals("ML")){
                        for(Seat a: secML.get(sec)){
                            if(secA.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < secA.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                    if(A.equals("GSL")){
                        for(Seat a: secGSL.get(sec)){
                            if(secA.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < secA.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                }
                
            }
            
            sPrint("\n==== Choose Seat ====");

            sPrint("\nSeat Number: ");
            try {
                SN = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                sPrint("Please input the correct information");
            }
            if(A.equals("FL")){
                for (Seat a : fieldLevel) {
                    if (a.getSection() == sec && a.getSeatNumber() == SN) {
                        if(!clientA.contains(a)){
                            avl = true;
                        }
                    }
                }
            }
            if(A.equals("ML")){
                for (Seat a : mainLevel) {
                    if (a.getSection() == sec && a.getSeatNumber() == SN) {
                        if(!clientA.contains(a)){
                            avl = true;
                        }
                    }
                }
            }
            if(A.equals("GSL")){
                for (Seat a : grandStandLevel) {
                    if (a.getSection() == sec && a.getSeatNumber() == SN) {
                        if(!clientA.contains(a)){
                            avl = true;
                        }
                    }
                }
            }

            if(!avl){
                sPrint("\nSeat is not available.");
                sPrint("\nTry again.");
                waitTime(2000);

                k--;
            }
            if(avl){
                if(A.equals("FL")){
                    for (Seat a : fieldLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            clientA.add(a);
                            newSeats.add(a);
                        }
                    }
                }
                if(A.equals("ML")){
                    for (Seat a : mainLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            clientA.add(a);
                            newSeats.add(a);
                        }
                    } 
                }
                if(A.equals("GSL")){
                    for (Seat a : grandStandLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            clientA.add(a);
                            newSeats.add(a);
                        }
                    } 
                }
            }
        }
    
        Buy(clientA, sec, A, newSeats,false);

    }

    public static int previewSeats(Character sec, String A) {

        sPrint("\nAvailable Seats:");

        ArrayList<Seat> secA = new ArrayList<>();
        if(A.equals("FL")){
            if(secFL.get(sec).size() == 100){
                waitTime(2000);
                sPrint("\nNo seats available.");
                waitTime(2000);
                return 3;
            }
            for (Seat a : fieldLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("ML")){
            if(secML.get(sec).size() == 100){
                waitTime(2000);
                sPrint("\nNo seats available.");
                waitTime(2000);
                return 3;
            }
            for (Seat a : mainLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("GSL")){
            if(secGSL.get(sec).size() == 250){
                waitTime(2000);
                sPrint("\nNo seats available.");
                waitTime(2000);
                return 3;
            }
            for (Seat a : grandStandLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        secA.sort(Comparator.comparingInt(Seat::getSeatNumber));
        
        ArrayList<Seat> clientA = new ArrayList<>();

            if(A.equals("FL")){
                for(Seat a: secFL.get(sec)){
                    if(a.getSeatNumber() < secA.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            if(A.equals("ML")){
                for(Seat a: secML.get(sec)){
                    if(a.getSeatNumber() < secA.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            if(A.equals("GSL")){
                for(Seat a: secGSL.get(sec)){
                    if(a.getSeatNumber() < secA.get(0).getSeatNumber()){
                        System.out.print("   ");
                    }
                }
            }
            for (int i = 0; i < secA.size(); i++) {
                if(clientA.size() == 0){
                    System.out.print(secA.get(i).getSeatNumber() + " ");
                }else{
                    if(clientA.contains(secA.get(i))){
                        System.out.print("   ");
                    }else{
                        System.out.print(secA.get(i).getSeatNumber() + " ");
                    }
                }
                
                if(i != secA.size()-1){
                    if(secA.get(i+1).getSeatNumber() - secA.get(i).getSeatNumber() > 1){
                        for(int j = 1; secA.get(i+1).getSeatNumber() - secA.get(i).getSeatNumber()>j; j++){
                            System.out.print("   ");
                        }
                    }
                }
                if (secA.get(i).getSeatNumber() % 25 == 0) {
                    System.out.print("\n");
                    if(A.equals("FL")){
                        for(Seat a: secFL.get(sec)){
                            if(secA.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < secA.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                    if(A.equals("ML")){
                        for(Seat a: secML.get(sec)){
                            if(secA.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < secA.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                    if(A.equals("GSL")){
                        for(Seat a: secGSL.get(sec)){
                            if(secA.get(i).getSeatNumber() < a.getSeatNumber() && a.getSeatNumber() < secA.get(i+1).getSeatNumber()){
                                System.out.print("   ");
                            }
                        }
                    }
                }
                
            }
        
        waitTime(2000);
        boolean OPTION = true;
        while (OPTION) {
            sPrint("\nPlease select an option: ");
            sPrint("""
                   1. Select Seats
                   2. Buy Remaining Seats
                   3. Return""");
            //
            try {
                sPrint("Enter Option Number: ");
                int input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1:
                        // Aqui method para add client
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

    @SuppressWarnings({"Unnecessary return statement", "UnnecessaryReturnStatement"}) // This avoids a warning
    public static void Buy(ArrayList<Seat> Seat, Character sec, String Level, ArrayList<Seat> newSeats, boolean Undo) {
        int price = 0;
        String level = "";
        String conf = "";
        if(!Undo){
            try {
                sPrint("==== Payment ====");

                if(Level.equals("FL")){ 
                    price = 300*newSeats.size();
                    level = "FieldLevel";
                    sPrint("\nTotal Cost: $" + price);
                }
                if(Level.equals("ML")){
                    price = 120*newSeats.size();
                    level = "MainLevel";
                    sPrint("\nTotal Cost: $" + price);
                }
                if(Level.equals("GSL")){
                    price = 45*newSeats.size();
                    level = "GrandStandLevel";
                    sPrint("\nTotal Cost: $" + price);
                }

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
            if(Undo){
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
    
            
            for (Seat b : Seat) {
                if(Level.equals("FL") || Level.equals("FieldLevel")){
                    for(Seat a: fieldLevel){
                        if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                            FLseats.put(client, Seat);
                            fieldLevel.remove(a);
                            secFL.get(sec).add(a);
                            break;
                        }
                    }
                }
                if(Level.equals("ML")|| Level.equals("MainLevel")){
                    for(Seat a: mainLevel){
                        if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                            MLseats.put(client, Seat);
                            mainLevel.remove(a);
                            secML.get(sec).add(a);
                            break;
                        }
                    } 
                }
                if(Level.equals("GSL")|| Level.equals("GSL")){
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

    public static void BuyAll(Character sec, String A) {
        ArrayList<Seat> newSeats = new ArrayList<>();
        boolean avl = false;
        int SN = 0; 

        ArrayList<Seat> secA = new ArrayList<>();
        if(A.equals("FL")){
            for (Seat a : fieldLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("ML")){
            for (Seat a : mainLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("GSL")){
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

                if(A.equals("FL")){
                    for (Seat a : fieldLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            if(!clientA.contains(a)){
                                avl = true;
                            }
                        }
                    }
                }
                if(A.equals("ML")){
                    for (Seat a : mainLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            if(!clientA.contains(a)){
                                avl = true;
                            }
                        }
                    }
                }
                if(A.equals("GSL")){
                    for (Seat a : grandStandLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            if(!clientA.contains(a)){
                                avl = true;
                            }
                        }
                    }
                }
                if(avl){
                    if(A.equals("FL")){
                        for (Seat a : fieldLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                                clientA.add(a);
                                newSeats.add(a);
                            }
                        }
                    }
                    if(A.equals("ML")){
                        for (Seat a : mainLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                                clientA.add(a);
                                newSeats.add(a);
                            }
                        } 
                    }
                    if(A.equals("GSL")){
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

    public static void Reservation(String level) {        
        boolean MENU = true;
        while (MENU) {
            { // Verifies if there are seats still available
                if(level.equals("FL") && !fieldLevel.isEmpty()){
                sPrint("\n===UPRM Baseball Stadium Seat Manager===");
                sPrint("\nPlease Select a Section: ");
                sPrint("1. Section A (Available Seats: " + (100 - secFL.get('A').size()) + ")"
                        + "\n2. Section B (Available Seats: " + (100 - secFL.get('B').size()) + ")"
                        + "\n3. Section C (Available Seats: " + (100 - secFL.get('C').size()) + ")"
                        + "\n4. Section D (Available Seats: " + (100 - secFL.get('D').size()) + ")"
                        + "\n5. Section E (Available Seats: " + (100 - secFL.get('E').size()) + ")"
                        + "\n6. Return");
                } else if (fieldLevel.isEmpty()){
                    sPrint("\nNo seats are available at the moment\nWould you like to enter the Waiting List for this level? (Y/N)");
                    String wList = scanner.next();
                    if(wList.equals("n")){
                        sPrint("\nCancelling operation...");
                        waitTime(1500);
                        return;
                    } else if(wList.equals("y")) {
                        WaitingList.fieldWaitListAdd(client);
                        return;
                    }
                } 
                if(level.equals("ML") && !mainLevel.isEmpty()){
                sPrint("\n===UPRM Baseball Stadium Seat Manager===");
                sPrint("\nPlease Select a Section: ");
                sPrint("1. Section A (Available Seats: " + (100 - secML.get('A').size()) + ")"
                        + "\n2. Section B (Available Seats: " + (100 - secML.get('B').size()) + ")"
                        + "\n3. Section C (Available Seats: " + (100 - secML.get('C').size()) + ")"
                        + "\n4. Section D (Available Seats: " + (100 - secML.get('D').size()) + ")"
                        + "\n5. Section E (Available Seats: " + (100 - secML.get('E').size()) + ")"
                        + "\n6. Section F (Available Seats: " + (100 - secML.get('F').size()) + ")"
                        + "\n7. Section G (Available Seats: " + (100 - secML.get('G').size()) + ")"
                        + "\n8. Section H (Available Seats: " + (100 - secML.get('H').size()) + ")"
                        + "\n9. Section I (Available Seats: " + (100 - secML.get('I').size()) + ")"
                        + "\n10. Section J (Available Seats: " + (100 - secML.get('J').size()) + ")"
                        + "\n11. Return"
                        + "\n12. Buy everything");
                } else if(mainLevel.isEmpty()) {
                    sPrint("\nNo seats are available at the moment\nWould you like to enter the Waiting List for this level? (Y/N)");
                    String wList = scanner.next();
                    if(wList.equals("n")){
                        sPrint("\nCancelling operation...");
                        waitTime(1500);
                        return;
                    } else if(wList.equals("y")) {
                        WaitingList.mainWaitListAdd(client);
                        return;
                    }
                }
            
                if(level.equals("GSL") && !grandStandLevel.isEmpty()){
                sPrint("\n===UPRM Baseball Stadium Seat Manager===");
                sPrint("\nPlease Select a Section: ");
                sPrint("1. Section A (Available Seats: " + (250 - secGSL.get('A').size()) + ")"
                        + "\n2. Section B (Available Seats: " + (250 - secGSL.get('B').size()) + ")"
                        + "\n3. Section C (Available Seats: " + (250 - secGSL.get('C').size()) + ")"
                        + "\n4. Section D (Available Seats: " + (250 - secGSL.get('D').size()) + ")"
                        + "\n5. Section E (Available Seats: " + (250 - secGSL.get('E').size()) + ")"
                        + "\n6. Section F (Available Seats: " + (250 - secGSL.get('F').size()) + ")"
                        + "\n7. Section G (Available Seats: " + (250 - secGSL.get('G').size()) + ")"
                        + "\n8. Section H (Available Seats: " + (250 - secGSL.get('H').size()) + ")"
                        + "\n9. Return");
                } else if(grandStandLevel.isEmpty()){
                    sPrint("\nNo seats are available at the moment\nWould you like to enter the Waiting List for this level? (Y/N)");
                    String wList = scanner.next();
                    if(wList.equals("n")){
                        sPrint("\nCancelling operation...");
                        waitTime(1500);
                        return;
                    } else if(wList.equals("y")) {
                        WaitingList.grandWaitListAdd(client);
                        return;
                    }
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
                                if(number_of_seats<=100 && number_of_seats>=1 && (level.equals("FL") || level.equals("ML"))){
                                    retry = false;
                                }else if(level.equals("FL")){
                                    sPrint("Number of seats need to be from 1 to " + secFL.get('A').size());
                                }else if(level.equals("ML")){
                                    sPrint("Number of seats need to be from 1 to " + secML.get('A').size());
                                }else if(number_of_seats<=250 && number_of_seats>=1 && level.equals("GSL")){
                                    retry = false;
                                }else if(level.equals("GSL")){
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
                        if(level.equals("ML") || level.equals("GSL")){
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
                        if(level.equals("GSL")){
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
                    case 12: // Just for testing of WL methods
                        BuyAll('A', level);
                        BuyAll('B', level);
                        BuyAll('C', level);
                        BuyAll('D', level);
                        BuyAll('E', level);
                        BuyAll('F', level);
                        BuyAll('G', level);
                        BuyAll('H', level);
                        BuyAll('I', level);
                        BuyAll('J', level);
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

    public static void reserveSeat() {
        boolean MENU = true;
        while (MENU) {
            sPrint("\n===UPRM Baseball Stadium Seat Manager===");
            sPrint("\nPlease Select a Seat Level: ");
            sPrint("1. Field Level" + " (Available Seats: " + fieldLevel.size() + ")"
                    + "\n2. Main Level" + " (Available Seats: " + mainLevel.size() + ")"
                    + "\n3. Grandstand Level" + " (Available Seats: " + grandStandLevel.size() + ")"
                    + "\n4. Return");
            
            try {
                sPrint("Enter Option Number: ");
                int input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1 -> Reservation("FL");
                    // Aqui method para add client
                    case 2 -> Reservation("ML");
                    case 3 -> Reservation("GSL");
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

//TODO FINISH RESERVATION CANCELATION
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
                        cancelContinuation("FieldLevel", canceledClient, false);
                        return;
                    }
                    break;
                case 2:
                    if(mainflag){
                        cancelContinuation("MainLevel", canceledClient, false);
                        return;
                    }
                    break;
                case 3:
                    if(grandflag){
                        cancelContinuation("GrandStandLevel",canceledClient, false);
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

public static void cancelContinuation(String level, Client client, boolean Undo){
    int count = 0;
    boolean menu = true;
    ArrayList<Seat> seatsToReturn = new ArrayList<>(); //Here we will store the seats to unreserve
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
            for(Seat s : FLseats.get(client)){
                System.out.print(s.getSection() + "" + s.getSeatNumber() + " ");
                if(count == 25){
                    System.out.println();
                    count = 0;
                }
            } count = 0;
        }
        if(level.equals("GrandStandLevel")){
            sPrint("\nGrand Stand Level: ");
            for(Seat s : FLseats.get(client)){
                System.out.print(s.getSection()+ "" + s.getSeatNumber() + " ");
                if(count == 25){
                    System.out.println();
                    count = 0;
                }
            } count = 0;
        }
        if(!Undo){
            sPrint("""
                \nEnter Seats in the format SectionNumber, Example: A24, B100.
                - Enter 1 when finished to continue  
                - Enter 2 to cancel ALL reservations in this level. 
                - Enter 0 to leave without saving. 
                """);
        }
        char section;
        int seatNumber;
        try{
            String cancel;
            if(Undo){
                cancel = "2";
            }else{
                cancel = scanner.nextLine();
                
            }
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
            } else if (cancel.equals("0")) { //Client exited without saving so we must return canceled reservations
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
            else if(cancel.equals("1")){ //Client wants to cancel selected reservations
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
                if(!Undo){
                    Transaction.undoStack.add(new Transaction(client, seatsToReturn, price, "Cancelation", level));
                }
                return;
            }
        
            else if(cancel.equals("2")){ //Client wants to cancel all reservations
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
                if(!Undo){
                    Transaction.undoStack.add(new Transaction(client, seatsToReturn, price, "Cancelation", level));
                }
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
public static void returnSeats(ArrayList<Seat> seatsToReturn, String level) {
    for(Seat seat : seatsToReturn){
        if(level.equals("FieldLevel")){
            fieldLevel.add(seat);
        }
        if(level.equals("MainLevel")){
            mainLevel.add(seat);
        }
        if(level.equals("GrandStandLevel")){
            grandStandLevel.add(seat);
        }
    }
}


    public static void WaitList(){
        Scanner sc = new Scanner(System.in);

        sPrint("\\*Waiting list menu*\\\n");
        sPrint("Please select an option:"
        + "\n1. Show Waiting List"
        + "\n2. Quit Waiting List"
        + "\n3. Exit");

        try{
            sPrint("Enter Option Number: ");
            int option = sc.nextInt();

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
            sc.nextLine();
        }
    }
    
    public static void showWaitingList() {
        Scanner sc = new Scanner(System.in);
        sPrint("\\*Select Waiting list to display*\\\n");
        sPrint("Please select an option:"
        + "\n1. Main level Waiting List"
        + "\n2. Field level Waiting List"
        + "\n3. Grand Stand level Waiting List"
        + "\n4. Exit");

        try{
            sPrint("Enter Option Number: ");
            int option = sc.nextInt();

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
            sc.nextLine();
        }
    }

    public static void WLDequeHelper(){
        Scanner sc = new Scanner(System.in);

        sPrint("\nSelect WL to deque from");
        sPrint("1. Main Level WL"
        + "\n2. Field level WL"
        + "\n3. Grand stand level WL"
        + "\n4. Cancel");
        
        try{
            sPrint("Enter Option Number: ");
            int option = sc.nextInt();

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
            sc.nextLine();
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


    public static void fLevelCreate(){
        // Field Level
        secFL.put('A', FLA);
        secFL.put('B', FLB);
        secFL.put('C', FLC);
        secFL.put('D', FLD);
        secFL.put('E', FLE);
    }

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

    // Hacemos algo tipo putty?
    public static void main(String[] args) {
        
        fLevelCreate();
        mLevelCreate();
        gsLevelCreate();

        boolean MENU = true;

        createSeats();

        while (MENU) {
            sPrint("\n\n===UPRM Baseball Stadium Seat Manager===");
            sPrint("\nPlease Select an Option: ");
            sPrint("""
                   1. Reserve Seat
                   2. Cancel a Reservation
                   3. Reservation History
                   4. Undo Previous Transaction
                   5. Show Wait List
                   6. Close App
                   """);

            try {
                sPrint("Enter Option Number: ");
                int input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1 -> {
                        // Reserve Seat Client
                        client = Client.addClient();
                        reserveSeat();
                    }
                    case 2 -> {        
                        cancelReservation();
                    }
                    case 3 -> // Show Reservation History
                        Transaction.printTransactionLinkedList(transactionRegister, scanner);
                    case 4 -> {     // TODO Undo Previous Transaction
                        Transaction.undoLastTransaction();
                    }
                    case 5 -> // WaitList
                        WaitingList.WaitList();
                    case 6 -> {
                        sPrint("Closing Program...");
                        waitTime(3000);
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
}
