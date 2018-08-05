package com.mtw.diego.jokesonthego.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mtw.diego.jokesonthego.R;
import com.mtw.diego.jokesonthego.entity.Joke;
import com.mtw.diego.jokesonthego.helper.database.AppDatabase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private List<Joke> jokes;
    private Context ctx;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView host;
        TextView joke;

        public ViewHolder(View v) {
            super(v);
            joke = v.findViewById(R.id.fragment_joke);
            host = v.findViewById(R.id.fragment_host_joke);
        }
    }

    public FavoritesAdapter(List<Joke> l, Context ctx) {
        this.jokes = l;
        this.ctx = ctx;
    }

    public void remove(int index) {
        Disposable d = Observable.fromCallable(() -> {
            AppDatabase.getDatabase(ctx).jokeDao().delete(jokes.get(index));
            return true;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(e -> {
            jokes.remove(index);
            notifyDataSetChanged();
        });

    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_favorite, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Joke j = jokes.get(position);
        holder.host.setText(j.getHost().getName());
        holder.joke.setText(j.getJoke());
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }
}