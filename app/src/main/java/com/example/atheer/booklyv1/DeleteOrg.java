package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteOrg extends AppCompatActivity {

    android.widget.ListView ListView;
    ArrayList<String> list;
    ArrayList<orguser> list1;
    ArrayAdapter<String> adapter;
    orguser org;
    String name1;
    String status;

    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_org);

        setTitle("Delete Organizations");


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);

        loaduserinfo();


        mDrawerLayout = findViewById(R.id.drawerId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(


                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();



                        int id = menuItem.getItemId();

                        if (id == R.id.settingsId) {

                            startActivity(new Intent(DeleteOrg.this,settingsorg.class));
                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(DeleteOrg.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(DeleteOrg.this,mynav.class));

                        } else if (id == R.id.servicesId){

                            startActivity(new Intent(DeleteOrg.this,orgServices.class));

                        } else if (id == R.id.ReservationsId){

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        } else if (id == R.id.ReportsId){

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        }


                        return true;
                    }
                });



    }



    private void loaduserinfo() {

        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();
        ref =  database.getReference().child("client").child(userId);




        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = "";
                int points = 0;


                for(DataSnapshot ds: dataSnapshot.getChildren() ){
                    name = dataSnapshot.child("name").getValue(String.class);

                    if (dataSnapshot.hasChild("totalPoint")) {
                        points = dataSnapshot.child("totalPoint").getValue(int.class);
                        navUserponts.setVisibility(View.VISIBLE);
                        navUserponts.setText(points + "");
                    }



                    navUsername.setText(name);



                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        super.onStart();
        if(mAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(this,loginActivity.class));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;





        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
