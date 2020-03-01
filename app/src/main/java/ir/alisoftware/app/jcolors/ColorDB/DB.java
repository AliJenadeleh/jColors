package ir.alisoftware.app.jcolors.ColorDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import ir.alisoftware.app.jcolors.ListRecycler.ColorItem;

public class DB {
    private final static int DB_VERSION = 1;
    private final static String DBNAME = "COLORDB";
    private final static String COLORTABLENAME = "tcolors";
    private Context context;
    private SQLiteDatabase db;

    private void InitiDB(){
        this.db =
                context.openOrCreateDatabase(DBNAME,Context.MODE_PRIVATE,null);

        if(db.getVersion() < DB_VERSION){
            db.execSQL("CREATE TABLE if not exists [tcolors](" +
                    "  [id] integer primary key autoincrement," +
                    "  [colorname] CHAR(1000) NOT NULL," +
                    "  [alpha] int DEFAULT 0," +
                    "  [red] int DEFAULT 0," +
                    "  [green] int DEFAULT 0," +
                    "  [blue] int DEFAULT 0);");

            db.setVersion(DB_VERSION);
        }
    }

    public  DB(Context context){
        this.context = context;
        InitiDB();
    }

    public ArrayList<String> getColorNames(){
        ArrayList<String> names = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select colorname from [tcolors]",null);
        cursor.moveToFirst();
        int inx =  cursor.getColumnIndex("colorname");
        while(!cursor.isAfterLast()){
            names.add(cursor.getString(inx));
            cursor.moveToNext();
        }

        return names;
    }

    public ArrayList<ColorItem> getColorItems(){
        ArrayList<ColorItem> names = new ArrayList<ColorItem>();
        Cursor cursor = db.rawQuery("select id,colorname,alpha,red,green,blue from [tcolors]",null);
        cursor.moveToFirst();
        int iid = cursor.getColumnIndex("id");
        int iname =  cursor.getColumnIndex("colorname");
        int ialpha = cursor.getColumnIndex("alpha");
        int ired = cursor.getColumnIndex("red");
        int igreen = cursor.getColumnIndex("green");
        int iblue = cursor.getColumnIndex("blue");
        while(!cursor.isAfterLast()){
            String name = cursor.getString(iname);
            int a = cursor.getInt(ialpha);
            int r = cursor.getInt(ired);
            int g = cursor.getInt(igreen);
            int b = cursor.getInt(iblue);
            int id = cursor.getInt(iid);
            names.add(new ColorItem(ColorItem.ToColor(a,r,g,b),name,id));
            cursor.moveToNext();
        }

        return names;
    }

    public void InsertNewColor(String colorName,int alpha,int red,int green,int blue){
        String script = "insert into [tcolors](colorname,alpha,red,green,blue) values('%1$s',%2$d,%3$d,%4$d,%5$d)";
        script= String.format(script,colorName,alpha,red,green,blue);
        db.execSQL(script);
    }

    public void Delete(int id){
        db.execSQL("delete from tcolors where id=" + id);
    }
}
