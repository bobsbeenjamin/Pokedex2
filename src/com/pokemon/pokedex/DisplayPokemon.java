package com.pokemon.pokedex;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayPokemon extends Activity {
	// Member data
	String nameStr;
	TextView number, name, type, weight, height, desc;
	ImageView picture;
	TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_pokemon);
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		   nameStr = extras.getString("name");
		// Create Pokemon
		Pokemon thisPokemon = new Pokemon(nameStr);
		// Grab other stuff from database, using new Pokemon
		String[] data = thisPokemon.getStats();
        number.setText(data[0]);
        name.setText(data[1]);
        type.setText(data[2]);
        weight.setText(data[3]);
        height.setText(data[4]);
        desc.setText(data[5]);
        // Grab pic based on pic path
        int id = getResources().getIdentifier("com.pokemon.pokedex:drawable/" + data[7], null, null);
        picture.setImageResource(id);
        // 
        tts = new TextToSpeech(DisplayPokemon.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
               if(status != TextToSpeech.ERROR){
                  tts.setLanguage(Locale.UK);
               }
            }
         });
        vocalize(thisPokemon.getVoice());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_pokemon, menu);
		return true;
	}
	
	// Make this thing talk!
	protected void vocalize(String voice) {
	      tts.speak(voice, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	@Override
    protected void onPause() {
       if(tts != null){
          tts.stop();
          tts.shutdown();
       }
       super.onPause();
	}
}
