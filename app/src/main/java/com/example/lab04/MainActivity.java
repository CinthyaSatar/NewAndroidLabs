package com.example.lab04;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    MyListAdapter myAdapter;
    private ArrayList<myObj> elements = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText input = findViewById(R.id.type);
        Switch isUrgent = findViewById(R.id.urgent);
        ListView myList = findViewById(R.id.toDo);
        String alertTitle = getResources().getString(R.string.alertTitle);
        String alertMessage = getResources().getString(R.string.alertMessage);
        String alertYes = getResources().getString(R.string.alertPositiveButton);
        String alertNo = getResources().getString(R.string.alertNegativeButton);




        Button add = findViewById(R.id.add);
        add.setOnClickListener(click -> {
            elements.add(new myObj(input.getText().toString(), isUrgent.isChecked()));
            myAdapter.notifyDataSetChanged();
            input.setText("");

        });

        myList.setAdapter( myAdapter = new MyListAdapter() );
        myList.setOnItemLongClickListener((parent, view, pos, id) ->{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(alertTitle)
                    .setMessage(alertMessage + pos)
                    .setPositiveButton(alertYes, (click, arg) -> {
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(alertNo, (click, arg) -> {})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            myAdapter.notifyDataSetChanged();
            return false;
        });

    }
    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }
        public String getItem(int position) {
            return elements.get(position).text;
        }
        public Boolean isUrgent(int position) {
            return elements.get(position).urgency;
        }
        public long getItemId(int position) {
            return (long) position;
        }
        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();
            if (newView == null) {
                newView = inflater.inflate(R.layout.todo, parent, false);
            }
            TextView tView = newView.findViewById(R.id.insertText);
            tView.setText(getItem(position));
            Color mColor = new Color();
            if(isUrgent(position))
                newView.setBackgroundColor(Color.parseColor("#ff0000"));
            return newView;
        }
    }
    private class myObj {
        String text;
        boolean urgency;

        public myObj(String text,boolean urgency ){
            this.text=text;
            this.urgency=urgency;
        }
    }
}