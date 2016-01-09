package com.example.medha.knowyourcountry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l = (ListView) findViewById(R.id.mobile_list);
        DataBaseHelper db = new DataBaseHelper(this);
        String[] data = db.getCountryList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, data);
        l.setAdapter(adapter);
        l.setOnItemClickListener(a);
    }
    AdapterView.OnItemClickListener a = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           // Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), InfoPageActivity.class);
            i.putExtra("SESSION_ID", ""+(id+1));
            startActivity(i);
        }
    };


}

