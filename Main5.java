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

  public class HotelManagement {

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

        try (BufferedReader in = new BufferedReader(new FileReader(EMPLOYEE_DATA_FILE))) {
            try {
                employeeNumberInputInt = Integer.parseInt(employeeNumberInputString);
                while ((line = in.readLine()) != null) {
                    employeeLines = line.split(", ");
                    if (employeeNumberInputInt == Integer.parseInt(employeeLines[2])) {
                        if (employeeNumberInputString.length() == 6) {
                            if (employeeNumberInputInt == 000000) {
                                returnValueInt = 0;
                            } else {
                                returnValueInt = 2;
                            }
                        } else {
                            returnValueInt = 1;
                        }
                        break; // Break the loop once the correct number is found
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter your 6 digit employee number. \n");
                returnValueInt = 1;
            }
        } catch (IOException e) {
            System.out.println("Error reading " + EMPLOYEE_DATA_FILE + ": " + e.getMessage());
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

        try (BufferedReader in = new BufferedReader(new FileReader(EMPLOYEE_DATA_FILE))) {
            try {
                employeeNumberInputInt = Integer.parseInt(employeeNumberInputString);
                employeePinNumberInputInt = Integer.parseInt(employeePinNumberInputString);

                while ((line = in.readLine()) != null) {
                    employeeLines = line.split(", ");
                    if (employeePinNumberInputInt == 0) {
                        returnValueInt = 0;
                    } else if (employeeNumberInputInt == Integer.parseInt(employeeLines[2]) &&
                               employeePinNumberInputInt == Integer.parseInt(employeeLines[3])) {
                        returnValueInt = 2;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("This employee number does not exist. Please enter your 6 digit employee number. \n");
                returnValueInt = 1;
            }
        } catch (IOException e) {
            System.out.println("Error reading " + EMPLOYEE_DATA_FILE + ": " + e.getMessage());
        }

        if (returnValueInt == 1) {
            System.out.println("Incorrect PIN number. Try again. \n");
        }
        return returnValueInt;
    }

    public static String askForDate() {
        Scanner sc = new Scanner(System.in);
        int monthInt, dayInt, yearInt;
        String monthStr = "", dayStr = "", yearStr = "";
        boolean monthEnteredGood = false, dayEnteredGood = false, yearEnteredGood = false;
        String fullDate = "";

        System.out.println("Please enter the following:");

        while (!monthEnteredGood) {
            try {
                System.out.print("Enter month: ");
                monthInt = sc.nextInt();
                monthStr = String.format("%02d", monthInt); // Format to two digits

                if (monthInt >= 1 && monthInt <= 12) {
                    monthEnteredGood = true;
                } else {
                    System.out.println("Please enter a month within the range of 1-12");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer for month. Try again");
                sc.next(); 
            }
        }

        while (!dayEnteredGood) {
            try {
                System.out.print("Enter day: ");
                dayInt = sc.nextInt();
                dayStr = String.format("%02d", dayInt); // Format to two digits

                if (dayInt >= 1 && dayInt <= 31) {
                    dayEnteredGood = true;
                } else {
                    System.out.println("Please enter a day within the range of 1-31");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer for day. Try again");
                sc.next(); 
            }
        }

        while (!yearEnteredGood) {
            try {
                System.out.print("Enter year: ");
                yearInt = sc.nextInt();
                yearStr = String.valueOf(yearInt);

                if (yearStr.length() == 4 && yearInt >= 2020) {
                    yearEnteredGood = true;
                } else {
                    System.out.println("Please enter a 4 digit year (ex: 2022)");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer for year. Try again");
                sc.next();
            }
        }

        fullDate = monthStr + "/" + dayStr + "/" + yearStr;
        sc.nextLine(); // Clear the buffer
        return fullDate;
    }

    public static void availableRoomsPerDate(String fullDate) {
        final String ROOM_DATA_FILE = "hotelRoomData.txt";
        final String BOOKING_DATA_FILE = "bookingData.txt";
        ArrayList<String> availableRooms = new ArrayList<>();

        try (BufferedReader roomIn = new BufferedReader(new FileReader(ROOM_DATA_FILE))) {
            String roomDataLine;
            while ((roomDataLine = roomIn.readLine()) != null) {
                String[] roomDataLines = roomDataLine.split(", ");
                availableRooms.add(roomDataLines[0]);
            }
        } catch (IOException e) {
            System.out.println("Error reading room data file: " + e.getMessage());
        }

        try (BufferedReader bookingIn = new BufferedReader(new FileReader(BOOKING_DATA_FILE))) {
            String bookingDataLine;
            while ((bookingDataLine = bookingIn.readLine()) != null) {
                String[] bookingDataLines = bookingDataLine.split(", ");
                if (bookingDataLines[1].equals(fullDate)) {
                    availableRooms.remove(bookingDataLines[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading booking data file: " + e.getMessage());
        }

        System.out.println("Available Rooms on " + fullDate + ":");
        for (String room : availableRooms) {
            System.out.println(room);
        }
    }

    public static void allReservationsPerDate(String fullDate) {
        final String BOOKING_DATA_FILE = "bookingData.txt";
        ArrayList<String> reservations = new ArrayList<>();

        try (BufferedReader bookingIn = new BufferedReader(new FileReader(BOOKING_DATA_FILE))) {
            String bookingDataLine;
            while ((bookingDataLine = bookingIn.readLine()) != null) {
                String[] bookingDataLines = bookingDataLine.split(", ");
                if (bookingDataLines[1].equals(fullDate)) {
                    reservations.add(bookingDataLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading booking data file: " + e.getMessage());
        }

        System.out.println("Reservations on " + fullDate + ":");
        for (String reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public static void allReservationsPerName() {
        final String BOOKING_DATA_FILE = "bookingData.txt";
        ArrayList<String> reservations = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the customer name: ");
        String name = sc.nextLine();

        try (BufferedReader bookingIn = new BufferedReader(new FileReader(BOOKING_DATA_FILE))) {
            String bookingDataLine;
            while ((bookingDataLine = bookingIn.readLine()) != null) {
                String[] bookingDataLines = bookingDataLine.split(", ");
                if (bookingDataLines[2].equalsIgnoreCase(name)) {
                    reservations.add(bookingDataLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading booking data file: " + e.getMessage());
        }

        System.out.println("Reservations under " + name + ":");
        for (String reservation : reservations) {
            System.out.println(reservation);
        }
    }


   public static void bookingReservations() {
      final String BOOKING_DATA_FILE = "bookingData.txt";
      final String ROOM_DATA_FILE = "hotelRoomData.txt";
      Scanner sc = new Scanner(System.in);
      String roomNumber;
      String fullDate;
      String name;
      boolean roomAvailable = true;
      ArrayList<String> bookedRooms = new ArrayList<>();

      System.out.print("Enter customer name: ");
      name = sc.nextLine();

      fullDate = askForDate();
      availableRoomsPerDate(fullDate);

      System.out.print("Enter room number: ");
      roomNumber = sc.nextLine();

      try {
         BufferedReader bookingIn = new BufferedReader(new FileReader(BOOKING_DATA_FILE));
         String bookingDataLine = bookingIn.readLine();

         while (bookingDataLine != null) {
            String[] bookingDataLines = bookingDataLine.split(", ");

            if (bookingDataLines[0].equals(roomNumber) && bookingDataLines[1].equals(fullDate)) {
               roomAvailable = false;
            }
            bookingDataLine = bookingIn.readLine();
         }

         bookingIn.close();
      } catch (IOException e) {
         System.out.println("Error reading booking data file.");
      }

      if (roomAvailable) {
         try {
            BufferedWriter bookingOut = new BufferedWriter(new FileWriter(BOOKING_DATA_FILE, true));
            bookingOut.write(roomNumber + ", " + fullDate + ", " + name);
            bookingOut.newLine();
            bookingOut.close();
            System.out.println("Reservation successfully booked for " + name + " on " + fullDate + " in room " + roomNumber + ".");
         } catch (IOException e) {
            System.out.println("Error writing to booking data file.");
         }
      } else {
         System.out.println("The room " + roomNumber + " is already booked on " + fullDate + ".");
      }
   }

public void cancelReservation() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter customer name for cancellation: ");
    String customerName = scanner.nextLine();
    Reservation reservationToRemove = null;
    for (Reservation reservation : reservations) {
        if (reservation.getCustomerName().equalsIgnoreCase(customerName)) {
            System.out.println("Reservation found: " + reservation);
            System.out.print("Are you sure you want to cancel this reservation? (yes/no): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                reservationToRemove = reservation;
                break;
            } else {
                System.out.println("Cancellation aborted.");
                return;
            }
        }
    }
    if (reservationToRemove != null) {
        reservations.remove(reservationToRemove);
        System.out.println("Reservation cancelled successfully.");
        System.out.println("Sending cancellation email to customer...");
        System.out.println("Email sent.");
    } else {
        System.out.println("Reservation not found.");
    }
}

public void changeCustomerNameForReservation() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter current customer name: ");
    String currentName = scanner.nextLine();
    System.out.print("Enter new customer name: ");
    String newName = scanner.nextLine();
    Reservation reservationToUpdate = null;
    for (Reservation reservation : reservations) {
        if (reservation.getCustomerName().equalsIgnoreCase(currentName)) {
            System.out.println("Reservation found: " + reservation);
            System.out.print("Are you sure you want to change the customer name to " + newName + "? (yes/no): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                reservationToUpdate = reservation;
                break;
            } else {
                System.out.println("Update aborted.");
                return;
            }
        }
    }
    if (reservationToUpdate != null) {
        reservationToUpdate.setCustomerName(newName);
        System.out.println("Customer name updated successfully.");
        System.out.println("Sending notification email to customer...");
        System.out.println("Email sent.");
    } else {
        System.out.println("Reservation not found.");
    }
}

public class HotelManagement {

    private List<Reservation> reservations = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    public void changeDateForReservation() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();
            if (customerName.isEmpty()) {
                System.out.println("Customer name cannot be empty.");
                return;
            }

            System.out.print("Enter new reservation date (YYYY-MM-DD): ");
            String newDate = scanner.nextLine();
            if (newDate.isEmpty()) {
                System.out.println("Date cannot be empty.");
                return;
            }

            Reservation reservationToUpdate = null;
            for (Reservation reservation : reservations) {
                if (reservation.getCustomerName().equalsIgnoreCase(customerName)) {
                    System.out.println("Reservation found: " + reservation);
                    System.out.print("Are you sure you want to change the date to " + newDate + "? (yes/no): ");
                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        reservationToUpdate = reservation;
                        break;
                    } else {
                        System.out.println("Update aborted.");
                        return;
                    }
                }
            }

            if (reservationToUpdate != null) {
                reservationToUpdate.setDate(newDate);
                System.out.println("Reservation date updated successfully.");
                System.out.println("Sending notification email to customer...");
                System.out.println("Email sent.");
            } else {
                System.out.println("Reservation not found.");
            }
        } finally {
            scanner.close();
        }
    }

    public void changeRoomNumberForReservation() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();
            if (customerName.isEmpty()) {
                System.out.println("Customer name cannot be empty.");
                return;
            }

            System.out.print("Enter new room number: ");
            int newRoomNumber;
            try {
                newRoomNumber = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid room number format.");
                return;
            }

            Reservation reservationToUpdate = null;
            for (Reservation reservation : reservations) {
                if (reservation.getCustomerName().equalsIgnoreCase(customerName)) {
                    System.out.println("Reservation found: " + reservation);
                    System.out.print("Are you sure you want to change the room number to " + newRoomNumber + "? (yes/no): ");
                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        reservationToUpdate = reservation;
                        break;
                    } else {
                        System.out.println("Update aborted.");
                        return;
                    }
                }
            }

            if (reservationToUpdate != null) {
                reservationToUpdate.setRoomNumber(newRoomNumber);
                System.out.println("Room number updated successfully.");
                System.out.println("Sending notification email to customer...");
                System.out.println("Email sent.");
            } else {
                System.out.println("Reservation not found.");
            }
        } finally {
            scanner.close();
        }
    }

    public void changeEmployeePinNumber() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter employee name: ");
            String employeeName = scanner.nextLine();
            if (employeeName.isEmpty()) {
                System.out.println("Employee name cannot be empty.");
                return;
            }

            System.out.print("Enter new PIN number: ");
            String newPin = scanner.nextLine();
            if (newPin.isEmpty()) {
                System.out.println("PIN number cannot be empty.");
                return;
            }

            Employee employeeToUpdate = null;
            for (Employee employee : employees) {
                if (employee.getName().equalsIgnoreCase(employeeName)) {
                    System.out.println("Employee found: " + employee);
                    System.out.print("Are you sure you want to change the PIN number to " + newPin + "? (yes/no): ");
                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        employeeToUpdate = employee;
                        break;
                    } else {
                        System.out.println("Update aborted.");
                        return;
                    }
                }
            }

            if (employeeToUpdate != null) {
                employeeToUpdate.setPinNumber(newPin);
                System.out.println("PIN number updated successfully.");
                System.out.println("Sending notification email to employee...");
                System.out.println("Email sent.");
            } else {
                System.out.println("Employee not found.");
            }
        } finally {
            scanner.close();
        }
    }

    public void addHotelRoom() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter room number: ");
            int roomNumber;
            try {
                roomNumber = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid room number format.");
                return;
            }

            System.out.print("Enter room type: ");
            String roomType = scanner.nextLine();
            if (roomType.isEmpty()) {
                System.out.println("Room type cannot be empty.");
                return;
            }

            System.out.print("Enter room price: ");
            double roomPrice;
            try {
                roomPrice = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid room price format.");
                return;
            }

            System.out.print("Enter number of beds: ");
            int numberOfBeds;
            try {
                numberOfBeds = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number of beds format.");
                return;
            }

            Room room = new Room(roomNumber, roomType, roomPrice, numberOfBeds);
            rooms.add(room);
            System.out.println("Room added successfully.");
            System.out.println("Updating hotel room database...");
            System.out.println("Database updated.");
        } finally {
            scanner.close();
        }
    }

    public void deleteHotelRoom() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter room number to delete: ");
            int roomNumber;
            try {
                roomNumber = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid room number format.");
                return;
            }

            Room roomToRemove = null;
            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNumber) {
                    System.out.println("Room found: " + room);
                    System.out.print("Are you sure you want to delete this room? (yes/no): ");
                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        roomToRemove = room;
                        break;
                    } else {
                        System.out.println("Deletion aborted.");
                        return;
                    }
                }
            }

            if (roomToRemove != null) {
                rooms.remove(roomToRemove);
                System.out.println("Room deleted successfully.");
                System.out.println("Updating hotel room database...");
                System.out.println("Database updated.");
            } else {
                System.out.println("Room not found.");
            }
        } finally {
            scanner.close();
        }
    }

    public void addEmployee() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter employee name: ");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("Employee name cannot be empty.");
                return;
            }

            System.out.print("Enter employee PIN number: ");
            String pinNumber = scanner.nextLine();
            if (pinNumber.isEmpty()) {
                System.out.println("PIN number cannot be empty.");
                return;
            }

            System.out.print("Enter employee position: ");
            String position = scanner.nextLine();
            if (position.isEmpty()) {
                System.out.println("Position cannot be empty.");
                return;
            }

            System.out.print("Enter employee salary: ");
            double salary;
            try {
                salary = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format.");
                return;
            }

            Employee employee = new Employee(name, pinNumber, position, salary);
            employees.add(employee);
            System.out.println("Employee added successfully.");
            System.out.println("Updating employee records...");
            System.out.println("Employee records updated.");
        } finally {
            scanner.close();
        }
    }

    public void deleteEmployee() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter employee name to delete: ");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("Employee name cannot be empty.");
                return;
            }

            Employee employeeToRemove = null;
            for (Employee employee : employees) {
                if (employee.getName().equalsIgnoreCase(name)) {
                    System.out.println("Employee found: " + employee);
                    System.out.print("Are you sure you want to delete this employee? (yes/no): ");
                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        employeeToRemove = employee;
                        break;
                    } else {
                        System.out.println("Deletion aborted.");
                        return;
                    }
                }
            }

            if (employeeToRemove != null) {
                employees.remove(employeeToRemove);
                System.out.println("Employee deleted successfully.");
                System.out.println("Updating employee records...");
                System.out.println("Employee records updated.");
            } else {
                System.out.println("Employee not found.");
            }
        } finally {
            scanner.close();
        }
    }
}
