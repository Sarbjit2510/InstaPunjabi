package com.instapunjabiapp;





import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.Ragnarok.BitmapFilter;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SavePicActivity extends Activity implements OnTouchListener, OnClickListener{

	private static final String TAG = "instapunjbiapp";
	    ImageView imageView;	  	   		
		// These matrices will be used to move and zoom image
		Matrix matrix = new Matrix();
		Matrix savedMatrix = new Matrix();				
		// We can be in one of these 3 states
		static final int NONE = 0;
		static final int DRAG = 1;
		static final int ZOOM = 2;
		int mode = NONE;
		// Remember some things for zooming
		PointF start = new PointF();
		PointF mid = new PointF();
		float oldDist = 1f;
		private float[] lastEvent;
		private float d;
		private float newRot;
		Bitmap gallerybitmap = null;
	    BitmapFactory.Options options = new BitmapFactory.Options();
		private int rotationAngle=0;
		private ExifInterface ei;
		Button rotatebtn;
		Button filterbtn;
		Button brightnessbtn;
		Button cropbtn;
		ImageButton tickbutton;
		ImageButton tickbutton1;
		Button next;
		Bitmap bigBitmap;
		Bitmap filterBitmap;
		Bitmap cropBitmap;
		Bitmap brightnessBitmap;
		static Bitmap finalBitmap;
		boolean isFilterShown=false;
		boolean isBarShown=false;
		int brightnessValue;
		HorizontalScrollView scrollView;
		LinearLayout scrollayout;
		LinearLayout layout;
		LinearLayout seekbarlayout;
		SeekBar bar;
		ImageView img;
		int imgid; 
		int effects;
		File f;
		final int PIC_CROP = 1;
		String galleryimagepath="";
		public static String[] styles = {"Gray Scale", "Relief", "Average Blur", "Oil Painting", "Neon", "Pixelate", "Old TV", 
            "Invert Color", "Block", "Aged photo", "Sharpen", "Light", "Lomo", "HDR", "Gaussian Blur",
            "Soft Glow", "Sketch", "Motion Blur", "Gotham","None"};
		ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_save_pic);
		imageView=(ImageView) findViewById(R.id.imageView1);
		imageView.setOnTouchListener(this);
		scrollayout=(LinearLayout) findViewById(R.id.mainscrolllayout );
	    scrollayout.setVisibility(View.GONE);
	    seekbarlayout=(LinearLayout) findViewById(R.id.seeklayout);
	    seekbarlayout.setVisibility(View.GONE);
	    bar=(SeekBar) findViewById(R.id.seekBar1);	    
	    rotatebtn=(Button) findViewById(R.id.rotatetbtn);	   
	    filterbtn=(Button) findViewById(R.id.filterbtn);
	    tickbutton=(ImageButton) findViewById(R.id.tickButton);
	    tickbutton1=(ImageButton) findViewById(R.id.tickButton1);
	    cropbtn=(Button) findViewById(R.id.cropbtn);
	    brightnessbtn=(Button) findViewById(R.id.brightnessbtn); 
	    next=(Button) findViewById(R.id.nextbtn);
	    
	   
	    galleryimagepath=getIntent().getStringExtra("path");
//	    matrix.postRotate(90F);
	    options.inSampleSize = 5;
//		Bitmap cambitmap = null;
//		try {
//			byte[] byteArray = getIntent().getByteArrayExtra("data");
//			//Log.i(TAG, "...."+ byteArray);
//			// BitmapFactory.Options options = new BitmapFactory.Options();
//			 
//			cambitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//			// to make image in portrait mode.
//			
//			cambitmap = Bitmap.createBitmap(cambitmap, 0, 0, cambitmap.getWidth(), cambitmap.getHeight(), matrix, true);
//		  
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
							
	    
	    if(("").equals(galleryimagepath)){
	    	
	    }
	    else{	
		try {
			 
			//Log.i(TAG, "...."+ galleryimagepath);
			// to scale the image size and to avoid outofmemory error.
//			 BitmapFactory.Options options = new BitmapFactory.Options();
//			  options.inSampleSize = 8; 
			gallerybitmap = BitmapFactory.decodeFile(galleryimagepath,options);         
                    
                    ei = new ExifInterface(galleryimagepath);
            
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch(orientation) {
           case ExifInterface.ORIENTATION_ROTATE_90:
            rotationAngle = 90;
           break;
           case ExifInterface.ORIENTATION_ROTATE_180:
            rotationAngle=180;
           break;
        
            case ExifInterface.ORIENTATION_ROTATE_270:
             rotationAngle=270;
            break;                   
}                     
               matrix.setRotate(rotationAngle);                  
			// to make image in portrait mode.
			gallerybitmap = Bitmap.createBitmap(gallerybitmap, 0, 0,options.outWidth, options.outHeight, matrix, true);
//						gallerybitmap = Bitmap.createBitmap(gallerybitmap, 0, 0, gallerybitmap.getWidth(), gallerybitmap.getHeight(), matrix, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    }    	    
		if(gallerybitmap!=null){
		imageView.setImageBitmap(gallerybitmap);		
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);	
		bigBitmap=gallerybitmap;
		//Log.d(TAG, "gallleryyyy...."+bigBitmap);
		}
		else{			
			imageView.setImageBitmap(CameraActivity.cambitmap);
//			matrix.postRotate(270F);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);	
			bigBitmap=CameraActivity.cambitmap;
			//Log.d(TAG, "camBitmap...."+bigBitmap);
		}
		finalBitmap=bigBitmap; 
		// Log.d(TAG, "Finalbitmap...."+finalBitmap);
       layout=(LinearLayout) findViewById(R.id.editscrllyout);	
	    for (int i = 0; i < styles.length; i++) {
	    	LinearLayout linear=new LinearLayout(this);
	    	linear.setOrientation(LinearLayout.VERTICAL);	    	
	    	img=new ImageView(this);
	    	effects=i+1;
			img.setImageBitmap(applyStyle(effects, Bitmap.createScaledBitmap(bigBitmap, 90, 90 , false)));
			TextView txt=new TextView(this);
			txt.setText(styles[i]);
			linear.addView(img);
			linear.addView(txt);
			txt.setGravity(Gravity.CENTER);
			layout.addView(linear);
			img.setOnClickListener(this);
			img.setId(effects);
			imgid=img.getId();
//			Log.d(TAG, "IMAGE ID.........."+imgid);
			
	    }
		
		rotatebtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				rotateImage();			
			}			
		});
		
		filterbtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showFilter();
				
			}
		});
		
        tickbutton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(isFilterShown == true){
					
					if(filterBitmap!=null){
						finalBitmap=filterBitmap;
						imageView.setImageBitmap(finalBitmap);
						//Log.v("filterBitmap", "filterBitmap="+filterBitmap);
						filterBitmap=null;
						
					}
					
					else{
//						Log.v("elseee", "elseee");
					}
					scrollayout.setVisibility(View.GONE);
					isFilterShown=false;
				}
			}
		});
        
        cropbtn.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				FileOutputStream fo=null;

				//you can create a new file name "test.jpg" in sdcard folder.
				
				f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"test.jpg");
				Log.e(TAG, "PIcUri..."+f);
				try {
//					f.createNewFile();
				
				//write the bytes in file
				fo = new FileOutputStream(f);
				fo.write(bytes.toByteArray());

				// remember close de FileOutput
				
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(fo != null){
						try {
							fo.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				performCrop(Uri.fromFile(f));
				Log.e(TAG, "PIcUri..."+Uri.fromFile(f));

			}
			
		});
		
		brightnessbtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showBrightnessBar();				
			}
		});
		
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {			
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {				
			}			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {							
			}			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				 brightnessValue = progress;
				 if(finalBitmap !=null){
					 Bitmap imagbitmap=finalBitmap;
					 brightnessBitmap=null;
					 brightnessBitmap=processingBitmap_Brightness(imagbitmap,brightnessValue);
					 imagbitmap=null;					
					 imageView.setImageBitmap(brightnessBitmap);					 
				 }
				 }
		});	
		
		tickbutton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(brightnessBitmap!=(null)){
					finalBitmap=brightnessBitmap;
					imageView.setImageBitmap(finalBitmap);
					brightnessBitmap=null;
					 brightnessValue=0;
				}
				
				else{
//					Log.v("brightnessBitmap null", "brightnessBitmap null");
				}
				seekbarlayout.setVisibility(View.GONE);
				isBarShown=false;
				
			}
		});
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(finalBitmap != null){
				Intent i=new Intent(SavePicActivity.this, FinalImage.class);
				startActivity(i);
				}
			}
		});
	}//oncreate
	
	// for Croping Image Code......
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

		
	    if (requestCode == PIC_CROP) {
	        if (data != null) {
	            // get the returned data	        	
	            Bundle extras = data.getExtras();
	            Log.d(TAG, "extra....."+extras);
	            // get the cropped bitmap
	            if(finalBitmap != null){
	            finalBitmap= extras.getParcelable("data");
	            imageView.setImageBitmap(finalBitmap);
	            }
	        }
	    }
	    if(f.exists()){
			f.delete();
			Log.d(TAG, "file deleted");
		}

	}
	
	private void performCrop(Uri picUri) {
	    try {

	        Intent cropIntent = new Intent("com.android.camera.action.CROP");
	        // indicate image type and Uri
	        cropIntent.setDataAndType(picUri, "image/*");
	        // set crop properties
	        cropIntent.putExtra("crop", "true");
	        // indicate aspect of desired crop
	        cropIntent.putExtra("aspectX", 1);
	        cropIntent.putExtra("aspectY", 1);
	        // indicate output X and Y
	        cropIntent.putExtra("outputX", 256);
	        cropIntent.putExtra("outputY", 256);
	        // retrieve data on return
	        cropIntent.putExtra("return-data", true);
	        // start the activity - we handle returning in onActivityResult
	        startActivityForResult(cropIntent, PIC_CROP);
	    }
	    // respond to users whose devices do not support the crop action
	    catch (ActivityNotFoundException anfe) {
	        // display an error message
	        String errorMessage = "Whoops - your device doesn't support the crop action!";
	        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
	        toast.show();
	    }
	}
	
	// Use This Code for fast movemwnt of seekbar for Brightness....
	private Bitmap processingBitmap_Brightness(Bitmap src, int brightnessValue){
		Bitmap alteredBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
		Canvas canvas = new Canvas(alteredBitmap);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		float b = brightnessValue;  // I know seems redundant, but used for possible scaling if needed
		cm.set(new float[] {    1, 0, 0, 0, b,
		                        0, 1, 0, 0, b,
		                        0, 0, 1, 0, b,
		                        0, 0, 0, 1, 0});

		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		Matrix matrix = new Matrix();
		canvas.drawBitmap(src, matrix, paint);
		return alteredBitmap;
	}
	
	
	// Move, Rotate, Zoom Picture...
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		   ImageView view = (ImageView) v;
		   view.setScaleType(ImageView.ScaleType.MATRIX);
		   float scale;		 
		
		   // Dump touch event to log
		   dumpEvent(event);

		   // Handle touch events here...
		   switch (event.getAction() & MotionEvent.ACTION_MASK) {
		   case MotionEvent.ACTION_DOWN: //first finger down only
		      savedMatrix.set(matrix);
		      start.set(event.getX(), event.getY());
//		      Log.d(TAG, "mode=DRAG" );
		      mode = DRAG;
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
		      
		   case MotionEvent.ACTION_UP: //first finger lifted
		   case MotionEvent.ACTION_POINTER_UP: //second finger lifted
		      mode = NONE;
//		      Log.d(TAG, "mode=NONE" );
		      break;

		 
		   case MotionEvent.ACTION_MOVE:
            if (mode == DRAG) {
                // ...
                matrix.set(savedMatrix);
                matrix.postTranslate(event.getX() - start.x, event.getY()
                        - start.y);
            } else if (mode == ZOOM && event.getPointerCount() == 2) {
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
		   
		   return true; // indicate event was handled

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
		   point.set(x/2, y/2);

		}

		 
		/** Show an event in the LogCat view, for debugging */

		private void dumpEvent(MotionEvent event) {
		   String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
		      "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
		   StringBuilder sb = new StringBuilder();
		   int action = event.getAction();
		   int actionCode = action & MotionEvent.ACTION_MASK;
		   sb.append("event ACTION_" ).append(names[actionCode]);
		   if (actionCode == MotionEvent.ACTION_POINTER_DOWN
		         || actionCode == MotionEvent.ACTION_POINTER_UP) {
		      sb.append("(pid " ).append(
		      action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
	          sb.append(")" );
		   }
		 
		   sb.append("[" );

		   for (int i = 0; i < event.getPointerCount(); i++) {
		      sb.append("#" ).append(i);
		      sb.append("(pid " ).append(event.getPointerId(i));
		      sb.append(")=" ).append((int) event.getX(i));
		      sb.append("," ).append((int) event.getY(i));
		      if (i + 1 < event.getPointerCount())

		         sb.append(";" );
		   }
		   
		   sb.append("]" );
//		   Log.d(TAG, sb.toString());

		}										
		
		
		// for apply filters
		private Bitmap applyStyle(int styleNo,Bitmap originBitmap) {
			Bitmap changeBitmap;
			switch (styleNo) {
			case BitmapFilter.AVERAGE_BLUR_STYLE:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.AVERAGE_BLUR_STYLE, 5); // maskSize, must odd
				break;
			case BitmapFilter.GAUSSIAN_BLUR_STYLE:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.GAUSSIAN_BLUR_STYLE, 1.2); // sigma
				break;
			case BitmapFilter.SOFT_GLOW_STYLE:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.SOFT_GLOW_STYLE, 0.6);
				break;
			case BitmapFilter.LIGHT_STYLE:
				int width = originBitmap.getWidth();
				int height = originBitmap.getHeight();
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.LIGHT_STYLE, width / 3, height / 2, width / 2);
				break;
			case BitmapFilter.LOMO_STYLE:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.LOMO_STYLE, 
						(originBitmap.getWidth() / 2.0) * 95 / 100.0);
				break;
			case BitmapFilter.NEON_STYLE:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.NEON_STYLE, 200, 100, 50);
				break;
			case BitmapFilter.PIXELATE_STYLE:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.PIXELATE_STYLE, 10);
				break;
			case BitmapFilter.MOTION_BLUR_STYLE:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.MOTION_BLUR_STYLE, 10, 1);
				break;
			case BitmapFilter.OIL_STYLE:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, BitmapFilter.OIL_STYLE, 5);
				break;
			default:
				changeBitmap = BitmapFilter.changeStyle(originBitmap, styleNo);
				break;
			}
			return changeBitmap;
		}
		 
		@Override
		public void onClick(View v) {
			
			if(finalBitmap != null){
				filterBitmap=null;
				Bitmap imagebitmap=finalBitmap;
				imgid=v.getId();
				//Log.d(TAG, "IMAGE ID.........."+imgid);
				switch (imgid) {
				default:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, imgid);
					imagebitmap=null;										
					break;
				case BitmapFilter.AVERAGE_BLUR_STYLE:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.AVERAGE_BLUR_STYLE, 5); // maskSize, must odd
					imagebitmap=null;
					break;
				case BitmapFilter.GAUSSIAN_BLUR_STYLE:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.GAUSSIAN_BLUR_STYLE, 1.2); // sigma
					imagebitmap=null;
					break;
				case BitmapFilter.SOFT_GLOW_STYLE:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.SOFT_GLOW_STYLE, 0.6);
					imagebitmap=null;
					break;
				case BitmapFilter.LIGHT_STYLE:
					imagebitmap=finalBitmap;
					int width = imagebitmap.getWidth();
					int height = imagebitmap .getHeight();
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.LIGHT_STYLE, width / 3, height / 2, width / 2);
					imagebitmap=null;
					break;
				case BitmapFilter.LOMO_STYLE:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.LOMO_STYLE, 
							(bigBitmap.getWidth() / 2.0) * 95 / 100.0);
					imagebitmap=null;
					break;
				case BitmapFilter.NEON_STYLE:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.NEON_STYLE, 200, 100, 50);
					imagebitmap=null;
					
					break;
				case BitmapFilter.PIXELATE_STYLE:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.PIXELATE_STYLE, 10);
					imagebitmap=null;
					break;
				case BitmapFilter.MOTION_BLUR_STYLE:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.MOTION_BLUR_STYLE, 10, 1);
					imagebitmap=null;
					break;
				case BitmapFilter.OIL_STYLE:
					imagebitmap=finalBitmap;
					filterBitmap=null;
					filterBitmap = BitmapFilter.changeStyle(imagebitmap, BitmapFilter.OIL_STYLE, 5);
					imagebitmap=null;
					break;
				
				}
				imageView.setImageBitmap(filterBitmap);
				
				}			
		}								
		public void rotateImage(){
			Matrix matrix=new Matrix();
			 if(finalBitmap != null){
					matrix.postRotate(90);
					finalBitmap = Bitmap.createBitmap(finalBitmap , 0, 0,finalBitmap.getWidth(),finalBitmap.getHeight(), matrix, true);
//					finalBitmap=scaledBitmap;
					imageView.setImageBitmap(finalBitmap);
					}		   	    			
		  }						
		public void showFilter(){
			seekbarlayout.setVisibility(View.GONE);
			isBarShown=false;			
			if(isFilterShown==false){
			scrollayout.setVisibility(View.VISIBLE);
			isFilterShown=true;
			}
		}
		
	  public void showBrightnessBar(){
		  scrollayout.setVisibility(View.GONE);
			isFilterShown=false;
			if(isBarShown == false){
			seekbarlayout.setVisibility(View.VISIBLE);
			isBarShown=true;
			}
	  }
	  
	  @Override
	public void onBackPressed() {
		  ProgressDialog dialog=new ProgressDialog(this);
		  dialog.setMessage("Please wait......");
		  dialog.show();
		     super.onBackPressed();		     		 
	}
		
}//class
	
	

	


