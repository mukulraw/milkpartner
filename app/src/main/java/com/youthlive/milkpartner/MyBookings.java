package com.youthlive.milkpartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyBookings extends AppCompatActivity {

    TextView edit1 , edit2 , edit3 , edit4;


   /* RecyclerView grid;
    GridLayoutManager manager;
    BookingAdapter adapter;
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);


        edit1 = (TextView) findViewById(R.id.edit);
        edit2 = (TextView) findViewById(R.id.edit1);
        edit3 = (TextView) findViewById(R.id.edit3);
        edit4 = (TextView) findViewById(R.id.edit4);

      edit1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              Intent i =  new Intent(MyBookings.this , Home.class);
              startActivity(i);


          }
      });


        /*grid = (RecyclerView)findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext() , 1);

        adapter = new BookingAdapter(this);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);*/

    }
}
