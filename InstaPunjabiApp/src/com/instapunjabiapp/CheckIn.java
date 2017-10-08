package com.instapunjabiapp;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



public class CheckIn extends ListActivity {

	private static final String TAG = "instapunjbiapp";
	private ProgressDialog pDialog;	 
    // URL to get contacts JSON
   public GPSTracker gps;
    // JSON Node names
    private static final String TAG_RESPONCE = "response";
    private static final String TAG_CONTACTS = "venues";
    static String VENUE_ID = "id";
    static String VENUE_NAME = "name";
    // contacts JSONArray
    ArrayList<String> namelist=new ArrayList<String>();
    ArrayList<String> idlist=new ArrayList<String>();
    JSONArray contacts = null; 
    // Hashmap for ListView
    public static Bitmap listtxtBitmap=null;
    String lat,lon;
    String url;
    static String venuename=null;
    static String venueid=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_in);
		gps=new GPSTracker(this);	 
        ListView lv = getListView();
        if(gps.canGetLocation){     	
        	lat=String.valueOf(gps.getLatitude());
        	lon=String.valueOf(gps.getLongitude());
//        	Log.d(TAG, ""+lat);
//        	Log.d(TAG, ""+lon);
        	url = "https://api.foursquare.com/v2/venues/search?ll="+lat+","+lon+"&client_id=EJRTOGASZJ4WQ1U3AVN1XIHC2ITED54IN0OD1NQZN2IGKIDV&client_secret=MXRNN4OBRMSC55ZCNLQ4YJDNRCVILT2ALUXNHMAV1QG14ZDW&v=20142406";
        	 new GetContacts().execute();
        }else{
        	Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
        }
        
        
        
        
       
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 // getting values from selected ListItem
			
//				venuename=((TextView)view.findViewById(R.id.name)).getText().toString();
//				venueid=((TextView)view.findViewById(R.id.id)).getText().toString();
//				Log.d("idd....", "vidddddd"+venueid);
//			
		
				
				venuename=namelist.get(position);
				Log.v("venueid", "venuename"+venuename);
			venueid=idlist.get(position);
				Log.v("venueid", "venueid"+venueid);
				listtxtBitmap=textAsBitmap(venuename, 40, Color.WHITE);
				finish();
                //Toast.makeText(CheckIn.this, ""+name, Toast.LENGTH_LONG).show();      
			}
		});
}//oncreate
	 /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
    	
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(CheckIn.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject jsonObj1 = jsonObj.getJSONObject(TAG_RESPONCE);                    
                    // Getting JSON Array node
                    contacts = jsonObj1.getJSONArray(TAG_CONTACTS);
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);                       
//                        String id = c.getString(VENUE_ID);
//                        String name = c.getString(VENUE_NAME);
//                        String email = c.getString(TAG_EMAIL);
//                        String address = c.getString(TAG_ADDRESS);
//                        String gender = c.getString(TAG_GENDER);
// 
//                        // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject(TAG_PHONE);
//                        String mobile = phone.getString(TAG_PHONE_MOBILE);
//                        String home = phone.getString(TAG_PHONE_HOME);
//                        String office = phone.getString(TAG_PHONE_OFFICE); 
                        // tmp hashmap for single contact
                      
                        // adding each child node to HashMap key => value
                        namelist.add(c.getString(VENUE_NAME) );
                        idlist.add(c.getString(VENUE_ID));
//                        contact.put(TAG_EMAIL, email);
//                        contact.put(TAG_PHONE_MOBILE, mobile); 
                        // adding contact to contact list
                      
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
           ArrayAdapter<String>adapter=new ArrayAdapter<String>(CheckIn.this, android.R.layout.simple_list_item_1, namelist);
 
            setListAdapter(adapter);
        }
 
    }
    public Bitmap textAsBitmap(String text, float textSize,int color) {
	    Paint paint = new Paint();
	    paint.setTextSize(textSize);
	    paint.setColor(color);
	    paint.setTextAlign(Paint.Align.LEFT);
	    int width = (int) (paint.measureText(text) + 0.5f); // round
	    float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
	    int height = (int) (baseline + paint.descent() + 0.5f);
	    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(image);
	    canvas.drawText(text, 0, baseline, paint);
	    return image;
	}
}//class