package ProyectMain;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
public class Client {
    public String name;
    public String email;
    public String phonenum;

    public Client (String name, String em, String pn){
        this.name = name;
        this.email = em;
        this.phonenum = pn;
    }

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

    public static boolean validEmail(String email){
        int at = 0;
        int dot = 0;
        
        if(email.contains("@") && email.contains(".")){
            at = email.lastIndexOf("@");
            dot = email.lastIndexOf(".");
            if(at < dot && email.length()-dot >= 2){
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

    public static String removeHyphen(String phone){
        if(phone.contains("-")){
            return phone.replaceAll("-", "");
        }
        return phone;
    }

    public static boolean validPhoneNumber(String phone){
        if(phone.length() == 10){
            for(Character character: phone.toCharArray()){
                if(Character.isDigit(character)){
                    continue;
                }
                return false;
            }

            return true;
        }
        System.out.println("\nInvalid phone number.");
        if(phone.length() != 10){
            System.out.println("\nPhone number must be of length 10.");
        }
        return false;
    }

    public static Boolean checkClientIsInSystem(String identifier, Set<Client> FL, Set<Client> ML, Set<Client>GL){
        Set<Client> allClients = new HashSet<>();
        allClients.addAll(FL);
        allClients.addAll(ML);
        allClients.addAll(GL);
        for(Client c : allClients){
            if(c.getEmail().equals(identifier) || c.getNumber().equals(identifier)){
                return true;
            }
        }
        return false;
    }
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

    public static void clientPrint(Client c) {
        System.out.println("Client: " + c.getName() + ", Email: " + c.getEmail() + ", PhoneNum: " + c.getPhone());
    }

    public static Client addClient(Scanner scanner) {
        String Cname = ""; // variables para crear y añadir cliente
        String email = "";
        String num = "";
        
        try {
            Stadium.sPrint("==== Client Information ====");

            Stadium.sPrint("\nPlease enter the client's name: ");
            Cname = scanner.nextLine();
            
            Stadium.sPrint("\nPlease enter the client's email: ");
            email = scanner.nextLine();

            while(!Client.validEmail(email)){
                Stadium.sPrint("\nPlease enter the client's email: ");
                email = scanner.nextLine();
            }

            Stadium.sPrint("\nPlease enter the client's phone number: ");
            num = scanner.nextLine();
            num = Client.removeHyphen(num);
            while(!Client.validPhoneNumber(num)){
                Stadium.sPrint("\nPlease enter the client's phone number: ");
                num = scanner.nextLine();
                num = Client.removeHyphen(num);
            }


        } catch (InputMismatchException e) {
            Stadium.sPrint("Please input the correct information");
        }
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
