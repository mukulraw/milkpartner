package com.youthlive.milkpartner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.youthlive.milkpartner.BookingPOJO.Datum;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by tvs on 11/14/2017.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    Context context;

    List<Datum> list = new ArrayList<>();

    public BookingAdapter(Context context , List<Datum>list){

        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.booking_list_model ,parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Datum item =list.get(position);

        holder.one.setText(String.valueOf(position + 1));

        holder.id.setText(item.getBookingId());

       holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent i = new Intent(context , Home2.class);

                Bundle b = new Bundle();
                b.putString("state" , item.getState());
                b.putString("district" , item.getDistrict());
                b.putString("bookingid" , item.getBookingId());
                b.putString("ares" , item.getArea());
                b.putString("image" , item.getImage());

                Intent i = new Intent();

                i.putExtras(b);

               // context.setResult(RESULT_OK, i);
                //context.finish();

                //context.startActivity(i);

            }
        });



    }


    public void setgrid(List<Datum>list){


        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{


       TextView edit , id , one;

        public MyViewHolder(View itemView) {
            super(itemView);

            edit = (TextView)itemView.findViewById(R.id.edit);
            id = (TextView)itemView.findViewById(R.id.id);
            one = (TextView)itemView.findViewById(R.id.one);


        }
    }
}
