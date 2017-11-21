package com.youthlive.milkpartner;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.youthlive.milkpartner.loginPOJO.loginBean;

import java.util.Objects;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText email , pass;

    TextView sign;

    ProgressBar bar;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        sign = (TextView) findViewById(R.id.signin);
        bar = (ProgressBar)findViewById(R.id.progress);




        if(!hasPermissions(this , PERMISSIONS))
        {
            ActivityCompat.requestPermissions(this , PERMISSIONS , REQUEST_CODE_ASK_PERMISSIONS);
        }
        else
        {

            String e = pref.getString("email" , "");
            String p = pref.getString("pass" , "");

            if (e.length() > 0 && p.length() > 0)
            {
                login(e , p);
            }

        }


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String e = email.getText().toString();
                final String p = pass.getText().toString();


                if (Utils.isValidMail(e))
                {

                    if (p.length() > 0)
                    {

                        login(e , p);

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this , "Invalid Password" , Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this , "Invalid Email" , Toast.LENGTH_SHORT).show();
                }






            }
        });
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    public void login(final String e , final String p)
    {
        bar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nationproducts.in")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Allapi cr = retrofit.create(Allapi.class);

        Call<loginBean> call = cr.login(e , p);

        call.enqueue(new Callback<loginBean>() {
            @Override
            public void onResponse(Call<loginBean> call, Response<loginBean> response) {


                if (Objects.equals(response.body().getStatus(), "1"))
                {

                    app b = (app)getApplicationContext();

                    b.userId = response.body().getData().getUserId();
                    b.userName = response.body().getData().getUserName();
                    b.email = response.body().getData().getEmail();
                    b.image = response.body().getData().getUserImage();

                    edit.putString("email" , e);
                    edit.putString("pass" , p);
                    edit.apply();

                    Toast.makeText(MainActivity.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this , Home.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                }



                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<loginBean> call, Throwable t) {
                bar.setVisibility(View.GONE);
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS)
        {
            if (ActivityCompat.checkSelfPermission(getApplicationContext() , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext() , Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            {



            }
            else
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.CAMERA)) {

                    Toast.makeText(getApplicationContext() , "Permissions are required for this app" , Toast.LENGTH_SHORT).show();
                    finish();

                }
                //permission is denied (and never ask again is  checked)
                //shouldShowRequestPermissionRationale will return false
                else {
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                            .show();
                    finish();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }
            }

        }


    }





}
