import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class Database {
	   public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
	        try (Scanner input = new Scanner(System.in)) {
	            String Code = null;
	            String Item = null;
	            int choice=1;
	                        while(choice==1||choice==2||choice==3){
	                            try (BufferedReader br = new BufferedReader(new FileReader("Database/Prompt.txt"))) {
	                                String line;
	                                while ((line = br.readLine()) != null)
	                                    System.out.println(line);
	                        choice=input.nextInt();
	                        switch (choice){
	                            case 1: //create item;
	                            	CreateItem(Code,Item,input);
	                                break;
	                            case 2: //flag item
	                            	LostItem(Code,Item,input,choice);
	                                break;    
	                            case 3: //item information;
	                            	ChekItems();
	                            break;
	                            default: System.out.println("Logging out");
	                                break;
	                        }
	                        }

	        }
	        input.close();
	    }
	    
	}
	   public static void CreateItem(String Code, String Item, Scanner input) throws IOException {
           System.out.println("What is the trackers serial code?");
           Code = input.next();
           System.out.println("What is the item?");
           Item = input.next();
           if(new File("Database/Missing/"+Code+".txt").exists()||new File("Database/TrackerIds/"+Code+".txt").exists())
           {
                System.out.println("Sorry that Tracker Id belongs to an existing item");         
           }
           else
           {
           try (PrintWriter writer = new PrintWriter("Database/Objects/Have/"+Code+".txt")) {
               writer.println(Item);
               writer.println(Code);
               writer.close();
           }         
           try (PrintWriter writer = new PrintWriter("Database/TrackerIds/"+Code+".txt")) {
               writer.close();
           }    
           }              
		}
	   public static void LostItem(String Code, String Item, Scanner input, int choice) throws IOException {
           System.out.println("What is the trackers serial code?");
           Code = input.next();
           System.out.println("If you have the item press 1, If you lost the item press 2");
           choice = input.nextInt();
	       switch (choice) {
	           case 1:
	               if(new File("Database/Objects/Lost/"+Code+".txt").exists())
	               {                                         
	                   try (PrintWriter writer = new PrintWriter("Database/Objects/Have/"+Code+".txt")) {
	                       try (BufferedReader br2 = new BufferedReader(new FileReader("Database/Objects/Lost/"+Code+".txt"))) {
	                           String st;
	                           while ((st = br2.readLine()) != null)
	                               writer.println(st);
	                           writer.close();
	                       }
	                       new File("Database/Objects/Lost/"+Code+".txt").delete();
	                       new File("Database/Missing/"+Code+".txt").delete();
	                   }  
	               }
	               else
	                   System.out.println("Either that item is already maked as 'Have' or does not exist");
	               if(new File("Database/Objects/Found/"+Code+".txt").exists())
	               {
	                   new File("Database/Objects/Found/"+Code+".txt").delete();
	               }
	               break;
	           case 2:
	               if(new File("Database/Objects/Have/"+Code+".txt").exists())
	               {
	                   try (PrintWriter writer = new PrintWriter("Database/Objects/Lost/"+Code+".txt"); BufferedReader br2 = new BufferedReader(new FileReader("Database/Objects/Have/"+Code+".txt"))) {
	                       String st;
	                       while ((st = br2.readLine()) != null) 
	                           writer.println(st);
	                       new File("Database/Objects/Have/"+Code+".txt").delete(); 
	                       writer.close();
	                   }
	                   Notifyphone(Code);
	               }
	               else
	                   System.out.println("Either that item is already maked as 'Lost' or does not exist");
	               break;
	           default:
	               System.out.println("sorry that wasnt a valid option");
	               break;
	       }
	   }
	   public static void ChekItems() throws IOException  {
		   System.out.println("Items that You Have");
           System.out.println("Item name"+"          "+"Tracker ID");
           String[] fileList = new File("Database/Objects/Have").list();
           if(fileList.length>0)
           {
           for(String file : fileList){
               try (BufferedReader br2 = new BufferedReader(new FileReader("Database/Objects/Have/"+file))) {
                   String line2;
                   while ((line2 = br2.readLine()) != null) 
                       System.out.print(line2+"                  ");
                   br2.close();
               }                                        
               System.out.println();
           }    
           }
           System.out.println("\nItems that You Lost");
           System.out.println("Item name"+"          "+"Tracker ID"+"          "+"Location");
           String[] fileList2 = new File("Database/Objects/Lost").list();
           if(fileList2.length>0)
           {
           for(String file2 : fileList2){
               if(new File("Database/Objects/Lost/"+file2).exists()){
                   try (BufferedReader br3 = new BufferedReader(new FileReader("Database/Objects/Lost/"+file2))) {
                       String line3;
                       while ((line3 = br3.readLine()) != null) 
                           System.out.print(line3+"                  ");
                       br3.close();
                   } 
               }
               if(new File("Database/Objects/Found/"+file2).exists()){
                   try (BufferedReader br4 = new BufferedReader(new FileReader("Database/Objects/Found/"+file2))) {
                       String line4;
                       while ((line4 = br4.readLine()) != null)
                           System.out.print(line4);
                       br4.close();
                   }  
               }
               else
                   System.out.print("N/A");
               System.out.println();
           }    
           }		   
	   }
	   public static void Notifyphone(String Code) throws IOException {
           try (PrintWriter writer = new PrintWriter("Database/Missing/"+Code+".txt")) {
               writer.println(Code);                            
           }
	   }

}


