package com.example.ex18;

/**
 * The Orders class defines the structure and constants for the Orders table in the database.
 * It contains static final String variables representing the table name and column names.
 * This class is used to ensure consistency and avoid typos when referring to the Orders table and its columns.
 */
public class Orders
{
    // The name of the Orders table in the database.
    public static final String TABLE_ORDERS = "Orders";

    // The name of the Order_Date column in the Orders table.
    // This column stores the date of the order.
    public static final String ORDER_DATE = "Order_Date";

    // The name of the Order_Time column in the Orders table.
    // This column stores the time of the order.
    public static final String ORDER_TIME = "Order_Time";


    // The name of the Employee_Card_Number column in the Orders table.
    // This column stores the card number of the employee who placed the order.
    public static final String EMPLOYEE_CARD_NUMBER = "Employee_Card_Number";

    // The name of the Meal_Id column in the Orders table.
    // This column stores the ID of the meal that was ordered.
    public static final String MEAL_ID = "Meal_Id";

    // The name of the Supplier_Id column in the Orders table.
    // This column stores the ID of the supplier from whom the meal was ordered.
    public static final String SUPPLIER_ID = "Supplier_Id";
}