package com.muhammadasmar.flipforte;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;

public class SearchFragment extends Fragment {
    //declare views
    AutoCompleteTextView autoCompleteTextView;
    PDFView pdfView;
    static final String TAG = "SearchFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);

        //initialize views
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteText);
        pdfView = (PDFView) view.findViewById(R.id.pdfView);
        pdfView.useBestQuality(true);

        //set up dropdown menu
        String[] menuOptions = {"Mendelsohn's Concerto"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.list_item, menuOptions);
        //autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        //on text view text change
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //leave empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //leave empty
                //change pdf when autocomplete text view text is changed
                String pdf = autoCompleteTextView.getText().toString() + ".pdf";
                Log.d(TAG, "PDF Name: " + pdf);
                pdfView.fromAsset(pdf)
                        .enableSwipe(true) // allows to block changing pages using swipe
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        // allows to draw something on the current page, usually visible in the middle of the screen
                        //.onDraw(onDrawListener)
                        // allows to draw something on all pages, separately for every page. Called only for visible pages
                        //.onDrawAll(onDrawListener)
                        //.onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
                        //.onPageChange(onPageChangeListener)
                        //.onPageScroll(onPageScrollListener)
                        //.onError(onErrorListener)
                        //.onPageError(onPageErrorListener)
                        //.onRender(onRenderListener) // called after document is rendered for the first time
                        // called on single tap, return true if handled, false to toggle scroll handle visibility
                        //.onTap(onTapListener)
                        .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                        .password(null)
                        .scrollHandle(null)
                        .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                        // spacing between pages in dp. To define spacing color, set view background
                        .spacing(0)
                        .invalidPageColor(Color.WHITE) // color of page that is invalid and cannot be loaded
                        .load();

            }

            @Override
            public void afterTextChanged(Editable s) {
                //leave empty
            }
        });
        return view;
    }
}