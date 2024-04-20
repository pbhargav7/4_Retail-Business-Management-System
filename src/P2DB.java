import java.io.Console;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;

/**
 * 
 * @author Bhargav Patel
 * 
 */

public class P2DB {

    private static Connection c; // The Connection object for the database
    private static int stock; // Used when entering a new purchase to verify stock

    public static String url = "jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111";
    public static String user = "";
    public static String pass = ""; // Prepare connection Strings

    /**
     * Prompts user for credentials and attempts to establish a connection with the
     * database.
     * 
     * @return Connection object to be used for database queries.
     */
    public static Connection connect() {
        try {

            OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL(url); // Set the connection

            Console console = System.console();
            if (console == null) {
                System.out.println("Console not available. Exiting..."); // Ensure a console environment
                System.exit(1);
            }

            user = console.readLine("Enter username: "); // Use console to gather
            pass = new String(console.readPassword("Enter password: ")); // user credentials

            System.out.println("\nAttempting to establish connection with " + url + "\n");
            c = ds.getConnection(user, pass); // Attempt the connection
            System.out.println("\nLogin Successful !!");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.out.println("Error: Invalid ID/Password...!!"); // Catch exceptions
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        return c; // Return the Connection object
    }

    /**
     * Builds and returns a string which can be used to call a stored procedure in
     * the database with parameter(s).
     * 
     * @param packageName The name of the package which contains the procedure.
     * @param procName    The name of the procedure.
     * @return The statement that can be executed on the database once parameters
     *         are added.
     */
    public static String getProcCallWithParam(String packageName, String procName) {
        return "begin ? := " + packageName + "." + procName + "(?); end;"; // Prepare the procedure call using
    } // package and procedure names
      // found from P2Util.java

    /**
     * Builds and returns a string which can be used to call a stored procedure in
     * the database without parameters.
     * 
     * @param packageName The name of the package which contains the procedure.
     * @param procName    The name of the procedure.
     * @return The statement that can be executed on the database.
     */
    public static String getProcCallWithoutParam(String packageName, String procName) {
        return "begin ? := " + packageName + "." + procName + "(); end;"; // Prepare the procedure call using
    } // package and procedure names
      // found from P2Util.java

    /**
     * Displays all the tuples in the Employee table.
     * 
     * @throws SQLException if thrown during database access
     */
    public static void showEmployees() throws SQLException {
        ResultSet rs = null;
        CallableStatement cs = null; // Prepare CallableStatement
        try { // and ResultSet
            cs = c.prepareCall(getProcCallWithoutParam(P2Util.PACKAGE_NAME, P2Util.GET_EMPLOYEES)); // Build the
                                                                                                    // procedure call
            cs.registerOutParameter(1, OracleTypes.CURSOR); // Register a cursor as output
            cs.execute();
            rs = (ResultSet) cs.getObject(1); // Execute and print the results
            System.out.printf("%-5s%-15s%-15s%-30s\n", "EID", "ENAME", "TELEPHONE#", "EMAIL");
            while (rs.next()) {
                System.out.printf("%-5s%-15s%-15s%-30s\n", rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage()); // Catch exceptions
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            if (rs != null) { // Close resultSet
                rs.close(); // and CallableStatement
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Displays all the tuples in the Customers table.
     * 
     * @throws SQLException if thrown during database access
     */
    public static void showCustomers() throws SQLException {
        ResultSet rs = null; // Prepare CallableStatement
        CallableStatement cs = null; // and ResultSet
        try {
            cs = c.prepareCall(getProcCallWithoutParam(P2Util.PACKAGE_NAME, P2Util.GET_CUSTOMERS)); // Build the
                                                                                                    // procedure call
            cs.registerOutParameter(1, OracleTypes.CURSOR); // Register a cursor as output
            cs.execute();
            rs = (ResultSet) cs.getObject(1); // Execute and print the results
            System.out.println("CID" + "\t" + "FIRST NAME" + "\t" + "LAST NAME" + "\t" + "PHONE#" + "\t\t"
                    + "Visits Made#" + "\t" + "Last Visit Date");
            while (rs.next()) {
                String cid = rs.getString(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String phone = rs.getString(4);
                String visitsMade = rs.getString(5);
                String lastVisitDate = rs.getDate(6).toString();
                System.out.printf("%s\t%s\t\t%s\t\t%s\t%s\t\t%s%n", cid, firstName, lastName, phone, visitsMade,
                        lastVisitDate);
            }
        } catch (SQLException e) { // Catch exceptions
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            if (rs != null) { // Close ResultSet
                rs.close(); // and CallableStatement
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Displays all the tuples in the Products table.
     * 
     * @throws SQLException if thrown during database access
     */
    public static void showProducts() throws SQLException {
        ResultSet rs = null;
        CallableStatement cs = null; // Prepare CallableStatement
        try { // and ResultSet
            cs = c.prepareCall(getProcCallWithoutParam(P2Util.PACKAGE_NAME, P2Util.GET_PRODUCTS)); // Build the
                                                                                                   // procedure call
            cs.registerOutParameter(1, OracleTypes.CURSOR); // Register a cursor as output
            cs.execute(); // Execute and print the results
            rs = (ResultSet) cs.getObject(1);

            System.out.printf("%-10s%-20s%-10s%-15s%-15s%-20s%n", "PID", "NAME", "QOH", "QOH_THRESHOLD", "ORIG_PRICE",
                    "DISCNT_CATEGORY");

            // print table rows
            while (rs.next()) {
                System.out.printf("%-10s%-20s%-10d%-15d%-15.2f%-20.2f%n", rs.getString(1), rs.getString(2),
                        rs.getInt(3), rs.getInt(4), rs.getDouble(5), rs.getDouble(6));
            }
        } catch (SQLException e) { // Catch exceptions
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            if (rs != null) { // Close ResultSet
                rs.close(); // and CallableStatement
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Displays all the tuples in the Product Discounts table.
     * 
     * @throws SQLException if thrown during database access
     */
    public static void showDiscounts() throws SQLException {
        ResultSet rs = null;
        CallableStatement cs = null; // Prepare CallableStatement
        try { // and ResultSet
            cs = c.prepareCall(getProcCallWithoutParam(P2Util.PACKAGE_NAME, P2Util.GET_PROD_DISCNT)); // Build the
                                                                                                      // procedure call
            cs.registerOutParameter(1, OracleTypes.CURSOR); // Register a cursor as output
            cs.execute(); // Execute and print the results
            rs = (ResultSet) cs.getObject(1);

            System.out.println("DISCNT_CATEGORY" + "\t\t" + "DISCNT_RATE");
            while (rs.next()) {
                System.out.println(
                        rs.getString(1) + "\t\t\t"
                                + rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) { // Catch exceptions
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            if (rs != null) { // Close ResultSet
                rs.close(); // and CallableStatement
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Displays all the tuples in the Purchases table.
     * 
     * @throws SQLException if thrown during database access
     */
    public static void showPurchases() throws SQLException {
        ResultSet rs = null;
        CallableStatement cs = null; // Prepare CallableStatement
        try { // and ResultSet
            cs = c.prepareCall(getProcCallWithoutParam(P2Util.PACKAGE_NAME, P2Util.GET_PURCHASES)); // Build the
                                                                                                    // procedure call
            cs.registerOutParameter(1, OracleTypes.CURSOR); // Register a cursor as output
            cs.execute(); // Execute and print the results
            rs = (ResultSet) cs.getObject(1);

            System.out.println(String.format("%-7s%-15s%-15s%-15s%-15s%-15s%-25s%-15s%-15s",
                    "PUR#", "EID", "PID", "CID", "PURTIME", "Qty", "Unit_Price", "Payment", "Saving"));
            while (rs.next()) {
                System.out.println(String.format("%-7s%-15s%-15s%-15s%-15s%-15s%-25s%-15s%-15s",
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9)));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage()); // Catch exceptions
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            if (rs != null) { // Close ResultSet
                rs.close(); // and CallableStatement
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Displays all the tuples in the Logs table.
     * 
     * @throws SQLException if thrown during database access
     */
    public static void showLogs() throws SQLException {
        ResultSet rs = null; // Prepare CallableStatement
        CallableStatement cs = null; // and ResultSet
        try {
            cs = c.prepareCall(getProcCallWithoutParam(P2Util.PACKAGE_NAME, P2Util.GET_LOGS)); // Build the procedure
                                                                                               // call
            cs.registerOutParameter(1, OracleTypes.CURSOR); // Register a cursor as output
            cs.execute(); // Execute and print the results
            rs = (ResultSet) cs.getObject(1);

            System.out.println(String.format("Logs Details:\n%-6s %-20s %-20s %-15s %-20s %-15s", "log#", "USER_NAME",
                    "OPERATION", "OP_TIME", "TABLE_NAME", "TUPLE_PKEY"));
            while (rs.next()) {
                System.out.println(String.format("%-6s %-20s %-20s %-15s %-20s %-15s",
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6)));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage()); // Catch exceptions
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            if (rs != null) { // Close ResultSet
                rs.close(); // and CallableStatement
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Execute the monthly sales report query for a user-given EID.
     * 
     * @param eid The EID of the employee.
     * @throws SQLException if thrown during database access
     */
    public static void getMonthlySalesReport(String eid) throws SQLException {
        ResultSet rs = null; // Prepare CallableStatement
        CallableStatement cs = null; // and ResultSet
        try {
            cs = c.prepareCall(P2DB.getProcCallWithParam(P2Util.PACKAGE_MONTHLY_SALES, P2Util.GET_MONTHLY_SALE)); // Build
                                                                                                                  // the
                                                                                                                  // procedure
                                                                                                                  // call
            cs.registerOutParameter(1, OracleTypes.CURSOR); // Register a cursor as output
            cs.setString(2, eid);
            cs.execute(); // Execute and print the results
            rs = (ResultSet) cs.getObject(1);
            if (!rs.isBeforeFirst()) {
                System.out.println("\nNo records found for the given EID..."); // If there are no results print an error
            } else if (rs != null) {
                System.out.printf("\n%-10s %-20s %-10s %-10s %-20s %-15s %-15s%n",
                        "Emp ID", "Emp Name", "Month", "Year", "No. of Purchases", "Quantity", "Total Amount");
                while (rs.next()) {
                    System.out.printf("%-10s %-20s %-10s %-10s %-20s %-15s %-15s%n",
                            rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getInt(5), rs.getDouble(6), rs.getDouble(7));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage()); // Catch exceptions
        } finally {
            if (rs != null) {
                rs.close(); // Close ResultSet
            } // and CallableStatement
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Add a new employee tuple to the database.
     * 
     * @param eid   User-provided EID
     * @param name  User-provided Name
     * @param phone User-provided Phone Number
     * @param email User-Provided Email Address
     * @throws SQLException if thrown during database access
     */
    public static void addEmployee(String eid, String name, String phone, String email) throws SQLException {
        ResultSet rs = null; // Prepare CallableStatement
        CallableStatement cs = null; // and ResultSet

        String s1 = "select eid from employees";
        cs = c.prepareCall(s1); // Execute a query to check if
                                // an employee with this EID already exists
        ResultSet rs1 = cs.executeQuery();
        while (rs1.next()) {
            if (rs1.getString(1).equals(eid)) {
                System.out.println("\n\nError: " + eid + " already exist!!"); // Print an error if employee already
                                                                              // exists
                rs1.close();
                return;
            }
        }

        try {
            cs = c.prepareCall("{ call my_package2.add_employee(?,?,?,?) }"); // Prepare procedure call string
            cs.setString(1, eid);
            cs.setString(2, name);
            cs.setString(3, phone);
            cs.setString(4, email);
            cs.executeUpdate(); // Execute query
            System.out.println("\nEmployee added successfully");
            System.out.println("TRIGGER: Logs updated with the ID: " + eid); // Show that add was successful
        } catch (SQLException se) { // and triggers were called
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage()); // Catch exceptions
        } finally {
            if (rs != null) { // Close ResultSet
                rs.close(); // and CallableStatement
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    /**
     * Check the stock of a given product.
     * 
     * @param pid PID of product to check
     * @return Return the stock
     * @throws SQLException if thrown during database access
     */
    public static int verifyStock(String pid) throws SQLException {
        System.out.println("Verifying stock");
        Statement st = null; // Prepare Statement
        ResultSet rs = null; // and ResultSet
        try {
            st = c.createStatement();
            String sql_query = "select QOH from Products where PID = '" + pid + "'"; // Build procedure call to check
                                                                                     // quantity on hand of product
            rs = st.executeQuery(sql_query); // Execute the query
            if (rs.next()) {
                stock = rs.getInt(1); // Set stock equal to the result
                System.out.println("\nAvailable Stock is " + rs.getInt(1)); // Display results
            }
        } catch (Exception e) {
            System.out.println("Exception in verifyStock: " + e.getMessage()); // Catch exceptions
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close(); // close ResultSet
            } // and Statement
            if (st != null) {
                st.close();
            }
        }
        return stock; // Return the resulting stock value
    }

    /**
     * Add a new purchase tuple to the database.
     * 
     * @param eid       The EID of employee in purchase
     * @param pid       The PID of product being purchased
     * @param cid       The CID of customer in purchase
     * @param qnty      The quantity of product being purchased
     * @param unitPrice The unit price of product being purchased
     * @throws SQLException if thrown during database access
     */
    public static void addPurchase(
            String eid, String pid, String cid, int qnty, Double unitPrice) throws SQLException {
        CallableStatement cs = null;
        ResultSet rs1 = null; // Prepare CallableStatement
                              // and ResultSet
        String s1a = "select eid from employees"; // Build query to check that employee exists
        cs = c.prepareCall(s1a);
        rs1 = cs.executeQuery(); // Execute this query
        boolean isEmpExist = false;
        while (rs1.next()) {
            if (rs1.getString(1).equals(eid)) { // ensure that the employee exists
                isEmpExist = true; // by checking that there are results
            } // from the query
        }
        if (!isEmpExist) {
            System.out.println("Employee Doesn't exist..!!"); // Print an error if the employee doesn't exist
            return; // and return from the function
        }

        String s1b = "select cid from customers";
        cs = c.prepareCall(s1b); // Build query to check that customer exists
        rs1 = cs.executeQuery(); // Execute this query
        boolean isCusExist = false;
        while (rs1.next()) {
            if (rs1.getString(1).equals(cid)) { // ensure that the customer exists
                isCusExist = true; // by checking that there are results
            } // from the query
        }
        if (!isCusExist) {
            System.out.println("Customer Doesn't exist..!!"); // Print an error if the customer doesn't exist
            return; // and return from the function
        }

        String s1c = "select pid from products";
        cs = c.prepareCall(s1c); // Build query to check that product exists
        rs1 = cs.executeQuery(); // Execute this query
        boolean isProExist = false;
        while (rs1.next()) {
            if (rs1.getString(1).equals(pid)) { // ensure that the product exists
                isProExist = true; // by checking that there are results
            } // from the query
        }
        if (!isProExist) {
            System.out.println("Product Doesn't exist..!!"); // Print an error if the product doesn't exist
            return; // and return from the function
        }

        int qohThreshold = 0; // Prepare qoh threshold
        int currentStock = P2DB.verifyStock(pid); // Get the quantity on hand of the product
        if (currentStock >= qnty) { // If there is enough in stock to complete the purchase,
            cs = c.prepareCall("{call proc_add_purchases.add_purchase(?, ?, ?, ?, ?)}"); // prepare the procedure call
                                                                                         // to add a new purchase
            cs.setString(1, eid);
            cs.setString(2, pid);
            cs.setString(3, cid);
            cs.setInt(4, qnty);
            cs.setDouble(5, unitPrice);
            cs.executeQuery(); // Execute the query

            CallableStatement cs1 = null;

            String s1 = "select qoh_threshold from products where PID = '" + pid + "'"; // Retrieve the QOH threshold
                                                                                        // from the database
            cs1 = c.prepareCall(s1);

            rs1 = cs1.executeQuery();
            while (rs1.next()) {
                System.out.println("Threshold Value is " + rs1.getInt(1));
                System.out.println();
                qohThreshold = rs1.getInt(1);
            }

            int remainingQnty = currentStock - qnty; // Calculate the remaining quantity
            if (remainingQnty >= qohThreshold) { // If the remaining quantity is greater than or equal
                System.out.println("Product Added Successfully!"); // to the threshold
                System.out.println("TRIGGER: Log entry has been inserted for the purchase with pur#: " + pid);
                // display messages indicating successful tuple add
                System.out.println("Customer's visit made count updated..."); // and additional updates to database from
                                                                              // triggers
                System.out.println(
                        "TRIGGER: Log entries (visits_made & last_visit_date) has been updated for the customer with cid:"
                                + cid);
            } else { // If the remaining quantity is less than the threshold
                System.out.println(
                        "The current qoh of the product is below the required threshold and new supply is required");
                // Notify the user that a resupply of the product is done
                System.out.println("new value of the qoh of the product has been updated...");
                System.out.println("Customer's visit made count updated..."); // and the qoh is updated due to this.
                                                                              // Also print details
                                                                              // of the updates due to insertion and
                                                                              // updates from triggers
                System.out.println(
                        "TRIGGER: Log entries (visits_made & last_visit_date) has been updated for the customer with cid:"
                                + cid);
                System.out.println("TRIGGER: Log entry has been updated for the product..");
            }
        } else if (qnty > currentStock) { // If there is not enough quantity in stock to complete the purchase,
            System.out.println("Insufficient quantity in stock!"); // notify the user that there is insufficient
                                                                   // quantity and do not
        } // perform the insertion.
        else {
            System.out.println("Unexpected Error..!");
        }
    }
}
