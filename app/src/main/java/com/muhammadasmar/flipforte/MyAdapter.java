package com.muhammadasmar.flipforte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    String pdfNames[], composers[];
    Context context;
    public MyAdapter(Context context, String pdfNames[], String composers[]) {
        this.context = context;
        this.pdfNames = pdfNames;
        this.composers = composers;
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
        holder.title.setText(pdfNames[position]);
        holder.composer.setText(composers[position]);
    }

    @Override
    public int getItemCount() {
        return pdfNames.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, composer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.pdf_title);
            composer = itemView.findViewById(R.id.pdf_composer);
        }
    }
}
