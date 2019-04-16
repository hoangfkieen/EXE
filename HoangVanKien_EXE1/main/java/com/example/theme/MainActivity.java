package com.example.theme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Spinner spThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_main);
        setupSpinnerItemSelection();

    }
    private void setupSpinnerItemSelection() {
        spThemes = (Spinner) findViewById(R.id.spThemes);
        spThemes.setSelection(ThemeApplication.currentPosition);
        ThemeApplication.currentPosition = spThemes.getSelectedItemPosition();

        spThemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (ThemeApplication.currentPosition != position) {
                    Utils.changeToTheme(MainActivity.this, position);
                }
                ThemeApplication.currentPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
