package com.denisudotgmail.firebasemicroblog;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new PostListFragment());
        ft.commit();
        setContentView(R.layout.activity_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,
                R.string.open_drawer,R.string.close_drawer){
            //run if panel close
            @Override
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            //run if panel open
            @Override
            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fm =getFragmentManager();
                Fragment fragment = fm.findFragmentByTag("visible_fragment");
                if(fragment instanceof PostListFragment){
                    currentPosition = 0;
                }
                if(fragment instanceof NewPostFragment){
                    currentPosition = 1;
                }
                if(fragment instanceof ProfileFragment){
                    currentPosition = 2;
                }
                setActionBarTitle(currentPosition);
            }
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment fragment = null;
                        switch (menuItem.getItemId()) {
                            case R.id.home_button:
                                fragment = new PostListFragment();
                                break;
                            case R.id.add_button:
                                fragment = new NewPostFragment();
                                break;
                            case R.id.profile_button:
                                fragment = new ProfileFragment();
                                break;
                            case R.id.logout_button:
                                Intent intent = new Intent(MenuActivity.this, AuthorizationActivity.class);
                                startActivity(intent);
                            break;
                            default:
                                fragment = new PostListFragment();
                            break;
                        }
                        if(fragment != null) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment, "visible_fragment");
                            ft.addToBackStack(null);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.commit();
                        }
                        mDrawerLayout.closeDrawer(navigationView);
                        menuItem.setChecked(true);
                        return true;
                    }
                });
    }

    private void setActionBarTitle(int position){
        String title;
        switch (position) {
            case 0:
                title = getResources().getString(R.string.post_list);
                break;
            case 1:
                title = getResources().getString(R.string.new_post);
                break;
            case 2:
                title = getResources().getString(R.string.profile);
                break;
            default:
                title = getResources().getString(R.string.app_name);
            break;
        }
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration){
        super.onConfigurationChanged(configuration);
        drawerToggle.onConfigurationChanged(configuration);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return drawerToggle.onOptionsItemSelected(item);
    }
}
