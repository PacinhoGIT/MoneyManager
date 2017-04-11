package com.example.patryk.moneymanager;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.ProgressDialog.STYLE_SPINNER;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setTitle("Money Manager");
        toolbar.setLogo(R.drawable.ewqrew);
        //toolbar.setNavigationIcon(R.drawable.money1);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        MainFragment mainFragment = new MainFragment();

        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.relativelayout_for_fragment,
                        mainFragment,
                        mainFragment.getTag()
                ).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.nav_first_layout)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Odrzucic zmiany ?");

            builder.setMessage("Na pewno chcesz wyjsc ? Niezapisane zmiany zostana utracone ?")
                    .setCancelable(false)
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                                            MainFragment mainFragment = new MainFragment();
                                            FragmentManager manager = getFragmentManager();
                                            manager.beginTransaction()
                                                    .replace(R.id.relativelayout_for_fragment,
                                                            mainFragment,
                                                            mainFragment.getTag()
                                                    ).commit();

                                                    id=0;



                        }
                    })
                    .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

       else if(id==R.id.nav_second_layout || id==R.id.nav_third_layout || id==R.id.nav_four_layout)
        {
                            MainFragment mainFragment = new MainFragment();
                            FragmentManager manager = getFragmentManager();
                            manager.beginTransaction()
                                    .replace(R.id.relativelayout_for_fragment,
                                            mainFragment,
                                            mainFragment.getTag()
                                    ).commit();
            id=0;
        }

       else  if(id==0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Wyscie z programu");
            builder.setMessage("Na pewno chcesz wyjsc ? Niezapisane zmiany zostana utracone ?")
                    .setCancelable(false)
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            id=0;

                            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage(" Do zobaczenia !");
                            progressDialog.show();

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {

                                            finish();
                                            progressDialog.dismiss();

                                        }
                                    },1000);

                        }
                    })
                    .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
         id = item.getItemId();

       FragmentManager fragmentManager = getFragmentManager();


        if (id == R.id.nav_first_layout) {
            FirstFragment cameraFragment = new FirstFragment();

            FragmentManager manager = getFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.relativelayout_for_fragment,
                            cameraFragment,
                            cameraFragment.getTag()
                    ).commit();



        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction()
                   .replace(R.id.relativelayout_for_fragment
                            , new SecondFragment())
               .commit();


        } else if (id == R.id.nav_third_layout) {
            fragmentManager.beginTransaction()
                .replace(R.id.relativelayout_for_fragment
                        , new ThirdFragment())
               .commit();


    }
        else if (id == R.id.nav_four_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.relativelayout_for_fragment
                            , new FourFragment())
                    .commit();


        }
        else if (id == R.id.exit) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Wyscie z programu");

            builder.setMessage("Na pewno chcesz wyjsc ? Niezapisane zmiany zostana utracone ?")
                    .setCancelable(false)
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            id=0;

                            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage(" Do zobaczenia !");
                            progressDialog.show();

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {

                                            finish();
                                            progressDialog.dismiss();

                                        }
                                    },1000);

                        }
                    })
                    .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            AlertDialog alertDialog = builder.create();
            alertDialog.show();

    }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
