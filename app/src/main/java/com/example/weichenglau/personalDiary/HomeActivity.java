package com.example.weichenglau.personalDiary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.weichenglau.personalDiary.fragment.CalendarFragment;
import com.example.weichenglau.personalDiary.fragment.StoryFragment;
import com.example.weichenglau.personalDiary.fragment.LoginFragment;
import com.example.weichenglau.personalDiary.fragment.LogoutFragment;
import com.example.weichenglau.personalDiary.fragment.SignupFragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mPager;
    private TabLayout mTabs;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();

        //hide the logout button at first
        //show only after the user login
        hideItem(R.id.nav_logout);
        hideItem(R.id.nav_home);
        hideItem(R.id.nav_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //float button listener
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPostActivity.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //check for user login
        if(firebaseAuth.getCurrentUser() != null){
            hideItem(R.id.nav_login);
            hideItem(R.id.nav_signup);
            showItem(R.id.nav_logout);
            showItem(R.id.nav_home);
            showItem(R.id.nav_calendar);

            //intent to story fragment
            StoryFragment story = new StoryFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, story, "story");
            transaction.addToBackStack("StoryFragment1");
            transaction.commit();

        }else{
            LoginFragment login = new LoginFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, login, "login");
            transaction.addToBackStack("loginFragment");

            transaction.commit();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if(manager.getBackStackEntryCount() == 0){
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            StoryFragment story = new StoryFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, story, "story");
            transaction.addToBackStack("StoryFragment1");
            transaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_login) {
            LoginFragment login = new LoginFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, login, "login");
            transaction.addToBackStack("loginFragment");
            transaction.commit();
        } else if (id == R.id.nav_signup) {
            SignupFragment signup = new SignupFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, signup, "signup");
            transaction.addToBackStack("SignupFragment");
            transaction.commit();

        } else if (id == R.id.nav_logout) {
            LogoutFragment logout = new LogoutFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, logout, "logout");
            transaction.addToBackStack("LogoutFragment");
            transaction.commit();
        }else if (id == R.id.nav_calendar) {
            CalendarFragment calendar = new CalendarFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, calendar, "calendar");
            transaction.addToBackStack("CalendarFragment");
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hideItem(int item1)
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(item1).setVisible(false);
    }

    public void showItem(int item1)
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(item1).setVisible(true);
    }
}
