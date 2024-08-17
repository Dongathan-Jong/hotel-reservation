import java.util.*;
import java.io.*;

public class Main5 {
   public static void main(String[] args) {
      int employeeLoginResult;
      Scanner sc = new Scanner(System.in);
      String fullDate = "";
      String option;
      int adminOrEmployee;
      boolean menuOption = false;
      boolean printMenuOptionsAgain = true;

      adminOrEmployee = loginPortal();

      while (printMenuOptionsAgain) {
         System.out.println("\nMENU OPTION. ENTER THE CORRESPONDING NUMBER TO NAVIGATE.");
         System.out.println("[log out] To go back to log in portal");
         System.out.println("[1] View all the available rooms on given date");
         System.out.println("[2] View all the reservations on a given date");
         System.out.println("[3] Look up reservations under a certain name");
         System.out.println("[4] Make a reservation");
         System.out.println("[5] Cancel a reservation");
         System.out.println("[6] Change the customer name for a reservation");
         System.out.println("[7] Change date for a reservation");
         System.out.println("[8] Change room number for a reservation");
         System.out.println("[9] Change employee PIN Number");

         if (adminOrEmployee == 0) {
            System.out.println("[10] Add Hotel Room");
            System.out.println("[11] Delete a hotel room");
            System.out.println("[12] Add an employee");
            System.out.println("[13] Delete an Employee");
         }

         System.out.print("Enter option: ");
         option = sc.nextLine();
         menuOption = false;

         
   }

   public static int loginPortal() {
      
   }

   public static int checkEmployeeNumber(String employeeNumberInputString) {
      
   }

   public static int checkEmployeePinNumber(String employeeNumberInputString, String employeePinNumberInputString) {
      
   }

   public static String askForDate() {
      
   }

   public static void availableRoomsPerDate(String fullDate) {
      
}
