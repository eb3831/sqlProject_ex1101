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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RemoveEmployee extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    Intent gi;
    ListView lv;
    ArrayList<String> employeeList;
    ArrayList<Integer> employeesCardNumbers;
    SQLiteDatabase db;
    HelperDB hlp;
    AlertDialog.Builder adb;
    ArrayAdapter<String> adp;
    Cursor crsr;
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
        setContentView(R.layout.activity_remove_employee);

        lv = findViewById(R.id.lv);

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

        else if (itemId == R.id.menuShowDate)
        {
            gi = new Intent(this, DataActivity.class);
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
     * This includes setting up the database, initializing ArrayLists, and setting up the spinner.
     */
    private void initAll()
    {
        isStrEmpty = true;

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        employeeList = new ArrayList<>();
        employeesCardNumbers = new ArrayList<>();

        getEmployees();
        if (isStrEmpty)
            Toast.makeText(this, "couldn't find any employees!", Toast.LENGTH_LONG).show();

        else
        {
            lv.setOnItemClickListener(this);
            adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);
            lv.setAdapter(adp);
        }

    }

    /**
     * Retrieves employee data from the database and populates the employeeList.
     */
    private void getEmployees()
    {
        String str = "";

        db = hlp.getReadableDatabase();
        crsr = db.query(Employees.TABLE_EMPLOYEES, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Employees.EMPLOYEE_KEY_ID);
        int col2 = crsr.getColumnIndex(Employees.CARD_NUMBER);
        int col3 = crsr.getColumnIndex(Employees.FIRST_NAME);
        int col4 = crsr.getColumnIndex(Employees.LAST_NAME);

        employeeList.clear();
        employeesCardNumbers.clear();

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            str = "ID number: " + crsr.getString(col1) + "\n" +
                    "First name: " + crsr.getString(col3) + "\n" +
                    "Last name: " + crsr.getString(col4) + "\n";

            employeeList.add(str);
            employeesCardNumbers.add(crsr.getInt(col2));
            crsr.moveToNext();
        }

        crsr.close();
        db.close();

        isStrEmpty = str.isEmpty();
    }

    /**
     * Handles the event when a user clicks on an item in the ListView.
     * Shows an AlertDialog to confirm deletion of the selected employee.
     * If the user confirms, the employee is removed from the database and the ListView is updated.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (can be used for styling or effects).
     * @param position The position of the view in the adapter.
     * @param id       The row ID of the item that was clicked (not used here).
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Delete Employee");
        adb.setMessage("Are you sure you want to delete this employee and all his data?");
        adb.setPositiveButton("Delete", (dialog, which) ->
        {
            String cardNumber = String.valueOf(employeesCardNumbers.get(position));;

            db = hlp.getWritableDatabase();
            db.delete(Employees.TABLE_EMPLOYEES, Employees.CARD_NUMBER + "=?", new String[]{cardNumber});
            db.delete(Orders.TABLE_ORDERS, Orders.EMPLOYEE_CARD_NUMBER + "=?", new String[]{cardNumber});
            db.close();

            employeeList.remove(position);
            adp.notifyDataSetChanged();

            Toast.makeText(this, "Employee deleted successfully", Toast.LENGTH_SHORT).show();
        });

        adb.setNegativeButton("Cancel", null);
        adb.show();
    }
}