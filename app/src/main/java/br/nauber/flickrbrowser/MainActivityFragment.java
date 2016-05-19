package br.nauber.flickrbrowser;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("Fragment", "Running process");

//        GetRawData theRawData=new GetRawData("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1");
     //   GetFlickrJsonData theRawData=new GetFlickrJsonData("android",true);

  //      theRawData.execute();


        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
