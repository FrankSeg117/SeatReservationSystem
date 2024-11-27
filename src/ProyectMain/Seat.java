package ProyectMain;

import java.util.ArrayList;

/*The Seat class is one of the main objects that handles the information of the seats each level will have
 * It has section, row, and seat number. 
 * This attributes allow us to organize each level correctly and keep track of exactly which seats are in use by 
 * cheking their numbers and section
 * The row allows the user to see a map of where a particular seat will be located.
 */

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
    //Getters
    public char getSection() {
        return section;
    }
    public int getRow() {
        return row;
    }
    public int getSeatNumber() {
        return seatNumber;
    }

    /*This method returns the price of an ArrayList of seats depending on the level passed as parameter
     * This is used on stadium for certain methods where we want to print on screen the price of either returning money
     * or payments made in cancelations and reservations.
     */
    public static int seatsTotalPrice(ArrayList<Seat> seats, String level){
        int price = 0;
        //Depending on the level, the price will vary
        if(level.equals("FieldLevel")){
            price = 300*seats.size(); //300 per seat in field level
        }
        if(level.equals("MainLevel")){
            price = 120*seats.size(); //120 per seat in main level
        }
        if(level.equals("GrandStandLevel")){
            price = 45*seats.size(); //45 per seat in grand stand level
        }
        return price;
    }
}
