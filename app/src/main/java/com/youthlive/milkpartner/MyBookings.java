package com.youthlive.milkpartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MyBookings extends AppCompatActivity {

    RecyclerView grid;
    GridLayoutManager manager;
    BookingAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        grid = (RecyclerView)findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext() , 1);

        adapter = new BookingAdapter(this);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

    }
}
