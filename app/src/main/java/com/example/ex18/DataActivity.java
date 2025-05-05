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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * DataActivity class.
 * This activity displays data from different tables in the database.
 * It allows the user to select which data to view using a spinner and displays the selected data in a ListView.
 * The activity also provides navigation to other activities through the options menu.
 *
 * @author eliya bitton eb3831@bs.amalnet.k12.il
 * @version 2.0
 * @since 29/4/2025
 *
 */
public class DataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener
{

    Intent gi;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    Spinner spinner2;
    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter<String> adp;
    ListView lv;
    boolean isFirstSelection = true;
    TextView tv;

    ArrayList<String> employeeList, mealsArray, foodSuppliersArray, ordersArray;
    ArrayList<String> optionsArray;

    /**
     * Called when the activity is first created.
     * Initializes the activity, sets the content view, and initializes UI elements.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        spinner2 = findViewById(R.id.spinner2);
        lv = findViewById(R.id.lv2);
        tv = findViewById(R.id.tv);

        initAll();
    }

    /**
     * Initializes the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     *         if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param menuItem The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
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

    /**
     * Initializes all necessary components for the activity.
     * This includes setting up the database, initializing ArrayLists, and setting up the spinner and ListView.
     */
    private void initAll()
    {
        tv.setText("");

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

    /**
     * Retrieves employee data from the database and populates the employeeList.
     */
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
        while (!crsr.isAfterLast())
        {
            employeeList.add(crsr.getString(col1) + ", " + crsr.getString(col2) + ", " + crsr.getString(col3) + ", "
                    + crsr.getString(col4) + ", " + crsr.getString(col5) + ", " + crsr.getString(col6));
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    /**
     * Retrieves food supplier data from the database and populates the foodSuppliersArray.
     */
    private void getFoodSuppliers()
    {
        db = hlp.getReadableDatabase();
        crsr = db.query(FoodSuppliers.TABLE_FOOD_SUPPLIERS, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(FoodSuppliers.SUPPLIER_KEY_ID);
        int col2 = crsr.getColumnIndex(FoodSuppliers.NAME);
        int col3 = crsr.getColumnIndex(FoodSuppliers.MAIN_PHONE);
        int col4 = crsr.getColumnIndex(FoodSuppliers.SECONDARY_PHONE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            String str = "ID: " + crsr.getString(col1) + "\n" +
                    "Name: " + crsr.getString(col2) + "\n" +
                    "Main phone: " + crsr.getString(col3) + "\n" +
                    "Sec phone: " + crsr.getString(col4);
            foodSuppliersArray.add(str);
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    /**
     * Retrieves meal data from the database and populates the mealsArray.
     */
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

    /**
     * Retrieves order data from the database and populates the ordersArray.
     */
    private void getOrders()
    {
        db = hlp.getReadableDatabase();
        crsr = db.query(Orders.TABLE_ORDERS, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Orders.ORDER_DATE);
        int col2 = crsr.getColumnIndex(Orders.ORDER_TIME);
        int col3 = crsr.getColumnIndex(Orders.EMPLOYEE_CARD_NUMBER);
        int col5 = crsr.getColumnIndex(Orders.SUPPLIER_ID);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            String str = "Date: " + crsr.getString(col1) + "\n" +
                    "Time: " + crsr.getString(col2) + "\n" +
                    "Worker ID: " + crsr.getString(col3) + "\n" +
                    "Company: " + crsr.getString(col5);
            ordersArray.add(str);
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    /**
     * Called when an item in the spinner is selected.
     * This method updates the ListView with the data corresponding to the selected item in the spinner.
     *
     * @param parent   The AdapterView where the selection happened.
     * @param view     The view within the AdapterView that was clicked.
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that is selected.
     */
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
                tv.setText("EMPLOYEES");
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);
                break;

            case 2:
                tv.setText("FOOD SUPPLIERS");
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodSuppliersArray);
                break;

            case 3:
                tv.setText("MEALS");
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mealsArray);
                break;

            case 4:
                tv.setText("ORDERS");
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ordersArray);
                break;
        }

        lv.setAdapter(adp);
    }

    /**
     * Called when nothing is selected in the spinner.
     * This method is part of the OnItemSelectedListener interface but is not used in this activity.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // do nothing //
    }

    /**
     * Called when an item in the ListView is clicked.
     * This method currently does nothing but can be used to handle item clicks in the ListView.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view that was clicked within the AdapterView.
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // do nothing //
    }
}
