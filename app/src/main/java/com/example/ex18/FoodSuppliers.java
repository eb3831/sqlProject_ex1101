package com.example.ex18;

/**
 * The FoodSuppliers class defines the structure and constants for the Food_Suppliers table in the database.
 * It contains static final String variables representing the table name and column names.
 * This class ensures consistency and avoids typos when referring to the Food_Suppliers table and its columns.
 */
public class FoodSuppliers
{
    // The name of the Food_Suppliers table in the database.
    public static final String TABLE_FOOD_SUPPLIERS = "Food_Suppliers";

    // The name of the _id column in the Food_Suppliers table.
    // This column stores the unique ID of the supplier.
    public static final String SUPPLIER_KEY_ID = "_id";

    // The name of the Name column in the Food_Suppliers table.
    // This column stores the name of the supplier.
    public static final String NAME = "Name";

    // The name of the Main_Phone column in the Food_Suppliers table.
    // This column stores the main phone number of the supplier.
    public static final String MAIN_PHONE = "Main_Phone";

    // The name of the Secondary_Phone column in the Food_Suppliers table.
    // This column stores the secondary phone number of the supplier.
    public static final String SECONDARY_PHONE = "Secondary_Phone";
}
