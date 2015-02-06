package com.brunocascio.expensis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private Toolbar toolbar;
    private FloatingActionButton btnNew;
    private TextView totalToday;
    private TextView totalMonth;
    private TextView totalYear;
    private List<Expense> expenses;
    private Map<String, Float> mTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Actionbar (toolbar)
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        // get expenses
        expenses = getExpenses();

        // get total amounts
        mTotal = getTotalAmounts();

        // Statistics
        // TODO: correct currencyCode
        String currencyCode = "$";

        totalToday = (TextView) findViewById(R.id.totalToday);
        totalToday.setText( currencyCode +" "+ mTotal.get("today") );

        totalMonth = (TextView) findViewById(R.id.totalMonth);
        totalMonth.setText( currencyCode +" "+ mTotal.get("month") );

        totalYear = (TextView) findViewById(R.id.totalYear);
        totalYear.setText( currencyCode +" "+ mTotal.get("year") );

        // Button new expense
        btnNew = (FloatingActionButton) findViewById(R.id.fab);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewExpenseActivity.class));
            }
        });

        // RecyclerView (Expenses List)
        expenseAdapter = new ExpenseAdapter(getApplicationContext(), expenses);

        recyclerView = (RecyclerView) findViewById(R.id.expensesList);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(expenseAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    /*
     * @return List<Expense>
     *     Return List of expenses orderBy date
     */
    public static List<Expense> getExpenses(){
        List<Expense> toRet = new ArrayList<>();

        toRet = Select.from(Expense.class)
                .orderBy("year DESC, month DESC, day DESC, id DESC")
                .list();

        return toRet;
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

    public Map<String,Float> getTotalAmounts() {
        HashMap<String, Float> totalAmounts = new HashMap<>();

        totalAmounts.put("today", (float) 0);
        totalAmounts.put("month", (float) 0);
        totalAmounts.put("year", (float) 0);

        float total;
        float amount;

        for (Expense e: expenses)
        {
            amount = e.getAmount();

            if ( e.isFromToday() ) {
                total = totalAmounts.get("today");
                total += amount;
                totalAmounts.put("today", total );
            }

            if ( e.isCurrentlyMonth() ) {
                total = totalAmounts.get("month");
                total += amount;
                totalAmounts.put("month", total );
            }

            if ( e.isCurrentlyYear() ) {
                total = totalAmounts.get("year");
                total += amount;
                totalAmounts.put("year", total );
            }
        }

        return totalAmounts;
    }
}
