package com.instapunjabiapp;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class AddFont extends Activity implements OnItemClickListener, OnClickListener {

	private static final String TAG = "instapunjbiapp";
	EditText typetxt;
	Button keybrdbtn;
	Button txtstylebtn;
	Button tickbtn;
	LinearLayout layout;
	ListView txtstylist;
	ImageView img;
	int myimgid;
	Typeface type;
	String color="";
	String txtdata="";
	public static Bitmap txtBitmap=null;
	LinearLayout scrollLayout;
	int[] colors=new int[]{R.drawable.gray1,R.drawable.blue1,R.drawable.aquamarine1,
			R.drawable.cornsilk1,R.drawable.brown,R.drawable.burlywood,R.drawable.red,R.drawable.coral
			,R.drawable.cyan1,R.drawable.gold1,R.drawable.chartreuse1,R.drawable.chocolate,R.drawable.cyan4
			,R.drawable.chartreuse4,R.drawable.chocolate1,R.drawable.maroon,R.drawable.blue4
			,R.drawable.gray69,R.drawable.brown4,R.drawable.bisque4,R.drawable.firebrick1
			,R.drawable.white};
	
	public static ArrayList<Integer> images=new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.add_font);
		
		scrollLayout=(LinearLayout) findViewById(R.id.scrolllayout);
		for (int i = 0; i < colors.length; i++) {
			ImageView myimg=new ImageView(this);
			myimg.setBackgroundResource(colors[i]);
			   scrollLayout.addView(myimg);
			   myimg.setId(i);
			   myimgid=myimg.getId();
			   myimg.setOnClickListener(this);		   
		}
		
		layout=(LinearLayout) findViewById(R.id.mylayout);
		layout.setVisibility(View.GONE);
		typetxt=(EditText) findViewById(R.id.typetxt);		
		showSoftKeyboard(typetxt);
		
		keybrdbtn=(Button) findViewById(R.id.keybrdbtn);		
		txtstylebtn=(Button) findViewById(R.id.textstylebtn);
		tickbtn=(Button) findViewById(R.id.tickbtn);
		
		images.clear();	
		images.add(R.drawable.alberttxtbold);
		images.add(R.drawable.allheart);
		images.add(R.drawable.angeltear);
		images.add(R.drawable.arabdance);
		images.add(R.drawable.arcade);
		images.add(R.drawable.arialblack);
		images.add(R.drawable.billionstar);
		images.add(R.drawable.blazed);
		images.add(R.drawable.dampfplatz);
		images.add(R.drawable.kinkee);
		images.add(R.drawable.nervous);
		images.add(R.drawable.timesnewroman);
		
		
				
		keybrdbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		txtstylebtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				layout.setVisibility(View.VISIBLE);	
				txtstylist=(ListView) findViewById(R.id.txtstyllist);
				txtstylist.setAdapter(new FontAdapter(AddFont.this));
				txtstylist.setOnItemClickListener(AddFont.this);
			}
		});
		
		tickbtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				txtdata=typetxt.getText().toString();
				if(txtdata.equals("")){
					Toast.makeText(AddFont.this, "Please Enter Text First", Toast.LENGTH_SHORT).show();
	 	        }else if(txtdata.equals(txtdata) && color.equals("")){					
					String color1="#030303";
					txtBitmap=textAsBitmap(txtdata, 40,type,color1);
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					txtBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
					finish();
		 	        }
				else if(txtdata.equals(txtdata) && color.equals(color)){
				txtBitmap=textAsBitmap(txtdata, 40,type,color); 
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				txtBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
 				finish();
	 	        }
			}
		});
		
	}// oncreate

	// show keyboard on tap of Edittext...
	public void showSoftKeyboard(View view) {
	    if (view.requestFocus()) {
	        InputMethodManager imm = (InputMethodManager)
	                getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	    }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//Toast.makeText(this, "it is my case0 "+position, Toast.LENGTH_LONG).show();
		switch (position) {
		case 0:			
			type=Typeface.createFromAsset(getAssets(),"AlbertText-Bold.ttf");
			typetxt.setTypeface(type);
			break;
		case 1:
			type=Typeface.createFromAsset(getAssets(),"ALLHEART.TTF");
			typetxt.setTypeface(type);
			break;			
		case 2:
			type=Typeface.createFromAsset(getAssets(),"ANGEL___.otf");
			typetxt.setTypeface(type);
			break;		
		case 3:
			type=Typeface.createFromAsset(getAssets(),"ArabDances.ttf");
			typetxt.setTypeface(type);
			break;			
		case 4:
			type=Typeface.createFromAsset(getAssets(),"Arcade.ttf");
			typetxt.setTypeface(type);
			break;			
		case 5:
			type=Typeface.createFromAsset(getAssets(),"arial.ttf");
			typetxt.setTypeface(type);
			break;
		case 6:
			type=Typeface.createFromAsset(getAssets(),"BillionStars_PersonalUse.ttf");
			typetxt.setTypeface(type);
			break;
		case 7:
			type=Typeface.createFromAsset(getAssets(),"Blazed.ttf");
			typetxt.setTypeface(type);
			break;
		case 8:
			type=Typeface.createFromAsset(getAssets(),"DampfPlatzsh.ttf");
			typetxt.setTypeface(type);
			break;
		case 9:
			type=Typeface.createFromAsset(getAssets(),"KINKEE__.TTF");
			typetxt.setTypeface(type);
			break;
		case 10:
			type=Typeface.createFromAsset(getAssets(),"Nervous.ttf");
			typetxt.setTypeface(type);
			break;
		case 11:
			type=Typeface.createFromAsset(getAssets(),"times.ttf");
			typetxt.setTypeface(type);
			break;
		default:
			break;
		}				
	}
	
	@Override
	public void onClick(View v) {
        myimgid=v.getId();		
		switch (myimgid) {
		case 0:
			typetxt.setTextColor(Color.parseColor("#030303"));
			color="#030303";
			break;
		case 1:
			typetxt.setTextColor(Color.parseColor("#0000ff"));
			color="#0000ff";
			break;
		case 2:
			typetxt.setTextColor(Color.parseColor("#7fffd4"));
			color="#7fffd4";
			break;
		case 3:
			typetxt.setTextColor(Color.parseColor("#fff8dc"));
			color="#fff8dc";
			break;
		case 4:
			typetxt.setTextColor(Color.parseColor("#a52a2a"));
			color="#a52a2a";
			break;
		case 5:
			typetxt.setTextColor(Color.parseColor("#deb887"));
			color="#deb887";
			break;
		case 6:
			typetxt.setTextColor(Color.parseColor("#ff0000"));
			color="#ff0000";
			break;
		case 7:
			typetxt.setTextColor(Color.parseColor("#ff7f50"));
			color="#ff7f50";
			break;
		case 8:
			typetxt.setTextColor(Color.parseColor("#00ffff"));
			color="#00ffff";
			break;
		case 9:
			typetxt.setTextColor(Color.parseColor("#ffd700"));
			color="#ffd700";
			break;
		case 10:
			typetxt.setTextColor(Color.parseColor("#7fff00"));
			color="#7fff00";
			break;
		case 11:
			typetxt.setTextColor(Color.parseColor("#d2691e"));
			color="#d2691e";
			break;
		case 12:
			typetxt.setTextColor(Color.parseColor("#008b8b"));
			color="#008b8b";
			break;
		case 13:
			typetxt.setTextColor(Color.parseColor("#458b00"));
			color="#458b00";
			break;
		case 14:
			typetxt.setTextColor(Color.parseColor("#ff7f24"));
			color="#ff7f24";
			break;
		case 15:
			typetxt.setTextColor(Color.parseColor("#b03060"));
			color="#b03060";
			break;
		case 16:
			typetxt.setTextColor(Color.parseColor("#00008b"));
			color="#00008b";
			break;
		case 17:
			typetxt.setTextColor(Color.parseColor("#b0b0b0"));
			color="#b0b0b0";
			break;
		case 18:
			typetxt.setTextColor(Color.parseColor("#8b2323"));
			color="#8b2323";
			break;
		case 19:
			typetxt.setTextColor(Color.parseColor("#8b7d6b"));
			color="#8b7d6b";
			break;
		case 20:
			typetxt.setTextColor(Color.parseColor("#ff3030"));
			color="#ff3030";
			break;		
		case 21:
			typetxt.setTextColor(Color.parseColor("#ffffff"));
			color="#ffffff";
			break;
		default:
			break;
		}					
	}
	
	public Bitmap textAsBitmap(String text, float textSize,Typeface type, String color) {
	    Paint paint = new Paint();
	    paint.setTextSize(textSize);
	    paint.setTypeface(type);
	    paint.setColor(Color.parseColor(color));	    
	    paint.setTextAlign(Paint.Align.LEFT);
	    int width = (int) (paint.measureText(text) + 0.5f); // round
	    float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
	    int height = (int) (baseline + paint.descent() + 0.5f);
	    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(image);
	    canvas.drawText(text, 0, baseline, paint);
	    return image;
	}
	
	@Override
	public void onBackPressed() {
	
		super.onBackPressed();
		
	}
			
}//class
