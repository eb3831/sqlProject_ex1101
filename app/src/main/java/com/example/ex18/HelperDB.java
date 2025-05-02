package com.example.ex18;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperDB extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "DBfile.db";
    private static final int DATABASE_VERSION = 1;
    private static String strCreate, strDelete;

    public HelperDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the employees table
        strCreate = "CREATE TABLE " + Employees.TABLE_EMPLOYEES;
        strCreate += " (" + Employees.EMPLOYEE_KEY_ID + " TEXT PRIMARY KEY,";
        strCreate += " " + Employees.CARD_NUMBER + " INTEGER,";
        strCreate += " " + Employees.LAST_NAME + " TEXT,";
        strCreate += " " + Employees.FIRST_NAME + " TEXT,";
        strCreate += " " + Employees.COMPANY + " TEXT,";
        strCreate += " " + Employees.PHONE_NUMBER + " TEXT";
        strCreate += ");";
        db.execSQL(strCreate);

        // Creates the food suppliers table
        strCreate = "CREATE TABLE " + FoodSuppliers.TABLE_FOOD_SUPPLIERS;
        strCreate += " (" + FoodSuppliers.SUPPLIER_KEY_ID + " TEXT PRIMARY KEY,";
        strCreate += " " + FoodSuppliers.NAME + " TEXT,";
        strCreate += " " + FoodSuppliers.MAIN_PHONE + " TEXT,";
        strCreate += " " + FoodSuppliers.SECONDARY_PHONE + " TEXT";
        strCreate += ");";
        db.execSQL(strCreate);

        // Creates the meals table
        strCreate = "CREATE TABLE " + Meals.TABLE_MEALS;
        strCreate += " (" + Meals.MEAL_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        strCreate += " " + Meals.STARTER + " TEXT,";
        strCreate += " " + Meals.MAIN_COURSE + " TEXT,";
        strCreate += " " + Meals.SIDE_DISH + " TEXT,";
        strCreate += " " + Meals.DESSERT + " TEXT,";
        strCreate += " " + Meals.DRINK + " TEXT";
        strCreate += ");";
        db.execSQL(strCreate);

        // Creates the orders table
        strCreate = "CREATE TABLE " + Orders.TABLE_ORDERS;
        strCreate += " (" + Orders.ORDER_DATE + " TEXT,";
        strCreate += " " + Orders.ORDER_TIME + " TEXT,";
        strCreate += " " + Orders.EMPLOYEE_ID + " INTEGER,";
        strCreate += " " + Orders.MEAL_ID + " INTEGER,";
        strCreate += " " + Orders.SUPPLIER_ID + " INTEGER";
        strCreate += ");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete = "DROP TABLE IF EXISTS " + Employees.TABLE_EMPLOYEES;
        db.execSQL(strDelete);

        strDelete = "DROP TABLE IF EXISTS " + Orders.TABLE_ORDERS;
        db.execSQL(strDelete);

        strDelete = "DROP TABLE IF EXISTS " + FoodSuppliers.TABLE_FOOD_SUPPLIERS;
        db.execSQL(strDelete);

        strDelete = "DROP TABLE IF EXISTS " + Meals.TABLE_MEALS;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
