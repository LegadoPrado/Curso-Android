package com.example.efren.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> names;
    private int layout;
    private OnItemClickListener itemClickListener;

    public MyAdapter(List<String> names, int layout, OnItemClickListener itemClickListener) {
        this.names = names;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
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
        viewHolder.bind(names.get(i), itemClickListener);
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

        public void bind(final String name, final OnItemClickListener listener){
            if  (name.equals("Lenin")){
                imageView.setImageResource(R.drawable.ic_launcher_background);
            }else if(name.equals("Jorge")){
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
            }else if(name.equals("Chusrpio(Edwin)")){
                imageView.setImageResource(R.drawable.ic_launcher_background);
            }
            this.textViewName.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(name, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String name, int position);
    }
}
