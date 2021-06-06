package com.muhammadasmar.flipforte;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    private static final String TAG = "Home.java";
    private MaterialButton signOutButton;
    private int startingPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            loadFragment(new SearchFragment(), 0);
        }
        signOutButton = (MaterialButton) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent (Home.this, MainActivity.class));
                finish();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            int newPosition = 0;
            switch(item.getItemId()) {
                case R.id.search:
                    fragment = new SearchFragment();
                    newPosition = 1;
                    break;
                case R.id.record:
                    fragment = new RecordFragment();
                    newPosition = 2;
                    break;
                case R.id.library:
                    fragment = new LibraryFragment();
                    newPosition = 3;
                    break;
                case R.id.edit:
                    fragment = new EditFragment();
                    newPosition = 4;
                    break;
            }
            return loadFragment(fragment, newPosition);
        }
    };

    private Boolean loadFragment(Fragment fragment, int newPosition) {
        if (fragment != null) {
            if(newPosition == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
            if(startingPosition > newPosition) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.from_left, R.anim.to_right)
                        .replace(R.id.fragment_container, fragment).commit();

            }
            if(startingPosition < newPosition) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.from_right, R.anim.to_left)
                        .replace(R.id.fragment_container, fragment).commit();

            }
            startingPosition = newPosition;
            return true;
        }
        return false;
    }
}