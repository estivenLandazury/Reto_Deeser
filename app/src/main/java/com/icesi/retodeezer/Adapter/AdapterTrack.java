package com.icesi.retodeezer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icesi.retodeezer.Activity.SongActivity;
import com.icesi.retodeezer.Entity.Track;
import com.icesi.retodeezer.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class AdapterTrack extends RecyclerView.Adapter<AdapterTrack.ViewHolderTracks> implements Serializable {

    private List<Track> song_tracks;

    private Track trackSelected;

    private Context context;

    public List<Track> getTracks() {
        return song_tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.song_tracks = tracks;
        notifyDataSetChanged();
    }

    public AdapterTrack(List<Track> tracks, Context context) {
        this.song_tracks = tracks;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolderTracks onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tracks,viewGroup,false);
        ViewHolderTracks viewHolderTracks = new ViewHolderTracks(view);
        return viewHolderTracks;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTracks viewHolderTracks, int i) {
        trackSelected = song_tracks.get(i);
        Picasso.get().load(trackSelected.getAlbum().getCover_medium()).into((ImageView)viewHolderTracks.root.findViewById(R.id.img_song));
        ((TextView)viewHolderTracks.root.findViewById(R.id.txv_songs_name)).setText(trackSelected.getTitle());
        ((TextView)viewHolderTracks.root.findViewById(R.id.txv_songs_artist)).setText(trackSelected.getArtist().getName());
        ((TextView)viewHolderTracks.root.findViewById(R.id.txv_songs_releaseYear)).setText(trackSelected.getRank() + " \uD83C\uDF1F");


        viewHolderTracks.root.setOnClickListener(v -> {
            Intent in = new Intent(viewHolderTracks.root.getContext(), SongActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            in.putExtra("idCancion", trackSelected.getId());
            context.startActivity(in);
        });


    }

    @Override
    public int getItemCount() {
        return song_tracks.size();
    }


    public static class ViewHolderTracks extends RecyclerView.ViewHolder {

        public LinearLayout root;

        public ViewHolderTracks(LinearLayout v) {
            super(v);
            root=v;
        }
    }


}
