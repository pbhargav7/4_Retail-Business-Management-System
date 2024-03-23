set echo on
SET SERVEROUTPUT ON

--- Q4 ---
--- trigger that can add a tuple to the logs table automatically whenever a new employee is added to the Employees table STARTS ---
CREATE OR REPLACE TRIGGER trg_add_employee
AFTER INSERT ON Employees
FOR EACH ROW
DECLARE
    v_user_name VARCHAR2(30) := USER;
BEGIN
    -- Insert log entry for the new employee
    INSERT INTO logs (log#, user_name, operation, op_time, table_name, tuple_pkey)
    VALUES (seqlog#.nextval, v_user_name, 'insert', SYSDATE, 'employees', :NEW.eid);

    DBMS_OUTPUT.PUT_LINE('New employee added with ID ' || :NEW.eid);
EXCEPTION
    WHEN OTHERS THEN
        -- Display user-friendly error message
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLCODE || ' - ' || SQLERRM);
END;
/
--- trigger that can add a tuple to the logs table automatically whenever a new employee is added to the Employees table ENDS ---


--- Q6 ---
--- (1) update the last_visit_date attribute of the Customers table ---
create or replace TRIGGER log_last_visit_date_update
AFTER UPDATE OF last_visit_date ON customers
FOR EACH ROW
DECLARE
  v_log_number logs.log#%TYPE;
BEGIN
  -- Insert a new row into the Logs table
  INSERT INTO logs(log#, user_name, operation, op_time, table_name, tuple_pkey)
  VALUES(seqlog#.NEXTVAL, USER, 'update', SYSDATE, 'customers', :OLD.cid);
  -- Print message for log entry
  DBMS_OUTPUT.PUT_LINE('Log entry has been updated for the customer with cid ' || :OLD.cid || '.');

EXCEPTION
  -- Handle exceptions
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error logging last_visit_date update: ' || SQLCODE || ' - ' || SQLERRM);
END;
/

--- (2) update the visits_made attribute of the Customers table ---
create or replace TRIGGER log_visits_made_update
AFTER UPDATE OF visits_made ON customers
FOR EACH ROW
DECLARE
  v_log_number logs.log#%TYPE;
BEGIN
  -- Insert a new row into the Logs table
  INSERT INTO logs(log#, user_name, operation, op_time, table_name, tuple_pkey)
  VALUES(seqlog#.NEXTVAL, USER, 'update', SYSDATE, 'customers', :OLD.cid);
EXCEPTION
  -- Handle exceptions
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error logging visits_made update: ' || SQLCODE || ' - ' || SQLERRM);
END;
/

--- (3) insert a tuple into the Purchases table ---
create or replace TRIGGER trg_insert_logs
AFTER INSERT ON Purchases
FOR EACH ROW
DECLARE
  v_error_msg VARCHAR2(200); -- Error message variable
BEGIN
  -- Insert log entry for the purchase operation
  INSERT INTO Logs(log#, user_name, operation, op_time, table_name, tuple_pkey)
  VALUES(seqlog#.NEXTVAL, USER, 'INSERT', SYSDATE, 'Purchases', :NEW.pur#);

  -- Print message for log entry
  DBMS_OUTPUT.PUT_LINE('Log entry has been inserted for the purchase with pur# ' || :NEW.pur# || '.');
EXCEPTION
  WHEN OTHERS THEN -- Handle all exceptions
    v_error_msg := 'Error: ' || SQLERRM;
    DBMS_OUTPUT.PUT_LINE(v_error_msg);
END;
/

--- (4) update the qoh attribute of the Products table --- 
create or replace TRIGGER log_qoh_update
AFTER UPDATE OF qoh ON products
FOR EACH ROW
DECLARE
  v_log_number logs.log#%TYPE;
BEGIN
  -- Insert a new row into the Logs table
  INSERT INTO logs(log#, user_name, operation, op_time, table_name, tuple_pkey)
  VALUES(seqlog#.NEXTVAL, USER, 'update', SYSDATE, 'products', :OLD.pid);
  -- Print message for log entry
  DBMS_OUTPUT.PUT_LINE('Log entry has been updated for the product with pid ' || :OLD.pid || '.');
EXCEPTION
  -- Handle exceptions
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error logging qoh update: ' || SQLCODE || ' - ' || SQLERRM);
END;

