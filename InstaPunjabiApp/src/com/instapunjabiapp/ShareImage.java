package com.instapunjabiapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import nit.TwitterApp;
import nit.TwitterApp.TwDialogListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ShareImage extends Activity {

	private static final String TAG = "instapunjbiapp";
	ImageView instaimage;
	Button cancel;
	ListView sharelist;
	static ArrayList<String> socialitems = new ArrayList<String>();
	static ArrayList<Integer> socialimages = new ArrayList<Integer>();

	Bitmap bitmap;
	GPSTracker gps;
	static String ds;
	String city;
	String location;
	String state;
	String country;
	double lat, lon;
	private TwitterApp mTwitter;
	TwitterUploader twit;
	//static String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_share_image);
		gps = new GPSTracker(this);
		if (gps.canGetLocation) {
			lat = gps.getLatitude();
			lon = gps.getLongitude();
			Log.d("lat...", "" + lat);
			Log.d("lon....", "" + lon);
		} else {
			Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG)
					.show();
		}
		
		bitmap = FinalImage.shareImageBitmap;
		// Log.d("aaaaa bitmap","aaaaaa bitmap"+bitmap);
		instaimage = (ImageView) findViewById(R.id.instaimage);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		socialimages.clear();
		socialimages.add(R.drawable.facebook);
		socialimages.add(R.drawable.twitter);
		socialimages.add(R.drawable.instagram);
		socialimages.add(R.drawable.foursqure);
		socialimages.add(R.drawable.gallery1chk);

		socialitems.clear();
		socialitems.add("Facebook");
		socialitems.add("Twitter");
		socialitems.add("Instagram");
		socialitems.add("Foursquare");
		socialitems.add("Gallery");

		sharelist = (ListView) findViewById(R.id.sharelist);
		sharelist.setAdapter(new SocialsiteAdapter(this));
		sharelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					Intent face = new Intent(ShareImage.this,
							ShareImageFacebook.class);
					startActivity(face);
					break;
				case 1:
					getAddress(lat, lon);
					Log.v("twittwe image path", "" + FinalImage.path);
					mTwitter = new TwitterApp(ShareImage.this,
							TwitterUploader.twitter_consumer_key,
							TwitterUploader.twitter_secret_key);
					twit = new TwitterUploader(ShareImage.this, mTwitter);
					if (mTwitter.hasAccessToken()) {
						twit.setImage(FinalImage.path);

					} else {
						mTwitter.setListener(mTwLoginDialogListener1);
						mTwitter.authorize();
					}

					break;
				case 2:
					// Log.d("Instagrraaammm bitmap","Instagrraaammm bitmap"+bitmap);
					getAddress(lat, lon);
					try {
						final ContentResolver cr = ShareImage.this
								.getContentResolver();
						Intent shareIntent = new Intent(Intent.ACTION_SEND);
						shareIntent.setAction(Intent.ACTION_SEND);
						shareIntent.setType("image/*");
						shareIntent.putExtra(Intent.EXTRA_STREAM, Uri
								.parse(MediaStore.Images.Media.insertImage(cr,
										bitmap, "#InstaPunjabi#Punjabi#" + city
												+ "#" + state + "#" + country,
										"#InstaPunjabi#Punjabi#" + city + "#"
												+ state + "#" + country)));
						shareIntent.setPackage("com.instagram.android");
						Log.v("", "we are hereeeeeeeeeee instagram");
						startActivity(shareIntent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						AlertDialog.Builder builder = new AlertDialog.Builder(
								ShareImage.this);
						builder.setMessage("Please Install The App First ")
								.setNeutralButton("Close", null);
						AlertDialog alert = builder.create();
						alert.show();
						e.printStackTrace();
					}
					break;
				case 3:

					try {
						if(CheckIn.venueid==null){
							Toast.makeText(ShareImage.this, "Select the place first",Toast.LENGTH_LONG).show();
						}
						else{
							Intent i= new Intent(ShareImage.this,ActivityWebView.class);
							i.putExtra("imagepath",FinalImage.path);
							startActivity(i);
							//dialog.dismiss();
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					
					break;
				case 4:
					// Log.d("Gallerrrryyy bitmap","Gallerrrryyy bitmap"+bitmap);
					Toast.makeText(ShareImage.this, "Image saved to Gallery ",
							Toast.LENGTH_LONG).show();
					savePhoto(bitmap);
					break;

				default:
					break;
				}

			}
		});

	}
	
	private final TwDialogListener mTwLoginDialogListener1 = new TwDialogListener() {
		@Override
		public void onComplete(String value) {
		String username = mTwitter.getUsername();
		username = (username.equals("")) ? "No Name" : username;
		twit.setImage(FinalImage.path);
		}

		@Override
		public void onError(String value) {
			
		}
		};

	public void getAddress(double lat, double lng) {
		
		ds="";
		
		Geocoder geocoder = new Geocoder(ShareImage.this, Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
			Address obj = addresses.get(0);
			city = addresses.get(0).getLocality();
			state = obj.getAdminArea();
			country = obj.getCountryName();
			location = obj.getAddressLine(0);
			Log.v("city name", "" + addresses.get(0).getLocality());
			if (city == null) {
				city = "";
			}
			if (state == null) {
				state = "";
			}
			if (country == null) {
				country = "";
			}
			ds = String.format("#InstaPunjabi #punjabi" + " #%s #%s #%s", city, state, country);
			String add = obj.getAddressLine(0);
			// = obj.getLatitude();
			// = obj.getLongitude();
			// = obj.getSubAdminArea();
			// = obj.getAdminArea();
			add = add + "\n" + obj.getCountryName();
			add = add + "\n" + obj.getAdminArea();
			add = add + "\n" + obj.getCountryCode();
			add = add + "\n" + obj.getLocality();
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

	private String savePhoto(Bitmap bmp) {
		File imageFileFolder = new File(
				Environment.getExternalStorageDirectory(), "InstaPunjabiImages"); // when
																					// you
																					// need
																					// to
																					// save
																					// the
																					// image
																					// inside
																					// your
																					// own
																					// folder
																					// in
																					// the
																					// SD
																					// Card
		// File path = Environment.getExternalStoragePublicDirectory(
		// Environment.DIRECTORY_PICTURES
		// ); //this is the default location inside SD Card - Pictures folder
		imageFileFolder.mkdir(); // when you create your own folder, you use
									// this line.
		FileOutputStream out = null;
		Calendar c = Calendar.getInstance();
		String date = "Image" + fromInt(c.get(Calendar.MONTH))
				+ fromInt(c.get(Calendar.DAY_OF_MONTH))
				+ fromInt(c.get(Calendar.YEAR))
				+ fromInt(c.get(Calendar.HOUR_OF_DAY))
				+ fromInt(c.get(Calendar.MINUTE))
				+ fromInt(c.get(Calendar.SECOND));
		File imageFileName = new File(imageFileFolder, date.toString() + ".jpg"); // imageFileFolder
		try {
			out = new FileOutputStream(imageFileName);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			scanPhoto(imageFileName.toString());
			out = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageFileName.toString();
	}

	private String fromInt(int val) {
		return String.valueOf(val);
	}

	/*
	 * invoke the system's media scanner to add your photo to the Media
	 * Provider's database, making it available in the Android Gallery
	 * application and to other apps.
	 */
	private void scanPhoto(String imageFileName) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(imageFileName);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		// this.cordova.getContext().sendBroadcast(mediaScanIntent); //this is
		// deprecated
		ShareImage.this.sendBroadcast(mediaScanIntent);
	}
	
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Do You want stay on this Activity")
		.setPositiveButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(ShareImage.this, CameraActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				ShareImage.this.finish();

			}
		})
		.setNegativeButton("yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {

					}
				}).show();
	}

}
