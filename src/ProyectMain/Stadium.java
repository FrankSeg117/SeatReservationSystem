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

    public Set<Seat> seats = new HashSet<>(); //Set that stores all seats

    public LinkedList<String> log = new LinkedList<>(); //placeholder, may need to create a class for transactions

    public HashMap<Client,Seat> Clseats = new HashMap<>(); //Structure to store Client in their seats

    public Stack<String> actions = new Stack<>(); //Para implementar la funcionalidad de deshacer, en la que se puede deshacer la última acción de reservación/cancelación

    public Queue<Client> waitlist = new LinkedList<>(); //Para implementar la funcionalidad de deshacer, en la que se puede deshacer la última acción de reservación/cancelación

    ///////////////////////////

    //Hacemos algo tipo putty?
    public static void main(String [] args) {  
        Scanner scanner = new Scanner(System.in);
    
        



















        
    //THIS IS AN ATTEMPT, WILL TRY NEW WAY        
        //This asks operator to open or close the app
        boolean runflag = false;
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


    }
}
