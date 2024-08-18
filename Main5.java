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

         while (!menuOption) {
            if (option.trim().equalsIgnoreCase("log out")) {
               adminOrEmployee = loginPortal();
               menuOption = true;
            } else if (option.equals("1")) {
               fullDate = askForDate();
               availableRoomsPerDate(fullDate);
               menuOption = true;
            } else if (option.trim().equals("2")) {
               fullDate = askForDate();
               allReservationsPerDate(fullDate);
               menuOption = true;
            } else if (option.trim().equals("3")) {
               allReservationsPerName();
               menuOption = true;
            } else if (option.trim().equals("4")) {
               bookingReservations();
               menuOption = true;
            } else if (option.trim().equals("5")) {
            } else if (option.trim().equals("6")) {
            } else if (option.trim().equals("7")) {
            } else if (option.trim().equals("8")) {
            } else if (option.trim().equals("9")) {
            } else if (adminOrEmployee == 0) {
               if (option.trim().equals("10")) {
                  System.out.println("option 10!!!! ");
                  menuOption = true;
               }
            } else {
               System.out.print("Not an option. Try again.\n");
               menuOption = false;
               System.out.print("Enter option: ");
               option = sc.nextLine();
            }
         }
      }
   }

   public static int loginPortal() {
      Scanner sc = new Scanner(System.in);
      boolean employeeLoginPortal = false;
      int returnValueInt = 1;
      String employeeNumberInputString = "";
      int goodEmployeeNumber = 1;
      String employeePinNumberInputString;
      int employeeNumAndPinMatch = 1;
      System.out.println("WELCOME TO LOG IN PORTAL.");

      while (!employeeLoginPortal) {
         while (goodEmployeeNumber == 1) {
            System.out.print("\nEnter your Employee Number: ");
            employeeNumberInputString = sc.nextLine();
            goodEmployeeNumber = checkEmployeeNumber(employeeNumberInputString);

            if (goodEmployeeNumber == 0) {
               returnValueInt = 0;
            } else if (goodEmployeeNumber == 2) {
               returnValueInt = 2;
            }
         }

         if (employeeNumAndPinMatch == 0) {
            employeeNumAndPinMatch = 1;
         }

         while (employeeNumAndPinMatch == 1) {
            System.out.print("\nEnter your Employee PIN Number: ");
            employeePinNumberInputString = sc.nextLine();
            employeeNumAndPinMatch = checkEmployeePinNumber(employeeNumberInputString, employeePinNumberInputString);

            if (employeeNumAndPinMatch == 0) {
               employeeLoginPortal = false;
               goodEmployeeNumber = 1;
            } else {
               goodEmployeeNumber = 2;
               employeeLoginPortal = true;
            }
         }
      }
      return returnValueInt;
   }

   public static int checkEmployeeNumber(String employeeNumberInputString) {
      final String EMPLOYEE_DATA_FILE = "employeeData.txt";
      String line;
      int employeeNumberInputInt;
      String[] employeeLines;
      int returnValueInt = 1;

      try {
         BufferedReader in = new BufferedReader(new FileReader(EMPLOYEE_DATA_FILE));

         try {
            employeeNumberInputInt = Integer.parseInt(employeeNumberInputString);
            line = in.readLine();

            while (line != null) {
               employeeLines = line.split(", ");

               if (returnValueInt == 1) {
                  if (employeeNumberInputInt == (Integer.parseInt(employeeLines[2]))) {
                     if (employeeNumberInputString.length() == 6) {
                        if (employeeNumberInputInt == 000000) {
                           returnValueInt = 0;
                        } else {
                           returnValueInt = 2;
                        }
                     } else {
                        returnValueInt = 1;
                     }
                  } else {
                     returnValueInt = 1;
                  }
               }
               line = in.readLine();
            }
         } catch (NumberFormatException e) {
            System.out.println("Please enter your 6 digit employee number. \n");
            returnValueInt = 1;
         }
         in.close();
      } catch (IOException e) {
         System.out.println(e + " error reading " + EMPLOYEE_DATA_FILE);
      }

      if (returnValueInt == 1) {
         System.out.println("That employee number does not exist. \n");
      }
      return returnValueInt;
   }

   public static int checkEmployeePinNumber(String employeeNumberInputString, String employeePinNumberInputString) {
      final String EMPLOYEE_DATA_FILE = "employeeData.txt";
      int returnValueInt = 1;
      String line;
      int employeeNumberInputInt;
      int employeePinNumberInputInt;
      String[] employeeLines;

      try {
         BufferedReader in = new BufferedReader(new FileReader(EMPLOYEE_DATA_FILE));

         try {
            employeeNumberInputInt = Integer.parseInt(employeeNumberInputString);
            employeePinNumberInputInt = Integer.parseInt(employeePinNumberInputString);
            line = in.readLine();

            while (line != null) {
               employeeLines = line.split(", ");

               if (returnValueInt == 1) {
                  if (employeePinNumberInputInt == 0) {
                     returnValueInt = 0;
                  } else if ((employeeNumberInputInt == (Integer.parseInt(employeeLines[2]))) && (employeePinNumberInputInt == (Integer.parseInt(employeeLines[3])))) {
                     returnValueInt = 2;
                  } else {
                     returnValueInt = 1;
                  }
               }
               line = in.readLine();
            }
         } catch (NumberFormatException e) {
            System.out.println("This employee number does not exist. Please enter your 6 digit employee number. \n");
            returnValueInt = 1;
         }
         in.close();
      } catch (IOException e) {
         System.out.println(e + " error in " + EMPLOYEE_DATA_FILE);
         returnValueInt = 1;
      }

      if (returnValueInt == 1) {
         System.out.println("Incorrect PIN number. Try again. \n");
      }
      return returnValueInt;
   }

   public static String askForDate() {
      Scanner sc = new Scanner(System.in);
      int monthInt;
      int dayInt;
      int yearInt;
      String monthStr = "";
      String dayStr = "";
      String yearStr = "";
      boolean monthEnteredGood = false;
      boolean dayEnteredGood = false;
      boolean yearEnteredGood = false;
      String fullDate = "";

      System.out.println("Please enter the following:");

      while (!monthEnteredGood) {
         try {
            System.out.print("Enter month: ");
            monthInt = sc.nextInt();
            monthStr = "" + monthInt;

            if ((monthInt <= 12) && (monthInt >= 1)) {
               if (monthStr.length() != 2) {
                  monthStr = "0" + monthStr;
               }
               monthEnteredGood = true;
            } else {
               System.out.println("Please enter a month within the range of 1-12");
               monthEnteredGood = false;
            }
         } catch (InputMismatchException e) {
            System.out.println("Please enter an integer for month. Try again");
            sc.next();
            monthEnteredGood = false;
         }
      }

      while (!dayEnteredGood) {
         try {
            System.out.print("Enter day: ");
            dayInt = sc.nextInt();
            dayStr = "" + dayInt;

            if ((dayInt <= 31) && (dayInt >= 1)) {
               if (dayStr.length() != 2) {
                  dayStr = "0" + dayStr;
               }
               dayEnteredGood = true;
            } else {
               System.out.println("Please enter a day within the range of 1-31");
               dayEnteredGood = false;
            }
         } catch (InputMismatchException e) {
            System.out.println("Please enter an integer for day. Try again");
            sc.next();
            dayEnteredGood = false;
         }
      }

     
   }

   public static void availableRoomsPerDate(String fullDate) {
      
   }

   public static void allReservationsPerDate(String fullDate) {
      
   }

   public static void allReservationsPerName() {
      
   }

   public static void bookingReservations() {
      
   }
}
