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
        "Squirtle", "Wartortle", "Blastoise"};
    
    static final String[] icons = new String[] {
        "bulbasaur_pic", "ivysaur_pic", "venusaur_pic",
        "charmander_pic", "charmeleon_pic", "charizard_pic",
        "squirtle_pic", "wartortle_pic", "blastoise_pic"};
    
    Context context;
    
    public GridViewCustomAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }
    
    public int getCount() {
        return 9;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_row, parent, false);
            
            TextView textViewTitle = (TextView) row.findViewById(R.id.textView);
            ImageView imageView = (ImageView) row.findViewById(R.id.imageView);
            
            textViewTitle.setText(names[position]);
            int id = context.getResources().getIdentifier("com.pokemon.pokedex:drawable/" + icons[position], null, null);
            imageView.setImageResource(id);
            
        }
        
        return row;
        
    }

}
