package ProyectMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
// import java.util.Iterator;
import java.util.Comparator;

// import javax.print.FlavorException;

import java.util.Queue;

import java.util.Scanner;
import java.util.InputMismatchException;

import java.lang.Thread;

public class Stadium {
    /*
     * Sets
     */

    public static Set<Seat> fieldLevel = new HashSet<>();
    public static Set<Seat> FLA = new HashSet<>();
    public static Set<Seat> FLB = new HashSet<>();
    public static Set<Seat> FLC = new HashSet<>();
    public static Set<Seat> FLD = new HashSet<>();
    public static Set<Seat> FLE = new HashSet<>();
    public static Set<Seat> mainLevel = new HashSet<>();
    public static Set<Seat> grandStandLevel = new HashSet<>();

    /*
     * Linked Lists
     */

    public LinkedList<Transaction> Tregister = new LinkedList<>(); // placeholder, may need to create a class for transactions

    /*
     * Hash Maps
     */

    public static HashMap<Client, Seat> FLseats = new HashMap<>(); // Structure to store Client in field level seats
    public HashMap<Client, Seat> MLseats = new HashMap<>(); // Structure to store Client in main level seats
    public HashMap<Client, Seat> GSseats = new HashMap<>(); // Structure to store Client in grandstand level seats
    public static HashMap<Character, Set<Seat>> secFL = new HashMap<>();

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
    public static int allFL = 0;
    public static int secAFL = 0;
    public static int secBFL = 0;
    public static int secCFL = 0;
    public static int secDFL = 0;
    public static int secEFL = 0;

    public static boolean buy = false;
    public static boolean show = false;

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

    public void AssignClientTOSeat(Client client, char section, String level) {
        if (level.equals("FIELD")) {
            Seat s = null;
            for (Seat seat : fieldLevel) {
                if (seat.getSection() == section /* and is not taken */) {

                }
            }
            FLseats.put(client, s);
        }
    }

    public static void Select(Character sec, int NofS) {
        Scanner SelectMenu = new Scanner(System.in);

        boolean avl = false;

        int SN = 0; 
        
        System.out.println("Available Seats:");

        ArrayList<Seat> secA = new ArrayList<>();
        for (Seat a : fieldLevel) {
            if (a.getSection() == sec) {
                secA.add(a);
            }
        }
        secA.sort(Comparator.comparingInt(Seat::getSeatNumber));
        
        ArrayList<Seat> clientA = new ArrayList<>();

        for(int k = 0; k<NofS; k++){
            for (int i = 0; i < secA.size(); i++) {
                System.out.print(secA.get(i).getSeatNumber() + " ");
                if(i != secA.size()-1){
                    if(secA.get(i+1).getSeatNumber() - secA.get(i).getSeatNumber() > 1){
                        for(int j = 1; secA.get(i+1).getSeatNumber() - secA.get(i).getSeatNumber()>j; j++){
                            System.out.print("   ");
                        }
                    }
                }
                if (secA.get(i).getSeatNumber() % 25 == 0) {
                    System.out.print("\n");
                }
            }
            try {
                System.out.println("\n==== Choose Seat ====");

                System.out.println("\nSeat Number: ");

                SN = SelectMenu.nextInt();

                for (Seat a : fieldLevel) {
                    if (a.getSection() == sec && a.getSeatNumber() == SN) {
                        avl = true;
                    }
                }

                if(!avl){
                    sPrint("\nSeat is not available.");
                    sPrint("\nTry again.");
                    waitTime(2000);

                    k--;
                }
                if(avl){
                    for (Seat a : fieldLevel) {
                        if (a.getSection() == sec && a.getSeatNumber() == SN) {
                            clientA.add(a);
                        }
                    }
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Please input the correct information");
            }
        }
        BuyFL(clientA, sec);

    }

    public static void BuyFL(ArrayList<Seat> Seat, Character sec) {
        Scanner BuyMenu = new Scanner(System.in);

        String conf = "";
        try {
            System.out.println("==== Payment ====");

            System.out.println("\nTotal Cost: $" + 300*Seat.size());

            System.out.println("\nDo you wish to confirm your payment?");

            System.out.println("\nYes or No (Y/N):");

            conf = BuyMenu.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Please input the correct information");
        }

        if (conf.toLowerCase().equals("y")) {
            System.out.println("\nTransaction Completed.");

            System.out.println("\nReturrning Back...");

            for (Seat a : fieldLevel) {
                for(Seat b: Seat){
                    if (a.getSection() == sec && a.getSeatNumber() == b.getSeatNumber()) {
                        FLseats.put(client, a);
                        fieldLevel.remove(a);
                        secFL.get(sec).add(a);
                        break;
                    }
                }
            }

            waitTime(2000);
            return;
        }
        System.out.println("\nReturrning Back...");

        waitTime(200);
    }

    public static Client addClient() {
        String Cname = ""; // variables para crear y añadir cliente
        String email = "";
        String num = "";
        Scanner AddClientMenu = new Scanner(System.in);
        try {
            System.out.println("==== Client Information ====");

            System.out.println("\nPlease enter the client's name: ");
            Cname = AddClientMenu.nextLine();

            System.out.println("\nPlease enter the client's email: ");
            email = AddClientMenu.nextLine();

            System.out.println("\nPlease enter the client's phone number: ");
            num = AddClientMenu.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Please input the correct information");
        }
        // AddClientMenu.close();
        return new Client(Cname, email, num);
    }

    public static void fieldLevelFun() {
        boolean MENU = true;
        Scanner menu = new Scanner(System.in);
        while (MENU) {
            System.out.println("\n===UPRM Baseball Stadium Seat Manager===");
            System.out.println("\nPlease Select a Section: ");
            System.out.println("1. Section A (Available Seats: " + (100 - secFL.get('A').size()) + ")"
                    + "\n2. Section B (Available Seats: " + (100 - secFL.get('B').size()) + ")"
                    + "\n3. Section C (Available Seats: " + (100 - secFL.get('C').size()) + ")"
                    + "\n4. Section D (Available Seats: " + (100 - secFL.get('D').size()) + ")"
                    + "\n5. Section E (Available Seats: " + (100 - secFL.get('E').size()) + ")"
                    + "\n6. Return");

            try {
                System.out.println("Enter Option Number: ");
                int input = menu.nextInt();
                int nSt;
                switch (input) {
                    case 1:
                        // Aqui method para add client
                        sPrint("\nHow many seats do you want? ");
                        nSt = menu.nextInt();
                        Select('A',nSt);

                        break;
                    case 2:
                        sPrint("\nHow many seats do you want? ");
                        nSt = menu.nextInt();
                        Select('B', nSt);
                        break;
                    case 3:
                        sPrint("\nHow many seats do you want? ");
                        nSt = menu.nextInt();
                        Select('C', nSt);
                        break;
                    case 4:
                        sPrint("\nHow many seats do you want? ");
                        nSt = menu.nextInt();
                        Select('D', nSt);
                        break;
                    case 5:
                        sPrint("\nHow many seats do you want? ");
                        nSt = menu.nextInt();
                        Select('E', nSt);
                        break;
                    case 6:
                        return;
                    default:
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
                menu.nextLine();
            }
        }
        // menu.close();
    }

    public static void reserveSeat() {
        boolean MENU = true;
        Scanner menu = new Scanner(System.in);
        while (MENU) {
            System.out.println("\n===UPRM Baseball Stadium Seat Manager===");
            System.out.println("\nPlease Select a Seat Level: ");
            System.out.println("1. Field Level" + " (Available Seats: " + fieldLevel.size() + ")"
                    + "\n2. Main Level" + " (Available Seats: " + mainLevel.size() + ")"
                    + "\n3. Grandstand Level" + " (Available Seats: " + grandStandLevel.size() + ")"
                    + "\n4. Return");
            //
            try {
                System.out.println("Enter Option Number: ");
                int input = menu.nextInt();
                switch (input) {
                    case 1:
                        fieldLevelFun();
                        // Aqui method para add client
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        return;
                    default:
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
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

    public static void showWaitingList() { // TODO : showWaitingList
        show = true;
        Queue<Client> copy = new LinkedList<>(waitlist);
        while(show){
            Client Frank = new Client("Frank", "a", "b");
            Client Tom = new Client("Tom", "a", "b");
            Client John = new Client("John", "a", "b");

            copy.add(Frank);
            copy.add(Tom);
            copy.add(John);
            
            for (int i = 0; i < wl_Size; i++) {
                clientPrint(copy.peek());
                copy.remove();
            }
            
        }
        show = false;
        show ? 
    }

    // Hacemos algo tipo putty?
    public static void main(String[] args) {
        secFL.put('A', FLA);
        secFL.put('B', FLB);
        secFL.put('C', FLC);
        secFL.put('D', FLD);
        secFL.put('E', FLE);

        Scanner menu = new Scanner(System.in);
        boolean AddC = false;
        boolean MENU = true;

        createSeats();

        while (MENU) {
            System.out.println("===UPRM Baseball Stadium Seat Manager===");
            System.out.println("\nPlease Select an Option: ");
            System.out.println("1. Reserve Seat"
                    + "\n2. Cancel a Reservation"
                    + "\n3. Reservation History"
                    + "\n4. Undo Previous Reservation"
                    + "\n5. Wait List"
                    + "\n6. Close App");

            try {
                System.out.println("Enter Option Number: ");
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
                    case 3: // Undo Previous Reservatioon

                        break;
                    case 4: // Undo PreviousReservation

                        break;
                    case 5: // Wait
                        // showWaitingList();
                        break;
                    case 6:
                        System.out.println("Closing Program...");
                        return;
                    default:
                        continue;

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
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
