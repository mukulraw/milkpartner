package com.youthlive.milkpartner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tvs on 11/14/2017.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    Context context;

    public BookingAdapter(Context context){

        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.booking_list_model ,parent , false);
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{


        Button edit;

        public MyViewHolder(View itemView) {
            super(itemView);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context , Home.class);
                    context.startActivity(i);

                }
            });
        }
    }
}
