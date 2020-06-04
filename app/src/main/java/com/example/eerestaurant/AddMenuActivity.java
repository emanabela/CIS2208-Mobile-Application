package com.example.eerestaurant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.*;

public class AddMenuActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaring mydbhelper
    DatabaseHelper myDb;

    //declaring spinner
    Spinner categorySpinner;

    //declaring edittext
    EditText editTextPlateName, editTextPlatePricce;

    //declaring button
    Button buttonAddPlate;

    //declaring imagebutton
    ImageButton addImage;

    //integer needed to get te photo from the admin's device
    private static final int SELECT_PHOTO = 7777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);


        //initialising the db
        myDb = new DatabaseHelper(this);

        //initialising spinner
        categorySpinner = (Spinner)findViewById(R.id.menu_category);

        //creating Spinneraddapter
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Categories));

        //creatining the list in the menu (Starter, Main Course, Desserts)
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(myAdapter);

        //initialising editText
        editTextPlateName = (EditText)findViewById(R.id.editTextPlateName);
        editTextPlatePricce = (EditText)findViewById(R.id.editTextPlatePrice);

        //initialising button
        buttonAddPlate = (Button)findViewById(R.id.button_addPlate);

        //initialising image button
        addImage = (ImageButton)findViewById(R.id.add_image);

        //adding onClick Listener
        buttonAddPlate.setOnClickListener(this);
        addImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //switch for the onclick
        switch (v.getId()){
            case R.id.button_addPlate:

                //geting the bitmap
                Bitmap bitmap = getBitmapForImageSave();

                //converting the bitmap to byte array to store in db
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmap.recycle();

                //calling the addItem method
                AddItem(categorySpinner.getSelectedItem().toString(),
                        editTextPlateName.getText().toString(),
                        editTextPlatePricce.getText().toString(), byteArray);break;

            case R.id.add_image:
                //calling the getImage Method
                getImage();

        }
    }

    //method to insert the new menu item in the database
    public void AddItem(String category, String name, String price, byte[] image){
        //inserting the menu item in the database
        boolean result = myDb.insertMenuItem(category, name, price, image);

        //checking whether the item was added successfully or not (notifying the admin whether the plate was successfully registered or not)
        if(result){
            Toast. makeText(getApplicationContext(),
                    "Plate Added",Toast. LENGTH_LONG).show();
            startActivity(new Intent(this,
                    MainActivity.class));
        }
        else{
            Toast. makeText(getApplicationContext(),
                    "Could not add plate",Toast. LENGTH_LONG).show();
        }
    }

    //method to let the admin select the image of the plate from his device
    public void getImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    //this method is used to capture the image selected from the user
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null){
            Uri pickedImage = data.getData();

            //setting the  image to appear on the image button in the layout
            addImage.setImageURI(pickedImage);

        }
    }

    //method to get the bitmap image to save it in the database
    public Bitmap getBitmapForImageSave(){
        return ((BitmapDrawable)addImage.getDrawable()).getBitmap();
    }
}
