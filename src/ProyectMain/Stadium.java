package ProyectMain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Queue;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Stadium {

    ////////In progress////////
    public static Set<Seat> fieldLevel = new HashSet<>();

    public static Set<Seat> mainLevel = new HashSet<>();

    public static Set<Seat> grandStandLevel = new HashSet<>();

    public LinkedList<String> log = new LinkedList<>(); //placeholder, may need to create a class for transactions

    public HashMap<Client,Seat> Clseats = new HashMap<>(); //Structure to store Client in their seats

    public Stack<String> actions = new Stack<>(); //Para implementar la funcionalidad de deshacer, en la que se puede deshacer la última acción de reservación/cancelación

    public Queue<Client> waitlist = new LinkedList<>(); //Para implementar la funcionalidad de deshacer, en la que se puede deshacer la última acción de reservación/cancelación

    ///////////////////////////
    public static Client addClient(){
        String Cname = ""; //variables para crear y añadir cliente
        String email = "";
        String num = "";
        Scanner menu = new Scanner(System.in);
        try {
            System.out.println("==== Adding Client ====");

            
            System.out.println("\nPlease enter the client's name: ");
            Cname = menu.nextLine();

            System.out.println("\nPlease enter the client's email: ");
            email = menu.nextLine();

            System.out.println("\nPlease enter the client's phone number: ");
            num = menu.nextLine();
        
        }
        catch(InputMismatchException e){
            System.out.println("Please input the correct information");
        }

        return new Client(Cname,email,num);
    }

    public static void reserveSeat(){
     
    }

    public static void createSeats(){
        Seat seat;
        int row = 1;
        int sec = 0;
        char alpha = 'A';
        for(int i = 1; i<501; i++){
            alpha = (char) ((int)'A' + sec);
            seat = new Seat(alpha,row,i);
            fieldLevel.add(seat);
            if(i%25 == 0){
                row++;
            }
            if(i%100==0){
                sec++;
                row = 1;
            }
        }

        row = 1;
        sec = 0;
        alpha = 'A';
        for(int i = 1; i < 1001; i++){
            alpha = (char) ((int)'A' + sec);
            seat = new Seat(alpha,row,i);
            mainLevel.add(seat);
            if(i%25 == 0){
                row++;
            }
            if(i%100==0){
                sec++;
                row = 1;
            }
        }

        row = 1;
        sec = 0;
        alpha = 'A';
        for(int i = 1; i < 2001; i++){
            alpha = (char) ((int)'A' + sec);
            seat = new Seat(alpha,row,i);
            grandStandLevel.add(seat);
            if(i%25 == 0){
                row++;
            }
            if(i%250==0){
                sec++;
                row = 1;
            }
        }
    }
    //Hacemos algo tipo putty?
    public static void main(String [] args) {  
        Scanner menu = new Scanner(System.in);
        boolean AddC = false;
        boolean MENU = true;
        
        createSeats();

        while(MENU){
            System.out.println("===UPRM Baseball Stadium Seat Manager===");
            System.out.println("\nPlease Select an Option: ");
            System.out.println("1. Reserve Seat" 
            + "\n2. Cancel a Reservation"
            + "\n3. Reservation History"
            + "\n4. Undo Previous Reservation"
            + "\n5. Wait List"
            + "\n6. Close App");

            try{
                System.out.println("Enter Option Number: ");
                int input = menu.nextInt();
                switch (input) {
                    case 1:
                        AddC = true;
                        Client client  = addClient();
                        reserveSeat();
                    //Aqui method para add client
                        break;
                    case 2:
                
                        break;
                    case 3:
                        
                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:
                        System.out.println("Closing Program...");
                        return;
                    default:
                        continue;
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Invalid Input");
                menu.nextLine();
            }
        }
















        
    //THIS IS AN ATTEMPT, WILL TRY NEW WAY        
        //This asks operator to open or close the app
        /*boolean runflag = false;
        while (!runflag) {
            
            System.out.println("Do you want to turn on the program? 1/0 \n1 for yes \n0 for no");
            try {
                int input = scanner.nextInt();
                if (input == 1) { //if 1 open
                    runflag = true;
                    scanner.nextLine(); 

                } else if (input == 0) { //if 0 close
                    System.out.println("Closing...");
                    scanner.close(); 
                    return;
                } else {    //if not 1 or 0 we ask again
                    System.out.println("Please input either 1 or 0.");
                    scanner.nextLine(); 
                }
            } catch (InputMismatchException e) { //if somehow something else is inputed we have the exception ready
                System.out.println("Invalid input. Please enter either 1 or 0.");
                scanner.nextLine(); 
            }
        }
        String state = "MAIN"; //Esto es un place holder en caso que hagamos idea de switch cases 

        String Cname = ""; //variables para crear y añadir cliente
        String email = "";
        String num = "";
        try {
            System.out.println("==== Welcome to the UPRM Baseball Stadium! ====");

            
            System.out.println("\nPlease enter the client's name: ");
            Cname = scanner.nextLine();

            System.out.println("\nPlease enter the client's email: ");
            email = scanner.nextLine();

            System.out.println("\nPlease enter the client's phone number: ");
            num = scanner.nextLine();
           
        }
        catch(InputMismatchException e){
            System.out.println("Please input the correct information");
        }
        Client client  = new Client(Cname,email,num);
//ONE WAY OF DOING THINGS
*/

    }
}
