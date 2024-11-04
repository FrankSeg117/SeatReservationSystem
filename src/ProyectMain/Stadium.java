package ProyectMain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Queue;

public class Stadium {

    ////////In progress

    public Set<Seat> seats = new HashSet<>(); //Set that stores all seats

    public LinkedList<String> log = new LinkedList<>(); //placeholder, may need to create a class for transactions

    public HashMap<Client,Seat> Clseats = new HashMap<>(); //Structure to store Client in their seats

    public Stack<String> actions = new Stack<>(); //Para implementar la funcionalidad de deshacer, en la que se puede deshacer la última acción de reservación/cancelación

    public Queue<Client> waitlist = new LinkedList<>(); //Para implementar la funcionalidad de deshacer, en la que se puede deshacer la última acción de reservación/cancelación

    ////////
}
