package com.brunocascio.expensis;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private ExpensiDataSource datasource;
	//private List<Expensi> totalExpensis;
    private EditText description, price;
    private DatePicker calendar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[] {
                        		getString(R.string.nav_add_exp),
                                getString(R.string.nav_add_cap),
                                getString(R.string.nav_show_exp),
                                getString(R.string.nav_exit),
                        }),
                this);
        
        datasource = new ExpensiDataSource(this);
        datasource.open();
        
        //totalExpensis = datasource.getAllExpensis();
        
        calendar = (DatePicker) findViewById(R.id.fecha);
        //calendar.setCalendarViewShown(true);
        
        description = (EditText) findViewById(R.id.text_description);
        price = (EditText) findViewById(R.id.text_price);

    }
    
    
    private String dateFromPicker(DatePicker c){
    	String cad = "";
    	cad += c.getDayOfMonth()+"-"+c.getMonth()+"-"+c.getYear();
    	return cad;
    }
    
    public void onClick(View view) {
	    
	    if (view.getId() == R.id.button_add){	      
	      Expensi expensi = new Expensi(
	    		  description.getText().toString(),
	    		  Float.parseFloat(price.getText().toString()),
	    		  dateFromPicker(calendar)
	    		  );
	      
	      datasource.createExpensi(expensi);    
	      Toast.makeText(this, "Guardado: "+description.getText().toString(), Toast.LENGTH_SHORT).show();
	    }
    }
    
    @Override
    protected void onResume() {
      datasource.open();
      super.onResume();
    }

    @Override
    protected void onPause() {
      datasource.close();
      super.onPause();
    }


	@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
    	System.out.println(position+" ");
    	if (position == 2){
    		Intent intent = new Intent(this, ListExpensisActivity.class);
    		startActivity(intent);
    	}
    	//Toast.makeText(getApplicationContext(), position+"", Toast.LENGTH_SHORT);
        return true;
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
