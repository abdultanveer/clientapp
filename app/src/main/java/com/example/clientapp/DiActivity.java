package com.example.clientapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.di.DaggerMyComponent;
import com.example.clientapp.di.MyComponent;
import com.example.clientapp.di.SharedPrefModule;

import javax.inject.Inject;

public class DiActivity extends AppCompatActivity {
    EditText inUsername, inNumber;
    Button btnSave, btnGet;
    private MyComponent myComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di);
        myComponent = DaggerMyComponent.builder()
                .sharedPrefModule(new SharedPrefModule(this))
                .build();
        myComponent.inject(this);

        Button saveButton = findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        EditText nameEditText = findViewById(R.id.inUsername);
        EditText phnoText = findViewById(R.id.inNumber);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("un",nameEditText.getText().toString());
        editor.putString("no",phnoText.getText().toString());
        editor.apply();

    }


}
