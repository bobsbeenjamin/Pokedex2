package com.pokemon.pokedex;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.pokemon.pokedex.GridViewCustomAdapter;

public class ListPokemonActivity extends Activity {

    GridView gridView;
    GridViewCustomAdapter customAdapter;
    static final String[] names = new String[] {
        "Bulbasaur", "Ivysaur", "Venusaur",
        "Charmander", "Charmeleon", "Charizard",
        "Squirtle", "Wartortle", "Blastoise"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pokemon);
        
        gridView = (GridView)findViewById(R.id.gridViewCustom);
        customAdapter = new GridViewCustomAdapter(this);
        gridView.setAdapter(customAdapter);
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                String name;
                name = names[position];
                Intent intent = new Intent(getApplicationContext(), DisplayPokemon.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        
        });
        
    }
    
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_pokemon, menu);
        return true;
    }
    
    
    
    

}
