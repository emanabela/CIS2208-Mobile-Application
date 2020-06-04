package com.example.eerestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "restaurant.db";

    //user table
    //user table name
    public static final String TABLE_NAME = "user_table";
    //user table columns
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "USERNAME";
    public static final String COL_4 = "PASSWORD";

    //reservations table
    //reservations table name
    public static final String RTABLE_NAME = "reservations_table";
    //reservations table columns
    public static final String RCOL_1 = "ID";
    public static final String RCOL_2 = "TABLEID";
    public static final String RCOL_3 = "NAME";
    public static final String RCOL_4 = "EMAIL";
    public static final String RCOL_5 = "PHONENUMBER";
    public static final String RCOL_6 = "STARTTIME";
    public static final String RCOL_7 = "ENDTIME";
    public static final String RCOL_8 = "DATE";
    public static final String RCOL_9 = "APPROVAL";

    //menu table
    //menu table name
    public static final String MTABLE_NAME = "menu_table";
    //menu table columns
    public static final String MCOL_1 = "ID";
    public static final String MCOL_2 = "CATEGORY";
    public static final String MCOL_3 = "NAME";
    public static final String MCOL_4 = "PRICE";
    public static final String MCOL_5 = "IMAGE";

    //constructior
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 11);
    }

    //onCreateMethod, which is used to create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT," +
                " USERNAME TEXT, PASSWORD TEXT)");

        db.execSQL("create table " + RTABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TABLEID INTEGER, NAME TEXT," +
                "EMAIL TEXT, PHONENUMBER INTEGER, STARTTIME TEXT, ENDTIME TEXT, " +
                "DATE TEXT, APPROVAL TEXT)");

        db.execSQL("create table " + MTABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY TEXT, NAME TEXT," +
                " PRICE TEXT, IMAGE BLOB)");
    }

    //onUpgradeMethod which is used to upgrade the method, opon any changes.
    //When doing a change in tables, ex: creating a new table, the version of the database needs to be updated.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RTABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MTABLE_NAME);
        onCreate(db);
    }

    //insert method in user table
    public boolean insertData(String email, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, email);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, password);

        long result = db.insert(TABLE_NAME,null, contentValues);

        //checking whether the insertion of the data worked or not. Deoending on the result, the user will be informed in the main classes
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //insert method in reservations table
    public boolean insertReservation(int tableId, String name, String email, int phoneNumber,
                                     String startTime, String endTime, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RCOL_2, tableId);
        contentValues.put(RCOL_3, name);
        contentValues.put(RCOL_4, email);
        contentValues.put(RCOL_5, phoneNumber);
        contentValues.put(RCOL_6, startTime);
        contentValues.put(RCOL_7, endTime);
        contentValues.put(RCOL_8, date);
        contentValues.put(RCOL_9, "Not Approved");

        long result = db.insert(RTABLE_NAME, null, contentValues);


        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //insert method in menu table
    public boolean insertMenuItem(String category, String name, String price, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MCOL_2, category);
        contentValues.put(MCOL_3, name);
        contentValues.put(MCOL_4, price);
        contentValues.put(MCOL_5, image);

        long result = db.insert(MTABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //method to return all users
    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    //method to return all reservations
    public Cursor getAllReservations(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + RTABLE_NAME, null);
        return cursor;
    }

    //method to return all dishes
    public  Cursor getAllMennu(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MTABLE_NAME, null);
        return cursor;
    }

    public  Cursor getAllStarters(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MTABLE_NAME + " where CATEGORY = 'Starter'" , null);
        return cursor;
    }

    public  Cursor getAllMainCourses(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MTABLE_NAME + " where CATEGORY = 'Main Course'" , null);
        return cursor;
    }

    public  Cursor getAllDesserts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MTABLE_NAME + " where CATEGORY = 'Dessert'" , null);
        return cursor;
    }

    //getting singleMenuItem
    public Cursor getSingle(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MTABLE_NAME + " where ID = " + id, null);
        return cursor;
    }

    //method for admin to view all the reservations
    public Cursor getAllAdministrationViewReservations(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select distinct ID, TABLEID, NAME, EMAIL, PHONENUMBER, STARTTIME, DATE, APPROVAL from " + RTABLE_NAME + " order by " + RCOL_8, null);
        return cursor;
    }

    //method for user to get his reservations
    public Cursor getUsersReservations(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select distinct ID, TABLEID, NAME, EMAIL, PHONENUMBER, STARTTIME, DATE, APPROVAL from " + RTABLE_NAME + " where NAME = '"+ name +"' order by " + RCOL_8, null);
        return cursor;
    }

    //return single reservation
    public Cursor getSingleReservation(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + RTABLE_NAME + " where ID = " + id, null);
        return cursor;
    }

    //this method is used to update the database table reservations.
    public boolean updateApproval(long id, String approval){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RCOL_9, approval); //overwriting the current value of approval
        db.update(RTABLE_NAME, contentValues, "ID = ? ", new String[] {""+id});
        return true;
    }
}
