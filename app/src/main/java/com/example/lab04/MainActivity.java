package com.example.lab04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout dlayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,dlayout, toolbar, R.string.open, R.string.close);
        dlayout.addDrawerListener(toggle);
        toggle.syncState();

        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String message = null;
        int itemId = item.getItemId();
        if (itemId == R.id.choice1) {
            message = "You clicked on item 1";
        } else if (itemId == R.id.choice2) {
            message = "You clicked on item 2";
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home)
            setContentView(R.layout.activity_main);
        else if (itemId == R.id.joke) {
            Intent dadJokePage = new Intent(this, DadJoke.class);
            startActivity(dadJokePage);
        }
        else if (itemId == R.id.exit){
            finishAffinity();
        }

        return false;
    }




}