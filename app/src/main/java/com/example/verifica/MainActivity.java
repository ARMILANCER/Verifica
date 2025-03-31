package com.example.verifica;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> activities;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);

        sharedPreferences = getSharedPreferences("ActivityLogger", Context.MODE_PRIVATE);
        activities = new ArrayList<>();
        activities.addAll(getSavedActivities());

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activities);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activity = editText.getText().toString();
                if (!activity.equals("")) {
                    activities.add(activity);
                    adapter.notifyDataSetChanged();
                    saveActivities();
                    editText.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Campo 'Aggiungi Attività' vuoto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemLongClickListener(parent, view, position,id -> {
            activities.remove(position);
            adapter.notifyDataSetChanged();
            saveActivities();
            return true;
        });
    }
    private Set<String> getSavedActivities() {
        return sharedPreferences.getStringSet("activity_list", new HashSet<>());
    }
    private void saveActivities() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("activity_list", new HashSet<>(activities));
        editor.apply();
    }
}
