package com.azizulhakim.todotogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.azizulhakim.todotogether.fragment.GroupMyFragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends InterfaceActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "HomePage";
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    //navigationbar: name, email
    TextView mynameview;
    TextView myemailview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new GroupMyFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    "My Groups"
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);



        // set my name and email
        View headerView = navigationView.getHeaderView(0);
        String myname = FirebaseUtil.getMyName();
        String myemail = FirebaseUtil.getMyEmail();

        mynameview = (TextView)headerView.findViewById(R.id.my_name_display);
        myemailview = (TextView)headerView.findViewById(R.id.my_email_display);
        mynameview.setText( myname );
        myemailview.setText( myemail );
        // DONE.
    }

    @Override
    public void onBackPressed() {
        //   startActivity(new Intent(this, TaskListPage.class));
        // startActivity(new Intent(this, GroupChoosePage.class));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            this.moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInPage.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_project) {
            ///create new group
            startActivity(new Intent(this, CreateNewPostPage.class));

        } else if (id == R.id.nav_projects) {
            Toaster("Projects");
        }
        else if (id == R.id.nav_calendar) {
              startActivity(new Intent(this, CalendarPage.class));
            }
        else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, AboutPage.class));
        }
        else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            //String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        }
        else if (id == R.id.nav_send) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            //String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"");
            startActivity(Intent.createChooser(sharingIntent, "Send Via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
