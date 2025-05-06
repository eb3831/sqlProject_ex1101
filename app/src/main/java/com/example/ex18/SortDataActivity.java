package com.example.ex18;

import static com.example.ex18.Employees.TABLE_EMPLOYEES;
import static com.example.ex18.Orders.TABLE_ORDERS;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * Activity for displaying and sorting data from the database.
 * Supports sorting employees by last name, filtering orders by meal, and filtering orders by employee.
 * The user interacts with a Spinner to choose the sorting/filtering method and sees results in a ListView.
 *
 * @author eliya bitton eb3831@bs.amalnet.k12.il
 * @version 2.0
 * @since 29/4/2025
 *
 */
public class SortDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener
{
    Intent gi;
    String[] optionsArray = {"choose sorting option", "Sort employees by last name", "Show only orders from a specific meal", "show only orders from employee "};
    Spinner spinner2;
    ListView lv;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> sortedEmployeesArray, SortedOrdersArray, sortedMealsArray;
    String cardNumberOp, mealOp;
    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter<String> adp;
    TextView tv;
    AlertDialog.Builder adb;
    LinearLayout dialogView;
    Spinner filterDataDialogSp;
    ArrayList<String> filterDataDialogList;
    int mainSpinnerChosenOption;
    boolean isStrEmpty;

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
        setContentView(R.layout.activity_sort_data);

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

        if (itemId == R.id.menuMain) {
            gi = new Intent(this, MainActivity.class);
            startActivity(gi);
        }

        if (itemId == R.id.menuCredits)
        {
            gi = new Intent(this, CreditsActivity.class);
            startActivity(gi);
        }

        else if (itemId == R.id.menuShowDate)
        {
            gi = new Intent(this, DataActivity.class);
            startActivity(gi);
        }

        else if (itemId == R.id.menuRemoveEmployee)
        {
            gi = new Intent(this, RemoveEmployee.class);
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
        mainSpinnerChosenOption = 0;

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        sortedEmployeesArray = new ArrayList<>();
        SortedOrdersArray = new ArrayList<>();
        sortedMealsArray = new ArrayList<>();
        filterDataDialogList = new ArrayList<>();

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, optionsArray);
        spinner2.setAdapter(spinnerAdapter);

        spinner2.setOnItemSelectedListener(this);
        lv.setOnItemClickListener(this);

        isStrEmpty = true;
    }

    /**
     * Retrieves employees from the database and sorts them by last name.
     * Populates the sortedEmployyesArray with formatted strings of employee data.
     */
    private void sortEmployeesByLastName()
    {
        String str = "";

        String[] columns = {Employees.LAST_NAME, Employees.FIRST_NAME};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = Employees.LAST_NAME;
        String limit = null;

        db = hlp.getReadableDatabase();
        crsr = db.query(TABLE_EMPLOYEES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        int lastName = crsr.getColumnIndex(Employees.LAST_NAME);
        int firstName = crsr.getColumnIndex(Employees.FIRST_NAME);

        crsr.moveToFirst();
        sortedEmployeesArray.clear();
        while (!crsr.isAfterLast())
        {
            str = "Last name: " + crsr.getString(lastName) + "\n" +
                    "First name " + crsr.getString(firstName);
            sortedEmployeesArray.add(str);
            crsr.moveToNext();
        }

        isStrEmpty = str.isEmpty();
    }

    /**
     * Filters orders in the database by the given meal ID.
     * Populates sortedMealsArray with formatted strings of the filtered order data.
     *
     * @param mealOp the meal ID to filter orders by
     */
    private void filterOrdersByMeal(int mealOp)
    {
        String str = "";

        String[] columns = null;
        String selection =  Orders.MEAL_ID + "=?";
        String[] selectionArgs = {String.valueOf(mealOp)};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        db = hlp.getReadableDatabase();
        crsr = db.query(TABLE_ORDERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        int col1 = crsr.getColumnIndex(Orders.ORDER_DATE);
        int col2 = crsr.getColumnIndex(Orders.ORDER_TIME);
        int col3 = crsr.getColumnIndex(Orders.EMPLOYEE_CARD_NUMBER);
        int col5 = crsr.getColumnIndex(Orders.SUPPLIER_ID);

        crsr.moveToFirst();
        sortedMealsArray.clear();
        while (!crsr.isAfterLast())
        {
            str = "Date: " + crsr.getString(col1) + "\n" +
                    "Time: " + crsr.getString(col2) + "\n" +
                    "Worker ID: " + crsr.getString(col3) + "\n" +
                    "Company: " + crsr.getString(col5);

            sortedMealsArray.add(str);
            crsr.moveToNext();
        }

        adp.notifyDataSetChanged();

        isStrEmpty = str.isEmpty();
    }

    /**
     * Filters orders in the database by the given employee card number.
     * Populates SortedOrdersArray with formatted strings of the filtered order data.
     *
     * @param cardNumberOp the employee card number to filter orders by
     */
    private void filterOrdersByEmployee(int cardNumberOp)
    {
        String str = "";

        String[] columns = null;
        String selection = Orders.EMPLOYEE_CARD_NUMBER + "=?";
        String[] selectionArgs = {String.valueOf(cardNumberOp)};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        db = hlp.getReadableDatabase();
        crsr = db.query(TABLE_ORDERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        int col1 = crsr.getColumnIndex(Orders.ORDER_DATE);
        int col2 = crsr.getColumnIndex(Orders.ORDER_TIME);
        int col5 = crsr.getColumnIndex(Orders.SUPPLIER_ID);

        crsr.moveToFirst();
        SortedOrdersArray.clear();
        while (!crsr.isAfterLast())
        {
            str = "Date: " + crsr.getString(col1) + "\n" +
                    "Time: " + crsr.getString(col2) + "\n" +
                    "Company: " + crsr.getString(col5);
            SortedOrdersArray.add(str);
            crsr.moveToNext();
        }

        adp.notifyDataSetChanged();

        isStrEmpty = str.isEmpty();
    }

    /**
     * Loads all meals from the database and adds them to the provided lists.
     *
     * @param mealsList the list to store string descriptions of the meals
     * @param mealsIdsList the list to store the corresponding meal IDs
     */
    private void loadMeals(ArrayList<String> mealsList, ArrayList<Integer> mealsIdsList)
    {
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

    /**
     * Retrieves employee data from the database and populates the employeeList.
     */
    private void getEmployees(ArrayList<String> employeeList,
                              ArrayList<Integer> employeesCardNumbersList)
    {
        db = hlp.getReadableDatabase();
        crsr = db.query(TABLE_EMPLOYEES, null, null, null, null, null, null);

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
            employeesCardNumbersList.add(crsr.getInt(col6));
            crsr.moveToNext();
        }

        crsr.close();
        db.close();
    }

    /**
     * Displays a dialog allowing the user to choose a filter value (meal or employee).
     * Based on user selection, filters the data accordingly.
     *
     * @param isMealOption true to filter by meal, false to filter by employee
     */
    private void showFilterDataDialog(boolean isMealOption)
    {
        dialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_data_dialog, null);
        filterDataDialogSp = dialogView.findViewById(R.id.filterDataDialogSp);
        filterDataDialogList.clear();

        ArrayList<Integer> mealsIdsList = new ArrayList<>();
        ArrayList<Integer> employeesCardNumbersList = new ArrayList<>();

        adb = new AlertDialog.Builder(this);

        if(isMealOption)
        {
            adb.setTitle("Choose a meal to filter by");
            loadMeals(filterDataDialogList, mealsIdsList);
        }
        else
        {
            adb.setTitle("Choose an employee to filter by");
            getEmployees(filterDataDialogList, employeesCardNumbersList);
        }

        ArrayAdapter<String> filterDataDialogAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, filterDataDialogList);
        filterDataDialogSp.setAdapter(filterDataDialogAdapter);

        adb.setView(dialogView);
        adb.setCancelable(false);

        adb.setPositiveButton("Filter", (dialog, which) ->
        {
            int pos = filterDataDialogSp.getSelectedItemPosition();
            if(pos < 0)
            {
                Toast.makeText(this, "Please choose an option!", Toast.LENGTH_SHORT).show();
            }

            else if(isMealOption)
            {
                filterOrdersByMeal(mealsIdsList.get(pos));

                if (isStrEmpty)
                    Toast.makeText(this, "couldn't find any orders of this meal!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Filter made successfully!", Toast.LENGTH_SHORT).show();
            }

            else
            {
                filterOrdersByEmployee(employeesCardNumbersList.get(pos));

                if (isStrEmpty)
                    Toast.makeText(this, "couldn't find any orders from this employye!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Filter made successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        adb.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        adb.show();
    }

    /**
     * Called when the user presses the filter button.
     * Handles the logic to apply the selected sorting/filtering operation and updates the ListView.
     *
     * @param view the button view that triggered this method
     */
    public void filterDataEnter(View view)
    {
        switch (mainSpinnerChosenOption)
        {
            case 0:
                Toast.makeText(this, "Please choose an option!", Toast.LENGTH_LONG).show();
                break;

            case 1:
                tv.setText("SORTED EMPLOYeES BY LAST NAME");

                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sortedEmployeesArray);

                sortEmployeesByLastName();
                if (isStrEmpty)
                    Toast.makeText(this, "couldn't find any employyes!", Toast.LENGTH_LONG).show();

                break;

            case 2:
                tv.setText("ALL ORDERS FROM A SPECIFIC MEAL");
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sortedMealsArray);

                showFilterDataDialog(true);

                break;

            case 3:
                tv.setText("ALL ORDERS FROM AN EMPLOYEE");
                adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, SortedOrdersArray);

                showFilterDataDialog(false);


                break;
        }

        lv.setAdapter(adp);
    }

    /**
     * Called when an item in the spinner is selected.
     * This method updates mainSpinnerChosenOption according to the selected item in the spinner.
     *
     * @param parent   The AdapterView where the selection happened.
     * @param view     The view within the AdapterView that was clicked.
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that is selected.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        mainSpinnerChosenOption = position;
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
}
