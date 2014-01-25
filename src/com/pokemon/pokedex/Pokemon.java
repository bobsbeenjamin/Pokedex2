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
import org.json.JSONException;
import org.json.JSONObject;

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
		this.number = "";
		this.name = "";
		this.type = "";
		this.weight = "";
		this.height = "1";
		this.description = "";
		this.descriptionVoice = "";
		this.call = "";
		this.picturePath = "";
	   	mQueryTask = new GetPokemonTask();
	   	mQueryTask.execute(index); //query the DB
	}
   
   	public String[] getStats() {
		String[] temp = new String[8];
		temp[0] = number;
		temp[1] = name;
		temp[2] = type;
		temp[3] = String.valueOf(weight) + "lbs";
		temp[4] = height;		  
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
		    		Log.d("line=", "t " + line); //can provide useful errors
		    	}
	    	} catch (Exception e) {
	    		Log.e("Connection", "Error connecting to server -- " + e.toString());
	    	}
	    	
	    	Log.d("JSON string:", "" + sb.toString());
	    	Log.d("Response status code:", "" + responseCode);
	    	
	    	JSONObject jsonO = null;
	    	JSONArray jsonArray = null;
	    	JSONObject jsonString = null;
			try {
				jsonO = new JSONObject(sb.toString());
				Log.d("json root:", "" + jsonO);
				jsonArray = jsonO.getJSONArray("pokemon");
				jsonString = jsonArray.getJSONObject(0);
				Log.d("json stringy:", "" + jsonString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("json: ", ""+e);
			}
			try {
			number = jsonString.getString("pokemon_num");
			Log.d("test", ""+number);
			name = jsonString.getString("name");
			type = jsonString.getString("types");
			weight = jsonString.getString("weight");
			height = jsonString.getString("height");
			description = jsonString.getString("text_description");
			descriptionVoice = jsonString.getString("read_description");
			call = jsonString.getString("call_sound"); //may need to change this
			picturePath = jsonString.getString("picture_path");
			Log.d("test", "break json");
	    	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("json error:", ""+e);
	    	}
			getStats();
	    	
	    	return sb.toString();
	    }
	    /*
	    protected void onPostExecute(String jsonStr) {
	    	JSONObject jsonO = null;
	    	JSONArray jsonArray = null;
	    	JSONObject jsonString = null;
			try {
				jsonO = new JSONObject(jsonStr);
				Log.d("json root:", "" + jsonO);
				jsonArray = jsonO.getJSONArray("pokemon");
				jsonString = jsonArray.getJSONObject(0);
				//Log.d("json stringy:", "" + jsonString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("json: ", ""+e);
			}
			try {
			number = jsonString.getString("pokemon_num");
			name = jsonString.getString("name");
			type = jsonString.getString("types");
			weight = jsonString.getString("weight");
			height = jsonString.getString("height");
			description = jsonString.getString("text_description");
			descriptionVoice = jsonString.getString("read_description");
			call = jsonString.getString("call_sound"); //may need to change this
			picturePath = jsonString.getString("picture_path");
			//Log.d("test", "break json");
	    	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("json error:", ""+e);
	    	}
			getStats();
	    }*/
	    

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
