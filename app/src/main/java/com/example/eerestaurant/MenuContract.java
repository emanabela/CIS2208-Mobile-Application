package com.example.eerestaurant;

import android.provider.BaseColumns;

//this class is used to get the items from the database in the MenuAdapter. It mitigates the chance of typing any typos when referring to the column
public class MenuContract {
    private MenuContract(){}

    public static final class MenuEntry implements BaseColumns{
        public static final String TABLE_NAME = "menu_table";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_CATEGORY = "CATEGORY";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_PRICE = "PRICE";
    }
}
