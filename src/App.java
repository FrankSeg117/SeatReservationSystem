import ProyectMain.Menu;
import ProyectMain.Stadium;

/* Main File for the Seat Reservation System for the UPRM Baseball Stadium
 * by Frank A. Seguí, Jonathan González and Tomás Gómez
 * Project for the Data Structures Course in Fall 2
 */

public class App {

    /* main app run */
    public static void main(String[] args) {
        
        Stadium.fLevelCreate();
        Stadium.mLevelCreate();
        Stadium.gsLevelCreate();

        Stadium.createSeats();

        Menu.mainMenu();
    }
}
