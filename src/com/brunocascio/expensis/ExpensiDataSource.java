package com.brunocascio.expensis;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ExpensiDataSource {
	  // Database fields
	  private SQLiteDatabase database;
	  private ExpensisSQLiteHelper dbHelper;
	  
	  private String[] allColumns = { 
			  ExpensisSQLiteHelper.COLUMN_ID,
			  ExpensisSQLiteHelper.COLUMN_DESCRIPTION,
			  ExpensisSQLiteHelper.COLUMN_PRICE,
			  ExpensisSQLiteHelper.COLUMN_DATE
	  };

	  public ExpensiDataSource(Context context) {
	    dbHelper = new ExpensisSQLiteHelper(context);
	  }

	  public void open() throws SQLException {
		  database = dbHelper.getWritableDatabase();
	  }
	  
	  public void openReadable() throws SQLException {
		  database = dbHelper.getReadableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Expensi createExpensi(Expensi exp){
		  
	    ContentValues values = new ContentValues();
	    values.put(ExpensisSQLiteHelper.COLUMN_DESCRIPTION, exp.getDescription());
	    values.put(ExpensisSQLiteHelper.COLUMN_PRICE, exp.getPrice());
	    values.put(ExpensisSQLiteHelper.COLUMN_DATE, exp.getDate().toString());
	    
	    
	    long insertId = database.insert(
	    		ExpensisSQLiteHelper.TABLE_NAME
	    		, null
	    		, values
	    );
	    System.out.println(insertId);
	    
	    Cursor cursor = database.query(
	    		ExpensisSQLiteHelper.TABLE_NAME
	    		, allColumns
	    		, ExpensisSQLiteHelper.COLUMN_ID + " = " + insertId
	    		, null, null, null, null);
	    
	    cursor.moveToFirst();
	    Expensi newExpensi = cursorToExpensi(cursor);
	    cursor.close();
	    System.out.println(newExpensi.getDescription());
	    return newExpensi;
	  }

	  public void deleteExpensi(Expensi exp) {
	    long id = exp.getId();
	    System.out.println("exp deleted with id: " + id);
	    database.delete(
	    		ExpensisSQLiteHelper.TABLE_NAME,
	    		ExpensisSQLiteHelper.COLUMN_ID
	    		+ " = " + id, null
	    );
	  }

	  public List<Expensi> getAllExpensis() {
	    List<Expensi> expensis = new ArrayList<Expensi>();

	    Cursor cursor = database.query(ExpensisSQLiteHelper.TABLE_NAME,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Expensi expensi = cursorToExpensi(cursor);
	      expensis.add(expensi);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return expensis;
	  }

	  private Expensi cursorToExpensi(Cursor cursor) {
	    Expensi expensi = new Expensi(
	    		cursor.getString(1),
	    		cursor.getFloat(cursor.getColumnIndex(ExpensisSQLiteHelper.COLUMN_PRICE)),
	    		cursor.getString(cursor.getColumnIndex(ExpensisSQLiteHelper.COLUMN_DATE))
	    );
	    
	    expensi.setId(cursor.getLong(0));
	    return expensi;
	  }
}
