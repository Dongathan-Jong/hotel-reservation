public void addHotelRoom() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter room number: ");
    int roomNumber = Integer.parseInt(scanner.nextLine());
    System.out.print("Enter room type: ");
    String roomType = scanner.nextLine();
    System.out.print("Enter room price: ");
    double roomPrice = Double.parseDouble(scanner.nextLine());
    System.out.print("Enter number of beds: ");
    int numberOfBeds = Integer.parseInt(scanner.nextLine());
    Room room = new Room(roomNumber, roomType, roomPrice, numberOfBeds);
    rooms.add(room);
    System.out.println("Room added successfully.");
    System.out.println("Updating hotel room database...");
    System.out.println("Database updated.");
}

public void deleteHotelRoom() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter room number to delete: ");
    int roomNumber = Integer.parseInt(scanner.nextLine());
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
}

public void addEmployee() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter employee name: ");
    String name = scanner.nextLine();
    System.out.print("Enter employee PIN number: ");
    String pinNumber = scanner.nextLine();
    System.out.print("Enter employee position: ");
    String position = scanner.nextLine();
    System.out.print("Enter employee salary: ");
    double salary = Double.parseDouble(scanner.nextLine());
    Employee employee = new Employee(name, pinNumber, position, salary);
    employees.add(employee);
    System.out.println("Employee added successfully.");
    System.out.println("Updating employee records...");
    System.out.println("Employee records updated.");
}

public void deleteEmployee() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter employee name to delete: ");
    String name = scanner.nextLine();
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
}
