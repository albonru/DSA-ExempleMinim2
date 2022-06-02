package com.example.dsa_minim2exemple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dsa_minim2exemple.API.Repos;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Repos> dades;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<Repos> reposList, Context context) {
        this.dades = reposList;
        this.mInflater = LayoutInflater.from((Context) context);
        this.context = (Context) context;
    }

    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
