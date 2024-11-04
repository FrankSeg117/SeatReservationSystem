package ProyectMain;

public class Seat {
    public String section;
    public int row;
    public int seatNumber;
     
    //Constructor
    public Seat (String sec, int r, int sN){
        this.section = sec;
        this.row = r;
        this.seatNumber = sN;
    }

    public String getSection() {
        return section;
    }
    public void setSection(String section) {
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
