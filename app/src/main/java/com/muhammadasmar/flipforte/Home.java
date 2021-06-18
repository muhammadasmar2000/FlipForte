package com.muhammadasmar.flipforte;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;

public class Home extends AppCompatActivity {
    private static final String TAG = "Home.java";
    private MaterialButton signOutButton;
    private int startingPosition;
    MediaRecorder recorder;
    File downloadsPath;
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
                startActivity(new Intent(Home.this, MainActivity.class));
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();

        //configure audio recorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        Log.d(TAG, "Debug: Audio configured");

        //create new file for audio
        downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloadsPath, "/record.3gp");
        if (file.delete()) {
            Log.d(TAG, "Debug: File: " + file.getAbsolutePath() + " has been deleted");
        }
        recorder.setOutputFile(file);
        Log.d(TAG, "Debug: Output File path: " + file);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        Log.d(TAG, "Debug: Output file created");
    }

    public void startRecording() {
        try {
            //prepare the recording
            recorder.prepare();
            Log.d(TAG, "Debug: Media recorder prepared");

            //start the recording
            recorder.start();
            Log.d(TAG, "Debug: Recording started");
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            Log.d(TAG, "Debug: Media recorder prepare() failed by IOException");
            System.out.println("" + ioe);
        }
        catch (IllegalStateException ise) {
            ise.printStackTrace();
            Log.d(TAG, "Debug: Media recorder prepare() failed by IllegalStateException");
            System.out.println("" + ise);
        }
    }

    public void stopRecording() {
        if (recorder != null) {
            Log.d(TAG, "Debug: About to stop recording");
            recorder.stop();
            Log.d(TAG, "Debug: Recording stopped");
            //recorder.reset();
            recorder.release();
            recorder = null;
        }
        Log.d(TAG, "Debug: Recorder has been set to null");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            int newPosition = 0;
            switch (item.getItemId()) {
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
            if (newPosition == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
            if (startingPosition > newPosition) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.from_left, R.anim.to_right)
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
            if (startingPosition < newPosition) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.from_right, R.anim.to_left)
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
            startingPosition = newPosition;
            return true;
        }
        return false;
    }
}