package com.muhammadasmar.flipforte;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class RecordFragment extends Fragment {
    //declare views and variables
    private static final String TAG = "RecordFragment.java";
    private MaterialButton recordButton, stopButton;
    private MaterialTextView recordingStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record, container, false);
        recordButton = (MaterialButton) view.findViewById(R.id.recordButton);
        stopButton = (MaterialButton) view.findViewById(R.id.stopButton);
        recordingStatus = (MaterialTextView) view.findViewById(R.id.recordingStatus);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordButton.setEnabled(false);
                stopButton.setEnabled(true);
                ((Home)getActivity()).startRecording();
                recordingStatus.setText("Recording has started...");
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordButton.setEnabled(true);
                stopButton.setEnabled(false);
                ((Home)getActivity()).stopRecording();
                recordingStatus.setText("Recording has stopped...");
            }
        });

        return view;
    }
}
