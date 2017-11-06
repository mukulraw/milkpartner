package com.youthlive.milkpartner;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText email , pass;

    TextView sign;

    String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA};


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        sign = (TextView) findViewById(R.id.signin);


        if(!hasPermissions(this , PERMISSIONS))
        {
            ActivityCompat.requestPermissions(this , PERMISSIONS , REQUEST_CODE_ASK_PERMISSIONS);
        }


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this , Home.class);
                startActivity(i);

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
