package com.pokemon.app;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PokemonDetails extends AppCompatActivity {
    private TextView id;
    private TextView name;
    private TextView height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_details);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String id_value = extras.getString("id");
            String name_value = extras.getString("name");
            String height_value = extras.getString("height");

            //The key argument here must match that used in the other activity
            id = (TextView) findViewById(R.id.pokemon_id);
            name = (TextView) findViewById(R.id.pokemon_name);
            height = (TextView) findViewById(R.id.pokemon_height);
            id.setText(id_value);
            name.setText(name_value);
            height.setText(height_value);
        }


    }
}
