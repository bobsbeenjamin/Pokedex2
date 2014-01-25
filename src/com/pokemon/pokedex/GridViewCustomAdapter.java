package com.pokemon.pokedex;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewCustomAdapter extends ArrayAdapter {
    
    static final String[] names = new String[] {
        "Bulbasaur", "Ivysaur", "Venusaur",
        "Charmander", "Charmeleon", "Charizard",
        "Squirtle", "Wartortle", "Blastoise",
        "Caterpie", "Metapod", "Butterfree",
        "Weedle", "Kakuna", "Beedrill",
        "Pidgey", "Pidgeotto", "Pidgeot",
        "Rattata" , "Raticate", "Spearow",
        "Fearow", "Ekans", "Arbok", "Pikachu", "Raichu"};
    
    static final String[] icons = new String[] {
        "bulbasaur_pic", "ivysaur_pic", "venusaur_pic",
        "charmander_pic", "charmeleon_pic", "charizard_pic",
<<<<<<< HEAD
        "squirtle_pic", "wartortle_pic", "blastoise_pic"};
=======
        "squirtle_pic", "wartortle_pic", "blastoise_pic",
        "caterpie_pic", "metapod_pic", "butterfree_pic",
        "weedle_pic", "kakuna_pic", "beedrill_pic",
        "pidgey_pic", "pidgeotto_pic", "pidgeot_pic",
        "rattata_pic" , "raticate_pic", "spearow_pic",
        "fearow_pic", "ekans_pic", "arbok_pic", "pikachu_pic", "raichu_pic"};
>>>>>>> origin/master
    
    Context context;
    
    public GridViewCustomAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }
    
    public int getCount() {
        return 26;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_row, parent, false);
            

            
        }
        TextView textViewTitle = (TextView) row.findViewById(R.id.textView);
        ImageView imageView = (ImageView) row.findViewById(R.id.imageView);
        textViewTitle.setText(names[position]);
        int id = context.getResources().getIdentifier("com.pokemon.pokedex:drawable/" + icons[position], null, null);
        imageView.setImageResource(id);
        
        return row;
        
    }

}
