package com.instapunjabiapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList; 

import twitter4j.LoggerFactory;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;



public class FinalImage extends Activity implements OnTouchListener, OnGestureListener{

	
	int count;	
	
	private static final String TAG = "instapunjbiapp";
	// These matrices will be used to move and zoom image
			Matrix matrix=null;
			Matrix savedMatrix=null;				
			// We can be in one of these 3 states
			static final int NONE = 0;
			static final int DRAG = 1;
			static final int ZOOM = 2;

			private static final float MAX_ZOOM = 0;
			int mode = NONE;
			// Remember some things for zooming
			PointF start = new PointF();
			PointF mid = new PointF();
			float oldDist = 1f;
			private float[] lastEvent=null;
			private float d=0f;
			private float newRot=0f;
			
			private GestureDetectorCompat gestureScanner;
	ImageView myimg1;
	ImageView img4;
	ImageView img3;
	static Bitmap shareImageBitmap;
	Bitmap imageBitmap;
	ImageView img2;
	Button addfontbtn;
	Button emobtn;
	Button checkinbtn;
	Button morebtn;
	Button share;
	ImageButton tickButton;	
	LinearLayout btnsLayout;
	LinearLayout emoLayout;
	FrameLayout imagelayout;
	LinearLayout sidemorelayout;
	LinearLayout surfacelayout;
	boolean emodisplay=false;
	boolean sidemorelayoutdisplay=false;
	ListView morelistView;
	int image;
	static ArrayList<Integer> instaimages=new ArrayList<Integer>();
	static ArrayList<Integer> actvhrtimages=new ArrayList<Integer>();
	static ArrayList<Integer> smilyimages=new ArrayList<Integer>();
	static ArrayList<Integer> adultimages=new ArrayList<Integer>();
	static ArrayList<Integer> animalimages=new ArrayList<Integer>();
	static ArrayList<Integer> transprtimages=new ArrayList<Integer>();
	
	
	ArrayList<ImageView> images=new ArrayList<ImageView>();

	private Matrix savedMatrix2;
	
	static String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.final_image);
		
		matrix = new Matrix();
		savedMatrix = new Matrix();
		
		imagelayout=(FrameLayout) findViewById(R.id.imagelayout);
		addfontbtn=(Button) findViewById(R.id.addfontbtn);
		emobtn=(Button) findViewById(R.id.emobtn);
		tickButton=(ImageButton) findViewById(R.id.tickButton1);
		checkinbtn=(Button) findViewById(R.id.checkinbtn);
		morebtn=(Button) findViewById(R.id.morebtn);
		btnsLayout=(LinearLayout) findViewById(R.id.btnslayout);
		emoLayout=(LinearLayout) findViewById(R.id.emolayout);
		emoLayout.setVisibility(View.GONE);
		surfacelayout=(LinearLayout) findViewById(R.id.surfacelayout);
		morelistView=(ListView) findViewById(R.id.morelistView);
		myimg1=(ImageView) findViewById(R.id.mainimg);
		imageBitmap=SavePicActivity.finalBitmap;
		myimg1.setImageBitmap(imageBitmap);
		
		gestureScanner=new GestureDetectorCompat(this,this);
																
		images.clear();
		
		instaimages.clear();// instapunjabi images1
		instaimages.add(R.drawable.aaaa);
		instaimages.add(R.drawable.aat);
		instaimages.add(R.drawable.amb);
		instaimages.add(R.drawable.angry);
		instaimages.add(R.drawable.averusiya);
		instaimages.add(R.drawable.balleballe);
		instaimages.add(R.drawable.bewkuf);
		instaimages.add(R.drawable.bezti);
		instaimages.add(R.drawable.burrraahhh);
		instaimages.add(R.drawable.chak);
		instaimages.add(R.drawable.chakjawana);
		instaimages.add(R.drawable.chaldefatte);
		instaimages.add(R.drawable.chu);
		instaimages.add(R.drawable.dafaho);
		instaimages.add(R.drawable.dove);
		instaimages.add(R.drawable.free);
		instaimages.add(R.drawable.fukre);
		instaimages.add(R.drawable.gal);
		instaimages.add(R.drawable.hala);
		instaimages.add(R.drawable.hass);
		instaimages.add(R.drawable.hat);
		instaimages.add(R.drawable.hatpiche);
		instaimages.add(R.drawable.haye);
		instaimages.add(R.drawable.ho);
		instaimages.add(R.drawable.hu);
		instaimages.add(R.drawable.hui);
		instaimages.add(R.drawable.iis);
		instaimages.add(R.drawable.ji);
		instaimages.add(R.drawable.jee);
		instaimages.add(R.drawable.jna);
		instaimages.add(R.drawable.karlo);
		instaimages.add(R.drawable.kha);
		instaimages.add(R.drawable.khasma);
		instaimages.add(R.drawable.ki);
		instaimages.add(R.drawable.kida);
		instaimages.add(R.drawable.kivesohnio);
		instaimages.add(R.drawable.la);
		instaimages.add(R.drawable.lala);
		instaimages.add(R.drawable.lo);
		instaimages.add(R.drawable.ma);
		instaimages.add(R.drawable.mar);
		instaimages.add(R.drawable.mera);
		instaimages.add(R.drawable.noakro);
		instaimages.add(R.drawable.oye);
		instaimages.add(R.drawable.paia);
		instaimages.add(R.drawable.pakke);
		instaimages.add(R.drawable.para);
		instaimages.add(R.drawable.phr);
		instaimages.add(R.drawable.qo);
		instaimages.add(R.drawable.ro);
		instaimages.add(R.drawable.sa);
		instaimages.add(R.drawable.sak);
		instaimages.add(R.drawable.tera);
		instaimages.add(R.drawable.teri);
		instaimages.add(R.drawable.vehle);
		instaimages.add(R.drawable.yaar);
		instaimages.add(R.drawable.yan);
		instaimages.add(R.drawable.yara);
		instaimages.add(R.drawable.yaradatashan);
		instaimages.add(R.drawable.sawwaad);
		instaimages.add(R.drawable.puriii);	
        
		actvhrtimages.clear();// active folder images2
		actvhrtimages.add(R.drawable.aa);
		actvhrtimages.add(R.drawable.ab);
		actvhrtimages.add(R.drawable.ac);
		actvhrtimages.add(R.drawable.ad);
		actvhrtimages.add(R.drawable.ae);
		actvhrtimages.add(R.drawable.aa);
		
		smilyimages.clear();// smily Images3
		smilyimages.add(R.drawable.ap);
		smilyimages.add(R.drawable.aq);
		smilyimages.add(R.drawable.ar);
		smilyimages.add(R.drawable.as);
		smilyimages.add(R.drawable.at);
		smilyimages.add(R.drawable.ap);
		
		adultimages.clear();// adult images4
		adultimages.add(R.drawable.af);
		adultimages.add(R.drawable.ag);
		adultimages.add(R.drawable.ah);
		adultimages.add(R.drawable.ai);
		adultimages.add(R.drawable.aj);
		adultimages.add(R.drawable.af);
		
		animalimages.clear();// animal images5
		animalimages.add(R.drawable.ak);
		animalimages.add(R.drawable.al);
		animalimages.add(R.drawable.am);
		animalimages.add(R.drawable.an);
		animalimages.add(R.drawable.ao);
		animalimages.add(R.drawable.ak);
		
		transprtimages.clear();// transport images6
		transprtimages.add(R.drawable.car);
		transprtimages.add(R.drawable.av);
		transprtimages.add(R.drawable.aw);
		transprtimages.add(R.drawable.ax);
		transprtimages.add(R.drawable.ay);
		transprtimages.add(R.drawable.au);
		
        
	GridView gridView1=(GridView) findViewById(R.id.gridView1);
	gridView1.setAdapter(new InstaEmoAdapter(this));
	gridView1.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
				
			img2=new ImageView(FinalImage.this);		
			img2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img2.setScaleType(ScaleType.CENTER);
			img2.setImageResource(instaimages.get(position));
			imagelayout.addView(img2); 
			img2.setOnTouchListener(FinalImage.this);
			if(emodisplay == true){
				emoLayout.setVisibility(View.GONE);
				btnsLayout.setVisibility(View.VISIBLE);
				emodisplay=false;
				}
			
		}
	});
	
	GridView gridView2=(GridView) findViewById(R.id.gridView2);
	gridView2.setAdapter(new ActiveHrtImageAdapter(this));
	gridView2.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			img2=new ImageView(FinalImage.this);
			img2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img2.setScaleType(ScaleType.CENTER);
			img2.setImageResource(actvhrtimages.get(position));
			imagelayout.addView(img2); 
			img2.setOnTouchListener(FinalImage.this);
			if(emodisplay == true){
				emoLayout.setVisibility(View.GONE);
				btnsLayout.setVisibility(View.VISIBLE);
				emodisplay=false;
				}
			
		}
	});
	
	GridView gridView3=(GridView) findViewById(R.id.gridView3);
	gridView3.setAdapter(new SmilyImageAdapter(this));
	gridView3.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			img2=new ImageView(FinalImage.this);
			img2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img2.setScaleType(ScaleType.CENTER);			
			img2.setImageResource(smilyimages.get(position));
			imagelayout.addView(img2); 
			img2.setOnTouchListener(FinalImage.this);
			if(emodisplay == true){
				emoLayout.setVisibility(View.GONE);
				btnsLayout.setVisibility(View.VISIBLE);
				emodisplay=false;
				}
			
		}
	});

	
	
	GridView gridView4=(GridView) findViewById(R.id.gridView4);
	gridView4.setAdapter(new AdultImagesAdapter(this));
	gridView4.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			img2=new ImageView(FinalImage.this);
			img2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img2.setScaleType(ScaleType.CENTER);
			img2.setImageResource(adultimages.get(position));
			imagelayout.addView(img2); 
			img2.setOnTouchListener(FinalImage.this);
			if(emodisplay == true){
				emoLayout.setVisibility(View.GONE);
				btnsLayout.setVisibility(View.VISIBLE);
				emodisplay=false;
				}
			
		}
	});
	
	GridView gridView5=(GridView) findViewById(R.id.gridView5);
	gridView5.setAdapter(new AnimalImagesAdapter(this));
	gridView5.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			img2=new ImageView(FinalImage.this);
			img2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img2.setScaleType(ScaleType.CENTER);			
			img2.setImageResource(animalimages.get(position));
			imagelayout.addView(img2); 
			img2.setOnTouchListener(FinalImage.this);
			if(emodisplay == true){
				emoLayout.setVisibility(View.GONE);
				btnsLayout.setVisibility(View.VISIBLE);
				emodisplay=false;
				}
			
		}
	});
	
	GridView gridView6=(GridView) findViewById(R.id.gridView6);
	gridView6.setAdapter(new TransportImagesAdapter(this));
	gridView6.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			img2=new ImageView(FinalImage.this);
			img2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img2.setScaleType(ScaleType.CENTER);			
			img2.setImageResource(transprtimages.get(position));
			imagelayout.addView(img2);
			img2.setOnTouchListener(FinalImage.this);
			if(emodisplay == true){
				emoLayout.setVisibility(View.GONE);
				btnsLayout.setVisibility(View.VISIBLE);
				emodisplay=false;
				}
			
		}
	});
			
	   TabHost tabs = (TabHost)findViewById(R.id.tabhost);
	    tabs.setup();

	    TabHost.TabSpec spec = tabs.newTabSpec("tag1");
	    spec.setContent(R.id.tab1);
	    spec.setIndicator("",getResources().getDrawable(R.drawable.hatpiche));
	    tabs.addTab(spec);

	    spec= tabs.newTabSpec("tag2");
	    spec.setContent(R.id.tab2);
	    spec.setIndicator("",getResources().getDrawable(R.drawable.aa));
	    tabs.addTab(spec);

	    spec=tabs.newTabSpec("tag3");
	    spec.setContent(R.id.tab3);
	    spec.setIndicator("",getResources().getDrawable(R.drawable.ap));
	    tabs.addTab(spec);
	    
	    spec=tabs.newTabSpec("tag4");
	    spec.setContent(R.id.tab4);
	    spec.setIndicator("",getResources().getDrawable(R.drawable.af));
	    tabs.addTab(spec);
	    
	    spec=tabs.newTabSpec("tag5");
	    spec.setContent(R.id.tab5);
	    spec.setIndicator("",getResources().getDrawable(R.drawable.ak));
	    tabs.addTab(spec);
	    
	    spec=tabs.newTabSpec("tag6");
	    spec.setContent(R.id.tab6);
	    spec.setIndicator("",getResources().getDrawable(R.drawable.car));
	    tabs.addTab(spec);
		
		addfontbtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(FinalImage.this, AddFont.class);
				startActivity(i);
//				finish();
			}
		});			
		emobtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(emodisplay == false){
				emoLayout.setVisibility(View.VISIBLE);
				btnsLayout.setVisibility(View.GONE);
				emodisplay=true;
				}
			}
		});	
		
		tickButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(emodisplay == true){
					emoLayout.setVisibility(View.GONE);
					btnsLayout.setVisibility(View.VISIBLE);
					emodisplay=false;
				}
				
			}
		});
		
		checkinbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(FinalImage.this, CheckIn.class);
				startActivity(i);
			}
		});
		
		
		morebtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(FinalImage.this, MoreActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
		
		share=(Button) findViewById(R.id.sharebtn);
		share.setOnClickListener(new OnClickListener() {						
			@Override
			public void onClick(View v) {
				    View v1 =findViewById(R.id.imagelayout);
	                v1.setDrawingCacheEnabled(true);
	                shareImageBitmap = v1.getDrawingCache();
	                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	                shareImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	         
	         
	                File f = new File(Environment.getExternalStorageDirectory()
	                                        + File.separator + "MYSHAREIMAGE.jpg");
	                path=Environment.getExternalStorageDirectory()
                            + File.separator + "MYSHAREIMAGE.jpg";
	                Log.v("path of image",""+path);
	                try {
	                    f.createNewFile();
	                    FileOutputStream fo = new FileOutputStream(f);
	                    fo.write(bytes.toByteArray());
	                    fo.close();
	               } catch (Exception e) {
	                   // TODO Auto-generated catch block
	                   e.printStackTrace();
	               }
				Intent i=new Intent(FinalImage.this, ShareImage.class);
				startActivity(i);
				
			}
		});
	
	}//oncreate
	
	@Override
	protected void onResume() {	
	
		Log.d(TAG, "Checkin Bitmap...."+CheckIn.listtxtBitmap);
		Log.d(TAG, "AddFont Bitmap"+AddFont.txtBitmap);
		
		if(CheckIn.listtxtBitmap != null)
		{
			img2=new ImageView(FinalImage.this);
			img2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img2.setScaleType(ScaleType.CENTER);;// for accurate size of Image.
			img2.setImageBitmap(CheckIn.listtxtBitmap);
			imagelayout.addView(img2);
			img2.setOnTouchListener(this);
			
		}
		else if(AddFont.txtBitmap != null)
		{
			img2=new ImageView(FinalImage.this);
			img2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			img2.setScaleType(ScaleType.CENTER);;// for accurate size of Image.
			img2.setImageBitmap(AddFont.txtBitmap);
			imagelayout.addView(img2);
			img2.setOnTouchListener(this);
			
		}
		
		super.onResume();
	}
								
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "save");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(TAG, "Restore");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	     
		// TODO Auto-generated method stub
		ImageView view = (ImageView) v;
		view.setScaleType(ImageView.ScaleType.MATRIX);
		float scale;  

		// Dump touch event to log
		dumpEvent(event);

		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: // first finger down only
			
     // To set the ImageView at Its Own place. Without this important line New ImageView will Gain the		
    //	Size/Rotation/Position of last ImageView that we set before.				
		    matrix.set(view.getImageMatrix());// This Line is very important. 
			
		    savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			// Log.d(TAG, "mode=DRAG" );
			mode = DRAG;
			lastEvent=null;
			break;

		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
			}
			lastEvent = new float[4];
			lastEvent[0] = event.getX(0);
			lastEvent[1] = event.getX(1);
			lastEvent[2] = event.getY(0);
			lastEvent[3] = event.getY(1);
			d = rotation(event);
			break;

		case MotionEvent.ACTION_UP: // first finger lifted
		case MotionEvent.ACTION_POINTER_UP: // second finger lifted
			mode = NONE;
			lastEvent=null;
			// Log.d(TAG, "mode=NONE" );
			break;

		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				// ...
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);
			} else if (mode == ZOOM  && event.getPointerCount() == 2) {
				float newDist = spacing(event);
				matrix.set(savedMatrix);
				if (newDist > 10f) {
					scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
				if (lastEvent != null) {
					newRot = rotation(event);
					float r = newRot - d;
					matrix.postRotate(r, view.getMeasuredWidth() / 2,
							view.getMeasuredHeight() / 2);
				}
				
			}
			break;

		}
		// Perform the transformation
		view.setImageMatrix(matrix);

		//return true; // indicate event was handled
		return gestureScanner.onTouchEvent(event);
		

	}

	

	

	private float rotation(MotionEvent event) {
		double delta_x = (event.getX(0) - event.getX(1));
		double delta_y = (event.getY(0) - event.getY(1));
		double radians = Math.atan2(delta_y, delta_x);

		return (float) Math.toDegrees(radians);
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
		

	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);

	}

	/** Show an event in the LogCat view, for debugging */

	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
				"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}

		sb.append("[");

		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())

				sb.append(";");
		}
		sb.append("]");
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		img2.setVisibility(View.GONE);
		
		Toast.makeText(FinalImage.this, "Remove", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void onPause() {
		CheckIn.listtxtBitmap=null;
		AddFont.txtBitmap=null;
		super.onPause();
	}

}//class