package com.example.efren.biodiversidad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter_RecyclerView_Zones extends RecyclerView.Adapter<MyAdapter_RecyclerView_Zones.ViewHolder> {

    private List<Obj_Zones> names;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public MyAdapter_RecyclerView_Zones(Context context, List<Obj_Zones> names, int layout, OnItemClickListener itemClickListener) {
        this.names = names;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(context,names.get(i), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;

        public ViewHolder(View view){
            super(view);
            this.textViewName = (TextView) view.findViewById(R.id.TVZones);
        }

        public void bind(final Context context,final Obj_Zones name, final OnItemClickListener listener){
            this.textViewName.setText(name.getNombre());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(name, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Obj_Zones name, int position);
    }
}
