package com.pokemon.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ListView pokemon_list_view;
    private RequestQueue myRequest;
    private ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemon_list_view = findViewById(R.id.list_view_result);

        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        pokemon_list_view.setAdapter(adapter);
        myRequest = Volley.newRequestQueue( this);

        getPokemonList();
    }

    private void getPokemonList() {
        String url = "https://pokeapi.co/api/v2/pokemon/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONArray json_result = response.getJSONArray( "results");

                    for (int i = 0; i < json_result.length(); i++){
                        JSONObject pokemon_list = json_result.getJSONObject(i);

                        String pokemon_name = pokemon_list.getString("name");
                        arrayList.add(pokemon_name);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        myRequest.add(request);
    }
}