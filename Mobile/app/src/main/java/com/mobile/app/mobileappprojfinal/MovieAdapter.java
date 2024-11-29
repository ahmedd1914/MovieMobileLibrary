package com.mobile.app.mobileappprojfinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> movie_id, movie_title, movie_author, movie_description;

    MovieAdapter(Context context,
                 ArrayList<String> movie_id,
                 ArrayList<String> movie_title,
                 ArrayList<String> movie_author,
                 ArrayList<String> movie_description){
        this.context = context;
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.movie_author = movie_author;
        this.movie_description = movie_description;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.movie_id_txt.setText(String.valueOf(movie_id.get(i)));
        myViewHolder.movie_title_txt.setText(String.valueOf(movie_title.get(i)));
        myViewHolder.movie_author_txt.setText(String.valueOf(movie_author.get(i)));
        myViewHolder.movie_description_txt.setText(String.valueOf(movie_description.get(i)));
        myViewHolder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(movie_id.get(i)));
            intent.putExtra("title", String.valueOf(movie_title.get(i)));
            intent.putExtra("author", String.valueOf(movie_author.get(i)));
            intent.putExtra("description", String.valueOf(movie_description.get(i)));
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return movie_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movie_id_txt, movie_title_txt, movie_author_txt, movie_description_txt;

        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_id_txt = itemView.findViewById(R.id.movie_id_txt);
            movie_title_txt = itemView.findViewById(R.id.movie_title_txt);
            movie_author_txt = itemView.findViewById(R.id.movie_author_txt);
            movie_description_txt = itemView.findViewById(R.id.movie_description_txt);

            mainLayout = itemView.findViewById(R.id.HomeLayout);
        }
    }

}
