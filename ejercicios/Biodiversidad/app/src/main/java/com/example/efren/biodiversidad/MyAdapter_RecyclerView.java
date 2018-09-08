package com.example.efren.biodiversidad;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter_RecyclerView extends RecyclerView.Adapter<MyAdapter_RecyclerView.ViewHolder> {

    private List<Obj_Species_for_user> names;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public MyAdapter_RecyclerView(Context context,List<Obj_Species_for_user> names, int layout, OnItemClickListener itemClickListener) {
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
        public ImageView imageView;

        public ViewHolder(View view){
            super(view);
            this.textViewName = (TextView) view.findViewById(R.id.textViewName);
            this.imageView = (ImageView) view.findViewById(R.id.imagen);
        }

        public void bind(final Context context,final Obj_Species_for_user name, final OnItemClickListener listener){
            this.textViewName.setText(name.getNombre());

            Picasso.with(context)
                    .load("http://biodiversidad.alcohomeapp.com.mx/img/" + name.getId_especie() + ".png")
                    .error(R.drawable.ic_launcher_background)
                    .fit()
                    .centerInside()
                    .into(this.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(name, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Obj_Species_for_user name, int position);
    }
}
