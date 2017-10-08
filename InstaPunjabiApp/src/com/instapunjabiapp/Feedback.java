package com.instapunjabiapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends Activity {
 Button submit,back;
 EditText na, mail,feed;
 String name,email,feedback; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_form);
		submit=(Button)findViewById(R.id.Submit);
		back=(Button)findViewById(R.id.backfeed);
		na=(EditText)findViewById(R.id.name);
		mail=(EditText)findViewById(R.id.email);
		feed=(EditText)findViewById(R.id.feedback);		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Feedback.this.finish();
			}
		});
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(na.getText().toString().trim().length()!=0&&mail.getText().toString().trim().length()!=0&&feed.getText().toString().trim().length()!=0){
					if(isEmailValid(mail.getText().toString())){
				if(na!=null){
						name = na.getText().toString().replaceAll(" ", "%20")
								.replaceAll("&", "%26");
					}
					email = mail.getText().toString().replaceAll(" ", "%20")
							.replaceAll("&", "%26");
					feedback = feed.getText().toString().replaceAll(" ", "%20")
							.replaceAll("&", "%26");

					LongeOperation events1 = new LongeOperation();
					events1.execute("http://instapunjabi.com/temp/fetch.php?db_table=feedback&name="
							+ name
							+ "&email="
							+ email
							+ "&suggestion="
							+ feedback);
					Toast.makeText(Feedback.this,
							"Feedback Sent",
							Toast.LENGTH_LONG).show();
					}
					else{
						Toast.makeText(Feedback.this,
								"Please enter a valid email id",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(Feedback.this,
							"Please do not remain any field empty",
							Toast.LENGTH_LONG).show();
				}
			}
			});
}
	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}


	public class LongeOperation extends AsyncTask<String,Void, Void>{
	URL sourceUrl;
	Uri uri;
	ProgressDialog dialog=new ProgressDialog(Feedback.this);
		@Override
		protected Void doInBackground(String... urls){
		for (String url : urls){
			// TODO Auto-generated method stub
			System.out.println("in the url"+url);
			/** Send URL to parse XML Tags */
			 try {
				sourceUrl = new URL(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			 System.out.println("parsing url   "+sourceUrl);
			 Log.v("parsing url",""+sourceUrl);
			try {
				SAXParserFactory spf=SAXParserFactory.newInstance();      
				SAXParser sp= spf.newSAXParser();
				XMLReader xr=sp.getXMLReader();
				//URL soUrl=new URL(url);
			
				xr.parse(new InputSource (sourceUrl.openStream()));
				Log.v("urllllllllllllllll",""+sourceUrl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("sending data");
			dialog.show();
		}


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
		        dialog.dismiss();
		        dialog = null;
		    } catch (Exception e) {
		        // nothing
		    }

			
				
			}
		}

		
		
}
