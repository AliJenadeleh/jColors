package ir.alisoftware.app.jcolors;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import ir.alisoftware.app.jcolors.ColorDB.DB;

import static ir.alisoftware.app.jcolors.StaticStuffs.APPTAG;

public class DefineColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private String colorName;
    private SeekBar seekRed, seekGreen, seekBlue, seekAlpha;
    private TextView txtStyle1,txtStyle2,txtStyle3,txtStyle4;
    private TextView txtAlpha,txtRed,txtGreen,txtBlue;
    private MaterialButton btnBack,btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_color);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB db = new DB(getApplicationContext());
                db.InsertNewColor(colorName
                        ,seekAlpha.getProgress() // alpha
                        , seekRed.getProgress() // R
                        , seekGreen.getProgress() // G
                        , seekBlue.getProgress());

                Log.i(APPTAG,colorName + " was added!");
                finish();
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new MaterialButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        seekRed = findViewById(R.id.colorRed);
        seekGreen = findViewById(R.id.colorGreen);
        seekBlue = findViewById(R.id.colorBlue);
        seekAlpha = findViewById(R.id.colorAlpha);

        seekRed.setOnSeekBarChangeListener(this);
        seekGreen.setOnSeekBarChangeListener(this);
        seekBlue.setOnSeekBarChangeListener(this);
        seekAlpha.setOnSeekBarChangeListener(this);

        txtStyle1 = findViewById(R.id.txtStyle1);
        txtStyle2 = findViewById(R.id.txtStyle2);
        txtStyle3 = findViewById(R.id.txtStyle3);
        txtStyle4 = findViewById(R.id.txtStyle4);

        txtAlpha = findViewById(R.id.txtAlpha);
        txtRed = findViewById(R.id.txtRed);
        txtGreen = findViewById(R.id.txtGreen);
        txtBlue = findViewById(R.id.txtBlue);

        UpdateColors();
    }


    private void UpdateColors(){
        int color = Color.argb(
                seekAlpha.getProgress() // alpha
                , seekRed.getProgress() // R
                , seekGreen.getProgress() // G
                , seekBlue.getProgress()); // B;

        txtStyle1.setBackgroundColor(color);
        txtStyle2.setBackgroundColor(color);

        txtStyle3.setTextColor(color);
        txtStyle4.setTextColor(color);

        colorName = String.format("#%06X", (0xFFFFFFFF & color));


        txtAlpha.setText("Alpha : " + seekAlpha.getProgress());

        txtRed.setText("Red : " + seekRed.getProgress());

        txtGreen.setText("Green : " + seekGreen.getProgress());

        txtBlue.setText("Blue : " + seekBlue.getProgress());

        txtStyle1.setText(colorName);
        txtStyle2.setText(colorName);
        txtStyle3.setText(colorName);
        txtStyle4.setText(colorName);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        UpdateColors();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
