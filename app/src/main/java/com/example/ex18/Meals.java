package com.example.ex18;

/**
 * The Meals class defines the structure and constants for the Meals table in the database.
 * It contains static final String variables representing the table name and column names.
 * This class helps maintain consistency and avoid typos when working with the Meals table.
 */
public class Meals
{
    // The name of the Meals table in the database.
    public static final String TABLE_MEALS = "Meals";

    // The name of the _id column in the Meals table.
    // This column stores the unique ID of the meal.
    public static final String MEAL_KEY_ID = "_id";

    // The name of the Starter column in the Meals table.
    // This column stores the starter dish of the meal.
    public static final String STARTER = "Starter";

    // The name of the Main_Course column in the Meals table.
    // This column stores the main course of the meal.
    public static final String MAIN_COURSE = "Main_Course";

    // The name of the Side_dish column in the Meals table.
    // This column stores the side dish of the meal.
    public static final String SIDE_DISH = "Side_dish";

    // The name of the Dessert column in the Meals table.
    // This column stores the dessert of the meal.
    public static final String DESSERT = "Dessert";

    // The name of the Drink column in the Meals table.
    // This column stores the drink included in the meal.
    public static final String DRINK = "Drink";
}
