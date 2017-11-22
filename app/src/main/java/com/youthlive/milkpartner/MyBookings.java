package com.youthlive.milkpartner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.youthlive.milkpartner.Book1POJO.BookBean;
import com.youthlive.milkpartner.BookingPOJO.BookingBean;
import com.youthlive.milkpartner.BookingPOJO.Datum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyBookings extends AppCompatActivity {


    RecyclerView grid;

    GridLayoutManager manager;

    BookingAdapter adapter;

    ProgressBar bar;

    EditText search;

    List<Datum> list;

    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        cd = new ConnectionDetector(this);

        bar = (ProgressBar)findViewById(R.id.progress);

        search = (EditText) findViewById(R.id.search);

        grid = (RecyclerView)findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext() , 1);

        list = new ArrayList<>();

        adapter = new BookingAdapter(this , list);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (charSequence.length()>0){


                   List<Datum> item = new ArrayList<>();

                    for (int j=0 ; j<list.size(); j++){


                        String s = list.get(j).getBookingId();

                        if (s.contains(charSequence))
                        {
                            item.add(list.get(j));
                        }


                    }

                    adapter.setgrid(item);

                }

                else {

                    adapter.setgrid(list);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        if (cd.isConnectingToInternet()){

            bar.setVisibility(View.VISIBLE);
            app b = (app)getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://nationproducts.in")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Allapi cr = retrofit.create(Allapi.class);

            Call<BookingBean> call = cr.booking(b.userId);

            call.enqueue(new Callback<BookingBean>() {
                @Override
                public void onResponse(Call<BookingBean> call, Response<BookingBean> response) {


                    list = response.body().getData();

                    adapter.setgrid(list);

                    bar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<BookingBean> call, Throwable t) {


                    bar.setVisibility(View.GONE);

                }
            });



        }

        else {

            Toast.makeText(MyBookings.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }





    }

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
                    b.putString("area" , item.getArea());
                    b.putString("image" , item.getImage());
                    b.putString("sample" , item.getSample());
                    b.putString("location" , item.getLocation());

                    Intent i = new Intent();

                    i.putExtras(b);

                    MyBookings.this.setResult(RESULT_OK, i);
                    MyBookings.this.finish();

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


}
