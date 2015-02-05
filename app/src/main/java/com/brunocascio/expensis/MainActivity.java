package com.brunocascio.expensis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;


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

        final MainActivity that = this;


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

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(expenseAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    public static List<Expense> getData(){
        return Expense.listAll(Expense.class);
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
