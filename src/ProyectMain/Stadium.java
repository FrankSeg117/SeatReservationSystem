package ProyectMain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Stadium {
    /*
     * Sets
     */
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

    public static LinkedList<Transaction> transRegister = new LinkedList<>(); // placeholder, may need to create a class for transactions

    /*
     * Hash Maps
     */

    public static HashMap<Client, ArrayList<Seat>> FLseats = new HashMap<>(); // Structure to store Client in field level seats
    public static HashMap<Client, ArrayList<Seat>> MLseats = new HashMap<>(); // Structure to store Client in main level seats
    public static HashMap<Client, ArrayList<Seat>> GSLseats = new HashMap<>(); // Structure to store Client in Grand Stand level seats
    public HashMap<Client, Seat> GSseats = new HashMap<>(); // Structure to store Client in grandstand level seats
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

    public static Queue<Client> waitlist = new LinkedList<>();
    // Here we store the waitlist of clients that want a certein
    // level

    /*
     * Variables
     */

    public static int wl_Size = waitlist.size();

    public static boolean buy = false;

    public static Client client;

    public static void sPrint(String a) {System.out.println(a);} // Method to print faster like Python
    public static void clientPrint(Client c) {System.out.println(c.toString());}

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

    // public void AssignClientTOSeat(Client client, char section, String level) {
    //     if (level.equals("FIELD")) {
    //         Seat s = null;
    //         for (Seat seat : fieldLevel) {
    //             if (seat.getSection() == section /* and is not taken */) {

    //             }
    //         }
    //         FLseats.put(client, s);
    //     }
    // }

    public static void Select(Character sec, int NofS, String A) {  //QUE RAYOS ES NOFS BROOOOOO TODO EXPLAINT WHAT IS THIS
        Scanner SelectMenu = new Scanner(System.in);
        boolean avl = false;
        int SN = 0; 
        int n = 0;

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

        for(int k = 0; k<NofS; k++){
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
            try {
                sPrint("\n==== Choose Seat ====");

                sPrint("\nSeat Number: ");

                SN = SelectMenu.nextInt();

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
                            }
                        }
                    }
                    if(A.equals("ML")){
                        for (Seat a : mainLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                                clientA.add(a);
                            }
                        } 
                    }
                    if(A.equals("GSL")){
                        for (Seat a : grandStandLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                                clientA.add(a);
                            }
                        } 
                    }
                }
                
            } catch (InputMismatchException e) {
                sPrint("Please input the correct information");
            }
        }
        
        int price = 0;
        if(A.equals("FL")){
            price = 300*clientA.size();
        }
        if(A.equals("ML")){
            price = 120*clientA.size();
        }
        if(A.equals("GSL")){
            price = 45*clientA.size();
        }
        //Implementation for reservations in transaction history TODO FIX ERROR OF 100 TRANSACTIONS
        transRegister.add(new Transaction(clientA.size(), client, clientA, price, "Reservation")); 
    
    
        Buy(clientA, sec, A, NofS);

    }

    public static int previewSeats(Character sec, String A) {
        Scanner SelectMenu = new Scanner(System.in);
        boolean avl = false;
        int SN = 0; 
        
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
        Scanner option = new Scanner(System.in);
        while (OPTION) {
            sPrint("\nPlease select an option: ");
            sPrint("1. Select Seats"
                    + "\n2. Buy Remaining Seats"
                    + "\n3. Return");
            //
            try {
                sPrint("Enter Option Number: ");
                int input = option.nextInt();
                switch (input) {
                    case 1:
                        // Aqui method para add client
                        return 0;
                    case 2:
                        return 1;
                    case 3:
                        return 2;
                    default:
                        continue;
                }
            } catch (InputMismatchException e) {
                sPrint("Invalid Input");
                option.nextLine();
            }
        }

        return 2;
        
    }

    public static void Buy(ArrayList<Seat> Seat, Character sec, String A, int NofS) {
        Scanner BuyMenu = new Scanner(System.in);
        int price = 0;
        String conf = "";
        try {
            sPrint("==== Payment ====");

            if(A.equals("FL")){
                sPrint("\nTotal Cost: $" + 300*NofS);
            }
            if(A.equals("ML")){
                sPrint("\nTotal Cost: $" + 120*NofS);
            }
            if(A.equals("GSL")){
                sPrint("\nTotal Cost: $" + 45*NofS);
            }

            sPrint("\nDo you wish to confirm your payment?");

            sPrint("\nYes or No (Y/N):");

            conf = BuyMenu.nextLine();

        } catch (InputMismatchException e) {
            sPrint("Please input the correct information");
        }

        if (conf.toLowerCase().equals("y")) {
            sPrint("\nTransaction Completed.");

            sPrint("\nReturrning Back...");

            for (Seat b : Seat) {
                if(A.equals("FL")){
                    for(Seat a: fieldLevel){
                        if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                            FLseats.put(client, Seat);
                            fieldLevel.remove(a);
                            secFL.get(sec).add(a);
                            break;
                        }
                    }
                }
                if(A.equals("ML")){
                    for(Seat a: mainLevel){
                        if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                            MLseats.put(client, Seat);
                            mainLevel.remove(a);
                            secML.get(sec).add(a);
                            break;
                        }
                    } 
                }
                if(A.equals("GSL")){
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
        waitTime(200);
    }

    public static void BuyAll(Character sec, String A) {
        boolean avl = false;
        int SN = 0; 
        
        int NofS = 0; //NECESITO SABER Q ES ESTO

        ArrayList<Seat> secA = new ArrayList<>();
        if(A.equals("FL")){
            NofS = fieldLevel.size();
            for (Seat a : fieldLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("ML")){
            NofS = fieldLevel.size();
            for (Seat a : mainLevel) {
                if (a.getSection() == sec) {
                    secA.add(a);
                }
            }
        }
        if(A.equals("GSL")){
            NofS = fieldLevel.size();
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
                            }
                        }
                    }
                    if(A.equals("ML")){
                        for (Seat a : mainLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                              
                                clientA.add(a);
                            }
                        } 
                    }
                    if(A.equals("GSL")){
                        for (Seat a : grandStandLevel) {
                            if (a.getSection() == sec && a.getSeatNumber() == SN) {
                               
                                clientA.add(a);
                            }
                        } 
                    }
                }
                if(!avl){
                    continue;
                }
        }


        
        int price = 0;
        if(A.equals("FL")){
            price = 300*clientA.size();
        }
        if(A.equals("ML")){
            price = 120*clientA.size();
        }
        if(A.equals("GSL")){
            price = 45*clientA.size();
        }
        //Implementation for reservations in transaction history TODO FIX ERROR OF 100 TRANSACTIONS
        transRegister.add(new Transaction(clientA.size(), client, clientA, price, "Reservation")); 


        Buy(clientA, sec, A, secA.size());

    }

    public static Client addClient() {
        String Cname = ""; // variables para crear y añadir cliente
        String email = "";
        String num = "";
        Scanner AddClientMenu = new Scanner(System.in);
        try {
            sPrint("==== Client Information ====");

            sPrint("\nPlease enter the client's name: ");
            Cname = AddClientMenu.nextLine();

            sPrint("\nPlease enter the client's email: ");
            email = AddClientMenu.nextLine();

            sPrint("\nPlease enter the client's phone number: ");
            num = AddClientMenu.nextLine();

        } catch (InputMismatchException e) {
            sPrint("Please input the correct information");
        }
        if(!FLseats.isEmpty()){
            for(Client a: FLseats.keySet()){
                if(email.equals(a.getEmail())){
                    sPrint("\nClient is already on the system.");
                    return a;
                }
                waitTime(2000);
            }
        }if(!MLseats.isEmpty()){
            for(Client a: MLseats.keySet()){
                if(email.equals(a.getEmail())){
                    sPrint("\nClient is already on the system.");
                    return a;
                }
                waitTime(2000);
            }
        }if(!GSLseats.isEmpty()){
            for(Client a: GSLseats.keySet()){
                if(email.equals(a.getEmail())){
                    sPrint("\nClient is already on the system.");
                    return a;
                }
                waitTime(2000);
            }
        }
        return new Client(Cname, email, num);
    }

    public static void Reservation(String a) {
        boolean MENU = true;
        Scanner menu = new Scanner(System.in);
        while (MENU) {
            if(a.equals("FL")){
                sPrint("\n===UPRM Baseball Stadium Seat Manager===");
                sPrint("\nPlease Select a Section: ");
                sPrint("1. Section A (Available Seats: " + (100 - secFL.get('A').size()) + ")"
                        + "\n2. Section B (Available Seats: " + (100 - secFL.get('B').size()) + ")"
                        + "\n3. Section C (Available Seats: " + (100 - secFL.get('C').size()) + ")"
                        + "\n4. Section D (Available Seats: " + (100 - secFL.get('D').size()) + ")"
                        + "\n5. Section E (Available Seats: " + (100 - secFL.get('E').size()) + ")"
                        + "\n6. Return");
            }if(a.equals("ML")){
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
                        + "\n11. Return");
            }
            if(a.equals("GSL")){
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
            }

            try {
                sPrint("Enter Option Number: ");
                int input = menu.nextInt();
                int nSt;
                int sel;
                switch (input) {
                    case 1:
                        // Aqui method para add client
                        sPrint("\nSeats Avaialble in this section:");
                        sel = previewSeats('A', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('A',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('A', a);
                            break;
                        }else{
                          break;  
                        }
                    case 2:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('B', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('B',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('B', a);
                            break;
                        }else{
                          break;  
                        }
                    case 3:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('C', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('C',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('C', a);
                            break;
                        }else{
                            break;  
                        }
                    case 4:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('D', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('D',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('D', a);
                            break;
                        }else{
                            break;  
                        }
                    case 5:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('E', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('E',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('E', a);
                            break;
                        }else{
                            break;  
                        }
                    case 6:
                        if(a.equals("ML") || a.equals("GSL")){
                            sPrint("\nHow many seats do you want? ");
                            sel = previewSeats('F', a);
                            if(sel == 0){
                                sPrint("\nHow many seats do you want? ");
                                nSt = menu.nextInt();
                                Select('F',nSt,a);
                                break;
                            }else if (sel == 1){
                                BuyAll('F', a);
                                break;
                            }else{
                                break;  
                            }
                        }
                        return;
                    case 7:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('G', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('G',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('G', a);
                            break;
                        }else{
                            break;  
                        }
                    case 8:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('H', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('H',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('H', a);
                            break;
                        }else{
                            break;  
                        }
                    case 9:
                        if(a.equals("GSL")){
                            return;
                        }
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('I', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('I',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('I', a);
                            break;
                        }else{
                            break;  
                        }
                    case 10:
                        sPrint("\nHow many seats do you want? ");
                        sel = previewSeats('J', a);
                        if(sel == 0){
                            sPrint("\nHow many seats do you want? ");
                            nSt = menu.nextInt();
                            Select('J',nSt,a);
                            break;
                        }else if (sel == 1){
                            BuyAll('J', a);
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
                menu.nextLine();
            }
        }
    }

    public static void reserveSeat() {
        boolean MENU = true;
        Scanner menu = new Scanner(System.in);
        while (MENU) {
            sPrint("\n===UPRM Baseball Stadium Seat Manager===");
            sPrint("\nPlease Select a Seat Level: ");
            sPrint("1. Field Level" + " (Available Seats: " + fieldLevel.size() + ")"
                    + "\n2. Main Level" + " (Available Seats: " + mainLevel.size() + ")"
                    + "\n3. Grandstand Level" + " (Available Seats: " + grandStandLevel.size() + ")"
                    + "\n4. Return");
            //
            try {
                sPrint("Enter Option Number: ");
                int input = menu.nextInt();
                switch (input) {
                    case 1:
                        Reservation("FL");
                        // Aqui method para add client
                        break;
                    case 2:
                        Reservation("ML");
                        break;
                    case 3:
                        Reservation("GSL");
                        break;
                    case 4:
                        return;
                    default:
                        continue;
                }
            } catch (InputMismatchException e) {
                sPrint("Invalid Input");
                menu.nextLine();
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
    
    // public static void showWaitingList() { // TODO : showWaitingList
    //     Queue<Client> copy = new LinkedList<>();
    //     Scanner exit = new Scanner(System.in);
    //     boolean show = true;

    //     Client Frank = new Client("Frank", "a", "b");
    //     Client Tom = new Client("Tom", "a", "b");
    //     Client John = new Client("John", "a", "b");

    //     sPrint("Press [E] to Exit...");

    //     copy.add(Frank);
    //     copy.add(Tom);
    //     copy.add(John);
    //     while(show){            
    //         for (int i = 0; i < wl_Size; i++) {
    //             clientPrint(copy.poll());
    //         }
    //     }
    //     exit.nextLine();
    // }


    // Hacemos algo tipo putty?
    public static void main(String[] args) {
        
        // Field Level
        secFL.put('A', FLA);
        secFL.put('B', FLB);
        secFL.put('C', FLC);
        secFL.put('D', FLD);
        secFL.put('E', FLE);

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

        secGSL.put('A', GSLA);
        secGSL.put('B', GSLB);
        secGSL.put('C', GSLC);
        secGSL.put('D', GSLD);
        secGSL.put('E', GSLE);
        secGSL.put('F', GSLF);
        secGSL.put('G', GSLG);
        secGSL.put('H', GSLH);

        Scanner menu = new Scanner(System.in);
        boolean AddC = false;
        boolean MENU = true;

        createSeats();

        while (MENU) {
            sPrint("===UPRM Baseball Stadium Seat Manager===");
            sPrint("\nPlease Select an Option: ");
            sPrint("1. Reserve Seat"
                    + "\n2. Cancel a Reservation"
                    + "\n3. Reservation History"
                    + "\n4. Undo Previous Reservation"
                    + "\n5. Wait List"
                    + "\n6. Close App");

            try {
                sPrint("Enter Option Number: ");
                int input = menu.nextInt();
                switch (input) {
                    case 1: // Reserve Seat Client
                        AddC = true;
                        client = addClient();
                        reserveSeat();
                        // Aqui method para add client
                        break;
                    case 2: // Cancel a Reservation
                
                        break;
                    case 3: // Show Reservation History
                        Transaction.printTransactionLinkedList(transRegister, menu);
                        break;
                    case 4: // Undo Previous Reservation

                        break;
                    case 5: // Wait
                        // showWaitingList();
                        break;
                    case 6:
                        sPrint("Closing Program...");
                        menu.close();
                        return;
                    default:
                        continue;

                }
            } catch (InputMismatchException e) {
                sPrint("Invalid Input");
                menu.nextLine();
            }
        }

        // THIS IS AN ATTEMPT, WILL TRY NEW WAY
        // This asks operator to open or close the app
        /*
         * boolean runflag = false;
         * while (!runflag) {
         * 
         * System.out.
         * println("Do you want to turn on the program? 1/0 \n1 for yes \n0 for no");
         * try {
         * int input = scanner.nextInt();
         * if (input == 1) { //if 1 open
         * runflag = true;
         * scanner.nextLine();
         * 
         * } else if (input == 0) { //if 0 close
         * System.out.println("Closing...");
         * scanner.close();
         * return;
         * } else { //if not 1 or 0 we ask again
         * System.out.println("Please input either 1 or 0.");
         * scanner.nextLine();
         * }
         * } catch (InputMismatchException e) { //if somehow something else is inputed
         * we have the exception ready
         * System.out.println("Invalid input. Please enter either 1 or 0.");
         * scanner.nextLine();
         * }
         * }
         * String state = "MAIN"; //Esto es un place holder en caso que hagamos idea de
         * switch cases
         * 
         * String Cname = ""; //variables para crear y añadir cliente
         * String email = "";
         * String num = "";
         * try {
         * System.out.println("==== Welcome to the UPRM Baseball Stadium! ====");
         * 
         * 
         * System.out.println("\nPlease enter the client's name: ");
         * Cname = scanner.nextLine();
         * 
         * System.out.println("\nPlease enter the client's email: ");
         * email = scanner.nextLine();
         * 
         * System.out.println("\nPlease enter the client's phone number: ");
         * num = scanner.nextLine();
         * 
         * }
         * catch(InputMismatchException e){
         * System.out.println("Please input the correct information");
         * }
         * Client client = new Client(Cname,email,num);
         * //ONE WAY OF DOING THINGS
         */

    }
}
