package ProyectMain;

import java.util.ArrayList;

public class Seat {
    public char section;
    public int row;
    public int seatNumber;
     
    //Constructor
    public Seat (char sec, int r, int sN){
        this.section = sec;
        this.row = r;
        this.seatNumber = sN;
    }

    public char getSection() {
        return section;
    }
    public void setSection(char section) {
        this.section = section;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public static int seatsTotalPrice(ArrayList<Seat> seats, String level){
        int price = 0;
        if(level.equals("Field")){
            price = 300*seats.size();
        }
        if(level.equals("Main")){
            price = 120*seats.size();
        }
        if(level.equals("Grand")){
            price = 45*seats.size();
        }
        return price;
    }

}
