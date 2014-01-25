package com.pokemon.pokedex;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;



public class MainActivity extends Activity {
	private GetPokemonTask mQueryTask = null;
	
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

    public void submitQuery(View view) {
    	String mNumber = "4";
        mQueryTask = new GetPokemonTask();
        mQueryTask.execute(mNumber);
        //Intent intent = new Intent(this, ListPokemonActivity.class);
        //startActivity(intent);
    }
    
	/*	queries the database with a specific pokemon number
		returns the json string for the corresponding pokemon
	 */    
    public class GetPokemonTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... arg0) {
		    int responseCode = 0;
		    String line = "";
		    StringBuilder sb = new StringBuilder();
		    //String pokemon_id = getIntent().getStringExtra("query_num");
		    
		    //BasicNameValuePair pokemon_id = new BasicNameValuePair("id", arg0[0]);
	    	List<NameValuePair> params = new ArrayList<NameValuePair>();
	    	params.add(new BasicNameValuePair("id", arg0[0]));
		    
	    	try {
		    	URL url = new URL("http://passthedoodle.com/poke/query.php");
			    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			    conn.setRequestMethod("POST");
			    conn.setDoInput(true);
		    	conn.setDoOutput(true);
		    	
		    	OutputStream out = conn.getOutputStream();
		    	BufferedWriter writer = new BufferedWriter(
		    		new OutputStreamWriter(out, "UTF-8"));
		    	writer.write(getQuery(params));
		    	writer.flush();
		    	writer.close();
		    	out.close();
		    	
		    	conn.connect();
		    	
		    	responseCode = conn.getResponseCode();
		    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    	while ((line = reader.readLine()) != null) {
		    		sb.append(line+"\n");
		    		//Log.d("line=", "" + line); //can provide useful errors
		    	}
	    	} catch (Exception e) {
	    		Log.e("Connection", "Error connecting to server -- " + e.toString());
	    	}
	    	
	    	Log.d("JSON string:", "" + sb);
	    	Log.d("Response status code:", "" + responseCode);
	    	return sb.toString();
	    }

		private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
			StringBuilder result = new StringBuilder();
			boolean first = true;
			
			for (NameValuePair pair : params) {
				if (first) first = false;
				else result.append("&");
				
				result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
				result.append("=");
				result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
			}
			
			return result.toString();
		}
    }
    
}
