package ProyectMain;

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

    public String getPhone(){
        return phonenum;
    }

}
