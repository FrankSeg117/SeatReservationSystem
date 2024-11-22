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

    public static boolean validEmail(String email){

        if(email.contains("@") && email.contains(".")){
            return true;
        }
        System.out.println("Invalid email address.");
        return false;
    }

}
