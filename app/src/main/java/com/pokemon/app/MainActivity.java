package com.pokemon.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        // Based on which pokemon is clicked bring the details for the specified pokemon
        pokemon_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int new_position = position + 1;
                String url = "https://pokeapi.co/api/v2/pokemon/"+new_position;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {

                            String name = response.getString("name");
                            String height = response.getString("height");
                            String id = response.getString("id");
                            openNewActivity(id, name, height);

//                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                            alertDialog.setTitle("Alert");
//                            alertDialog.setMessage("The response is " + name);
//                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                            alertDialog.show();

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
        });
    }

    /**
     * This method collect the pokemon list form the pokemon api
     */
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

    public void openNewActivity(String id, String name, String height){
        Intent intent = new Intent(this, PokemonDetails.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("height", height);
        startActivity(intent);
    }
}