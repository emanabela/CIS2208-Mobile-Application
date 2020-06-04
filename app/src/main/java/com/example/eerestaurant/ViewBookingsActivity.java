package com.example.eerestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Calendar;

//this page is used by the admin to view all of the bookings
public class ViewBookingsActivity extends AppCompatActivity implements ReservationsAdapter.OnReservationListener {

    //initialising mydb
    DatabaseHelper myDb;

    //getting adapter
    private ReservationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        //declaring mydb
        myDb = new DatabaseHelper(this);

        //getting the recyclerView
        RecyclerView recyclerView = findViewById(R.id.list_of_bookings);

        //setting the Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //declaring the Adapter
        mAdapter = new ReservationsAdapter(this, getAllReservations(), this);

        //setting the adapter
        recyclerView.setAdapter(mAdapter);

    }

    //method to get all of the reservations
    private Cursor getAllReservations(){
        return myDb.getAllAdministrationViewReservations();
    }

    //onclick method for the recycleviewer
    @Override
    public void onReservationClick(int position) {
        startActivity(new Intent(this,
                ApproveReservationActivity.class));
    }
}
