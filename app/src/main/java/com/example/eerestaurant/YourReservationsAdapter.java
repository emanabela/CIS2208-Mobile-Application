package com.example.eerestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//adapter for user's reservations
public class YourReservationsAdapter extends RecyclerView.Adapter<YourReservationsAdapter.YourReservationsViewHolder>{

    //initialising private variables
    private Context mContext;
    private Cursor mCursor;

    //constructor for Menu Adapter
    public YourReservationsAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    //class for the ViewHolder
    public class YourReservationsViewHolder extends RecyclerView.ViewHolder{

        //initialising variables
        public TextView tableIdText;
        public TextView nameText;
        public TextView emailText;
        public TextView phoneNumberText;
        public TextView startTimeText;
        public TextView dateText;
        public TextView approvalText;

        //constructor
        public YourReservationsViewHolder(@NonNull View itemView) {
            super(itemView);

            tableIdText = itemView.findViewById(R.id.list_item_tableId);
            nameText = itemView.findViewById(R.id.list_item_name);
            emailText = itemView.findViewById(R.id.list_item_email);
            phoneNumberText = itemView.findViewById(R.id.list_item_phonenumber);
            startTimeText = itemView.findViewById(R.id.list_item_time);
            dateText = itemView.findViewById(R.id.list_item_date);
            approvalText = itemView.findViewById(R.id.list_item_approval);

        }

    }

    @NonNull
    @Override
    public YourReservationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_listview, parent, false);
        return new YourReservationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourReservationsViewHolder holder, int position) {
        //checking if there is nothing more left in the cursor
        if(!mCursor.moveToPosition(position)){
            return;
        }

        //getting the items from the cursor, using the MenuContract class
        int tableid = mCursor.getInt(mCursor.getColumnIndex(ReservationsContract.ReservationsEnty.COLUMN_TABLEID));
        String name = mCursor.getString(mCursor.getColumnIndex(ReservationsContract.ReservationsEnty.COLUMN_NAME));
        String email = mCursor.getString(mCursor.getColumnIndex(ReservationsContract.ReservationsEnty.COLUMN_EMAIL));
        int phonenumber = mCursor.getInt(mCursor.getColumnIndex(ReservationsContract.ReservationsEnty.COLUMN_PHONENUMBER));
        String starttime = mCursor.getString(mCursor.getColumnIndex(ReservationsContract.ReservationsEnty.COLUMN_STARTTIME));
        String date = mCursor.getString(mCursor.getColumnIndex(ReservationsContract.ReservationsEnty.COLUMN_DATE));
        String approval = mCursor.getString(mCursor.getColumnIndex(ReservationsContract.ReservationsEnty.COLUMN_APPROVAL));

        //loading the data in the activity_menulistview layout
        holder.tableIdText.setText(String.valueOf(tableid));
        holder.nameText.setText(name);
        holder.emailText.setText(email);
        holder.phoneNumberText.setText(String.valueOf(phonenumber));
        holder.startTimeText.setText(starttime);
        holder.dateText.setText(date);
        holder.approvalText.setText(approval);
    }

    //method to get the number of items in the cursor
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
