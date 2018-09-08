package com.example.efren.biodiversidad;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Show_My_Species extends AppCompatActivity {

    private ArrayList<Obj_Species_for_user> Obj_species = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__my__species);

        new Informacion_Especie().execute();
    }

    private class Informacion_Especie extends AsyncTask<String, String, ArrayList<Obj_Species_for_user>> {

        @Override
        protected ArrayList<Obj_Species_for_user> doInBackground(String... strings) {

            try {
                JSONArray jsonArray = new JSONArray(new Read_Page().Read_Page("http://biodiversidad.alcohomeapp.com.mx/webService_especies_por_usuario.php?id_usuario=" + getIntent().getStringExtra("Id")));
                JSONObject jsonObject;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    Obj_Species_for_user Obj_class = new Obj_Species_for_user();
                    Obj_class.setId_especie(jsonObject.getInt("id_especie"));
                    Obj_class.setNombre(jsonObject.getString("nombre"));
                    Obj_class.setLatitud(jsonObject.getDouble("latitud"));
                    Obj_class.setLongitud(jsonObject.getDouble("longitud"));
                    Obj_class.setCantidad(jsonObject.getInt("cantidad"));

                    Obj_species.add(Obj_class);
                }
                return Obj_species;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<Obj_Species_for_user> s) {
            super.onPostExecute(s);
            if (s != null) {
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                mLayoutManager = new LinearLayoutManager(Show_My_Species.this);
                mAdapter = new MyAdapter_RecyclerView(Show_My_Species.this, s, R.layout.recycler_view_item, new MyAdapter_RecyclerView.OnItemClickListener() {
                    @Override
                    public void onItemClick(Obj_Species_for_user name, int position) {
                        Intent intent = new Intent(Show_My_Species.this, Show_Information.class);
                        for (int i = 0; i < s.size(); i++) {
                            if (name.getNombre().equals(s.get(i).getNombre())) {
                                intent.putExtra("Id", String.valueOf(s.get(i).getId_especie()));
                                intent.putExtra("nombre", s.get(i).getNombre());
                                intent.putExtra("lat", String.valueOf(s.get(i).getLatitud()));
                                intent.putExtra("long", String.valueOf(s.get(i).getLongitud()));
                                intent.putExtra("cantidad", String.valueOf(s.get(i).getCantidad()));
                            }
                        }
                        startActivity(intent);
                    }
                });
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                Toast.makeText(Show_My_Species.this, "Error al cargar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}