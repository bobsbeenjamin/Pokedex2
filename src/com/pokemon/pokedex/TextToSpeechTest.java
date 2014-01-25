package com.pokemon.pokedex;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TextToSpeechTest extends Activity {
   TextView number, name, type, weight, height, desc;
   ImageView picture;
   TextToSpeech tts;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_text_to_speech_test);
      final Pokemon pikachu = new Pokemon("25");
      number = (TextView) findViewById(R.id.vocalTestNumber);
      name = (TextView) findViewById(R.id.vocalTestName);
      type = (TextView) findViewById(R.id.vocalTestType);
      weight = (TextView) findViewById(R.id.vocalTestWeight);
      height = (TextView) findViewById(R.id.vocalTestHeight);
      desc = (TextView) findViewById(R.id.vocalTestDesc);
      picture = (ImageView) findViewById(R.id.vocalTestImage);
      Button tester = (Button) findViewById(R.id.vocalTestButton);
      tts = new TextToSpeech(TextToSpeechTest.this, new TextToSpeech.OnInitListener() {
         
         @Override
         public void onInit(int status) {
            if(status != TextToSpeech.ERROR){
               tts.setLanguage(Locale.UK);
            }
            
         }
      });
      
      tester.setOnClickListener(new View.OnClickListener() {
         
         @Override
         public void onClick(View v) {
            String[] temp = pikachu.getStats();
            number.setText(temp[0]);
            name.setText(temp[1]);
            type.setText(temp[2]);
            weight.setText(temp[3]);
            height.setText(temp[4]);
            desc.setText(temp[5]);
            int id = getResources().getIdentifier("com.pokemon.pokedex:drawable/" + temp[7], null, null);
            picture.setImageResource(id);
            vocalize(pikachu.getVoice());
         }
      });
   }

   protected void vocalize(String voice) {
      tts.speak(voice, TextToSpeech.QUEUE_FLUSH, null);
      
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.text_to_speech_test, menu);
      return true;
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
