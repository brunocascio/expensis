package com.brunocascio.expensis;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.drakeet.materialdialog.MaterialDialog;


public class NewExpenseActivity extends ActionBarActivity implements DatePickerController {

    private Toolbar toolbar;
    private EditText inputExpenseDate;
    private EditText expenseAmount;
    private EditText expenseDescription;
    private MaterialDialog mMaterialDialog;
    private DayPickerView calendar;
    private View viewCalendar;
    private String today;
    private Calendar c;

    public NewExpenseActivity(){
        // today
        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        this.today = df.format(c.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        // Action Bar (Toolbar)
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Calendar Plugin
        viewCalendar = LayoutInflater.from(this).inflate(R.layout.calendar, null);
        calendar = (DayPickerView) viewCalendar.findViewById(R.id.pickerView);
        calendar.setmController(this);

        // Popup date with calendar inside
        mMaterialDialog = new MaterialDialog(this).setView(viewCalendar);

        // expense amount input
        expenseAmount = (EditText) findViewById(R.id.expenseAmount);

        // expense description input
        expenseDescription = (EditText) findViewById(R.id.expenseDescription);

        // date (read only)
        inputExpenseDate = (EditText) findViewById(R.id.expenseDate);
        inputExpenseDate.setText(today);
        inputExpenseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ( hasFocus ) { mMaterialDialog.show(); }
            }
        });
        inputExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_expense, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if ( id == R.id.saveExpense ){
            //TODO: save expense
            Log.i("save", "click");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getMaxYear() {
        return c.get(Calendar.YEAR);
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        inputExpenseDate.setText(day + "-" + month + "-" + year);
        mMaterialDialog.dismiss();
    }
}
