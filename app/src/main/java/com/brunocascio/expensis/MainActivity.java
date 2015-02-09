package com.brunocascio.expensis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.brunocascio.expensis.Activities.NewExpenseActivity;
import com.brunocascio.expensis.Adapters.ExpenseAdapter;
import com.brunocascio.expensis.Models.Expense;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;


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
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("lifecycle","create");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Actionbar (toolbar)
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        // get expenses
        expenses = Expense.getExpenses();

        totalToday = (TextView) findViewById(R.id.totalToday);
        totalMonth = (TextView) findViewById(R.id.totalMonth);
        //totalYear = (TextView) findViewById(R.id.totalYear);

        recyclerView = (RecyclerView) findViewById(R.id.expensesList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        expenseAdapter = new ExpenseAdapter(getApplicationContext(), expenses);
        recyclerView.setAdapter(expenseAdapter);

        mMaterialDialog = new MaterialDialog(this);

        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(this,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            final int pos = position;
                            Expense e = expenseAdapter.getExpense(pos);
                            Expense.deleteAll(Expense.class, " id = ? ", e.getId() + "");
                            expenseAdapter.removeExpense(pos);
                            updateHeader();

                            /*mMaterialDialog.setMessage("Desea borrar este gasto?");

                            mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Expense e = expenseAdapter.getExpense(pos);
                                        Expense.deleteAll(Expense.class, " id = ? ", e.getId() + "");
                                        expenseAdapter.removeExpense(pos);
                                        updateHeader();
                                    } catch ( IndexOutOfBoundsException e) {
                                        Log.e("indexOfBound", "Invalid position");
                                    }
                                    mMaterialDialog.dismiss();
                                }
                            });

                            mMaterialDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                }
                            });

                            mMaterialDialog.show();*/
                        }
                    }
            )
        );

        // SET amounts
        updateHeader();

        // Button new expense
        btnNew = (FloatingActionButton) findViewById(R.id.fab);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewExpenseActivity.class));
            }
        });
    }

    private void updateHeader() {
        // get total amounts
        mTotal = Expense.getTotalAmounts();

        // Statistics
        // TODO: correct currencyCode
        String currencyCode = "$";

        totalToday.setText( currencyCode +" "+ mTotal.get("today") );
        totalMonth.setText( currencyCode +" "+ mTotal.get("month") );
        //totalYear.setText( currencyCode +" "+ mTotal.get("year") );
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



    static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

        private final GestureDetector mGestureDetector;
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        public RecyclerItemClickListener(Activity activity, OnItemClickListener listener) {
            mListener = listener;

            mGestureDetector = new GestureDetector(activity.getApplicationContext(),
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                    }
                }
            );
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

            if (childView != null &&
                    mListener != null &&
                        mGestureDetector.onTouchEvent(motionEvent)) {

                mListener.onItemClick(childView, recyclerView.getChildPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }
    }
}
