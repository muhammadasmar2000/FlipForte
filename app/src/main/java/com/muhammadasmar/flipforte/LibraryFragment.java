package com.muhammadasmar.flipforte;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LibraryFragment extends Fragment {
    RecyclerView recyclerView;
    String pdfName[], composer[];
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        pdfName = getResources().getStringArray(R.array.pdf_list);
        composer = getResources().getStringArray(R.array.composer_list);
        MyAdapter myAdapter = new MyAdapter(getActivity(), pdfName, composer);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
