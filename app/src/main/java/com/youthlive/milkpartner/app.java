package com.youthlive.milkpartner;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by TBX on 11/18/2017.
 */

public class app extends Application {


    String userId = "";
    String userName = "";
    String email = "";
    String image = "";

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

    }
}
