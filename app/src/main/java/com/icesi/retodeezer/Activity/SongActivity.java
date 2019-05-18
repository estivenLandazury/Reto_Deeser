package com.icesi.retodeezer.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icesi.retodeezer.Entity.Track;
import com.icesi.retodeezer.R;
import com.icesi.retodeezer.ServiceRest.ServicesManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SongActivity extends AppCompatActivity implements View.OnClickListener {


    private Track trackSelect;
    private ImageView img_track;
    public static String REPRODUCTION_URL = "://www.deezer.com/track/";

    private TextView txv_nombreCancion, txv_artista, txv_album, txv_duracion;

    private Button btn_escuchar;

    private String idCancion;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        img_track = findViewById(R.id.imv_track);
        txv_nombreCancion = findViewById(R.id.txv_nombreCancion);
        txv_artista = findViewById(R.id.txv_artista);
        txv_album = findViewById(R.id.txv_album);
        txv_duracion = findViewById(R.id.txv_duracion);


        btn_escuchar = findViewById(R.id.btn_escuchar);
        btn_escuchar.setOnClickListener(this);
        obtnerInfo();
    }


    public void obtnerInfo() {
        idCancion = getIntent().getStringExtra("idCancion");

        new Thread(new Runnable() {
            @Override
            public void run() {
                new ServicesManager.GET_Track(idCancion, new ServicesManager.GET_Track.OnResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        runOnUiThread(() -> {
                            JSONObject js = null;

                            try {
                                js = new JSONObject(response);
                                String js_track = js.toString();

                                trackSelect = new Gson().fromJson(js_track, new TypeToken<Track>() {
                                }.getType());
                                String img = trackSelect.getAlbum().getCover_medium();
                                Picasso.get().load(img).into(img_track);
                                txv_nombreCancion.setText(trackSelect.getTitle());
                                txv_artista.setText(trackSelect.getArtist().getName());
                                txv_album.setText(trackSelect.getAlbum().getTitle());
                                txv_duracion.setText(trackSelect.getDuration() + "Seconds");

                            } catch (JSONException e) {
                                Log.e("Error Track", e.getMessage());
                            }

                        });
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        String rpod = "deezer" + REPRODUCTION_URL + idCancion;

        if (btn_escuchar.equals(v)) {

            Intent intent = getPackageManager().getLaunchIntentForPackage("deezer.android.app");

            if (intent != null) {
                Intent accion = new Intent(Intent.ACTION_VIEW, Uri.parse(rpod));
                startActivity(accion);
            } else {
                Intent optionTwo = new Intent(Intent.ACTION_VIEW, Uri.parse("https" + REPRODUCTION_URL + idCancion));
                startActivity(optionTwo);
//                mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri.parse("https" + REPRODUCTION_URL + idCancion) );
//                mediaPlayer.start();
            }
        }

    }
}
