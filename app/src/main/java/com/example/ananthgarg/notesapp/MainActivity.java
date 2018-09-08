package com.example.ananthgarg.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


public class MainActivity extends AppCompatActivity {
    static ArrayList<String> arrayList;
    static ArrayAdapter arrayAdapter;
    ListView listView;
SharedPreferences sharedPreferences;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.addaniote) {
             Intent intent = new Intent(this,TextActivity.class);
             startActivity(intent);
         }
         return  false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.example.ananthgarg.notesapp", Context.MODE_PRIVATE);
HashSet<String> hashSet =(HashSet<String>) sharedPreferences.getStringSet("notes",null);

if(hashSet==null) {
    arrayList = new ArrayList<String>();
    arrayList.add("New Text");
}
else
{
    arrayList = new ArrayList(hashSet);
}


        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"You Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new  Intent(MainActivity.this,TextActivity.class);
                if(arrayList.size()>0)
                intent.putExtra("Value",position);
                startActivity(intent);

            }
        });
listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert!!")
                .setMessage("Do you Really want to Delete it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "It's done!", Toast.LENGTH_SHORT).show();
                        arrayList.remove(position);
arrayAdapter.notifyDataSetChanged();
                        HashSet<String> set = new HashSet<String>(arrayList);
                        try {
                            sharedPreferences.edit().putStringSet("notes",set).apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(MainActivity.this, "It's do", Toast.LENGTH_SHORT).show();

            }
        }).show();
        return true;
            }
});
    }
}
