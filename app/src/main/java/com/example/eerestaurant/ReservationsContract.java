package com.example.eerestaurant;

import android.provider.BaseColumns;

//this class is used to get the items from the database in the ReservationsAdapter. It mitigates the chance of typing any typos when referring to the column
public class ReservationsContract {
    private ReservationsContract() {}

    public static final class ReservationsEnty implements BaseColumns{
        public static final String TABLE_NAME = "reservations_table";
        public static final String COLUMN_ID ="ID";
        public static final String COLUMN_TABLEID ="TABLEID";
        public static final String COLUMN_NAME ="NAME";
        public static final String COLUMN_EMAIL ="EMAIL";
        public static final String COLUMN_PHONENUMBER ="PHONENUMBER";
        public static final String COLUMN_STARTTIME ="STARTTIME";
        public static final String COLUMN_DATE ="DATE";
        public static final String COLUMN_APPROVAL ="APPROVAL";
    }
}
