package com.brunocascio.expensis;

import java.util.Calendar;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class ListExpensisActivity extends Activity implements OnClickListener, OnDateSetListener{
	private ExpensiDataSource datasource;
	private ListView listView;
	private List<Expensi> L;
	private EditText edittext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_expensis);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Database pipe
		datasource = new ExpensiDataSource(this);
	    datasource.openReadable();
	    
	    L = datasource.getAllExpensis(); 
	    
	    edittext = (EditText) findViewById(R.id.fechaOrder);
	    edittext.setOnClickListener(this);
	    
	    // =============== Lista de gastos ==============================
	    listView = (ListView) findViewById(R.id.listExp);
	    
	    ArrayAdapter<Expensi> adapter = new ArrayAdapter<Expensi>(
	    		this
	    		,android.R.layout.simple_list_item_activated_1
	    		, L //Pasamos todos los gastos
	    );
	    listView.setAdapter(adapter);
        
	}
	
	@Override
    protected void onResume() {
      datasource.openReadable();
      super.onResume();
    }

    @Override
    protected void onPause() {
      datasource.close();
      super.onPause();
    }

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_expensis, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		String fecha = day+"-"+(month+1)+"-"+year;
		this.edittext.setText(fecha);
		
		// Get results of Database for this Date
		List<Expensi> L = datasource.getExpensisFromDate(edittext.getText().toString());
		
		ArrayAdapter<Expensi> adapter = new ArrayAdapter<Expensi>(
	    		this
	    		,android.R.layout.simple_list_item_activated_1
	    		, L
	    );
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.fechaOrder){
			DatePickerDialog dialog = new DatePickerDialog(
					  this
					, this
					, Calendar.getInstance().get(Calendar.YEAR)
					, Calendar.getInstance().get(Calendar.MONTH)
					, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		       dialog.show();
		}
		
	}

}
