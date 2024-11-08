package ProyectMain;

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


}
