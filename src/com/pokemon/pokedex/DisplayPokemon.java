package com.pokemon.pokedex;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;
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
		// Assign TextView/ImageView IDs
		number = (TextView) findViewById(R.id.Num);
		name = (TextView) findViewById(R.id.Name);
		type = (TextView) findViewById(R.id.Type);
		weight = (TextView) findViewById(R.id.Weight);
		height = (TextView) findViewById(R.id.Height);
		desc = (TextView) findViewById(R.id.Description);
		picture = (ImageView) findViewById(R.id.pic);
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null)
		   nameStr = extras.getString("name");
		// Create Pokemon
		// Grab other stuff from database, using new Pokemon
		
		Pokemon thisPokemon = new Pokemon(nameStr);
		String[] data = thisPokemon.getStats();
		while (data[0].equals("") || data[1].equals("") || data[2].equals("") || data[3].equals("") || data[4].equals("") || data[5].equals("") || data[7].equals("")) {
			data = thisPokemon.getStats();
		}
		Log.d("info: ",	""+data[0]+" "+data[1]+" "+data[2]);
        number.setText(data[0]);
        name.setText(data[1]);
        type.setText(data[2]);
        weight.setText(data[3]);
        height.setText(data[4]);
        desc.setText(data[5]);
        Log.d("test", "break 1");
        // Grab pic based on pic path
        int id = getResources().getIdentifier("com.pokemon.pokedex:drawable/" + data[7], null, null);
        picture.setImageResource(id);
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
