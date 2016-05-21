package br.nauber.flickrbrowser;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by naubergois on 5/21/16.
 */
public class MyAppClass extends MultiDexApplication {


    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }


    protected void attachBaseContext(Context newBase) {
        MultiDex.install(newBase);

        super.attachBaseContext(newBase);
    }
}