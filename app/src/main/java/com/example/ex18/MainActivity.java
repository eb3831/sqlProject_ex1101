package com.example.ex18;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Intent gi;
    SQLiteDatabase db;
    HelperDB hlp;
    Spinner spinner, orderFoodSupplierSpinner, orderMealSpinner, orderEmployeeSpinner;
    String [] optionsArray = {"choose an option to add", "add employee", "add food supplier", "add meal", "add order"};
    AlertDialog.Builder adb;
    LinearLayout dialogView;
    EditText employeeCardNumberEt, employeeLastNameEt, employeeFirstNameEt, employeeCompanyEt,
            employeeIdentityEt, employeePhoneNumberEt, foodSupplierSecPhoneEt,
            foodSupplierTaxNumberEt, foodSupplierCompanyEt, foodSupplierMainPhoneEt, mealStarterEt,
            mealMainEt, mealSideEt, mealDessertEt, mealDrinkEt;
    ArrayList<String> employeeList, mealsList, foodSuppliersList;
    ArrayList<Integer> mealsIdsList;
    int mainSpinnerChosenOption;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);

        initAll();

    }



    /**
     * Creates the options menu.
     * @param menu The menu to be created.
     * @return true if the menu is created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles menu item selections.
     * @param menuItem The selected menu item.
     * @return true if the menu item is handled successfully.
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

        return super.onOptionsItemSelected(menuItem);
    }

    private void initAll()
    {
        mainSpinnerChosenOption = 0;

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, optionsArray);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(this);

        employeeList = new ArrayList<>();
        mealsIdsList = new ArrayList<>();
        mealsList = new ArrayList<>();
        foodSuppliersList = new ArrayList<>();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        mainSpinnerChosenOption = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    private void showEmployeeDialog()
    {
        dialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.input_employee_dialog, null);

        employeeCardNumberEt = dialogView.findViewById(R.id.employeeCardNumberEt);
        employeeLastNameEt = dialogView.findViewById(R.id.employeeLastNameEt);
        employeeFirstNameEt = dialogView.findViewById(R.id.employeeFirstNameEt);
        employeeCompanyEt = dialogView.findViewById(R.id.employeeCompanyEt);
        employeeIdentityEt = dialogView.findViewById(R.id.employeeIdentityEt);
        employeePhoneNumberEt = dialogView.findViewById(R.id.employeePhoneNumberEt);

        adb = new AlertDialog.Builder(this);
        adb.setTitle("Add Employee");
        adb.setView(dialogView);
        adb.setCancelable(false);

        adb.setPositiveButton("Save", (dialog, which) -> {
            if (isEmployeeDataValid())
            {
                saveEmployeeDataToDB();
                Toast.makeText(this, "Added new employee!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            }

        });
        adb.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        adb.show();
    }

    private boolean isEmployeeDataValid()
    {
        return !employeeCardNumberEt.getText().toString().isEmpty() &&
                !employeeLastNameEt.getText().toString().isEmpty() &&
                !employeeFirstNameEt.getText().toString().isEmpty() &&
                !employeeCompanyEt.getText().toString().isEmpty() &&
                !employeeIdentityEt.getText().toString().isEmpty() &&
                !employeePhoneNumberEt.getText().toString().isEmpty();
    }

    private void saveEmployeeDataToDB()
    {
        int cardNumber = Integer.parseInt(employeeCardNumberEt.getText().toString());
        String lastName = employeeLastNameEt.getText().toString();
        String firstName = employeeFirstNameEt.getText().toString();
        String company = employeeCompanyEt.getText().toString();
        String identity = employeeIdentityEt.getText().toString();
        String phone = employeePhoneNumberEt.getText().toString();

        ContentValues cv = new ContentValues();

        cv.put(Employees.CARD_NUMBER, cardNumber);
        cv.put(Employees.LAST_NAME, lastName);
        cv.put(Employees.FIRST_NAME, firstName);
        cv.put(Employees.COMPANY, company);
        cv.put(Employees.EMPLOYEE_KEY_ID, identity);
        cv.put(Employees.PHONE_NUMBER, phone);

        db = hlp.getWritableDatabase();
        db.insert(Employees.TABLE_EMPLOYEES, null, cv);
        db.close();
    }

    private void showFoodSupplierDialog()
    {
        dialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.input_food_supplier_dialog, null);

        foodSupplierTaxNumberEt = dialogView.findViewById(R.id.foodSupplierTaxNumberEt);
        foodSupplierCompanyEt = dialogView.findViewById(R.id.foodSupplierCompanyEt);
        foodSupplierMainPhoneEt = dialogView.findViewById(R.id.foodSupplierMainPhoneEt);
        foodSupplierSecPhoneEt = dialogView.findViewById(R.id.foodSupplierSecPhoneEt);

        adb = new AlertDialog.Builder(this);
        adb.setTitle("Add Food Supplier");
        adb.setView(dialogView);
        adb.setCancelable(false);

        adb.setPositiveButton("Save", (dialog, which) ->
        {
            if (isFoodSupplierDataValid())
            {
                saveFoodSupplierDataToDB();
                Toast.makeText(this, "Added new food supplier!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            }
        });

        adb.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        adb.show();
    }

    private boolean isFoodSupplierDataValid()
    {
        return !foodSupplierTaxNumberEt.getText().toString().isEmpty() &&
                !foodSupplierCompanyEt.getText().toString().isEmpty() &&
                !foodSupplierMainPhoneEt.getText().toString().isEmpty() &&
                !foodSupplierSecPhoneEt.getText().toString().isEmpty();
    }

    private void saveFoodSupplierDataToDB()
    {
        String taxNumber = foodSupplierTaxNumberEt.getText().toString();
        String companyName = foodSupplierCompanyEt.getText().toString();
        String mainPhone = foodSupplierMainPhoneEt.getText().toString();
        String secondaryPhone = foodSupplierSecPhoneEt.getText().toString();

        ContentValues cv = new ContentValues();

        cv.put(FoodSuppliers.SUPPLIER_KEY_ID, taxNumber);
        cv.put(FoodSuppliers.NAME, companyName);
        cv.put(FoodSuppliers.MAIN_PHONE, mainPhone);
        cv.put(FoodSuppliers.SECONDARY_PHONE, secondaryPhone);

        db = hlp.getWritableDatabase();
        db.insert(FoodSuppliers.TABLE_FOOD_SUPPLIERS, null, cv);
        db.close();
    }

    private void showMealDialog()
    {
        dialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.input_meal_dialog, null);

        mealStarterEt = dialogView.findViewById(R.id.mealStarterEt);
        mealMainEt = dialogView.findViewById(R.id.mealMainEt);
        mealSideEt = dialogView.findViewById(R.id.mealSideEt);
        mealDessertEt = dialogView.findViewById(R.id.mealDessertEt);
        mealDrinkEt = dialogView.findViewById(R.id.mealDrinkEt);

        adb = new AlertDialog.Builder(this);
        adb.setTitle("Add Meal");
        adb.setView(dialogView);
        adb.setCancelable(false);

        adb.setPositiveButton("Save", (dialog, which) ->
        {
            if (isMealDataValid())
            {
                saveMealDataToDB();
                Toast.makeText(this, "Added new meal!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            }
        });

        adb.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        adb.show();
    }

    private boolean isMealDataValid()
    {
        return !mealStarterEt.getText().toString().isEmpty() &&
                !mealMainEt.getText().toString().isEmpty() &&
                !mealSideEt.getText().toString().isEmpty() &&
                !mealDessertEt.getText().toString().isEmpty() &&
                !mealDrinkEt.getText().toString().isEmpty();
    }

    private void saveMealDataToDB()
    {
        String starter = mealStarterEt.getText().toString();
        String mainCourse = mealMainEt.getText().toString();
        String sideDish = mealSideEt.getText().toString();
        String dessert = mealDessertEt.getText().toString();
        String drink = mealDrinkEt.getText().toString();

        ContentValues cv = new ContentValues();

        cv.put(Meals.STARTER, starter);
        cv.put(Meals.MAIN_COURSE, mainCourse);
        cv.put(Meals.SIDE_DISH, sideDish);
        cv.put(Meals.DESSERT, dessert);
        cv.put(Meals.DRINK, drink);

        db = hlp.getWritableDatabase();
        db.insert(Meals.TABLE_MEALS, null, cv);
        db.close();
    }

    private void loadEmployees(ArrayList<String> employeeList)
    {
        db = hlp.getReadableDatabase();
        Cursor crsr = db.query(Employees.TABLE_EMPLOYEES, new String[]{Employees.CARD_NUMBER},
                null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Employees.CARD_NUMBER);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            employeeList.add("" + crsr.getInt(col1));
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    private void loadMeals(ArrayList<String> mealsList, ArrayList<Integer> mealsIdsList) {
        db = hlp.getReadableDatabase();
        Cursor crsr = db.query(Meals.TABLE_MEALS, null,
                null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Meals.MEAL_KEY_ID);
        int col2 = crsr.getColumnIndex(Meals.STARTER);
        int col3 = crsr.getColumnIndex(Meals.MAIN_COURSE);
        int col4 = crsr.getColumnIndex(Meals.SIDE_DISH);
        int col5 = crsr.getColumnIndex(Meals.DESSERT);
        int col6 = crsr.getColumnIndex(Meals.DRINK);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            mealsList.add(crsr.getString(col2) + ", " + crsr.getString(col3) + ", "
                    + crsr.getString(col4)
            + ", " + crsr.getString(col5) + ", " + crsr.getString(col6));
            mealsIdsList.add(crsr.getInt(col1));
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    private void loadFoodSuppliers(ArrayList<String> foodSuppliersList)
    {
        db = hlp.getReadableDatabase();
        Cursor crsr = db.query(FoodSuppliers.TABLE_FOOD_SUPPLIERS, new String[]{FoodSuppliers.SUPPLIER_KEY_ID},
                null, null, null, null, null);

        int col1 = crsr.getColumnIndex(FoodSuppliers.SUPPLIER_KEY_ID);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            foodSuppliersList.add("" + crsr.getInt(col1));
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    private void showOrderDialog()
    {
        dialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.input_order_dialog, null);

        orderEmployeeSpinner = dialogView.findViewById(R.id.orderEmployeeSpinner);
        orderMealSpinner = dialogView.findViewById(R.id.orderMealSpinner);
        orderFoodSupplierSpinner = dialogView.findViewById(R.id.orderFoodSupplierSpinner);

        employeeList.clear();
        mealsIdsList.clear();
        mealsList.clear();
        foodSuppliersList.clear();

        employeeList.add("Choose a card number");
        mealsList.add("Choose a meal");
        foodSuppliersList.add("Choose a supplier tax number");

        loadEmployees(employeeList);
        loadMeals(mealsList, mealsIdsList);
        loadFoodSuppliers(foodSuppliersList);

        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, employeeList);
        ArrayAdapter<String> mealsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mealsList);
        ArrayAdapter<String> suppliersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, foodSuppliersList);

        orderEmployeeSpinner.setAdapter(employeeAdapter);
        orderMealSpinner.setAdapter(mealsAdapter);
        orderFoodSupplierSpinner.setAdapter(suppliersAdapter);

        adb = new AlertDialog.Builder(this);
        adb.setTitle("Add Order");
        adb.setView(dialogView);
        adb.setCancelable(false);

        adb.setPositiveButton("Save", (dialog, which) -> {
            if (isOrderDataValid())
            {
                saveOrderDataToDB();
                Toast.makeText(this, "Added new order!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            }
        });

        adb.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        adb.show();
    }

    private boolean isOrderDataValid()
    {
        return orderEmployeeSpinner.getSelectedItemPosition() != 0 &&
                orderMealSpinner.getSelectedItemPosition() != 0 &&
                orderFoodSupplierSpinner.getSelectedItemPosition() != 0;
    }

    private void saveOrderDataToDB()
    {
        Calendar calendar = Calendar.getInstance();

        // Get components
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 0-23
        int minute = calendar.get(Calendar.MINUTE);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are 0-based
        int year = calendar.get(Calendar.YEAR);

        // Format with leading zeros
        String date = String.format("%02d-%02d-%04d", day, month, year);
        String time = String.format("%02d:%02d", hour, minute);

        int selectedEmployeeCardNumber = Integer.parseInt(employeeList.get(
                orderEmployeeSpinner.getSelectedItemPosition()));
        int selectedMealId = mealsIdsList.get(orderMealSpinner.getSelectedItemPosition() - 1);
        int selectedSupplierId = Integer.parseInt(foodSuppliersList.get(
                orderFoodSupplierSpinner.getSelectedItemPosition()));

        ContentValues cv = new ContentValues();
        cv.put(Orders.ORDER_DATE, date);
        cv.put(Orders.ORDER_TIME, time);
        cv.put(Orders.EMPLOYEE_CARD_NUMBER, selectedEmployeeCardNumber);
        cv.put(Orders.MEAL_ID, selectedMealId);
        cv.put(Orders.SUPPLIER_ID, selectedSupplierId);

        db = hlp.getWritableDatabase();
        db.insert(Orders.TABLE_ORDERS, null, cv);
        db.close();
    }

    public void addData(View view)
    {
        switch (mainSpinnerChosenOption)
        {
            case 1:
                showEmployeeDialog();
                break;

            case 2:
                showFoodSupplierDialog();
                break;

            case 3:
                showMealDialog();
                break;

            case 4:
                showOrderDialog();
                break;
        }
    }
}