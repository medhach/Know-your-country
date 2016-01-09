package com.example.medha.knowyourcountry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoPageActivity extends Activity {
    TextView name, capital, langs, demo, area, popu, curr, callcode;
    String[] data;
    ImageView flag;
    int value;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        Intent i= getIntent();
        value= Integer.parseInt(i.getStringExtra("SESSION_ID"));
        name = (TextView)findViewById(R.id.country_name);
        capital = (TextView)findViewById(R.id.capital);
        langs = (TextView)findViewById(R.id.lang);
        demo = (TextView)findViewById(R.id.demonym);
        area = (TextView)findViewById(R.id.area);
        popu = (TextView)findViewById(R.id.population);
        curr = (TextView)findViewById(R.id.curr1);
        callcode = (TextView)findViewById(R.id.callcode);
        flag = (ImageView)findViewById(R.id.imageView);
        next = (Button)findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InfoPageActivity.class);
                i.putExtra("SESSION_ID", ""+(value+1));
                startActivity(i);
            }
        });
        DataBaseHelper db = new DataBaseHelper(this);
         data = db.getData(value);
        setData();
    }

    public void setData()
    {
        name.setText(""+data[0]);
        capital.setText(""+data[1]);
        langs.setText(""+data[2]);
        demo.setText(""+data[3]);
        area.setText(""+data[4]);
        popu.setText(""+data[5]);
        curr.setText(""+data[6]);
        callcode.setText(""+data[7]);
        if(value==0)
        flag.setImageResource(R.drawable.f1);
        else if(value==1)
            flag.setImageResource(R.drawable.f2);
        else if(value==2)
            flag.setImageResource(R.drawable.f3);
        else if(value==3)
            flag.setImageResource(R.drawable.f4);
        else if(value==4)
            flag.setImageResource(R.drawable.f5);
        else if(value==5)
            flag.setImageResource(R.drawable.f6);
        else if(value==6)
            flag.setImageResource(R.drawable.f7);
        else if(value==7)
            flag.setImageResource(R.drawable.f8);
        else if(value==8)
            flag.setImageResource(R.drawable.f9);
        else if(value==9)
            flag.setImageResource(R.drawable.f10);
        else if(value==10)
            flag.setImageResource(R.drawable.f11);
        else
        {}
    }
}
