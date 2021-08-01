package com.muhammadasmar.flipforte;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UploadFragment extends Fragment {
    private String url, user_info;
    //private WebView webview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload, container, false);
        user_info = ((Home)getActivity()).userID;
        //Toast.makeText(getContext(), "Debug: UserID: " + user_info, Toast.LENGTH_LONG).show();
        url = "https://www.flipforte.net/api/upload-file.php?identifier=" + user_info;
//        webview = (WebView) view.findViewById(R.id.upload_webview);
//        webview.setWebViewClient(new WebViewClient());
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.loadUrl(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
        return view;
    }
}
