drop sequence SEQLOG#;
drop sequence SEQPUR#;

drop PACKAGE FUN_ADD_EMPLOYEE;
drop PACKAGE FUN_SHOW_TABLES;
drop PACKAGE MONTHLY_SALES_PKG;
drop PACKAGE MY_PACKAGE2;
drop PACKAGE PROC_ADD_PURCHASES;
drop PACKAGE PROC_MONTHLY_SALE_ACTIVITIES;
drop PACKAGE PROC_SHOW_TABLES;



CREATE SEQUENCE seqpur#
 	 START WITH 10015
  	INCREMENT BY 1
  	NOCACHE
  	NOCYCLE;

CREATE SEQUENCE seqlog#
  	START WITH 1001
  	INCREMENT BY 1
  	NOCACHE
  	NOCYCLE;


set echo on
SET SERVEROUTPUT ON

--- Q2 ---
--- procedure to show the tuples in each of the six tables STARTS ---
CREATE OR REPLACE PACKAGE proc_show_tables AS

  PROCEDURE show_employees;
  PROCEDURE show_customers;
  PROCEDURE show_products;
  PROCEDURE show_prod_discnt;
  PROCEDURE show_purchases;
  PROCEDURE show_logs;

END proc_show_tables;
/

CREATE OR REPLACE PACKAGE BODY proc_show_tables AS

  PROCEDURE show_employees AS
  BEGIN
    FOR emp IN (SELECT * FROM Employees) LOOP
      DBMS_OUTPUT.PUT_LINE('EID: ' || emp.eid || ', Name: ' || emp.name ||
                           ', Telephone#: ' || emp.telephone# || ', Email: ' || emp.email);
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
  END show_employees;

  PROCEDURE show_customers AS
  BEGIN
    FOR cust IN (SELECT * FROM Customers) LOOP
      DBMS_OUTPUT.PUT_LINE('CID: ' || cust.cid || ', First Name: ' || cust.first_name ||
                           ', Last Name: ' || cust.last_name || ', Phone#: ' || cust.phone# ||
                           ', Visits Made: ' || cust.visits_made || ', Last Visit Date: ' || cust.last_visit_date);
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
  END show_customers;

  PROCEDURE show_products AS
  BEGIN
    FOR prod IN (SELECT * FROM Products) LOOP
      DBMS_OUTPUT.PUT_LINE('PID: ' || prod.pid || ', Name: ' || prod.name ||
                           ', QOH: ' || prod.qoh || ', QOH Threshold: ' || prod.qoh_threshold ||
                           ', Original Price: ' || prod.orig_price || ', Discount Category: ' || prod.discnt_category);
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
  END show_products;

  PROCEDURE show_prod_discnt AS
  BEGIN
    FOR discnt IN (SELECT * FROM Prod_Discnt) LOOP
      DBMS_OUTPUT.PUT_LINE('Discount Category: ' || discnt.discnt_category ||
                           ', Discount Rate: ' || discnt.discnt_rate);
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
  END show_prod_discnt;

  PROCEDURE show_purchases AS
  BEGIN
    FOR pur IN (SELECT * FROM Purchases) LOOP
      DBMS_OUTPUT.PUT_LINE('Purchase #: ' || pur.pur# || ', EID: ' || pur.eid ||
                           ', PID: ' || pur.pid || ', CID: ' || pur.cid ||
                           ', Purchase Time: ' || pur.pur_time || ', Quantity: ' || pur.quantity ||
                           ', Unit Price: ' || pur.unit_price || ', Payment: ' || pur.payment ||
                           ', Saving: ' || pur.saving);
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
  END show_purchases;

  PROCEDURE show_logs AS
  BEGIN
    FOR log IN (SELECT * FROM Logs) LOOP
      DBMS_OUTPUT.PUT_LINE('Log #: ' || log.log# || ', User Name: ' || log.user_name ||
                           ', Operation: ' || log.operation || ', Operation Time: ' || log.op_time ||
                           ', Table Name: ' || log.table_name || ', Tuple Primary Key: ' || log.tuple_pkey);
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
  END show_logs;

END proc_show_tables;
/
--- procedure to show the tuples in each of the six tables ENDS ---

--- functions to show the tuples in each of the six tables STARTS ---
CREATE OR REPLACE PACKAGE fun_show_tables AS

  FUNCTION show_employees RETURN SYS_REFCURSOR;
  FUNCTION show_customers RETURN SYS_REFCURSOR;
  FUNCTION show_products RETURN SYS_REFCURSOR;
  FUNCTION show_prod_discnt RETURN SYS_REFCURSOR;
  FUNCTION show_purchases RETURN SYS_REFCURSOR;
  FUNCTION show_logs RETURN SYS_REFCURSOR;

END fun_show_tables;
/

CREATE OR REPLACE PACKAGE BODY fun_show_tables AS

  FUNCTION show_employees RETURN SYS_REFCURSOR AS
    cur SYS_REFCURSOR;
  BEGIN
    OPEN cur FOR SELECT * FROM Employees;
    RETURN cur;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
      RETURN NULL;
  END show_employees;

  FUNCTION show_customers RETURN SYS_REFCURSOR AS
    cur SYS_REFCURSOR;
  BEGIN
    OPEN cur FOR SELECT * FROM Customers;
    RETURN cur;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
      RETURN NULL;
  END show_customers;

  FUNCTION show_products RETURN SYS_REFCURSOR AS
    cur SYS_REFCURSOR;
  BEGIN
    OPEN cur FOR SELECT * FROM Products;
    RETURN cur;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
      RETURN NULL;
  END show_products;

  FUNCTION show_prod_discnt RETURN SYS_REFCURSOR AS
    cur SYS_REFCURSOR;
  BEGIN
    OPEN cur FOR SELECT * FROM Prod_Discnt;
    RETURN cur;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
      RETURN NULL;
  END show_prod_discnt;

  FUNCTION show_purchases RETURN SYS_REFCURSOR AS
    cur SYS_REFCURSOR;
  BEGIN
    OPEN cur FOR SELECT * FROM Purchases;
    RETURN cur;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
      RETURN NULL;
  END show_purchases;

  FUNCTION show_logs RETURN SYS_REFCURSOR AS
    cur SYS_REFCURSOR;
  BEGIN
    OPEN cur FOR SELECT * FROM Logs;
    RETURN cur;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
      RETURN NULL;
  END show_logs;

END fun_show_tables;
/
--- functions to show the tuples in each of the six tables ENDS ---



--- Q3 ---
---  procedure to report the monthly sale activities for any given employee STARTS ---
CREATE OR REPLACE PACKAGE proc_monthly_sale_activities AS
  PROCEDURE monthly_sale_activities(employee_id IN VARCHAR2);
END proc_monthly_sale_activities;
/

CREATE OR REPLACE PACKAGE BODY proc_monthly_sale_activities AS
  PROCEDURE monthly_sale_activities(employee_id IN VARCHAR2) IS
    employee_name VARCHAR2(50);
    total_sales NUMBER;
    total_quantity NUMBER;
    total_amount NUMBER(10,2);
    month_year VARCHAR2(8);
    CURSOR cur IS
      SELECT TO_CHAR(pur_time, 'MON-YYYY') month_year,
             COUNT(*) total_sales,
             SUM(quantity) total_quantity,
             SUM(unit_price * quantity) total_amount
        FROM Purchases
       WHERE eid = employee_id
       GROUP BY TO_CHAR(pur_time, 'MON-YYYY');
  BEGIN
    SELECT name INTO employee_name FROM Employees WHERE eid = employee_id;
    FOR rec IN cur LOOP
      month_year := rec.month_year;
      total_sales := rec.total_sales;
      total_quantity := rec.total_quantity;
      total_amount := rec.total_amount;
      DBMS_OUTPUT.PUT_LINE('Employee ID: ' || employee_id);
      DBMS_OUTPUT.PUT_LINE('Employee Name: ' || employee_name);
      DBMS_OUTPUT.PUT_LINE('Month-Year: ' || month_year);
      DBMS_OUTPUT.PUT_LINE('Total Sales: ' || total_sales);
      DBMS_OUTPUT.PUT_LINE('Total Quantity: ' || total_quantity);
      DBMS_OUTPUT.PUT_LINE('Total Amount: ' || total_amount);
      DBMS_OUTPUT.PUT_LINE('------------------------------');
    END LOOP;
  END monthly_sale_activities;
END proc_monthly_sale_activities;
/
---  procedure to report the monthly sale activities for any given employee ENDS ---

--- Function to report the monthly sale activities for any given employee STARTS ---
create or replace PACKAGE monthly_sales_pkg AS
  TYPE monthly_sales_cursor_type IS REF CURSOR;
  FUNCTION monthly_sale_activities(p_emp_id IN employees.eid%TYPE) RETURN monthly_sales_cursor_type;
END monthly_sales_pkg;
/

create or replace PACKAGE BODY monthly_sales_pkg AS
  FUNCTION monthly_sale_activities(p_emp_id IN employees.eid%TYPE) RETURN monthly_sales_cursor_type IS
    v_cursor monthly_sales_cursor_type;
  BEGIN
    OPEN v_cursor FOR
      WITH monthly_sales AS (
        SELECT
          purchases.eid,
          employees.name,
          TO_CHAR(purchases.pur_time, 'MON') AS month,
          TO_CHAR(purchases.pur_time, 'YYYY') AS year,
          COUNT(DISTINCT purchases.pur#) AS sales_count,
          SUM(purchases.quantity) AS quantity_sold,
          SUM(purchases.quantity * purchases.unit_price) AS sales_amount
        FROM purchases
        JOIN employees ON employees.eid = purchases.eid
        WHERE employees.eid = p_emp_id
        GROUP BY purchases.eid, employees.name, TO_CHAR(purchases.pur_time, 'MON'), TO_CHAR(purchases.pur_time, 'YYYY')
      )
      SELECT * FROM monthly_sales;
    RETURN v_cursor;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('No sales found for the employee with ID ' || p_emp_id);
      RETURN NULL;
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLCODE || ' - ' || SQLERRM);
      RETURN NULL;
  END monthly_sale_activities;
END monthly_sales_pkg;
/
--- Function to report the monthly sale activities for any given employee ENDS ---


--- Q4 ---
--- procedure for adding tuples to the Employees table STARTS ---
create or replace PACKAGE my_package2 AS
   PROCEDURE add_employee (
      e_id IN Employees.eid%TYPE,
      e_name IN Employees.name%TYPE,
      e_telephone IN Employees.telephone#%TYPE,
      e_email IN Employees.email%TYPE
   );
END my_package2;
/

create or replace PACKAGE BODY my_package2 AS
   PROCEDURE add_employee (
      e_id IN Employees.eid%TYPE,
      e_name IN Employees.name%TYPE,
      e_telephone IN Employees.telephone#%TYPE,
      e_email IN Employees.email%TYPE
   )
   IS
   BEGIN
      INSERT INTO Employees (eid, name, telephone#, email)
      VALUES (e_id, e_name, e_telephone, e_email);

      COMMIT;

      DBMS_OUTPUT.PUT_LINE('Employee added successfully');

      -- Add an ORDER BY clause to sort the output by eid
      FOR emp IN (SELECT * FROM Employees ORDER BY eid)
      LOOP
         DBMS_OUTPUT.PUT_LINE(emp.eid || ' ' || emp.name || ' ' || emp.telephone# || ' ' || emp.email);
      END LOOP;
   EXCEPTION
      WHEN OTHERS THEN
         DBMS_OUTPUT.PUT_LINE('Error adding employee: ' || SQLERRM);
         ROLLBACK;
   END add_employee;
END my_package2;
/
--- procedure for adding tuples to the Employees table ENDS ---

--- Function STARTS ---
create or replace PACKAGE FUN_ADD_EMPLOYEE AS
    -- Exception for when an employee with the same ID already exists
    DuplicateEmployeeException EXCEPTION;
    PRAGMA EXCEPTION_INIT(DuplicateEmployeeException, -20001);

    -- Function to add an employee to the Employees table
    FUNCTION add_employee(
        e_id IN Employees.eid%TYPE,
        e_name IN Employees.name%TYPE,
        e_telephone# IN Employees.telephone#%TYPE,
        e_email IN Employees.email%TYPE
    ) RETURN VARCHAR2;
END FUN_ADD_EMPLOYEE;
/

create or replace PACKAGE BODY FUN_ADD_EMPLOYEE AS
    -- Function to add an employee to the Employees table
    FUNCTION add_employee(
        e_id IN Employees.eid%TYPE,
        e_name IN Employees.name%TYPE,
        e_telephone# IN Employees.telephone#%TYPE,
        e_email IN Employees.email%TYPE
    ) RETURN VARCHAR2 AS
        -- Declare a REF CURSOR to return the result
        c_result SYS_REFCURSOR;
        v_exists NUMBER;
    BEGIN
        -- Check if the employee already exists
        SELECT COUNT(*) INTO v_exists FROM Employees WHERE eid = e_id;
        IF v_exists > 0 THEN
            -- Raise an exception if the employee already exists
            RAISE DuplicateEmployeeException;
        ELSE
            -- Insert the employee into the Employees table
            INSERT INTO Employees (eid, name, telephone#, email)
            VALUES (e_id, e_name, e_telephone#, e_email);
            COMMIT;

            -- Return success message
            RETURN 'Employee added successfully';
        END IF;
    EXCEPTION
        WHEN DuplicateEmployeeException THEN
            -- Handle the duplicate employee exception
            RETURN 'Employee with the same ID already exists';
        WHEN OTHERS THEN
            -- Handle any other exceptions
            ROLLBACK;
            RETURN 'Error: ' || SQLERRM;
    END add_employee;
END FUN_ADD_EMPLOYEE;
/
--- Function ENDS ---

--- Q5 ---
--- Proc STARTS ---
create or replace PACKAGE proc_add_purchases AS

  PROCEDURE add_purchase(e_id IN CHAR, p_id IN CHAR, c_id IN CHAR,
                          pur_qty IN NUMBER, pur_unit_price IN NUMBER);

END proc_add_purchases;
/

create or replace PACKAGE BODY proc_add_purchases AS 
  PROCEDURE add_purchase(e_id IN CHAR, p_id IN CHAR, c_id IN CHAR, pur_qty IN NUMBER, pur_unit_price IN NUMBER) IS 
    v_pur# NUMBER;
    v_payment NUMBER;
    v_saving NUMBER;
    v_qoh NUMBER;
    v_qoh_threshold NUMBER;
    v_last_visit_date DATE;
  BEGIN 
    -- Generate new purchase number
    SELECT seqpur#.NEXTVAL INTO v_pur# FROM dual;

    -- Compute payment and saving
    SELECT pur_qty * pur_unit_price INTO v_payment FROM dual;
    SELECT (orig_price - pur_unit_price) * pur_qty INTO v_saving FROM products WHERE pid = p_id;

    -- Check if quantity to be purchased is available
    SELECT qoh, qoh_threshold INTO v_qoh, v_qoh_threshold FROM products WHERE pid = p_id;
    IF pur_qty > v_qoh THEN 
      DBMS_OUTPUT.PUT_LINE('Insufficient quantity in stock.');
      RETURN;
    END IF;

    -- Add new purchase tuple
    INSERT INTO purchases(pur#, eid, pid, cid, pur_time, quantity, unit_price, payment, saving) 
    VALUES(v_pur#, e_id, p_id, c_id, sysdate, pur_qty, pur_unit_price, v_payment, v_saving);

    -- Update qoh and check threshold
    UPDATE products SET qoh = qoh - pur_qty WHERE pid = p_id;
    SELECT qoh INTO v_qoh FROM products WHERE pid = p_id;
    IF v_qoh < v_qoh_threshold THEN 
      DBMS_OUTPUT.PUT_LINE('The current qoh of the product is below the required threshold and new supply is required.');
      UPDATE products SET qoh = v_qoh_threshold + 20 WHERE pid = p_id;
      DBMS_OUTPUT.PUT_LINE('The new value of qoh is ' || (v_qoh_threshold + 20));
    END IF;

    -- Update customer visits and last visit date
    SELECT last_visit_date INTO v_last_visit_date FROM customers WHERE cid = c_id;
    IF v_last_visit_date IS NULL OR TRUNC(v_last_visit_date) <> TRUNC(sysdate) THEN 
      UPDATE customers SET visits_made = visits_made + 1, last_visit_date = sysdate WHERE cid = c_id;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Purchase added successfully.');
  EXCEPTION 
    WHEN OTHERS THEN 
      DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
  END add_purchase;
END proc_add_purchases;
/
--- Proc ENDS ---