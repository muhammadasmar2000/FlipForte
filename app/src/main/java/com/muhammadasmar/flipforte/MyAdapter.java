package com.muhammadasmar.flipforte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<String> pdfNames, uploaded;
    Context context;
    public MyAdapter(Context context, ArrayList<String> pdfNames, ArrayList<String> uploaded) {
        this.context = context;
        this.pdfNames = pdfNames;
        this.uploaded = uploaded;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.library_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.title.setText(pdfNames.get(position));
        holder.upload.setText(uploaded.get(position));
    }

    @Override
    public int getItemCount() {
        //number of rows
        return pdfNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, upload;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.pdf_title);
            upload = itemView.findViewById(R.id.pdf_upload);
        }
    }
}
