package com.example.efren.biodiversidad;

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

public class Show_Zones extends AppCompatActivity {

    private ArrayList<Obj_Zones> obj_zones = new ArrayList<>();
    private Obj_Zones obj_zone;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__zones);
        new Zonas().execute();
    }

    private class Zonas extends AsyncTask<String, String, ArrayList<Obj_Zones>> {
        @Override
        protected ArrayList<Obj_Zones> doInBackground(String... strings) {
            try {
                JSONArray jsonArray = new JSONArray(new Read_Page().Read_Page("http://biodiversidad.alcohomeapp.com.mx/webService_obtener_zonas.php"));
                JSONObject jsonObject;
                for (int i = 0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    obj_zone = new Obj_Zones();
                    obj_zone.setId(jsonObject.getInt("id"));
                    obj_zone.setNombre(jsonObject.getString("nombre"));
                    obj_zone.setLat1(jsonObject.getDouble("lat1"));
                    obj_zone.setLat2(jsonObject.getDouble("lat2"));
                    obj_zone.setLong1(jsonObject.getInt("long1"));
                    obj_zone.setLong2(jsonObject.getInt("long2"));
                    obj_zones.add(obj_zone);
                }
                return obj_zones;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Obj_Zones> s) {
            super.onPostExecute(s);

            if (s != null) {
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewPublic);
                mLayoutManager = new LinearLayoutManager(Show_Zones.this);
                mAdapter = new MyAdapter_RecyclerView_Zones(Show_Zones.this, s, R.layout.recycler_view_zones, new MyAdapter_RecyclerView_Zones.OnItemClickListener() {
                    @Override
                    public void onItemClick(Obj_Zones name, int position) {

                    }
                });
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                Toast.makeText(Show_Zones.this, "Error al cargar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}