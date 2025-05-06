package com.example.ex18;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * the credits activity.
 *
 *
 * @author eliya bitton eb3831@bs.amalnet.k12.il
 * @version 2.0
 * @since 29/4/2025
 *
 */
public class CreditsActivity extends AppCompatActivity
{
    Intent gi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
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

        if (itemId == R.id.menuMain)
        {
            gi = new Intent(this, MainActivity.class);
            startActivity(gi);
        }

        else if ( itemId == R.id.menuShowDate )
        {
            gi = new Intent(this, DataActivity.class);
            startActivity(gi);
        }

        else if ( itemId == R.id.menuShowSortDate )
        {
            gi = new Intent(this, SortDataActivity.class);
            startActivity(gi);
        }
        else if ( itemId == R.id.menuRemoveEmployee )
        {
            gi = new Intent(this, RemoveEmployee.class);
            startActivity(gi);
        }

        return super.onOptionsItemSelected(menuItem);
    }
}