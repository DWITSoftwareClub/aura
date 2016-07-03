package com.example.sanjay.aurademo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Aura_Activity extends AppCompatActivity implements View.OnClickListener {
Button yes, no;
    Intent intent;
    String mood;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String moodText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aura_);
        spinner= (Spinner)findViewById(R.id.spinner);
        adapter= ArrayAdapter.createFromResource(this,R.array.your_mood,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mood=spinner.getSelectedItem().toString();
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected",Toast.LENGTH_LONG).show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        yes=(Button) findViewById(R.id.btnYes);
        no=(Button) findViewById(R.id.btnNo);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    public void onClick(View v) {
        int id= v.getId();
        switch (id)
        {
            case R.id.btnYes :
                 intent= new Intent(Aura_Activity.this,AutomatedPlaylist.class);
                    intent.putExtra("userMood",mood);
                startActivity(intent);
                break;
            case R.id.btnNo :
                intent= new Intent(this,All_Songs.class);
                startActivity(intent);
                break;

        }





        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aura_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
