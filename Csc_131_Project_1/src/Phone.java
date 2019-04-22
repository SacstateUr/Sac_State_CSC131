//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Phone {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner input = new Scanner(System.in);
        boolean TrackerSignal = false;
        String Code;
        String Location;
        System.out.println("Was a tracker signal recieved?(true/false)");
        TrackerSignal=input.nextBoolean();
        if(TrackerSignal)
        {
           System.out.println("What is the tracker ID");
           Code=input.next();
           if(new File("Database/Missing/"+Code+".txt").exists())
           {
               System.out.println("What is your current location (No spaces)");
               Location=input.next();
               PrintWriter writer = new PrintWriter("Database/Objects/Found/"+Code+".txt");
               writer.println(Location);
               writer.close();          
               System.out.println("Item Found");
           }
           else
            System.out.println("Item not Missing");  
            input.close();
        }
        else
            System.out.println("No Signal.");      
    }
}