package ProyectMain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;


/* This class handles the object Transaction, which stores in its attributes the details of any transaction made in the system.
 * It stores the seatAmount, Client, ArrayList of seats used, transaction cost, level (if field, main, or grand stand level),
 * and the type of transaction
 * This class also holds a stack for transactions, used for the undos of transactions which will make the opposite of the
 * stored operation
 * This class made it easier to hold said values and access them easier without creating more clutter in the code.
 */

public class Transaction {

    private int seatAmount; //seat amount which is the size of rseats
    private Client client; //Client that made the transaction
    private ArrayList<Seat> rseats; //Arraylist of seats which will make it easier to check which seats are needed with random access
    private int money; //the price, or money transfered in the transaction
    private String transactionType; //Transaction type will either be "Reservation"  or "Cancelation"
    private String level; //Levels will be either "FieldLevel", "MainLevel" or "GrandStandLevel"

    //Stack of transactions, used for the Undo the most recent transaction
    public static Stack<Transaction> undoStack = new Stack<>();

    //Transaction constructor
    public Transaction(Client client, ArrayList<Seat> reservedSeats, int money, String transactionType, String level) {
        this.seatAmount = reservedSeats.size();
        this.client = client;
        this.rseats = reservedSeats;
        this.money = money;
        this.transactionType = transactionType;
        this.level = level;
    }

    // Getters
    public int getSeatAmount() {
        return seatAmount;
    }

    public Client getClient() {
        return client;
    }

    public ArrayList<Seat> getRseats() {
        return rseats;
    }

    public int getMoney() {
        return money;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getLevel(){
        return level;
    }

    // Setters this getters exist for any possible debug need
    public void setSeatAmount(int seatAmount) {
        this.seatAmount = seatAmount;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public void setRseats(ArrayList<Seat> rseats) {
        this.rseats = rseats;
    }
    public void addRseats(Seat e) {
        this.rseats.add(e);
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public void addMoney(int money) {
        this.money += money;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


    /* This method is called in the main menu for the printing of all recent transactions if any
     * It uses a toString method below to transform any transaction object to a easily readable string
     * which allows to see the key attributes of the transaction
     * It takes as parameter a scanner to check when to return to main menu and the linkedlist that stores all transaction
     * objects generated.
     * This stores a log of every Reservation, Cancelation either they are an Undo or first timer.
     */
    public static void printTransactionLinkedList(LinkedList<Transaction> transactionLinkedList, Scanner scanner) {
        System.out.println("===== Transaction History =====");
        System.out.println("\nCurrent transaction history: ");
        boolean exit = false;
        if (transactionLinkedList.isEmpty()) { //If there are no previous transactions we return to main menu
            System.out.println("There are no previous Transactions.\n");
            Stadium.waitTime(2000);
            return;
        }
        for (int i = 0; i < transactionLinkedList.size(); i++) { //Loop to print every transaction inside the linkedlist
            System.out.println(transactionLinkedList.size());
            System.out.println("\nTransaction Number: " + (i + 1) + " {");
            System.out.println(transactionLinkedList.get(i).toString());
        }
        while (!exit) { //While loop to keep displaying the log until the key '0' is pressed to return back to main menu
            System.out.println("\nPress 0 to exit:");
            if (scanner.hasNextInt()) { // Check if the input is an integer
                int input = scanner.nextInt();
                if (input == 0)
                    exit = true;
            } else {
                System.out.println("Invalid input. Please input a number.");
                scanner.next(); // Consume the invalid input
            }
        }
        return;
    }


    /* This method overrides toString() in order to implement our own for the transaction object
     * Its used on the method that prints everything on the transaction linked list so that every detail
     * can be easily visualized on screen 
     */
    @Override
    public String toString() {
        StringBuilder seatsInfo = new StringBuilder();
        if(rseats.isEmpty()){
            System.out.println("Array empty");
            return null;
        }
        seatsInfo.append(this.getLevel() + "/Section " + rseats.get(0).getSection() + ": ");

        for (Seat seat : rseats) {
            seatsInfo.append(seat.getSeatNumber()).append(", ");
        }

        return    " - Client Name: " + client.getName() + "\n - Client ID: " + client.getEmail() +
                "\n - Amount of Seats: " + seatAmount +
                "\n - ID Number of Individual Seats Bought: \n[ " + seatsInfo.toString().trim() + " ]" +
                "\n - Transaction Cost: $" + money +
                "\n - Type of transaction was: '" + transactionType + '\'' +
                '}';

    }

    /* This method undos the last transaction stored on the transaction stack, which depending on the type of transaction
     * it will do the opposite action to revert past changes.
     * It will ideally let you go back to the initial state of the system unless new operations are made.
     * If there are no transactions in the stack it will return.
     */
    public static void undoLastTransaction(){
        if(undoStack.isEmpty()){ //If the stack is empty we give a warning on screen and return
            Stadium.sPrint("There are no previous transactions to undo.");
            Stadium.waitTime(2000);
            return;
        }
        Transaction undo = undoStack.pop(); //Else, we pop the most recent transaction and store it in a variable
        HashMap<String, HashMap<Client,ArrayList<Seat>>> undoDict = new HashMap<>(); //Put dictionaries inside a dictionray for ease of access
        undoDict.put("FieldLevel", Stadium.FLseats);
        undoDict.put("MainLevel", Stadium.MLseats);
        undoDict.put("GrandStandLevel", Stadium.GSLseats);
        if(undo.transactionType.equals("Reservation")){ //If prev transaction was a reservation we do the opposite (cancelation)
            Stadium.cancelContinuation(undo.getLevel(), undo.getClient(), true, undo.getRseats());    
        }else{ //If previous transaction was a cancelation, we do the opposite (Buy)
            Stadium.Buy(undoDict.get(undo.getLevel()).get(undo.getClient()), undo.getRseats().get(0).getSection(), undo.getLevel(), undo.getRseats(),true);
        } 
    }
}
