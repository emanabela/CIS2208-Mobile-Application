package com.example.eerestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ApproveReservationActivity extends AppCompatActivity implements View.OnClickListener {

    //shared prefs
    public static final String SHARED_PREFS = "sharedResId";
    public static final String ITEMID = "resid";

    //Declaring mydbhelper
    DatabaseHelper myDb;

    //initialising textViews
    TextView textViewTableID, textViewName, textViewEmail, textViewPhoneNumber, textViewTime, textViewDate, textViewApproval;

    //initlaising buttons
    Button buttonApproveBooking, buttonDisapproveBooking;

    public long resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reservation);

        //declaring the database
        myDb = new DatabaseHelper(this);

        //declaring textViews
        textViewTableID = (TextView)findViewById(R.id.textView_tableID);
        textViewName = (TextView)findViewById(R.id.textView_name);
        textViewEmail = (TextView)findViewById(R.id.textView_email);
        textViewPhoneNumber = (TextView)findViewById(R.id.textView_phonenumber);
        textViewTime = (TextView)findViewById(R.id.textView_time);
        textViewDate = (TextView)findViewById(R.id.textView_date);
        textViewApproval = (TextView)findViewById(R.id.textView_approval);

        //declaring buttons
        buttonApproveBooking = (Button)findViewById(R.id.button_approve_booking);
        buttonDisapproveBooking = (Button)findViewById(R.id.button_disapprove_booking);

        //declaring onclicklisteners
        buttonApproveBooking.setOnClickListener(this);
        buttonDisapproveBooking.setOnClickListener(this);

        //getting he reservation id from the preferences (which is set in the ReservationsAdapter)
        resId = getResId();

        //calling the load data method and passing the resId as parameter to get the data of the reservation selection in the previous screen (ViewBookingsActivity)
        loadData(resId);
    }

    //onclick method
    @Override
    public void onClick(View v) {
        //switch to get get the button selected
        switch (v.getId()){
            case R.id.button_approve_booking:
                updateApproved(); //calling method updateApproved (found at the end of this java file)
                //going back to the main activity
                startActivity(new Intent(this,
                        MainActivity.class));break;

            case R.id.button_disapprove_booking:
                updateDisapproved();//calling method updateDisapproved (found at the end of this java file)
                //going back to the main activity
                startActivity(new Intent(this,
                        MainActivity.class));break;
        }
    }

    //method to get the the id from the shared preferences
    public long getResId(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFS, MODE_PRIVATE);

        return sharedPreferences.getLong(ITEMID, 0);
    }

    //method to get generate the data
    public void loadData(long id){
        //getting the data from the database and putting it in a cursor
        Cursor cursor = myDb.getSingleReservation(id);

        //checking if the cursor is empty, if it's empty, it goes back to the previous class
        if (cursor.getCount() == 0){
            startActivity(new Intent(this,
                    ViewBookingsActivity.class));
        }
        //if it's not empty, it loads the data in their respective text views
        else {
            while (cursor.moveToNext()){
                textViewTableID.setText(cursor.getString(1));
                textViewName.setText(cursor.getString(2));
                textViewEmail.setText(cursor.getString(3));
                textViewPhoneNumber.setText(cursor.getString(4));
                textViewTime.setText(cursor.getString(5));
                textViewDate.setText(cursor.getString(7));
                textViewApproval.setText(cursor.getString(8));
            }
        }
    }

    //method for approving the the reservation
    public void updateApproved(){
        myDb.updateApproval(resId, "Approved");
    }

    //method for disapproving the reservation
    public void updateDisapproved(){
        myDb.updateApproval(resId, "Disapproved");
    }
}
