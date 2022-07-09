package com.ADITIAILAWADHI.aditieducationalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner levelSpinner = findViewById(R.id.levelSpinner);
                String levelStr = levelSpinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("levelStr", levelStr);
                startActivity(intent);
            }
        });

        // initialise variable
        SwitchMaterial SwitchBtn = findViewById(R.id.switchBtn);

        // switch theme mode per user wishes
        SwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    buttonView.setText("Night Mode");
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    buttonView.setText("Light Mode");
                }
            }
        });

        //is night mode on?
        boolean isNightModeOn = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        SwitchBtn.setChecked(isNightModeOn);

        if(isNightModeOn) {
            SwitchBtn.setText("Night Mode");
        }else {
            SwitchBtn.setText("Light Mode");
        }
    }

    // remove eye flickers
    @Override
    public void recreate() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        startActivity(getIntent());
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.score){
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.home) {
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}