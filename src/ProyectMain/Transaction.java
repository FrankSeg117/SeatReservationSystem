package ProyectMain;

import java.util.ArrayList;

public class Transaction {

    private int seatAmount;
    private Client client;
    private ArrayList<Seat> rseats;
    private int money;
    private String transactionType; //Tipo de transaction: Reservacion, Cancelación, Deshacer

    public Transaction(int seatAmount, Client client, ArrayList<Seat> rseats, int money, String transactionType) {
        this.seatAmount = seatAmount;
        this.client = client;
        this.rseats = rseats;
        this.money = money;
        this.transactionType = transactionType;
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
    public void addRseats(Seat e){
        this.rseats.add(e);
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public void addMoney(int money){
        this.money += money;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        StringBuilder seatsInfo = new StringBuilder();
        for (Seat seat : rseats) {
            seatsInfo.append("Seat ").append(seat.getSeatNumber()).append(" ");
        }
        
        return "Transaction{" +
                "seatAmount=" + seatAmount +
                ", client=" + client.getName() +
                ", seats=[" + seatsInfo.toString().trim() + "]" +
                ", money=" + money +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
