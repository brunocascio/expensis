package com.brunocascio.expensis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;


public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private Toolbar toolbar;
    private FloatingActionButton btnNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Actionbar (toolbar)
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Button new expense
        btnNew = (FloatingActionButton) findViewById(R.id.fab);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewExpenseActivity.class));
            }
        });

        // List expenses
        expenseAdapter = new ExpenseAdapter( getApplicationContext(), getData() );

        recyclerView = (RecyclerView) findViewById(R.id.expensesList);

        recyclerView.setAdapter(expenseAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    public static List<Expense> getData(){
        List<Expense> L = new ArrayList<Expense>();
        for (int i=0; i < 3; i++){
            Expense e = new Expense();
            e.amount = 100 + i;
            e.day = 1;
            e.month = 1;
            e.year = 2015;
            e.description = "Gasto";
            L.add(e);
        }
        return L;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                System.out.print("settings");
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
