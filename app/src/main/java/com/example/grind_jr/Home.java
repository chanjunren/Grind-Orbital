package com.example.grind_jr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ImageView expenditure, income, quests;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        expenditure = (ImageView) findViewById(R.id.expenditure_habitat);
        income = (ImageView) findViewById(R.id.income_habitat);
        quests = (ImageView) findViewById(R.id.quests_page);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.navView);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView.setNavigationItemSelectedListener(this);
        expenditure.setOnClickListener(this);
        income.setOnClickListener(this);
        quests.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == expenditure) {
            startActivity(new Intent(Home.this, ExpenditureHabitat.class));
        } else if (v == quests) {
            startActivity(new Intent(Home.this, QuestBoard.class));
        } else if (v == income) {
            startActivity(new Intent(Home.this, IncomeHabitat.class));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.guide: {
                Toast.makeText(Home.this, "Logout selected", Toast.LENGTH_SHORT).show();
                break;
            } case R.id.summary: {
                startActivity(new Intent(this, Summary.class));
                break;
            } case R.id.logout:{
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Home.this, "Logging out..", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            } default: {
                Toast.makeText(Home.this, "This should not appear", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}