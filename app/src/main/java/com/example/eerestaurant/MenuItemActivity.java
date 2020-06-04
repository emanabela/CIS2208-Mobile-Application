package com.example.eerestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//this page is used to show the item when it is clicked in one of the menu fragments
public class MenuItemActivity extends AppCompatActivity {

    //Declaring mydbhelper
    DatabaseHelper myDb;

    //shared prefs
    public static final String SHARED_PREFS = "shareId";
    public static final String ITEMID = "itemid";


    //initialising TextViews
    TextView textViewMenuItemName, textViewMenuItemPrice;

    //initialising ImageView
    ImageView imageViewItemMenuItemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        //initialising db
        myDb = new DatabaseHelper(this);

        //declaring TextViews
        textViewMenuItemName = (TextView) findViewById(R.id.textViewMenuItemName);
        textViewMenuItemPrice = (TextView) findViewById(R.id.textViewMenuItemPrice);

        //declaring ImageViews
        imageViewItemMenuItemImage = (ImageView)findViewById(R.id.imageViewMenuItemImage);

        //getting the item selectedId
        long itemId = getItemId();

        //calling the fillpage method and passing the itemId as parameter
        fillPage(itemId);
    }

    //this method is use to get the item id set in the preferences
    public long getItemId(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFS, MODE_PRIVATE);

        return sharedPreferences.getLong(ITEMID, 0);
    }

    //method to get the details from the database and show them to the user
    public void fillPage(long id){

        //getting the data from the db and putting them in a cursor
        Cursor cursor = myDb.getSingle(id);

        //checking if the cursor is null, in that case it takes the user back to the memu activity
        if (cursor.getCount() == 0){
            //return to menu Page
            startActivity(new Intent(this,
                    MenuActivity.class));
        }

        //else if the cursor is not null ...
        else {
            byte[] image = null;

            //parsing the cursor to get the data
            while (cursor.moveToNext()){

                //assigning the text to their respective text views
                textViewMenuItemName.setText(cursor.getString(2));
                textViewMenuItemPrice.setText(cursor.getString(3));

                //getting the byte array of the image
                image = cursor.getBlob(4);

                //setting the image by calling the method getImage (declared after this method)
                imageViewItemMenuItemImage.setImageBitmap(getImage(image));
            }
        }
    }

    //method to chage from byte array to bitmap
    public Bitmap getImage(byte[] data){
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
