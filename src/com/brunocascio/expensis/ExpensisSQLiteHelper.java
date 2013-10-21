package com.brunocascio.expensis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExpensisSQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_NAME = "expensis";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_DESCRIPTION = "description";
  public static final String COLUMN_PRICE = "price";
  public static final String COLUMN_DATE = "date";

  private static final String DATABASE_NAME = "expensis.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, "
      + COLUMN_DESCRIPTION +" text,"
      + COLUMN_PRICE + " text not null," 
      + COLUMN_DATE +" text not null"
      +");";

  public ExpensisSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(ExpensisSQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }

} 