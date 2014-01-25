package com.pokemon.pokedex;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button testTTS = (Button) this.findViewById(R.id.ttsButton);
        testTTS.setOnClickListener(new View.OnClickListener() {
         
         @Override
         public void onClick(View v) {
            Intent intent = new Intent("android.intent.action.TEXTTOSPEECHTEST");
            startActivity(intent);
            
         }
      }); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void displayList(View view) {
        Intent intent = new Intent(this, ListPokemonActivity.class);
        startActivity(intent);
    }

}
