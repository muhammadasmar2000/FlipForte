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

import java.io.File;
import java.io.IOException;

public class RecordFragment extends Fragment {
    //declare views and variables
    private static final String TAG = "RecordFragment.java";
    private MaterialButton recordButton, stopButton;
    private MaterialTextView recordingStatus;
    private MediaRecorder mediaRecorder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record, container, false);
        recordButton = (MaterialButton) view.findViewById(R.id.recordButton);
        stopButton = (MaterialButton) view.findViewById(R.id.stopButton);
        recordingStatus = (MaterialTextView) view.findViewById(R.id.recordingStatus);

        mediaRecorder = new MediaRecorder();

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //configure audio recorder
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

                    //create new file for audio
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File file = new File(path, "/record.3gp");
                    mediaRecorder.setOutputFile(file);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.prepare();

                    //start the recording
                    mediaRecorder.start();
                    recordingStatus.setText("Recording has started...");

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
                recordingStatus.setText("Recording has stopped...");
            }
        });

        return view;
    }
}
