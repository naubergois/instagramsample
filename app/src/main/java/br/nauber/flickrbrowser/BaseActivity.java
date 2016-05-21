package br.nauber.flickrbrowser;

import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by naubergois on 5/16/16.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(Gravity.LEFT);
//        menu.findItem(R.id.leftBarDetail).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);

    }

    private Toolbar mToolbar;
    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";
    private String[] mPlanetTitles;
    private DrawerLayoutHorizontalSupport mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;

    private CharSequence mTitle;


    public DrawerLayoutHorizontalSupport getmDrawerLayout() {
        return mDrawerLayout;
    }

    public void setmDrawerLayout(DrawerLayoutHorizontalSupport mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {

            switch (position) {
                case 0:
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    startActivity(intent);
                    break;
            }

        }

    }

    /**
     * Swaps fragments in the main content view
     */


    public void setUpDrawer() {

        mPlanetTitles = new String[2];
        mPlanetTitles[0] = "Site do Omelete";
        mPlanetTitles[1] = "Omeleteve";

        mDrawerLayout = (DrawerLayoutHorizontalSupport) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));


        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        //mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mTitle = mDrawerTitle = getTitle();


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.action_search, R.string.action_search) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goToMain() {
        Intent openMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(openMainActivity);
    }

    protected Toolbar activateToolBar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);


            ImageView imageLogo = (ImageView) findViewById(R.id.ivLogo);
            imageLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMain();
                }
            });


            ImageView image = (ImageView) findViewById(R.id.home);
            assert image != null;

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDrawerLayout = (DrawerLayoutHorizontalSupport) findViewById(R.id.drawer_layout);
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    } else {

                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.leftBarDetail);

                        mDrawerLayout.openDrawer(layout);
                    }


                }
            });
            if (mToolbar != null) {

                mToolbar.setTitle("");

                mToolbar.inflateMenu(R.menu.menu_main);


                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {

            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }


        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


}
