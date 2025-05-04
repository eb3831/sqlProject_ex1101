package com.example.ex18;

/**
 * The Employees class defines the structure and constants for the Employees table in the database.
 * It contains static final String variables representing the table name and column names.
 * This class ensures consistency and prevents typos when referring to the Employees table and its columns.
 */
public class Employees
{
    // The name of the Employees table in the database.
    public static final String TABLE_EMPLOYEES = "Employees";

    // The name of the Card_Number column in the Employees table.
    // This column stores the unique card number assigned to the employee.
    public static final String CARD_NUMBER = "Card_Number";

    // The name of the Last_Name column in the Employees table.
    // This column stores the employee's last name.
    public static final String LAST_NAME = "Last_Name";

    // The name of the First_Name column in the Employees table.
    // This column stores the employee's first name.
    public static final String FIRST_NAME = "First_Name";

    // The name of the Company column in the Employees table.
    // This column stores the name of the company the employee belongs to.
    public static final String COMPANY = "Company";

    // The name of the _id column in the Employees table.
    // This column stores the internal unique ID for the employee.
    public static final String EMPLOYEE_KEY_ID = "_id";

    // The name of the Phone_Number column in the Employees table.
    // This column stores the employee's phone number.
    public static final String PHONE_NUMBER = "Phone_Number";
}