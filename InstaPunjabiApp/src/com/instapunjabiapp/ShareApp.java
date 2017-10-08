package com.instapunjabiapp;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.model.*;
import android.content.Intent;
public class ShareApp extends Activity {

	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private boolean pendingPublishReauthorization = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 Session.openActiveSession(ShareApp.this, true, new Session.StatusCallback() {

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
//		                TextView welcome = (TextView) findViewById(R.id.welcome);
		                Log.e("**********we are here","Hello " + user.getName() + "!");
		                Toast.makeText(ShareApp.this,"Hello App Shared on " + user.getName() + "!", Toast.LENGTH_LONG).show();
		                publishStory();
		                ShareApp.this.finish();
		              }
		            }
		          }).executeAsync();
		        }
		      }
		    });
		}// oncreate
	
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
	Bundle params = new Bundle();
	        
	        params.putString("link", "https://play.google.com/store/apps/details?id=com.Insta.instapunjabi");
	       

	        Request.Callback callback= new Request.Callback() {
//	            public void onCompleted(Response response) {
//	                JSONObject graphResponse = response
//	                                           .getGraphObject()
//	                                           .getInnerJSONObject();
//	                String postId = null;
//	                try {
//	                    postId = graphResponse.getString("id");
//	                } catch (JSONException e) {
//	                    Log.i("TAG",
//	                        "JSON error "+ e.getMessage());
//	                }
//	                FacebookRequestError error = response.getError();
//	                if (error != null) {
//	                    Toast.makeText(ShareApp.this,
//	                         error.getErrorMessage(),
//	                         Toast.LENGTH_SHORT).show();
//	                    } else {
//	                        Toast.makeText(ShareApp.this, 
//	                             postId,
//	                             Toast.LENGTH_LONG).show();
//	                        ShareApp.this.finish();
//	                }
//	            }
//	        };
	        @Override
	        public void onCompleted(Response response) {
	            if (response.getError() == null) {
	                // Tell the user success!
	            	Toast.makeText(ShareApp.this, 
	                     "Uploaded sucessfully",
	                      Toast.LENGTH_LONG).show();
	            	
	            }
	        }
	        };
	       
	        
	        Request request = new Request(Session.getActiveSession(), "me/feed", params, HttpMethod.POST);
	        request.executeAsync();
	       
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
	  Session.getActiveSession().onActivityResult(ShareApp.this, requestCode, resultCode, data);
	}
	}

