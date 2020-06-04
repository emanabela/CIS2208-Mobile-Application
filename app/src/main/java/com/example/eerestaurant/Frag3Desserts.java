package com.example.eerestaurant;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Frag3Desserts extends Fragment implements MenuAdapter.onItemListener{

    //initialising myDb
    DatabaseHelper myDb;

    //initialising MenuAdapter
    private MenuAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag3_dessert_layout, container, false);

        //declaring myDb
        myDb = new DatabaseHelper(view.getContext());

        //getting the recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.list_of_desserts);

        //setting the Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //declaring the Adapter
        mAdapter = new MenuAdapter(getContext(), gettAllItems(), this);

        //setting the adapter
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    //method to get All of the items from the databse
    private Cursor gettAllItems(){
        return myDb.getAllDesserts();
    }

    //method for the the onclick for the reyclerview.
    @Override
    public void onNoteClick(int position) {
        //going the the Activity were the user can view details on the menu Item
        startActivity(new Intent(getContext(),
                MenuItemActivity.class));
    }
}
