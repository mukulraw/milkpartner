package com.youthlive.milkpartner;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youthlive.milkpartner.Book1POJO.BookBean;
import com.youthlive.milkpartner.CityListPOJO.CityBean;
import com.youthlive.milkpartner.CityListPOJO.CityList;
import com.youthlive.milkpartner.StatePOJO.StateList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static java.security.AccessController.getContext;

public class Home2 extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView submit, upload, map, yes, no, yes1, no1 , start , yes2 , no2 , start1 , yes3 , no3 , name;
    Dialog dialog , dialog1 , dialog2 , dialog3;
    Location mLastLocation;

    ConnectionDetector cd;

    EditText area;

    TextView logout;

    TextView su;

    LinearLayout diph , diph1;


    ImageView image;
    CircleImageView profile;

    Context context;

    List<Bean> list;

    List<String> list1;

    ProgressBar bar;

    LinearLayout linear;

    LinearLayout linear1;

    LinearLayout linear2;

    Spinner sample;



    // String s, d , a , b , ima;


    List<String> statelist;

    //List<String> stateId;

    List<String> citylist;

    String stateId = "";

    String districtId = "";

    String longitude = "";

    String latitude = "";


    SharedPreferences pref;
    SharedPreferences.Editor edit;

    String samp = "";

    TextView state , district;

    Toolbar toolbar;

    String statename , stateid , districtname , districtid;

    protected LocationManager mLocationManager;
    protected LocationListener locationListener;

    private final int PICK_IMAGE_REQUEST = 2;
    private final int PICK_IMAGE_REQUEST1 = 3;
    private final int PLACE_PICKER_REQUEST = 4;
    private static final int CAMERA_REQUEST = 5;

    String mCurrentPhotoPath = "";

    private GoogleApiClient mGoogleApiClient;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        // locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        submit = (TextView) findViewById(R.id.sub);

        cd = new ConnectionDetector(this);

        su = (TextView) findViewById(R.id.sub);

        start = (TextView) findViewById(R.id.start);
        start1 = (TextView) findViewById(R.id.start1);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        upload = (TextView) findViewById(R.id.upload);

        map = (TextView) findViewById(R.id.map);

        su = (TextView) findViewById(R.id.su);

        diph = (LinearLayout) findViewById(R.id.diph);

        diph1 = (LinearLayout) findViewById(R.id.diph1);

        state =  (TextView) findViewById(R.id.state);

        district =  (TextView) findViewById(R.id.district);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);



        area = (EditText) findViewById(R.id.area);


        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cd.isConnectingToInternet()){


                    dialog2 = new Dialog(Home2.this);
                    dialog2.setContentView(R.layout.dialog);
                    dialog2.setCancelable(true);
                    dialog2.show();


                    Button ok = (Button) dialog2.findViewById(R.id.ok);

                    ok.setVisibility(View.GONE);

                    final RecyclerView grid = (RecyclerView) dialog2.findViewById(R.id.grid);
                    final GridLayoutManager manager = new GridLayoutManager(context, 1);

                    bar.setVisibility(View.VISIBLE);
                    Log.d("hfghfd", "response");

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://nationproducts.in")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Allapi cr = retrofit.create(Allapi.class);

                    Call<StateBean> call = cr.s();

                    call.enqueue(new Callback<StateBean>() {
                        @Override
                        public void onResponse(Call<StateBean> call, Response<StateBean> response) {


                            statelist.clear();


                            StateAdapter adapter = new StateAdapter(Home2.this , response.body().getStateList());
                            grid.setAdapter(adapter);
                            grid.setLayoutManager(manager);



                            bar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<StateBean> call, Throwable t) {


                            Log.d("sdfhsg", t.toString());

                            bar.setVisibility(View.GONE);
                        }
                    });




                }

                else {


                    Toast.makeText(Home2.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }



            }
        });


        bar = (ProgressBar) findViewById(R.id.progress);

        yes = (TextView) findViewById(R.id.yes);

        yes1 = (TextView) findViewById(R.id.yes1);

        yes2 = (TextView) findViewById(R.id.yes2);
        yes3 = (TextView) findViewById(R.id.yes3);

        no = (TextView) findViewById(R.id.no);

        no1 = (TextView) findViewById(R.id.no1);

        no2 = (TextView) findViewById(R.id.no2);
        no3 = (TextView) findViewById(R.id.no3);

        image = (ImageView) findViewById(R.id.cammra);
        profile = (CircleImageView) findViewById(R.id.image);



        sample = (Spinner) findViewById(R.id.sample);

        logout = (TextView)findViewById(R.id.logout);

        linear = (LinearLayout) findViewById(R.id.linear);

        linear1 = (LinearLayout) findViewById(R.id.linear1);

        name = (TextView) findViewById(R.id.name);

        // linear2 = (LinearLayout) findViewById(R.id.linear2);


        app b = (app)getApplicationContext();

        name.setText(b.userName);

        final ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(b.image , profile);

        statelist = new ArrayList<>();



        citylist = new ArrayList<>();


        list = new ArrayList<>();
        Bean b1 = new Bean();
        b1.setTitle("Vegetable oil");
        list.add(b1);


        Bean b2 = new Bean();
        b2.setTitle("Fat");
        list.add(b2);


        Bean b3 = new Bean();
        b3.setTitle("Detergents/caustic soda");
        list.add(b3);


        Bean b4 = new Bean();
        b4.setTitle("Hydrogen Peroxide");
        list.add(b4);


        Bean b5 = new Bean();
        b5.setTitle("Sugar");
        list.add(b5);


        Bean b6 = new Bean();
        b6.setTitle("Glucose");
        list.add(b6);


        Bean b7 = new Bean();
        b7.setTitle("Urea");
        list.add(b7);


        Bean b8 = new Bean();
        b8.setTitle("Starch");
        list.add(b8);


        Bean b9 = new Bean();
        b9.setTitle("Maltodextrin");
        list.add(b9);


        Bean b10 = new Bean();
        b10.setTitle("Boric Acid");
        list.add(b10);


        Bean b11 = new Bean();
        b11.setTitle("Ammonium Sulphate");
        list.add(b11);


        Bean b12 = new Bean();
        b12.setTitle("Nitrates");
        list.add(b12);


        Bean b13 = new Bean();
        b13.setTitle("Celluclose");
        list.add(b13);


        Bean b14 = new Bean();
        b14.setTitle("Neutralizer");
        list.add(b14);


        Bean b15 = new Bean();
        b15.setTitle("Pesticides");
        list.add(b15);


        Bean b16 = new Bean();
        b16.setTitle("Antibiotics");
        list.add(b16);


        Bean b17 = new Bean();
        b17.setTitle("Aflatoxin M1");
        list.add(b17);



        list1 = new ArrayList<>();
        list1.add("Cow Milk");
        list1.add("Buffalo Milk");
        list1.add("Goat Milk");
        list1.add("Camel Milk");
        list1.add("Mixed Milk");


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(Home2.this, android.R.layout.simple_spinner_item, list1);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sample.setAdapter(dataAdapter1);



        sample.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                samp = list1.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yes.setBackgroundResource(R.drawable.green);
                no.setBackground(null);
                yes.setTextColor(Color.WHITE);
                no.setTextColor(Color.BLACK);
                linear.setVisibility(View.VISIBLE);


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                no.setBackgroundResource(R.drawable.no);
                yes.setBackground(null);
                no.setTextColor(Color.WHITE);
                yes.setTextColor(Color.BLACK);
                linear.setVisibility(View.GONE);


            }
        });


        yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yes1.setBackgroundResource(R.drawable.green);
                no1.setBackground(null);
                yes1.setTextColor(Color.WHITE);
                no1.setTextColor(Color.BLACK);
                linear1.setVisibility(View.VISIBLE);

            }
        });

        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                no1.setBackgroundResource(R.drawable.no);
                yes1.setBackground(null);
                no1.setTextColor(Color.WHITE);
                yes1.setTextColor(Color.BLACK);
                linear1.setVisibility(View.GONE);

            }
        });

        yes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yes2.setBackgroundResource(R.drawable.green);
                no2.setBackground(null);
                yes2.setTextColor(Color.WHITE);
                no2.setTextColor(Color.BLACK);
                //linear2.setVisibility(View.VISIBLE);

            }
        });

        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                no2.setBackgroundResource(R.drawable.no);
                yes2.setBackground(null);
                no2.setTextColor(Color.WHITE);
                yes2.setTextColor(Color.BLACK);
                //linear2.setVisibility(View.GONE);

            }
        });


        yes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yes3.setBackgroundResource(R.drawable.green);
                no3.setBackground(null);
                yes3.setTextColor(Color.WHITE);
                no3.setTextColor(Color.BLACK);
                //linear2.setVisibility(View.VISIBLE);

            }
        });

        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                no3.setBackgroundResource(R.drawable.no);
                yes3.setBackground(null);
                no3.setTextColor(Color.WHITE);
                yes3.setTextColor(Color.BLACK);
                //linear2.setVisibility(View.GONE);

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit.remove("email");
                edit.remove("pass");
                edit.apply();

                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });



        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(Home2.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });



        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cd.isConnectingToInternet()){



                    dialog3 = new Dialog(Home2.this);
                    dialog3.setContentView(R.layout.dialog);
                    dialog3.setCancelable(true);
                    dialog3.show();


                    Button ok = (Button) dialog3.findViewById(R.id.ok);

                    ok.setVisibility(View.GONE);

                    final RecyclerView grid = (RecyclerView) dialog3.findViewById(R.id.grid);
                    final GridLayoutManager manager = new GridLayoutManager(context, 1);




                    bar.setVisibility(View.VISIBLE);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://nationproducts.in")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Allapi cr = retrofit.create(Allapi.class);

                    Call<CityBean> call = cr.city(stateId);

                    call.enqueue(new Callback<CityBean>() {
                        @Override
                        public void onResponse(Call<CityBean> call, Response<CityBean> response) {

                            DistrictAdapter adapter = new DistrictAdapter(Home2.this , response.body().getCityList());

                            grid.setLayoutManager(manager);
                            grid.setAdapter(adapter);

                            bar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<CityBean> call, Throwable t) {

                            Log.d("fjldfk", t.toString());

                            bar.setVisibility(View.GONE);

                        }
                    });


                }

                else {

                    Toast.makeText(Home2.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }




            }
        });




        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                dialog = new Dialog(Home2.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(true);
                dialog.show();


                Button ok = (Button) dialog.findViewById(R.id.ok);
                RecyclerView grid = (RecyclerView) dialog.findViewById(R.id.grid);
                GridLayoutManager manager = new GridLayoutManager(context, 1);


                final HomeAdapter adapter = new HomeAdapter(Home2.this, list);

                grid.setLayoutManager(manager);
                grid.setAdapter(adapter);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });


            }
        });


        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog1 = new Dialog(Home2.this);
                dialog1.setContentView(R.layout.dialog);
                dialog1.setCancelable(true);
                dialog1.show();


                Button ok = (Button) dialog1.findViewById(R.id.ok);
                RecyclerView grid = (RecyclerView) dialog1.findViewById(R.id.grid);
                GridLayoutManager manager = new GridLayoutManager(context, 1);


                final HomeAdapter1 adapter = new HomeAdapter1(Home2.this, list);

                grid.setLayoutManager(manager);
                grid.setAdapter(adapter);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog1.dismiss();
                    }
                });


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home2.this, Reg.class);
                startActivity(i);

            }
        });




        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (cd.isConnectingToInternet()){


                    String s = state.getText().toString();
                    String d = district.getText().toString();
                    String a = area.getText().toString();



                    if (s.length()>0){


                        if (d.length()>0){


                            if (a.length()>0){


                                if (latitude.length()>0){


                                    if (mCurrentPhotoPath.length()>0)

                                    {


                                        MultipartBody.Part body = null;


                                        File file = new File(mCurrentPhotoPath);


                                        final RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                                        body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

                                        bar.setVisibility(View.VISIBLE);
                                        app b = (app)getApplicationContext();

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl("http://nationproducts.in")
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        Allapi cr = retrofit.create(Allapi.class);

                                    /*Call<BookBean> call = cr.book(b.userId ,state.getText().toString() , district.getText().toString() , latitude , longitude , area.getText().toString() , body , samp);

                                    call.enqueue(new Callback<BookBean>() {
                                        @Override
                                        public void onResponse(Call<BookBean> call, Response<BookBean> response) {

                                            map.setText("Select Location");

                                            Toast.makeText(Home2.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                            Intent i = new Intent(Home2 .this , Submit.class);
                                            startActivity(i);

                                            bar.setVisibility(View.GONE);


                                        }

                                        @Override
                                        public void onFailure(Call<BookBean> call, Throwable t) {


                                            bar.setVisibility(View.GONE);



                                        }
                                    });
*/




                                    }
                                    else {


                                        Toast.makeText(Home2.this, "please Select an image", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else {

                                    Toast.makeText(Home2.this, "please Select a Location", Toast.LENGTH_SHORT).show();
                                }






                            }

                            else {
                                Toast.makeText(Home2.this, "please Select a Sample", Toast.LENGTH_SHORT).show();

                            }


                        }
                        else {

                            Toast.makeText(Home2.this, "please select a district", Toast.LENGTH_SHORT).show();

                        }


                    }
                    else {


                        Toast.makeText(Home2.this, "please select a state", Toast.LENGTH_SHORT).show();
                    }







                }
                else {

                    Toast.makeText(Home2.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }














            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fileName = System.currentTimeMillis()+".jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = null;
            try {
                photo = decodeUri(fileUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //Uri selectedImageUri = data.getData();



            mCurrentPhotoPath = getPath(Home2.this , fileUri);


            image.setImageBitmap(photo);
        } else if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {


            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();

            latitude = String.valueOf(place.getLatLng().latitude);
            longitude = String.valueOf(place.getLatLng().longitude);


            map.setText(name.toString());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException
    {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 72;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;

        while (true)
        {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
            {
                break;
            }
            width_tmp /= 2;

            height_tmp /= 2;

            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();

        o2.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                .openInputStream(selectedImage), null, o2);

        return bitmap;
    }

    private static String getPath(final Context context, final Uri uri) {
        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            map.setText(String.valueOf(mLastLocation.getLatitude()) +  "  "+String.valueOf(mLastLocation.getLongitude()));

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }






    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        Context context;

        List<Bean> list = new ArrayList<>();

        public HomeAdapter(Context context , List<Bean> list){


            this.context = context;

            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View v = LayoutInflater.from(context).inflate(R.layout.grid_list_model , parent , false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {


            final Bean item = list.get(position);

            holder.textView.setText(item.getTitle());



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView tv = new TextView(context);

                    tv.setPadding(20 , 20 , 20 , 20);

                    tv.setText(item.getTitle());

                    diph.addView(tv);

                    dialog.dismiss();

                }
            });

        }


        public void setgrid(List<Bean>list){

            this.list = list;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView textView;


            public MyViewHolder(View itemView) {
                super(itemView);

                textView = (TextView)itemView.findViewById(R.id.grid);




            }
        }
    }


    public class HomeAdapter1 extends RecyclerView.Adapter<HomeAdapter1.MyViewHolder> {

        Context context;

        List<Bean> list = new ArrayList<>();

        public HomeAdapter1(Context context , List<Bean> list){


            this.context = context;

            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View v = LayoutInflater.from(context).inflate(R.layout.grid_list_model , parent , false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {


            final Bean item = list.get(position);

            holder.textView.setText(item.getTitle());



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView tv = new TextView(context);

                    tv.setPadding(20 , 20 , 20 , 20);

                    tv.setText(item.getTitle());

                    diph1.addView(tv);

                    dialog1.dismiss();

                }
            });

        }


        public void setgrid(List<Bean>list){

            this.list = list;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView textView;


            public MyViewHolder(View itemView) {
                super(itemView);

                textView = (TextView)itemView.findViewById(R.id.grid);




            }
        }
    }


    public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {

        Context context;

        List<StateList> list = new ArrayList<>();

        public StateAdapter(Context context , List<StateList> list){


            this.context = context;

            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View v = LayoutInflater.from(context).inflate(R.layout.grid_list_model , parent , false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {


            final StateList item = list.get(position);

            holder.textView.setText(item.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    state.setText(item.getName());
                    stateId = item.getId();
                    dialog2.dismiss();

                }
            });

        }


        public void setgrid(List<StateList>list){

            this.list = list;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView textView;


            public MyViewHolder(View itemView) {
                super(itemView);

                textView = (TextView)itemView.findViewById(R.id.grid);




            }
        }
    }

    public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.MyViewHolder> {

        Context context;

        List<CityList> list = new ArrayList<>();

        public DistrictAdapter(Context context , List<CityList> list){


            this.context = context;

            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View v = LayoutInflater.from(context).inflate(R.layout.grid_list_model , parent , false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {


            final CityList item = list.get(position);

            holder.textView.setText(item.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    district.setText(item.getName());
                    districtId = item.getId();
                    dialog3.dismiss();

                }
            });

        }


        public void setgrid(List<CityList> list){

            this.list = list;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView textView;


            public MyViewHolder(View itemView) {
                super(itemView);

                textView = (TextView)itemView.findViewById(R.id.grid);




            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.booking)
        {

            Intent i = new Intent(Home2.this , MyBookings.class);
            startActivity(i);

        }

        else if (id == R.id.logout)
        {
            edit.remove("email");
            edit.remove("pass");
            edit.apply();

            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }




}