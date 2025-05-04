package com.example.ex18;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    Intent gi;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    Spinner spinner2;
    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter<String> adp;
    ListView lv;
    boolean isFirstSelection = true;

    ArrayList<String> employeeList, mealsArray, foodSuppliersArray, ordersArray;
    ArrayList<String> optionsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        spinner2 = findViewById(R.id.spinner2);
        lv = findViewById(R.id.lv);

        initAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem)
    {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.menuCredits)
        {
            gi = new Intent(this, CreditsActivity.class);
            startActivity(gi);

        }

        else if (itemId == R.id.menuMain)
        {
            gi = new Intent(this, MainActivity.class);
            startActivity(gi);

        }

        else if (itemId == R.id.menuShowSortDate)
        {
            gi = new Intent(this, SortDataActivity.class);
            startActivity(gi);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void initAll()
    {
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        employeeList = new ArrayList<>();
        ordersArray = new ArrayList<>();
        mealsArray = new ArrayList<>();
        foodSuppliersArray = new ArrayList<>();

        optionsArray = new ArrayList<>();
        optionsArray.add("choose the data you would like to see");
        optionsArray.add("employees");
        optionsArray.add("food suppliers");
        optionsArray.add("meals");
        optionsArray.add("orders");

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, optionsArray);
        spinner2.setAdapter(spinnerAdapter);

        spinner2.setOnItemSelectedListener(this);
        lv.setOnItemClickListener(this);

        getEmployees();
        getMeals();
        getFoodSuppliers();
        getOrders();
    }

    private void getEmployees()
    {
        db = hlp.getReadableDatabase();
        crsr = db.query(Employees.TABLE_EMPLOYEES, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Employees.EMPLOYEE_KEY_ID);
        int col2 = crsr.getColumnIndex(Employees.COMPANY);
        int col3 = crsr.getColumnIndex(Employees.FIRST_NAME);
        int col4 = crsr.getColumnIndex(Employees.LAST_NAME);
        int col5 = crsr.getColumnIndex(Employees.PHONE_NUMBER);
        int col6 = crsr.getColumnIndex(Employees.CARD_NUMBER);

        crsr.moveToFirst();
        employeeList.add("EMPLOYEES");
        while (!crsr.isAfterLast())
        {
            employeeList.add(crsr.getString(col1) + ", " + crsr.getString(col2) + ", " + crsr.getString(col3) + ", "
                    + crsr.getString(col4) + ", " + crsr.getString(col5) + ", " + crsr.getString(col6));
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    private void getFoodSuppliers()
    {
        db = hlp.getReadableDatabase();
        crsr = db.query(FoodSuppliers.TABLE_FOOD_SUPPLIERS, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(FoodSuppliers.SUPPLIER_KEY_ID);
        int col2 = crsr.getColumnIndex(FoodSuppliers.NAME);
        int col3 = crsr.getColumnIndex(FoodSuppliers.MAIN_PHONE);
        int col4 = crsr.getColumnIndex(FoodSuppliers.SECONDARY_PHONE);

        crsr.moveToFirst();
        foodSuppliersArray.add("FOOD SUPPLIERS");
        while (!crsr.isAfterLast())
        {
            String record = "ID: " + crsr.getString(col1) + "\n" +
                    "Name: " + crsr.getString(col2) + "\n" +
                    "Phone 1: " + crsr.getString(col3) + "\n" +
                    "2 Phone: " + crsr.getString(col4);
            foodSuppliersArray.add(record);
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    private void getMeals()
    {
        db = hlp.getReadableDatabase();
        crsr = db.query(Meals.TABLE_MEALS, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Meals.MEAL_KEY_ID);
        int col2 = crsr.getColumnIndex(Meals.STARTER);
        int col3 = crsr.getColumnIndex(Meals.MAIN_COURSE);
        int col4 = crsr.getColumnIndex(Meals.SIDE_DISH);
        int col5 = crsr.getColumnIndex(Meals.DESSERT);
        int col6 = crsr.getColumnIndex(Meals.DRINK);

        crsr.moveToFirst();
        mealsArray.add("MEALS");
        while (!crsr.isAfterLast())
        {
            String str = "Meal ID: " + crsr.getString(col1) + "\n" +
                    "Starter: " + crsr.getString(col2) + "\n" +
                    "Main Course: " + crsr.getString(col3) + "\n" +
                    "Side: " + crsr.getString(col4) + "\n" +
                    "Dessert: " + crsr.getString(col5) + "\n" +
                    "Drink: " + crsr.getString(col6);
            mealsArray.add(str);
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    private void getOrders()
    {
        db = hlp.getReadableDatabase();
        crsr = db.query(Orders.TABLE_ORDERS, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Orders.ORDER_DATE);
        int col2 = crsr.getColumnIndex(Orders.ORDER_TIME);
        int col3 = crsr.getColumnIndex(Orders.EMPLOYEE_CARD_NUMBER);
        int col4 = crsr.getColumnIndex(Orders.MEAL_ID);
        int col5 = crsr.getColumnIndex(Orders.SUPPLIER_ID);

        crsr.moveToFirst();
        ordersArray.add("ORDERS");
        while (!crsr.isAfterLast())
        {
            String str = "Date: " + crsr.getString(col1) + "\n" +
                    "Time: " + crsr.getString(col2) + "\n" +
                    "Worker ID: " + crsr.getString(col3) + "\n" +
                    "Meal ID: " + crsr.getString(col4) + "\n" +
                    "Company: " + crsr.getString(col5);
            ordersArray.add(str);
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (isFirstSelection)
        {
            isFirstSelection = false;
            return;
        }

        switch (position)
        {
            case 0:
                Toast.makeText(this, "Please choose an option!", Toast.LENGTH_SHORT).show();
                break;

            case 1:
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);
                break;

            case 2:
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodSuppliersArray);
                break;

            case 3:
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mealsArray);
                break;

            case 4:
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ordersArray);
                break;
        }

        lv.setAdapter(adp);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
}
