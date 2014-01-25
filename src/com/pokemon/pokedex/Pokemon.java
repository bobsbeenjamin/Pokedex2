package com.pokemon.pokedex;

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
import org.json.JSONArray;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class Pokemon {
	private GetPokemonTask mQueryTask = null;
	String number;
	String name;
	String type;
	String weight;
	String height;
	String description;
	String descriptionVoice;
	String call;
	String picturePath;
   
	public Pokemon(String index) {
	   	mQueryTask = new GetPokemonTask();
	   	mQueryTask.execute(index); //query the DB
	   	JSONArray jsonArray = new JSONArray(jsonString);
	   	
	   	List<String> list = new ArrayList<String>();
	   	for (int i=0; i<jsonArray.length(); i++) {
	   	    list.add( jsonArray.getString(i) );
	   	}
	   	
		this.number = list.get(0);
		this.name = list.get(1);
		this.type = list.get(2);
		this.weight = list.get(3);
		this.height = list.get(4);
		this.description = list.get(5);
		this.descriptionVoice = list.get(6);
		this.call = list.get(7);
		this.picturePath = list.get(8);
	}
   
   	public String[] getStats(){
		String[] temp = new String[8];
		temp[0] = number;
		temp[1] = name;
		temp[2] = type;
		temp[3] = String.valueOf(weight) + "lbs";
		//temp[4] = height;
		
		double tempDouble = Double.parseDouble(height);
		int tempInt = (int)tempDouble;
		temp[4] = String.valueOf(tempInt) + "\'";
		tempDouble-=tempInt;
		tempDouble*=100;
		tempInt = (int)tempDouble;
		temp[4] = temp[4] + String.valueOf(tempInt) + "\"";
		  
		temp[5] = description;
		temp[6] = call;
		temp[7] = picturePath;
		return temp;
   	}
   
   	public String getVoice() {
		return descriptionVoice;
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
