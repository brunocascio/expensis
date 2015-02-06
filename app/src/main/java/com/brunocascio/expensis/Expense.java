package com.brunocascio.expensis;

import com.brunocascio.expensis.Exceptions.InvalidFieldsException;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.query.Select;

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by d3m0n on 02/02/15.
 */
public class Expense extends SugarRecord<Expense> {

    // Table columns
    private String description;
    private float amount;
    private int month;
    private int day;
    private int year;

    @Ignore
    private static List<Expense> recentlyExpenses;

    @Ignore
    private static List<Expense> expenses;

    /*
     *  Default Constructor
     */
    public Expense(){}

    /*
     *  Constructor
     */
    public Expense(String description, float amount, int day, int month, int year){
        this.description = description;
        this.amount = amount;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /*
     * @return List<Expense>
     *     Return List of expenses orderBy date
     */
    public static List<Expense> getExpenses(){
        expenses = new ArrayList<>();

        expenses = Select.from(Expense.class)
                .orderBy("year DESC, month DESC, day DESC, id DESC")
                .list();

        return expenses;
    }

    /*
     * @return Map<String, Float>
     *     Return Map with amounts for today, currently month, and currently year
     */
    public static Map<String,Float> getTotalAmounts() {

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

    /*
     *  @return Expense
     *  @params
     *      description (String)
     *      amount (String)
     *      date (String) - Format d-MMM-yyyy
     */
    public static Expense prepareExpense(String description, String amount, String date)
            throws InvalidFieldsException {

        if ( description.equals("") || amount.equals("") || date.equals("") )
            throw new InvalidFieldsException("Empty Fields");

        if ( !isLegalDate(date) )
            throw new InvalidFieldsException("Invalid Date.");

        // parse date
        String[] arrayDate = date.split("-");

        if ( arrayDate.length != 3 )
            throw new InvalidFieldsException("Invalid Date. Format not valid.");

        // set attributes
        Expense e = new Expense();
        e.description = description;
        e.amount = Float.parseFloat(amount);
        e.day = Integer.parseInt(arrayDate[0]);
        e.month = Integer.parseInt(arrayDate[1]);
        e.year = Integer.parseInt(arrayDate[2]);

        return e;
    }

    /*
     * @return Boolean
     *     return true if a string date is valid
     */
    static boolean isLegalDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        return sdf.parse(s, new ParsePosition(0)) != null;
    }

    /*
     * @return List<Expense>
     *     retrieve recently added expenses (limit 10)
     */
    public static List<Expense> getRecentlyAddedExpenses() {

        return Select.from(Expense.class)
                .orderBy("id DESC")
                .limit("10")
                .list();
    }

    /*
     * @return Boolean
     *  return true if expense is from today
     */
    public boolean isFromToday(){

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = (c.get(Calendar.MONTH)+1);
        int year = c.get(Calendar.YEAR);

        return ( (this.day == day) && (this.month == month) && (this.year == year) );
    }

    /*
     * @return Boolean
     *  return true if expense is from currently month
     */
    public boolean isCurrentlyMonth(){

        Calendar c = Calendar.getInstance();
        int month = (c.get(Calendar.MONTH)+1);
        int year = c.get(Calendar.YEAR);

        return ( (this.month == month) && (this.year == year) );
    }

    /*
     * @return Boolean
     *  return true if expense is from currently year
     */
    public boolean isCurrentlyYear(){

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        return (this.year == year);
    }

    /*
     * @return String
     *  return formatted full date
     */
    public String getFullDate() {
        return this.day + "-" + this.month + "-" + this.year;
    }

    /*
     * @return String
     *  return formatted full date without year
     */
    public String getFullDateWithOutYear(){
        // Get month names
        final String[] months = DateFormatSymbols.getInstance(Locale.getDefault()).getMonths();

        return this.day + " " + months[this.month-1];
    }

    @Override
    public String toString() {
        return this.description + " | " + this.amount + " | " + this.getFullDate();
    }


    /*
       * Setters and getters
    */

    public int getAmount() {
        return Math.round(this.amount);
    }

    public String getDescription() {
        return this.description;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
