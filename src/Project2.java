import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Bhargav Patel
 * 
 */

public class Project2 {

    private static Scanner scan;                // Scanner for user input
    private static boolean exiting = false;     // Used for control flow in menu
    
    /**
     * Checks if a String representing a pid is well-formed.
     * 
     * @param s The pid String to check.
     * @return  true if the pid is well-formed, and false if it is malformed.
     */
    public static boolean pidValid(String s) {
        if (s.startsWith("p") 
            && s.length() == 4 
            && Character.isDigit(s.charAt(1))
            && Character.isDigit(s.charAt(2))
            && Character.isDigit(s.charAt(3))
            && !s.equals("p000")) {
                return true;                    // Returns true if the string begins with
        } else {                                // p and is followed by exactly three digits
            return false;                       // that are not 000
        }                                       // Returns false otherwise
    }

    
    /**
     * Checks if a String representing an eid is well-formed.
     * 
     * @param s The eid String to check.
     * @return  true if the eid is well-formed, and false if it is malformed.
     */
    public static boolean eidValid(String s) {
        if (s.startsWith("e") 
            && s.length() == 3
            && Character.isDigit(s.charAt(1))
            && Character.isDigit(s.charAt(2))
            && !s.equals("e00")) {
                return true;                    // Returns true if the string begins with
        }                                       // e and is followed by exactly two digits
        else {                                  // that are not 00
            return false;                       // Returns false otherwise
        }
    }

    
    /**
     * @param s The cid String to check.
     * @return  true if the cid is well-formed, and false if it is malformed.
     */
    public static boolean cidValid(String s) {
        if (s.startsWith("c") 
            && s.length() == 4 
            && Character.isDigit(s.charAt(1))
            && Character.isDigit(s.charAt(2))
            && Character.isDigit(s.charAt(3))
            && !s.equals("c000")) {             // Returns true if the string begins with
                return true;                    // c and is followed by exactly three digits
        } else {                                // that are not 000
            return false;                       // Returns false otherwise
        }
    }

    
    /**
     * Attempts to show employee tuples.
     */
    public static void showEmployees() {
        try {
            System.out.println("\nGetting Employee Tuples...\n");
            P2DB.showEmployees();                                       // try to call P2DB.showEmployees
        } catch (SQLException e) {                                      // catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Attempts to show customer tuples.
     */
    public static void showCustomers() {
        try {
            System.out.println("\nGetting Customer Tuples...\n");
            P2DB.showCustomers();                                       // try to call P2DB.showCustomers
        } catch (SQLException e) {                                      // catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Attempts to show product tuples.
     */
    public static void showProducts() {
        try {
            System.out.println("\nGetting Product Tuples...\n");
            P2DB.showProducts();                                        // try to call P2DB.showProducts
        } catch (SQLException e) {                                      // catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Attempts to show product discount tuples.
     */
    public static void showDiscounts() {
        try {
            System.out.println("\nGetting Discount Tuples...\n");
            P2DB.showDiscounts();                                       // try to call P2DB.showDiscounts
        } catch (SQLException e) {                                      // catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Attempts to show purchase tuples.
     */
    public static void showPurchases() {
        try {
            System.out.println("\nGetting Purchase Tuples...\n");
            P2DB.showPurchases();                                       // try to call P2DB.showPurchases
        } catch (SQLException e) {                                      // catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Attempts to show log tuples.
     */
    public static void showLogs() {
        try {
            System.out.println("\nGetting Log Tuples...\n");
            P2DB.showLogs();                                            // try to call P2DB.showLogs
        } catch (SQLException e) {                                      // catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Attempts to generate monthly sales report for an employee
     * given a user-provided eid.
     */
    public static void getMonthlySalesReport() {
        try {
            scan.nextLine(); // Scanner needs to consume previous line
            String input;
            System.out.println("\nMonthly Sale Activity:\n");
            System.out.print("Enter EID (example - e01): ");
            input = scan.nextLine();                    // Retrieve user input
            if (eidValid(input)) {                      // Check if the input is a well-formed eid
                P2DB.getMonthlySalesReport(input);      // Attempt to call P2DB.getMonthlySalesReport with the well-formed eid
            } else {
                System.out.println("\nIncorrect Employee ID. Please enter a correct EID."); // print error if input is malformed
            }
        } catch (SQLException e) {                      // Catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Attempts to add a new Employee tuple.
     */
    public static void addEmployee() {
        try {
            scan.nextLine(); // Scanner needs to consume previous line
            String eid;
            String name;
            String phone;
            String email;
            System.out.println("\nEmployee Details:\n");
            System.out.print("Enter EID: ");
            eid = scan.nextLine();
            System.out.print("Enter Name: ");
            name = scan.nextLine();
            System.out.print("Enter Phone Number: ");
            phone = scan.nextLine();                        // Retrieve the values for
            System.out.print("Enter Email Address: ");      // each column for the new tuple
            email = scan.nextLine();                        // from the user
            if (eidValid(eid)) {                            // Ensure that the eid is well-formed
                P2DB.addEmployee(eid, name, phone, email);  // Attempt to call P2DB.addEmployee with the user-provided values
            } else {
                System.out.println("Incorrect Employee ID. Please enter a correct EID."); // print error if input is malformed
            }
        } catch (SQLException e) {                          // Catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Adds 
     */
    public static void addPurchase() {
        try {
            scan.nextLine(); // Scanner needs to consume previous line
            String eid;
            String pid;
            String cid;
            int quantity;
            double unitPrice;

            System.out.println("\nNew Purchase:\n");
            System.out.print("Enter EID: ");
            eid = scan.nextLine();
            System.out.print("Enter PID: ");
            pid = scan.nextLine();
            System.out.print("Enter CID: ");
            cid = scan.nextLine();                              // Retrieve the values for
            System.out.print("Enter Unit Price: ");             // each column for the new tuple
            unitPrice = scan.nextDouble();                      // from the user
            System.out.print("Enter Quantity: ");
            quantity = scan.nextInt();
            if (eidValid(eid) && pidValid(pid) && cidValid(cid)) {          // Ensure that the eid, pid, and cid are well-formed
                P2DB.addPurchase(eid, pid, cid, quantity, unitPrice);       // Attempt to call P2DB.addPurchase with the user-provided values
            } else {
                System.out.println("Error: Input is not a valid entry!!");  // print error if input is malformed
            }
        } catch (SQLException e) {                                          // Catch Exception if thrown
            Logger.getLogger(Project2.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    /**
     * Prints the menu.
     */
    public static void displayMenu() {
        System.out.println("\n");
        System.out.println("   MENU OPTIONS                           ");
        System.out.println("------------------------------------------");
        System.out.println("   1. Show Employees                      ");
        System.out.println("   2. Show Customers                      ");
        System.out.println("   3. Show Products                       ");
        System.out.println("   4. Show Product Discounts              ");
        System.out.println("   5. Show Purchases                      ");
        System.out.println("   6. Show Logs                           ");
        System.out.println("   7. Generate Monthly Sales Report       ");
        System.out.println("   8. Add Employee                        ");
        System.out.println("   9. Add Purchase                        ");
        System.out.println("------------------------------------------");
        System.out.println("   0 Exit                               \n");
    }


    /**
     * Handle user input for menu navigation.
     */
     public static void handleMenu() {
        int user_input = -1;                // Initialize the input integer
        do {                                // Use a do-while loop
            System.out.print("input> ");    // Show the prompt
            while(!scan.hasNextInt()){      // Check for int input
                System.out.println("Error: Input should be a valid Integer"); // print error if not int
                displayMenu();                                                // then redisplay menu
                System.out.print("input> ");
                scan.next();
            }

            user_input = scan.nextInt();        // Retrieve int input from user
            switch (user_input) {
                case 1:
                    showEmployees();            // Call the associated function for the 
                    break;                      // provided input
                case 2:
                    showCustomers();
                    break;
                case 3:
                    showProducts();
                    break;
                case 4:
                    showDiscounts();
                    break;
                case 5:
                    showPurchases();
                    break;
                case 6:
                    showLogs();
                    break;
                case 7:
                    getMonthlySalesReport();
                    break;
                case 8:
                    addEmployee();
                    break;
                case 9:
                    addPurchase();
                    break;
                case 0:
                    exiting = true; // set exiting to true if
                    break;          // 0 is selected
                default:
                    System.out.println("Error: Invalid Choice");
                    break;          // handle invalid integer
            }
            scan.reset();
            if(!exiting) {          // if exiting is false, continue back to the menu after execution
                System.out.print("\nPress enter to return to menu.");
                System.console().readLine();
                displayMenu();
            }
                
        } while (user_input != 0);
    }


     /**
     * Prints a message that the user is logging out.
     * Called right before program ends.
     */
    private static void logout() {
        System.out.println("You Are Logged Out..!!");   // log out before terminating
    }


    public static void main(String []args) {
        scan = new Scanner(System.in);
        P2DB.connect();
        displayMenu();
        handleMenu();
        logout();
        scan.close();
    }
}
