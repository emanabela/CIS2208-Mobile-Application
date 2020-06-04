package com.example.eerestaurant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalTime;
import java.time.Period;
import java.util.Calendar;

import javax.xml.datatype.Duration;

public class BookActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring mydbhelper
    DatabaseHelper myDb;

    //putting the table ids in an array
    public static final int[] TableIds = {R.id.imageButton_table1, R.id.imageButton_table2, R.id.imageButton_table3, R.id.imageButton_table4, R.id.imageButton_table5,
            R.id.imageButton_table6, R.id.imageButton_table7, R.id.imageButton_table8, R.id.imageButton_table9};

    //creating table variables
    ImageButton[] tables = new ImageButton[TableIds.length];

    //creating the edittext
    public EditText editTextName, editTextEmail, editTextPhoneNumber;

    //creating the textview
    public TextView editTextTime, editTextDate, viewTableSelected;

    //creating reserve button
    public Button reserveButton;

    //table selected variable
    private int tableId;

    //shared preferences declaration
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    private String loggedinUsername, loggedinEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //declaring the db
        myDb = new DatabaseHelper(this);


        for (int i=0;i<tables.length;i++){
            //putting the imagebuttons of the "pick-a-table" graphical representation tables into an array
            tables[i] = (ImageButton)findViewById(TableIds[i]);
            //declaring the onClick
            tables[i].setOnClickListener(this);
        }

        //initialising editText
        editTextName = (EditText)findViewById(R.id.editTextBookName) ;
        editTextEmail = (EditText)findViewById(R.id.editTextBookEmail) ;
        editTextPhoneNumber = (EditText)findViewById(R.id.editTextBookPhone) ;

        //initialising textview
        editTextTime = (TextView)findViewById(R.id.editTextTime);
        editTextDate = (TextView)findViewById(R.id.editTextDate);
        viewTableSelected = (TextView)findViewById(R.id.viewSelectedTable);

        //initialising reserve button
        reserveButton = (Button)findViewById(R.id.reserve_button);

        //setting onclicklistener
        reserveButton.setOnClickListener(this);
        editTextTime.setOnClickListener(this);
        editTextDate.setOnClickListener(this);

        //calling the getUser method
        getUser();

        //checking if the user is logged in or not. If he is logged in, it puts details inside of the EditText boxes
        if(!loggedinEmail.equals("")){
            editTextEmail.setText(loggedinEmail);
            editTextName.setText(loggedinUsername);
        }
    }

    //onclick method
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        //case to get which button the user pressed
        switch (v.getId()){
            case R.id.imageButton_table1:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(1); break;

            case R.id.imageButton_table2:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(2); break;

            case R.id.imageButton_table3:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(3); break;

            case R.id.imageButton_table4:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(4); break;

            case R.id.imageButton_table5:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(5); break;

            case R.id.imageButton_table6:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(6); break;

            case R.id.imageButton_table7:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(7); break;

            case R.id.imageButton_table8:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(8); break;

            case R.id.imageButton_table9:
                //calling the selectTable method and passing the tableId as parameter
                selectTable(9); break;

            case R.id.editTextTime:
                //calling the handleTimeSelector, so that the user can pick the time preferred for the booking
                handleTimeSelector();break;

            case R.id.editTextDate:
                //calling the handleDateSelector, so that the user can pick the preferred date for the booking
                handleDateSelector();break;

            case R.id.reserve_button:
                //calling the add reservation method, and passing the details to save them in the db
                AddReservation(tableId, editTextName.getText().toString(), editTextEmail.getText().toString(),
                        Integer.parseInt(editTextPhoneNumber.getText().toString()), editTextTime.getText().toString(), editTextDate.getText().toString());
        }
    }

    //method to get the logged in user details from the SharedPrefernces
    public void getUser(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFS, MODE_PRIVATE);

        //setting the local variables loggedinUsername and loggedinEmail accordingly.
        loggedinUsername = sharedPreferences.getString(USERNAME, "");
        loggedinEmail = sharedPreferences.getString(EMAIL, "");
    }

    //method to get the table id Selected
    public void selectTable(int id){
        //storing the id of the table selected to a variable
        tableId = id;

        //showing the variable selected to the user
        String newViewTableSelectedText = "Selected Table: " + id;
        viewTableSelected.setText(newViewTableSelectedText);
    }

    //method to handle the time selector
    public void handleTimeSelector(){
        //creating calendar instance
        Calendar calendar = Calendar.getInstance();

        //getting hour and minute
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        //Creating a timePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                //parsing from int to string
                String h = ""+hourOfDay, t = "" + minute;

                //checking if the variable is a single digit ex: 1, and changing it to 01, to make it consistent
                if (hourOfDay < 10 && hourOfDay >= 0 ){
                    h = "0"+hourOfDay;
                }

                //checking if the variable is a single digit ex: 1, and changing it to 01, to make it consistent
                if (minute < 10 && minute >= 0 ){
                    t = "0"+minute;
                }

                //showing the time selected to the user
                String timeString = h + ":" + t;
                editTextTime.setText(timeString);
            }
        }, HOUR, MINUTE, true);

        //showing the dialog upon method call
        timePickerDialog.show();
    }

    //method to handle the Date Selector
    public void handleDateSelector(){
        //creating calendar instance
        Calendar calendar = Calendar.getInstance();

        //getting year, month and date
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        //creating datePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                //having to increment the month for some reason, it was appearing as one less without this step
                month++;

                //parsing from int to to string
                String d = ""+dayOfMonth, m = ""+month;

                //checking if the variable is a single digit ex: 1, and changing it to 01, to make it consistent
                if (dayOfMonth < 10 && dayOfMonth > 0 ) {
                    d = "0"+dayOfMonth;
                }

                //checking if the variable is a single digit ex: 1, and changing it to 01, to make it consistent
                if (month < 10 && month > 0 ) {
                    m = "0"+month;
                }

                //showing date selected to the user
                String dateString = d+"/"+ m + "/" + year;
                editTextDate.setText(dateString);
            }
        }, YEAR, MONTH, DATE);

        //showing the dialog upon method call
        datePickerDialog.show();
    }

    //method to add reservation to the database
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AddReservation(int tableId, String name, String email, int phoneNumber,
                               String startTime, String date){

        //checking for null and notifying the user with toast
        if(tableId == 0 || name == null || email == null || phoneNumber == 0 || startTime == null
        || date == null){
            Toast. makeText(getApplicationContext(),
                    "Some information is missing",
                    Toast. LENGTH_LONG).show();
        }

        //if everything is ok .....
        else {
            //checking if a reservation on that table at the time is already made by calling the method checkingIfBookingAlreadyExists
            if(checkingIfBookingAlreadyExists(tableId, startTime, date)) {

                //getting the endtime (to calculate if the table will be able to reserve again after 2 hours)
                LocalTime starting = LocalTime.parse(startTime);
                LocalTime endtime = starting.plusHours(2);
                String endTime = endtime.toString();

                //inserting the booking in the db.
                boolean check = myDb.insertReservation(tableId, name, email, phoneNumber, startTime, endTime, date);
                //checking if the booking was successfull or not
                if (check) {
                    Toast.makeText(getApplicationContext(),
                            "Reservation Sent",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Problem sending Reservation, contact us instead",
                            Toast.LENGTH_LONG).show();
                }
            }
            //if booking already exists ... notifying the user
            else {
                Toast.makeText(getApplicationContext(),
                        "Reservation Exists. Choose another table or another time/date",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    //method to check if the a booking is within the two hour period of the same booking
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkingIfBookingAlreadyExists(int tableId, String startTime, String date){

        //getting all the reservations from the db and putting them in a cursor
        Cursor cursor = myDb.getAllReservations();

        //if the cursor is empty (no bookings exist), the method returns true
        if (cursor.getCount() == 0)
            return true;
        else{
            //looping through the cursor to check each booking
            while (cursor.moveToNext()){

                //checking whether the dates are equal
                if(date.equals(cursor.getString(7))){

                    //checking whether the time is withing 2 hours of the booking
                    if((LocalTime.parse(startTime).isAfter(LocalTime.parse(cursor.getString(5)))) &&
                            (LocalTime.parse(startTime).isBefore(LocalTime.parse(cursor.getString(6))))){

                        //if the tableId is the same, it returns false, meaning the table is already occupied
                        if (tableId == cursor.getInt(1)){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }
}
