package ProyectMain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Transaction {

    private int seatAmount;
    private Client client;
    private ArrayList<Seat> rseats;
    private int money;
    private String transactionType; // Tipo de transaction: Reservacion, Cancelación, Deshacer
    private String level;

    public Transaction(Client client, ArrayList<Seat> reservedSeats, int money, String transactionType) {
        this.seatAmount = reservedSeats.size();
        this.client = client;
        this.rseats = reservedSeats;
        this.money = money;
        this.transactionType = transactionType;
        this.level = "Main Level";
    }

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

    // Setters
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

    public static void printTransactionLinkedList(LinkedList<Transaction> list, Scanner scanner) {
        System.out.println("===== Transaction History =====");
        System.out.println("\nCurrent transaction history: ");
        boolean exit = false;
        if (list.isEmpty()) {
            System.out.println("There are no previous Transactions.\n");
            Stadium.waitTime(2000);
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\nTransaction Number: " + (i + 1) + " {");
            System.out.println(list.get(i).toString());
        }
        while (!exit) {
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

    @Override
    public String toString() {
        StringBuilder seatsInfo = new StringBuilder();
        seatsInfo.append("" + "Section " + rseats.get(0).getSection() + ": ");

        for (Seat seat : rseats) {
            seatsInfo.append(seat.getSeatNumber()).append(", ");
        }

        return "\n - Client ID: " + client.getEmail() +
                "\n - Amount of Seats: " + seatAmount +
                "\n - ID Number of Individual Seats Bought: \n[ " + seatsInfo.toString().trim() + " ]" +
                "\n - Transaction Cost: $" + money +
                "\n - Type of transaction was: '" + transactionType + '\'' +
                '}';

    }
}
