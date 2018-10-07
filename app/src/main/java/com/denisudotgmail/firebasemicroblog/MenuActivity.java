package com.denisudotgmail.firebasemicroblog;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new PostListFragment());
        ft.commit();
        setContentView(R.layout.activity_menu);

        //firebase aunt
        mAuth = FirebaseAuth.getInstance();

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



        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment = null;
                        int title = 0;
                        switch (menuItem.getItemId()) {
                            case R.id.home_button:
                                fragment = new PostListFragment();
                                currentPosition = 0;
                                title = R.string.post_list;
                                break;
                            case R.id.add_button:
                                fragment = new NewPostFragment();
                                currentPosition = 1;
                                title = R.string.new_post;
                                break;
                            case R.id.profile_button:
                                fragment = new ProfileFragment();
                                currentPosition = 2;
                                title = R.string.profile;
                                break;
                            case R.id.logout_button:
                                mAuth.signOut();
                                Intent intent = new Intent(MenuActivity.this, AuthorizationActivity.class);
                                startActivity(intent);
                            break;
                            default:
                                fragment = new PostListFragment();
                                currentPosition = 0;
                                title = R.string.post_list;
                            break;
                        }
                        if(fragment != null) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment);
                            ft.addToBackStack(null);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.commit();
                            getSupportActionBar().setTitle(title);
                        }
                        mDrawerLayout.closeDrawer(navigationView);
                        menuItem.setChecked(true);

                        // close drawer when item is tapped


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }

//    private void selectItem(int position){
//        currentPosition = position;
//        Fragment fragment = null;
//        switch (position) {
//            case 1:
//                fragment = new ProfileFragment();
//                break;
//            case 2:
//                fragment = new NewPostFragment();
//                break;
//            default:
//                fragment = new PostListFragment();
//                break;
//        }
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.content_frame, fragment);
//        ft.addToBackStack(null);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.commit();
//        mDrawerLayout.closeDrawer(navigationView);
//    }

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
//        outState.putInt("position", currentPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return false;
    }
}
