package ir.alisoftware.app.jcolors.ListRecycler;

import android.graphics.Color;

public class ColorItem {
    public int ColorCode;
    public  String ColorName;
    public int Id;

    public ColorItem(int colorCode,String colorName){
        this.ColorCode = colorCode;
        this.ColorName = colorName;
    }

    public ColorItem(int colorCode,String colorName,int Id){
        this.ColorCode = colorCode;
        this.ColorName = colorName;
        this.Id = Id;
    }

    public static int ToColor(int Alpha,int Red,int Green,int Blue){
        return Color.argb(Alpha,Red,Green,Blue);
    }

}
