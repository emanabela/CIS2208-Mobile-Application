package com.example.eerestaurant;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


//adapter for food menu
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    public static final String SHARED_PREFS = "shareId";
    public static final String ITEMID = "itemid";

    //initialising private variables
    private Context mContext;
    private Cursor mCursor;

    //initialising clickListener
    private onItemListener monItemListener;

    //constructor for Menu Adapter
    public MenuAdapter(Context context, Cursor cursor, onItemListener onItemListener){
        mContext = context;
        mCursor = cursor;
        monItemListener = onItemListener;
    }

    //declaring public array for onclick
    public long[] itemPressed = new long[10000];

    //class for the ViewHolder
    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //initialising variables
        public TextView nameText;
        public TextView priceText;

        //initialising onClick listener
        onItemListener onItemListener;

        //constructor
        public MenuViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);

            nameText = itemView.findViewById(R.id.plate_name);
            priceText = itemView.findViewById(R.id.plate_price);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        //onclick method
        @Override
        public void onClick(View v) {
            onItemListener.onNoteClick(getAdapterPosition());
            setPreference(itemPressed[getAdapterPosition()]);
        }
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_menulistview, parent, false);
        return new MenuViewHolder(view, monItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

        //checking if there is nothing more left in the cursor
        if(!mCursor.moveToPosition(position)){
            return;
        }

        //getting the items from the cursor, using the MenuContract class
        String category = mCursor.getString(mCursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_CATEGORY));
        String name = mCursor.getString(mCursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_NAME));
        String price = mCursor.getString(mCursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_PRICE));
        itemPressed[position] = mCursor.getLong(mCursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_ID));

        //loading the data in the activity_menulistview layout
        holder.nameText.setText(name);
        holder.priceText.setText(price);

    }

    //method to get the number of items in the cursor
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    //interface method for the onclick
    public interface onItemListener{
        void onNoteClick(int position);
    }

    //setting preferences for the item selected, to be able to show it if clicked and goes to the next page, MenuItemActivity
    public void setPreference(long id){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFS, mContext.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(ITEMID, id);

        editor.apply();
    }
}
