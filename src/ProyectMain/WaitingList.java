package ProyectMain;
import java.util.*;

public class WaitingList {

    public static void WaitList(){
        Stadium.sPrint("\\*Waiting list menu*\\\n");
        Stadium.sPrint("""
                       Please select an option:
                       1. Show Waiting List
                       2. Quit Waiting List
                       3. Exit""");

        try{
            Stadium.sPrint("Enter Option Number: ");
            int option = Stadium.scanner.nextInt();

            switch (option){
                case 1:
                    showWaitingList();
                    break;
                case 2:
                    WLDequeHelper();
                    break;
                case 3:
                    return;
            }

        } catch(InputMismatchException e){
            Stadium.sPrint("Invalid input");
            Stadium.scanner.nextLine();
        }
    }
    
    public static void showWaitingList() {
        Stadium.sPrint("\\*Select Waiting list to display*\\\n");
        Stadium.sPrint("""
                       Please select an option:
                       1. Main level Waiting List
                       2. Field level Waiting List
                       3. Grand Stand level Waiting List
                       4. Exit""");

        try{
            Stadium.sPrint("Enter Option Number: ");
            int option = Stadium.scanner.nextInt();

            switch (option){
                case 1 -> {
                    Stadium.sPrint("\n\\*Showing: Main Level WL*\\");
                    int i = 1;
                    for(Client c : Stadium.mainWaitList){
                        System.out.println("Position " + i + " -> " + c.getName());
                        i++;
                    }
                    Stadium.sPrint("");
                    Stadium.waitTime(5000);
                }
                    case 2 -> {
                        Stadium.sPrint("\n\\*Showing: Field Level WL*\\");
                        int j = 1;
                        for(Client c : Stadium.fieldWaitList){
                            System.out.println("Position " + j + " -> " + c.getName());
                            j++;
                        }
                        Stadium.sPrint("");
                        Stadium.waitTime(5000);
                }
                    case 3 -> {
                        Stadium.sPrint("\n\\*Showing: Grand Stand Level WL*\\");
                        int k = 1;
                        for(Client c : Stadium.grandWaitList){
                            System.out.println("Position " + k + " -> " + c.getName());
                            k++;
                        }
                        Stadium.sPrint("");
                        Stadium.waitTime(5000);
                }
                case 4 -> {
                    return;
                }
            }

        } catch(InputMismatchException e){
            Stadium.sPrint("Invalid input");
            Stadium.scanner.nextLine();
        }
    }

    public static void WLDequeHelper(){

        Stadium.sPrint("\nSelect WL to deque from");
        Stadium.sPrint("""
                       1. Main Level WL
                       2. Field level WL
                       3. Grand stand level WL
                       4. Cancel""");
        
        try{
            Stadium.sPrint("Enter Option Number: ");
            int option = Stadium.scanner.nextInt();

            switch (option){
                case 1 -> WaitingListDeque(Stadium.client, Stadium.mainWaitList);
                case 2 -> WaitingListDeque(Stadium.client, Stadium.fieldWaitList);
                case 3 -> WaitingListDeque(Stadium.client, Stadium.grandWaitList);
                case 4 -> {
                    return;
                }
            }

        } catch(InputMismatchException e){
            Stadium.sPrint("Invalid input");
            Stadium.scanner.nextLine();
        }
    }

    public static void WaitingListDeque(Client c, Queue<Client> levelWL){
        Queue<Client> temp = new LinkedList<>();

        while(!levelWL.isEmpty()){ // This traverses the queue to delete indicated client
            if(!levelWL.peek().equals(c)){
                temp.add(levelWL.poll());
            } else {
                levelWL.poll();
            }
        }

        if(!temp.contains(c)){Stadium.sPrint("Client deleted from Waiting List succesfully");}
        Stadium.waitTime(3000);

        while(!temp.isEmpty()){ // This makes the original queue return to its original state
            levelWL.add(temp.poll());
        }
    }

    public static void mainWaitListAdd(Client c){
        Stadium.mainWaitList.add(c);
    }

    public static void fieldWaitListAdd(Client c){
        Stadium.fieldWaitList.add(c);
    }

    public static void grandWaitListAdd(Client c){
        Stadium.grandWaitList.add(c);
    }

    public static void printQueue(Queue<Client> queue) {
        for (Client c : queue) {
            System.out.println(c.getName());
        }
    }
}
