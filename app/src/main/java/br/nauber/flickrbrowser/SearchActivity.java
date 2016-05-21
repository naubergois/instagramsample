package br.nauber.flickrbrowser;


import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;


public class SearchActivity extends BaseListVideoActivity{

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        setUp();

        String key= getSavedPreferenceData(YOUTUBE_QUERY);;

        if(key.length()==0){
            key="marvel";
        }



        ProcessProcessVideos processProcessPhotos = new ProcessProcessVideos(key, true);
        processProcessPhotos.execute();

        Toolbar toolbar = activateToolBar();

        mSearchView=(SearchView) findViewById(R.id.searchautocomplete);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconified(false);
        mSearchView.setQueryHint("Enter the photo tag");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putString(YOUTUBE_QUERY, query).commit();
                mSearchView.clearFocus();
                //goToMain();
                finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                ProcessProcessVideos processProcessPhotos = new ProcessProcessVideos(newText, true);
                processProcessPhotos.execute();
                return true;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        return true;
    }

}
