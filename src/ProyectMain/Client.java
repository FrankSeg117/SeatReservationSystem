package ProyectMain;

import java.util.HashSet;
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

    public static Boolean clientIsInSystem(String identifier, Set<Client> FL, Set<Client> ML, Set<Client>GL){
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
    public static Client clientInSystem(String identifier, Set<Client> FL, Set<Client> ML, Set<Client>GL){
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
}
