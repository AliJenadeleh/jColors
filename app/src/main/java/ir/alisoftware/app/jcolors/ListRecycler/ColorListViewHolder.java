package ir.alisoftware.app.jcolors.ListRecycler;

import android.annotation.SuppressLint;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.android.material.snackbar.Snackbar;

import ir.alisoftware.app.jcolors.MainActivity;
import ir.alisoftware.app.jcolors.R;

public class ColorListViewHolder extends ViewHolder implements View.OnCreateContextMenuListener {

   private TextView txtColor;
   private CardView colorCard;
   private  Runnable RunSelectedMenu;

    public ColorListViewHolder(@NonNull View itemView)
    {
        super(itemView);
        txtColor = itemView.findViewById(R.id.colorView);
        colorCard = itemView.findViewById(R.id.colorCard);
        itemView.setOnCreateContextMenuListener(this);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RunSelectedMenu.run();
                return false;
            }
        });
    }

    public void bindview(ColorItem colorItem,Runnable runnable){
        txtColor.setText(colorItem.ColorName);
        //txtColor.setBackgroundColor(colorItem.ColorCode);
        colorCard.setBackgroundColor(colorItem.ColorCode);
        this.RunSelectedMenu = runnable;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(v.getContext());
        inflater.inflate(R.menu.card_item_menu,menu);
    }


}
