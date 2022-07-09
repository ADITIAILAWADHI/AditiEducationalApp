package com.ADITIAILAWADHI.aditieducationalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Button goButton = findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameText = findViewById(R.id.nameText);
                String username = nameText.getText().toString().trim();
                if(username.length() == 0){
                    Toast.makeText(LandingActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences preferences = getSharedPreferences("aditi_item", MODE_PRIVATE);
                preferences.edit().clear().putString("username", username).apply();
                Intent intent = new Intent(LandingActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.score) {
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.home) {
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.setting){
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}