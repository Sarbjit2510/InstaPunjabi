package com.instapunjabiapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;




public class ShareImageFacebook extends Activity {
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private boolean pendingPublishReauthorization = false;
	static String ds="#InstaPunjabi #punjabi ";
	 String city;
	 String location;
	 String state;
	 String country;
	  ProgressDialog dialog;
	  Bitmap facebookbitmap;
	  GPSTracker gps;
	  double lat,lon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog=new ProgressDialog(ShareImageFacebook.this);
  	  dialog.setMessage("Uploading Image");
  	  dialog.show();
		gps=new GPSTracker(this);
		 if(gps.canGetLocation){     	
	        	lat=gps.getLatitude();
	        	lon=gps.getLongitude();
	        	Log.d("lat...", ""+lat);
	        	Log.d("lon....", ""+lon);
	        }else{
	        	Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
	        }
		 Session.openActiveSession(ShareImageFacebook.this, true, new Session.StatusCallback() {

		      // callback when session changes state
		      
			@Override
		      public void call(Session session, SessionState state, Exception exception) {
		        if (session.isOpened()) {

		          // make request to the /me API
		        	Request.newMeRequest(session, new Request.GraphUserCallback() {

		            // callback after Graph API response with user object
		            @Override
		            public void onCompleted(GraphUser user, Response response) {
		              if (user != null) {
		            	  
		            	  getAddress(lat, lon);
			              publishStory();
			             
			              //ShareImageFacebook.this.finish();
		              }
		            }
		          }).executeAsync();
		        }
		      }
		    });
		 
//		 bitmap=FinalImage.imageBitmap;
//		 Log.d("Bitmap", "Bitmap..."+bitmap);
	}//oncreate
	
	 void publishStory() {
		 
		    Session session = Session.getActiveSession();

		    if (session != null){

		        // Check for publish permissions    
		        List<String> permissions = session.getPermissions();
		        if (!isSubsetOf(PERMISSIONS, permissions)) {
		            pendingPublishReauthorization = true;
		            Session.NewPermissionsRequest newPermissionsRequest = new Session
		                    .NewPermissionsRequest(this, PERMISSIONS);
		        session.requestNewPublishPermissions(newPermissionsRequest);
		            return;
		        }


		        Request.Callback callback= new Request.Callback() {
		            @Override
					public void onCompleted(Response response) {
		                JSONObject graphResponse = response
		                                           .getGraphObject()
		                                           .getInnerJSONObject();
		                String postId = null;
		                try {
		                    postId = graphResponse.getString("id");
		                } catch (JSONException e) {
		                    Log.i("TAG",
		                        "JSON error "+ e.getMessage());
		                }
		                FacebookRequestError error = response.getError();
		                if (error != null) {
		                    Toast.makeText(ShareImageFacebook.this,
		                         error.getErrorMessage(),
		                         Toast.LENGTH_SHORT).show();
		                    } else {
		                        
		                    	 dialog.dismiss();
		                        Toast.makeText(ShareImageFacebook.this, 
		                        		"Image uploaded Successfully"+postId,
		                             Toast.LENGTH_LONG).show();
		                }
		                
		            }
		            
		        };
		        facebookbitmap=FinalImage.shareImageBitmap;
		        Log.v("save to publish",""+facebookbitmap);
		       
		        Request photoRequest = Request.newUploadPhotoRequest(session,ShareImageFacebook.this.facebookbitmap, callback);
		        Bundle params = photoRequest.getParameters();
		        params.putString("message", ds);
		        photoRequest.executeAsync();
		    }
		}
	 
	 public void getAddress(double lat, double lng) {
		 Geocoder geocoder = new Geocoder(ShareImageFacebook.this,Locale.getDefault());
		 try {
		     List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
		     Address obj = addresses.get(0);
		    city=addresses.get(0).getLocality();
		    state=obj.getAdminArea();
		    country=obj.getCountryName();
		    location=obj.getAddressLine(0);
		     Log.v("city name",""+addresses.get(0).getLocality());
		     if(city==null){
		     	city="";	
		     }
		     if(state==null){
		     	state="";	
		     }
		     if(country==null){
		     	country="";	
		     }		    
		     ds=   String.format(ds+" #%s #%s #%s",city,state,country);
		     String add = obj.getAddressLine(0);		     
//		     = obj.getLatitude();
//		      = obj.getLongitude();
//		     = obj.getSubAdminArea();
//		     = obj.getAdminArea();
		     add = add + "\n" + obj.getCountryName();
		     add = add + "\n" + obj.getAdminArea();
		     add = add + "\n" + obj.getCountryCode();
		     add=add + "\n" + obj.getLocality();
		     ;
		     add = add + "\n" + obj.getPostalCode();		     		     		    
		     Log.v("IGA", "Address" + add);		     
		     // Toast.makeText(this, "Address=>" + add,
		     // Toast.LENGTH_SHORT).show();
		     // TennisAppActivity.showDialog(add);
		 } catch (IOException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		 }
		 }
	     
	 private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
		    for (String string : subset) {
		        if (!superset.contains(string)) {
		            return false;
		        }
		    }
		    return true;  
		}
	 
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);	   
		  Session.getActiveSession().onActivityResult(ShareImageFacebook.this, requestCode, resultCode, data);
		}
		
		
		 		
}//class