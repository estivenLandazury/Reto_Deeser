package com.icesi.retodeezer.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icesi.retodeezer.Adapter.AdapterTrack;
import com.icesi.retodeezer.Entity.Playlist;
import com.icesi.retodeezer.Entity.Track;
import com.icesi.retodeezer.R;
import com.icesi.retodeezer.ServiceRest.ServicesManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayListActivity extends AppCompatActivity {


    private ImageView imageViewPlayList;
    private TextView txv_descripcion, txv_song_fans, txv_title;
    private AdapterTrack adapterTrack;
    private List<Track>tracks;
    private RecyclerView recyclerView;
    private Playlist playListSelect;
    private String jsonTrack;
    private String jsonPlayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        imageViewPlayList=findViewById(R.id.img_imagePlaylist);
        txv_title= findViewById(R.id.title);
        txv_descripcion=findViewById(R.id.txv_descripcion);
        txv_song_fans=findViewById(R.id.txv_song_fans);
        tracks=new ArrayList<>();
        adapterTrack=new AdapterTrack(tracks, getApplicationContext());
        recyclerView= findViewById(R.id.rcv_song);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterTrack);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));


        obtenrInfo();

    }


    public void obtenrInfo(){

        String idPlayList= getIntent().getStringExtra("idPlayList");
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ServicesManager.GET_Playlist(idPlayList, response -> runOnUiThread(()->{
                    JSONObject js= null;

                    try {
                        js=new JSONObject(response);

                        JSONObject  track_js= js.getJSONObject("tracks");
                        JSONArray track_list=track_js.getJSONArray("data");

                        jsonTrack=track_list.toString();

                        jsonPlayList=js.toString();

                        playListSelect=new Gson().fromJson(jsonPlayList, new TypeToken<Playlist>(){}.getType());
                        tracks= new Gson().fromJson(jsonTrack,new TypeToken<List<Track>>(){}.getType());

                        adapterTrack.setTracks(tracks);

                        Picasso.get().load(playListSelect.getPicture_big()).into( imageViewPlayList);
                        txv_title.setText(playListSelect.getTitle());
                        String description = playListSelect.getDescription();

                        if(description.equals("")){
                            txv_descripcion.setText("Nothing Description PlayList");
                        }else{
                            txv_descripcion.setText(description);
                        }

                        txv_song_fans.setText("("+playListSelect.getNb_tracks()+")"+ " ("+ playListSelect.getFans()+")");

                    } catch (JSONException e) {
                        Log.e("Error-Landa", e.getMessage());
                    }
                }));

            }
        }).start();
    }





}
