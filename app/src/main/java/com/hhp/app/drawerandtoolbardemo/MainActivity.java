package com.hhp.app.drawerandtoolbardemo;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;

    private List<DrawerItem> mDrawerItemList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }

        prepareDrawerItems();
        setAdapter();


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void prepareDrawerItems() {
        mDrawerItemList = new ArrayList<DrawerItem>();
        mDrawerItemList.add(new DrawerItem(R.mipmap.ic_action_picture, R.string.drawer_title_photos));
        mDrawerItemList.add(new DrawerItem(R.mipmap.ic_action_movie, R.string.drawer_title_videos));
        mDrawerItemList.add(new DrawerItem(R.mipmap.ic_action_music_1, R.string.drawer_title_sounds));

    }

    private void setAdapter() {

        View headerView = prepareHeaderView(R.layout.header_navigation_drawer,
                "http://c0.f23.img.vnecdn.net/2013/03/26/9-292983-1376856976_500x0.jpg",
                "hiendvosf@gmail.com");

        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.navigation_item_row, mDrawerItemList);
        getSupportActionBar().setTitle(mDrawerItemList.get(0).getTitle());
        mDrawerList.addHeaderView(headerView);
        mDrawerList.setAdapter(adapter);
    }

    private View prepareHeaderView(int layoutRes, String url, String email) {
        View headerView = getLayoutInflater().inflate(layoutRes, mDrawerList,
                false);

        TextView tv = (TextView) headerView.findViewById(R.id.email);
        tv.setText(email);

        ImageView imgView = (ImageView) headerView.findViewById(R.id.image);
        ImageLoader loader = ImageLoader.getInstance();
        try {
            loader.displayImage(url, imgView, ROUND_DISPLAY_IMAGE_OPTIONS, null);

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            loader.clearMemoryCache();
        }

        return headerView;
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // minus 1 because we have header that has 0 position
        if (position < 1) { // because we have header, we skip clicking on it
            return;
        }
        String drawerTitle = getString(mDrawerItemList.get(position - 1)
                .getTitle());
        Toast.makeText(this,
                "You selected " + drawerTitle + " at position: " + position,
                Toast.LENGTH_SHORT).show();

        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerItemList.get(position - 1).getTitle());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private static final DisplayImageOptions.Builder DISPLAY_IMAGE_OPTIONS_BUILDER = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .displayer(new FadeInBitmapDisplayer(300, true, false, false))
            .showImageForEmptyUri(R.drawable.default_image)
            .showImageOnLoading(R.drawable.default_image)
            .showImageOnFail(R.drawable.default_image).cacheOnDisk(true)
            .cacheInMemory(true).bitmapConfig(Bitmap.Config.ARGB_8888);

    private static final DisplayImageOptions ROUND_DISPLAY_IMAGE_OPTIONS = DISPLAY_IMAGE_OPTIONS_BUILDER
            .displayer(new RoundedBitmapDisplayer(500)).build();


}
