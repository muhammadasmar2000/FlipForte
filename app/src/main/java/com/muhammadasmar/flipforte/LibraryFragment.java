package com.muhammadasmar.flipforte;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class LibraryFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> pdfName, uploaded;
    RequestQueue queue;
    String url, userID;
    private static final String TAG = "LibraryFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        pdfName = new ArrayList<String>();
        uploaded = new ArrayList<String>();
        getPDFList();
        //pdfName = getResources().getStringArray(R.array.pdf_list);
        //composer = getResources().getStringArray(R.array.composer_list);


        MyAdapter myAdapter = new MyAdapter(getActivity(), pdfName, uploaded);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void getPDFList() {
        userID = ((Home)getActivity()).userID;
        Log.d(TAG, "Debug: Google User ID: " + userID);
        url = "https://www.flipforte.net/api/get-files.php?identifier=" + userID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //successful JSON response
                try {
                    JSONArray filesJsonArray = response.getJSONArray("files");
                    for(int i = 0; i < filesJsonArray.length(); i++) {
                        JSONObject file = filesJsonArray.getJSONObject(i);

                        String pdf_name = file.getString("name");
                        String upload_date = file.getString("desc");

                        pdfName.add(pdf_name);
                        uploaded.add(upload_date);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
