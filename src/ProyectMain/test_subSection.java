package ProyectMain;
import java.util.ArrayList;
import java.util.Scanner;

class test{

    public static void print(String a){
        System.out.println(a);
    }

    public static void main(String[] args){
        ArrayList<String> sections = new ArrayList<>();
        ArrayList<String> subSections = new ArrayList<>();
        String currSection = "";
        Scanner sc = new Scanner(System.in);

        sections.add("F");
        sections.add("M");
        sections.add("G");

        subSections.add("a");
        subSections.add("b");
        subSections.add("c");
        subSections.add("d");
        subSections.add("e");
        subSections.add("f");
        subSections.add("g");
        subSections.add("h");

        

        print(currSection);
        sc.close();
    }
}