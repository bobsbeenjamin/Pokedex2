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
		
		/*
		double tempDouble = Double.parseDouble(height);
		int tempInt = (int)tempDouble;
		temp[4] = String.valueOf(tempInt) + "\'";
		tempDouble-=tempInt;
		tempDouble*=100;
		tempInt = (int)tempDouble;
		temp[4] = temp[4] + String.valueOf(tempInt) + "\"";
		*/
		  
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
				e.printStackTrace();
			}
	    	/*JSONArray jsonArray = null;
		   	
		   	JSONObject jsonResponse = null;
			try {
				jsonResponse = new JSONObject(jsonString);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				jsonArray = jsonResponse.getJSONArray("pokemon");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   	
		   	List<String> list = new ArrayList<String>();
		   	for (int i=0; i<jsonArray.length(); i++) {
		   	    try {
		   	    	Log.d("json part:", ""+jsonArray.getString(i));
					list.add(jsonArray.getString(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
		   	}
		   	*/
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
	    	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();  		
	    	}
			getStats();
			/*
			number = list.get("pokemon_num");
			name = list.get(1);
			type = list.get(2);
			weight = list.get(3);
			height = list.get(4);
			description = list.get(5);
			descriptionVoice = list.get(6);
			call = list.get(7);
			picturePath = list.get(8);
			getStats();
			*/
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
