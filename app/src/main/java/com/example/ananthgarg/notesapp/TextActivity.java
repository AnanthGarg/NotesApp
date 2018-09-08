package com.example.ananthgarg.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashSet;

public class TextActivity extends AppCompatActivity {
EditText editText;
     int pos;
     SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        editText = findViewById(R.id.editText2);
        sharedPreferences = this.getSharedPreferences("com.example.ananthgarg.notesapp", Context.MODE_PRIVATE);
        Intent intent = getIntent();
         pos = intent.getIntExtra("Value",-1);
        if(pos!=-1) {
            editText.setText(MainActivity.arrayList.get(pos));
        }
        else
        {
            MainActivity.arrayList.add("New Text");
            pos = MainActivity.arrayList.size()-1;
            editText.setText(MainActivity.arrayList.get(pos));
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }
        

        editText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               MainActivity.arrayList.set(pos, String.valueOf(s));
               MainActivity.arrayAdapter.notifyDataSetChanged();
               HashSet<String> set = new HashSet<String>(MainActivity.arrayList);
               try {
                   sharedPreferences.edit().putStringSet("notes",set).apply();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void afterTextChanged(Editable s) {


           }
       });




       // MainActivity.arrayAdapter.notifyDataSetChanged();
    }
}
