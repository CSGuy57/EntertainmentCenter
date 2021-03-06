package org.pltw.examples.entertainmentcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private FloatingActionButton fab;
    public static final String CREATE_TYPE = "org.pltw.examples.CREATETYPE";
    public static final String ET_ITEM = "org.pltw.examples.ET_ITEM";
    public static final String DISPLAY_FRAGMENT = "org.pltw.examples.DISPLAY_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EntertainmentActivity.class);
                Fragment f =
                        getSupportFragmentManager().findFragmentById(R.id.content_frame);
                i.putExtra(CREATE_TYPE, f.getClass().getSimpleName());
                startActivity(i);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(findViewById(R.id.content_frame) != null){
            Fragment fragment = null;
            Intent intent = getIntent();

            if(intent != null) {
                String fragmentToShow = intent.getStringExtra(DISPLAY_FRAGMENT);
                if(fragmentToShow != null) {
                    if (fragmentToShow.equals(Movie.class.getSimpleName())) {
                        fragment = new MovieFragment();
                    } else if (fragmentToShow.equals(VideoGame.class.getSimpleName())) {
                        fragment = new VideoGameFragment();
                    } else if (fragmentToShow.equals(BoardGame.class.getSimpleName())) {
                        fragment = new BoardGameFragment();
                    }
                } else {
                    fragment = new WelcomeFragment();
                }
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment contentFragment = null;

        if(id== R.id.nav_home) {
            contentFragment = new WelcomeFragment();
        } else if (id == R.id.nav_movies) {
            contentFragment = new MovieFragment();
        } else if (id == R.id.nav_video_games) {
            contentFragment = new VideoGameFragment();
        } else if (id == R.id.nav_board_games) {
            contentFragment = new BoardGameFragment();
        }

        if(contentFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.content_frame, contentFragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }
}
