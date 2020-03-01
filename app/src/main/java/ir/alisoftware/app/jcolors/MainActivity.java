package ir.alisoftware.app.jcolors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ir.alisoftware.app.jcolors.ColorDB.DB;
import ir.alisoftware.app.jcolors.ListRecycler.ColorAdaper;
import ir.alisoftware.app.jcolors.ListRecycler.ColorItem;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private Boolean Flag_Exit = false;
    private RecyclerView listView;

    @Override
    protected void onResume() {
        super.onResume();

        ReloadListView();
    }

    private ColorItem SelectedItem(){
        return ((ColorAdaper)listView.getAdapter()).getSelectedItem();
    }

    @SuppressLint({"WrongConstant", "ResourceAsColor"})
    private void DoCopy(){
        ClipboardManager manager =(ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText(getString(R.string.selected_color),SelectedItem().ColorName);
        manager.setPrimaryClip(cd);
        Snackbar snackbar =
        Snackbar.make(listView,R.string.color_copied,Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.string_ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        snackbar.show();
    }

    private void DoShare(){
        Uri uri = Uri.parse(SelectedItem().ColorName);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,SelectedItem().ColorName);
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.share_color));
        Intent chooser= Intent.createChooser(intent,getString(R.string.share_color));
        if(chooser != null)
        {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            startActivity(chooser);
        }
    }

    @SuppressLint("WrongConstant")
    private void _Delete(ColorItem item){
        new DB(getApplicationContext()).Delete(item.Id);
        Snackbar.make(listView,item.ColorName + " با موفقیت حذف شد" ,Snackbar.LENGTH_LONG).show();
        ReloadListView();
    }

    @SuppressLint("WrongConstant")
    public void DoDelete(){
        Snackbar.make(listView,
                R.string.ask_delete,Snackbar.LENGTH_LONG).setAction(R.string.string_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _Delete(SelectedItem());
            }
        }).show();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        switch(item.getItemId()){
            case R.id.menu_copy:
                DoCopy();
                return true;
            case R.id.menu_share:
                DoShare();
                return  true;
            case R.id.menu_delete:
                DoDelete();
                return true;
        }


        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fabAddColor);
        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DefineColorActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.recycleMain);

    }

    private void ReloadListView(){
        DB db = new DB(getApplicationContext());
        ArrayList<String> colors = db.getColorNames();
        ColorAdaper colorAdaper = new ColorAdaper();
        colorAdaper.setColors(db.getColorItems());

        RecyclerView.LayoutManager manager =
                    new GridLayoutManager(getApplicationContext(),2);
        listView.setAdapter(colorAdaper);
        listView.setLayoutManager(manager);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if(Flag_Exit)
            System.exit(0);
        else{

             Snackbar.make(fab,R.string.exit_ask,Snackbar.LENGTH_SHORT)
                     .setAction(R.string.Snack_Posetive, new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             System.exit(0);
                         }
                     }).show();

            Handler handler = new Handler();
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    Flag_Exit = false;
                }
            };
            handler.postDelayed(run,500);
        }
        //super.onBackPressed();
    }
} // Class
