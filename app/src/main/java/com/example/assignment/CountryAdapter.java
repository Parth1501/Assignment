package com.example.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Country;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryListHolder> {

    private ArrayList<Country> list;
    private OnItemClickListener listener;

    public interface  OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.listener=listener;
    }

    public CountryAdapter(ArrayList<Country> list) {
        this.list=list;
    }

    public static class CountryListHolder extends RecyclerView.ViewHolder {
        public TextView country;

        public CountryListHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            country=itemView.findViewById(R.id.countryname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null) {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CountryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list,parent,false);
        CountryListHolder clh=new CountryListHolder(v,listener);
        return clh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryListHolder holder, int position) {
        Country current=list.get(position);
        holder.country.setText(current.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
