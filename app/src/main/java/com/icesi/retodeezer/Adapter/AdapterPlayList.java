package com.icesi.retodeezer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icesi.retodeezer.Activity.PlayListActivity;
import com.icesi.retodeezer.Entity.Playlist;
import com.icesi.retodeezer.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;


public class AdapterPlayList extends RecyclerView.Adapter<AdapterPlayList.ViewHolderPlayList> implements Serializable {

    private List<Playlist> playlist;

    private Playlist selectedPlaylist;

    private int index;

    private Context context;

    public AdapterPlayList(List<Playlist> playlist, Context context) {
        this.playlist = playlist;
        this.context = context;
    }


    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderPlayList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_play_list, viewGroup, false);
        ViewHolderPlayList viewHolderPlayList = new ViewHolderPlayList(view);
        return viewHolderPlayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlayList viewHolderPlayList, int i) {
        selectedPlaylist = playlist.get(i);
        Picasso.get().load(selectedPlaylist.getPicture_small()).into((ImageView) viewHolderPlayList.root.findViewById(R.id.img_playlist_image));
        ((TextView) viewHolderPlayList.root.findViewById(R.id.txv_playlist_name)).setText(selectedPlaylist.getTitle());
        ((TextView) viewHolderPlayList.root.findViewById(R.id.txv_playlist_owner)).setText(selectedPlaylist.getUser().getName());
        ((TextView) viewHolderPlayList.root.findViewById(R.id.txv_playlist_numberSongs)).setText(selectedPlaylist.getNb_tracks() + "");
        viewHolderPlayList.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(viewHolderPlayList.root.getContext(), PlayListActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("idPlayList", selectedPlaylist.getId());
                context.startActivity(in);

            }
        });

//        viewHolderPlayList.root.setOnClickListener(v -> {
//            Intent in = new Intent(viewHolderPlayList.root.getContext(), PlayListActivity.class);
//            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            in.putExtra("idPlayList", selectedPlaylist.getId());
//            context.startActivity(in);
//        });

    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    public class ViewHolderPlayList extends RecyclerView.ViewHolder {

        public LinearLayout root;

        public ViewHolderPlayList(LinearLayout v) {
            super(v);
            root = v;
        }
    }
}

