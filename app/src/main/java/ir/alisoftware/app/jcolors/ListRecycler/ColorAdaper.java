package ir.alisoftware.app.jcolors.ListRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.alisoftware.app.jcolors.R;

public class ColorAdaper extends RecyclerView.Adapter {
    private ArrayList<ColorItem> colors;
    private ColorItem SelectedItem;
    public void setColors(ArrayList<ColorItem> colors){
        this.colors = colors;
    }
    public ColorItem getSelectedItem(){
        return SelectedItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.colors_layout,null);

        return new ColorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ColorItem item = colors.get(position);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                SelectedItem = item;
            }
        };

        ((ColorListViewHolder)holder).bindview(item,run);
    }

    @Override
    public int getItemCount() {
        return this.colors.size();
    }
}
