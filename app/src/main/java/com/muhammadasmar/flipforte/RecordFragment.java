package com.muhammadasmar.flipforte;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;

public class RecordFragment extends Fragment {
    //declare views and variables
    private static final String TAG = "RecordFragment.java";
    private MaterialButton recordButton, stopButton;
    private MaterialTextView recordingStatus;
    private MediaRecorder mediaRecorder;
    private String filename = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        recordButton = (MaterialButton) view.findViewById(R.id.recordButton);
        stopButton = (MaterialButton) view.findViewById(R.id.stopButton);
        recordingStatus = (MaterialTextView) view.findViewById(R.id.recording_status);

        filename = Environment.getExternalStorageDirectory().getAbsolutePath();
        filename += "/recorded_audio.mp3";

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
                recordingStatus.setText("Now Recording...");
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
                recordingStatus.setText("Recording has stopped...");
            }
        });
    }

    private void startRecording(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setOutputFile(filename);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        }
        catch (IOException e) {
            Log.d(TAG, "prepare() failed");
        }
        mediaRecorder.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }
}
