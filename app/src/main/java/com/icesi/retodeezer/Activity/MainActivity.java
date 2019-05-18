package com.icesi.retodeezer.Activity;

import
        android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icesi.retodeezer.Adapter.AdapterPlayList;
import com.icesi.retodeezer.Entity.Playlist;
import com.icesi.retodeezer.R;
import com.icesi.retodeezer.ServiceRest.ServicesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private RecyclerView rcvView;
    private EditText edt_search;
    private Button btn_search;
    private List<Playlist> playlists;
    private AdapterPlayList adapterPlayList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_search=findViewById(R.id.edt_search);
        btn_search=findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        rcvView = findViewById(R.id.rcv_items);
        rcvView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcvView.setHasFixedSize(true);
        playlists = new ArrayList<>();
        adapterPlayList= new AdapterPlayList(playlists, getApplicationContext());
        rcvView.setAdapter(adapterPlayList);
        rcvView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));



//        adapterPlayList= new AdapterPlayList(playlists, getApplicationContext());
    }


    @Override
    public void onClick(View v) {

        if(v.equals(btn_search)){
            String parametroBusqueda= edt_search.getText().toString();
            if(!parametroBusqueda.equals("")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new ServicesManager.GET_Search_For_Playlist(parametroBusqueda,response -> runOnUiThread(()->{
                            JSONObject js;

                            try {
                                js=new JSONObject(response);
                                JSONArray datos= js.getJSONArray("data");
                                String playListd= datos.toString();
                                playlists= new Gson().fromJson(playListd, new TypeToken<List<Playlist>>(){

                                }.getType());
                                adapterPlayList.setPlaylist(playlists);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }));

                    }
                }).start();

            }else{
//                mensaje("Ingrese texto en el campo de busqueda");
                Toast.makeText(MainActivity.this,"Ingrese texto en el campo de busqueda",Toast.LENGTH_LONG).show();

            }


        }

    }


    public void mensaje(String mensaje){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,mensaje,Toast.LENGTH_LONG).show();
            }
        });
    }
}
