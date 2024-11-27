package ProyectMain;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

/* This class handles one of the main objects, which are clients. 
 * Each have their own attributes of name, email, and phone number. The last two are used as the key identifiers
 * to check if a client exists either when making a new reservation or canceling one. These identifiers are used since
 * they will be unique for every person, while names can be duplicated.
 */

public class Client {
    public String name;
    public String email;
    public String phonenum;

    //Constructor
    public Client (String name, String em, String pn){
        this.name = name;
        this.email = em;
        this.phonenum = pn;
    }

    //Getters
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getNumber(){
        return phonenum;
    }
    public String getPhone(){
        return phonenum;
    }

    /*This method receives a string email and returns a boolean of whether is it valid or not (true or flase)
     * An email will be valid if it contains and '@' and a '.' somewhere after it, so we can confirm a domain was placed correctly
     * We did not make flags for certain domains, since they can vary from country to country and people.
     */
    public static boolean validEmail(String email){
        int at = 0;
        int dot = 0;
        //Check if email contains '@' and '.'
        if(email.contains("@") && email.contains(".")){
            at = email.lastIndexOf("@"); //Store index of where '@' is
            dot = email.lastIndexOf("."); //Store index of where '.' is
            if(at < dot && email.length()-dot >= 2){ //Check whether the '.' is after the '@' by comparing the indexes
                return true;
            }
        }
        System.out.println("\nInvalid email address.");
        if(!email.contains("@")){
            System.out.println("\nEmail does not contain @.");
        }
        if(!email.contains(".") || at > dot || email.length()-dot < 2){
            System.out.println("\nEmail does not contain domain.");
        }
        return false;
    }

    /*This method receives a string phone and returns it without the characters '-' otherwise called hyphen
     * It is used when creating a new client where it will check if the number inputted by the operator contains hyphens
     */
    public static String removeHyphen(String phone){
        if(phone.contains("-")){ //if phone contains '-' we remove all appearences from the string
            return phone.replaceAll("-", ""); 
        }
        return phone;
    }

    /* This method returns a boolean indicating if the string phone is valid depending on the length of the string
     * and if each of the characters inside it is a digit
     * its used on the methods that receive a client's information to validate if said input is correct
     */
    public static boolean validPhoneNumber(String phone){
        if(phone.length() == 10){ //First confirm length of the string greater than 10
            for(Character character: phone.toCharArray()){ //For each character in the string we check if its a digit
                if(Character.isDigit(character)){
                    continue;
                }
                return false;
            }
            return true;
        }
        System.out.println("\nInvalid phone number."); //If string contains something that is not a digit or was below length 10
        if(phone.length() != 10){                        //Will return false
            System.out.println("\nPhone number must be of length 10.");
        }
        return false;
    }

    /* This method receives an identifier which can be either a phonenumber or email and the three main keysets of the 
     * dictionaries in Stadium where Clients are paired with ArrayLists of Seats to check if a client with said identifier
     * is already present on the system.
     * This is used when entering a new client when reserving and when doing a cancelation, where we must confirm if a client with
     * the provided identifier exists so we can do the solicited operations
     */
    public static Boolean checkClientIsInSystem(String identifier, Set<Client> FL, Set<Client> ML, Set<Client>GL){
        Set<Client> allClients = new HashSet<>(); //We add all clients present in the system to this set
        allClients.addAll(FL);
        allClients.addAll(ML);
        allClients.addAll(GL);
        for(Client c : allClients){ 
            //If any of the emails or phone numbers of the clients in the system matches the provided identifier we return true
            if(c.getEmail().equals(identifier) || c.getNumber().equals(identifier)){
                return true;
            }
        }
        return false;
    }

    /* This method returns a client object that matches the identifier passed
     * Used in conjunction with the previous method checkClientIsInSystem so once we know it exists, we get the client
     * and return it so whatver operation if reservation or cancelation has the info on which chairs they must remove
     */
    public static Client getClientInSystem(String identifier, Set<Client> FL, Set<Client> ML, Set<Client>GL){
        Set<Client> allClients = new HashSet<>();
        allClients.addAll(FL);
        allClients.addAll(ML);
        allClients.addAll(GL);
        for(Client c : allClients){
            if(c.getEmail().equals(identifier) || c.getNumber().equals(identifier)){
                return c;
            }
        }
        return null;
    }

    //This method prints key attributes of a client c passed in the parameter. Used for debug purposes
    public static void clientPrint(Client c) {
        System.out.println("Client: " + c.getName() + ", Email: " + c.getEmail() + ", PhoneNum: " + c.getPhone());
    }

    /* The method addClient return a new client object and receives a scanner and it will ask for the clients important 
     * information so it can be properly added to the system
     * It's used when reserving a new seat where we need to add a new client on the system.
     * It also checks if the client passed exists already on the system
     */
    public static Client addClient(Scanner scanner) {
        String Cname = ""; // variables to create and return a client
        String email = "";
        String num = "";
        
        try {
            Stadium.sPrint("==== Client Information ====");

            Stadium.sPrint("\nPlease enter the client's name: ");
            Cname = scanner.nextLine();
            
            Stadium.sPrint("\nPlease enter the client's email: ");
            email = scanner.nextLine();

            while(!Client.validEmail(email)){ //if email passed does not meet requirements we keep asking until its inputed properly
                Stadium.sPrint("\nPlease enter the client's email: ");
                email = scanner.nextLine();
            }

            Stadium.sPrint("\nPlease enter the client's phone number: ");
            num = scanner.nextLine();
            num = Client.removeHyphen(num); //The input number has hyphens removed if any are present
            while(!Client.validPhoneNumber(num)){
                Stadium.sPrint("\nPlease enter the client's phone number: ");
                num = scanner.nextLine();
                num = Client.removeHyphen(num);
            }


        } catch (InputMismatchException e) {
            Stadium.sPrint("Please input the correct information");
        }
        //Check if client is present on the system return the existing client
        if(!Stadium.FLseats.isEmpty() || !Stadium.MLseats.isEmpty() || !Stadium.GSLseats.isEmpty()){
            if(Stadium.isInSystem(email) || Stadium.isInSystem(num)){
                Stadium.sPrint("\nClient is already on the system.");
                Stadium.waitTime(2000);
                return Stadium.getInSystem(email);
            }
        }
        return new Client(Cname, email, num);
    }
    
}
