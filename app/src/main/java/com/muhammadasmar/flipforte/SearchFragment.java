package com.muhammadasmar.flipforte;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class SearchFragment extends Fragment {
    //declare views
    private AutoCompleteTextView autoCompleteTextView;
    private WebView webview;
    private RequestQueue queue;
    private ArrayList<String> menuOptions;
    private ArrayLists arrayLists;
    private String url, userID;
    static final String TAG = "SearchFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);

        //initialize views
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteText);
        webview = (WebView) view.findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setJavaScriptEnabled(true);
        //HttpsTrustManager.allowAllSSL();
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        menuOptions = new ArrayList<>();
        arrayLists = new ArrayLists();

        userID = ((Home)getActivity()).userID;
        url = "https://www.flipforte.net/api/get-files.php?identifier=" + userID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);

        for (int i = 0; i < arrayLists.getPdfName().size(); i++) {
            menuOptions.add(arrayLists.pdfName.get(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.list_item, menuOptions);
        autoCompleteTextView.setAdapter(arrayAdapter);

        //on text view text change
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //leave empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //change pdf when autocomplete text view text is changed
                int position = 0;
                for (int i = 0; i < menuOptions.size(); i++) {
                    if(menuOptions.get(i).equals(autoCompleteTextView.getText().toString()) ) {
                        position = i;
                    }
                }
                //https://www.flipforte.net/pdfreader/viewer.html?resource=tLntPfrq1yfb9MvhqNIn-3SQiQRQ59bSQhwKfp0Xj&identifier=testuser
                String pdf_url = arrayLists.resource.get(position);
                webview.loadUrl("https://www.flipforte.net/pdfreader/viewer.html?resource=" + pdf_url + "&identifier=" + userID);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //leave empty
            }
        });
        return view;
    }

    private void parseJson (JSONObject response) {
        try {
            JSONArray filesJsonArray = response.getJSONArray("files");
            for (int i = 0; i < filesJsonArray.length(); i++) {
                JSONObject file = filesJsonArray.getJSONObject(i);
                menuOptions.add(file.getString("name"));
                Log.d(TAG, "parseJson: file name: " + file.getString("name"));

                arrayLists.pdfName.add(file.getString("name"));
                arrayLists.resource.add(file.getString("resource"));
                Log.d(TAG, "parseJson: resource: " + file.getString("resource"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}