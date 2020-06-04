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

//adapter for admin side to approve reservations
public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationsViewHolder> implements View.OnClickListener{

    public static final String SHARED_PREFS = "sharedResId";
    public static final String ITEMID = "resid";

    //initialising private variables
    private Context mContext;
    private Cursor mCursor;

    //initialising clickListener
    private OnReservationListener mOnReservationListener;

    //constructor for Reservations Adapter
    public ReservationsAdapter(Context context, Cursor cursor, OnReservationListener onReservationListener){
        mContext = context;
        mCursor = cursor;
        this.mOnReservationListener = onReservationListener;
    }

    //declaring public array for onclick
    public long[] itemPressed = new long[10000];

    @Override
    public void onClick(View v) {

    }

    //class for VIewHolder
    public class ReservationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //initialising variables
        public TextView tableIdText;
        public TextView nameText;
        public TextView emailText;
        public TextView phoneNumberText;
        public TextView startTimeText;
        public TextView dateText;
        public TextView approvalText;

        //initialising onClick Listener
        OnReservationListener onReservationListener;

        //constructor
        public ReservationsViewHolder(@NonNull View itemView, OnReservationListener onReservationListener) {
            super(itemView);

            tableIdText = itemView.findViewById(R.id.list_item_tableId);
            nameText = itemView.findViewById(R.id.list_item_name);
            emailText = itemView.findViewById(R.id.list_item_email);
            phoneNumberText = itemView.findViewById(R.id.list_item_phonenumber);
            startTimeText = itemView.findViewById(R.id.list_item_time);
            dateText = itemView.findViewById(R.id.list_item_date);
            approvalText = itemView.findViewById(R.id.list_item_approval);

            this.onReservationListener = onReservationListener;

            itemView.setOnClickListener(this);
        }

        //onclick method
        @Override
        public void onClick(View v) {
            onReservationListener.onReservationClick(getAdapterPosition());
            setPreferences(itemPressed[getAdapterPosition()]);
        }
    }

    @NonNull
    @Override
    public ReservationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_listview, parent, false);
        return new ReservationsViewHolder(view, mOnReservationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationsViewHolder holder, int position) {

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
        itemPressed[position] = mCursor.getLong(mCursor.getColumnIndex(ReservationsContract.ReservationsEnty.COLUMN_ID));

        //loading the data in the activity_listview layout
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

    //interface method for the onclick
    public interface OnReservationListener{
        void onReservationClick(int position);
    }

    //setting preferences for the item selected, to be able to show it if clicked and goes to the next page, ApproveReservationsActivity
    public void setPreferences(long id){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS, mContext.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(ITEMID, id);

        editor.apply();
    }
}
