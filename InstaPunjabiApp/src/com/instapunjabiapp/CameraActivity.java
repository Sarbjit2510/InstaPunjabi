package com.instapunjabiapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class CameraActivity extends Activity {

	private boolean isFlashOn=false;
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath="";
	private static final String TAG = "instapunjbiapp";
	private Preview preview;
	private SavePicActivity savePicActivity;
	private Camera camera;
	Activity act;
    Context ctx;
    SurfaceHolder surfaceHolder;
	int cameraId;
	private int cameraid;
	LinearLayout surfacelayout;
	LinearLayout mainLayout;
	Button capturebutton;
    Button gallerybtn; 
    Button flashbtn;
    Button changeCambtn;
	static public Bitmap cambitmap;
	private ProgressDialog dialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		act = this;
		AddFont.txtBitmap=null;
		CheckIn.listtxtBitmap=null;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.activity_main);			
	     preview = new Preview(this, (SurfaceView) findViewById(R.id.surfView1));	
		 surfacelayout=(LinearLayout) findViewById(R.id.surfacelayout);
		 surfacelayout.addView(preview);
		
	    capturebutton = (Button) findViewById(R.id.capturebtn);
	    changeCambtn=(Button) findViewById(R.id.changecamreabtn);
	    gallerybtn=(Button) findViewById(R.id.gallerybtn);
	    flashbtn=(Button) findViewById(R.id.flashbtn);
	   
		 capturebutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// preview.camera.takePicture(shutterCallback, rawCallback,
				// jpegCallback);
				dialog=new ProgressDialog(ctx);
				dialog.setMessage("please wait for a while...");
				dialog.show();		
				takeFocusedPicture();				
			}
		});
		 
		gallerybtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				 // in onCreate or any event where your want the user to
                // select a file
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);				
			}
		});
			   
		flashbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkFlash(ctx);		
			}
		});	
		
		changeCambtn.setOnClickListener(new OnClickListener() {						

			@Override
			public void onClick(View v) {
				checkCameraHardware(ctx);						      							
			}
		});
		
		
	}//oncreate
	
	// Availability of Front Camrea and to avoid force close.....
	private void checkCameraHardware(Context context){
		if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)){
			openFrontFacingCameraGingerbread();
		}
		else{
		    Toast.makeText(ctx, "Your Device has no Front Camera", Toast.LENGTH_SHORT).show();
		}						
	}
	// Availability of Flash and to avoid force close......
	private void checkFlash(Context context){
		if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
			Flashonoff();
		}
		else{
		    Toast.makeText(ctx, "Your Device has no Flash", Toast.LENGTH_SHORT).show();
		}						
	}
	
	 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (resultCode == RESULT_OK) {
	            if (requestCode == SELECT_PICTURE) {
	                Uri selectedImageUri = data.getData();
	                selectedImagePath = getPath(selectedImageUri);
	                Intent i=new Intent(CameraActivity.this,SavePicActivity.class);
	                i.putExtra("path", selectedImagePath);
	                i.putExtra("data", "");
	                startActivity(i);	                	                
	            }
	        }
	    }

	    /**
	     * helper to retrieve the path of an image URI
	     */
	    public String getPath(Uri uri) {
	            // just some safety built in 
	            if( uri == null ) {
	                // TODO perform some logging or show user feedback
	                return null;
	            }
	            // try to retrieve the image from the media store first
	            // this will only work for images selected from gallery
	            String[] projection = { MediaColumns.DATA };
	            Cursor cursor = managedQuery(uri, projection, null, null, null);
	            if( cursor != null ){
	                int column_index = cursor
	                .getColumnIndexOrThrow(MediaColumns.DATA);
	                cursor.moveToFirst();
	                return cursor.getString(column_index);
	            }
	            // this is our fallback here
	            return uri.getPath();
	    }

	
	    // make Camera in Portrait mode.
	    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
		public static void setCameraDisplayOrientation(Activity activity,
	             int cameraId, Camera camera) {
	             int result;
	           
	            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
	            if (currentapiVersion > android.os.Build.VERSION_CODES.FROYO){
	                // Do something for froyo and above versions
	                     android.hardware.Camera.CameraInfo info =
	                 new android.hardware.Camera.CameraInfo();
	         android.hardware.Camera.getCameraInfo(cameraId, info);
	         int rotation = activity.getWindowManager().getDefaultDisplay()
	     .getRotation();
	int degrees = 0;
	switch (rotation) {
	 case Surface.ROTATION_0: degrees = 0; break;
	 case Surface.ROTATION_90: degrees = 90; break;
	 case Surface.ROTATION_180: degrees = 180; break;
	 case Surface.ROTATION_270: degrees = 270; break;
	}


	if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	 result = (info.orientation + degrees) % 360;
	 result = (360 - result) % 360;  // compensate the mirror
	} else {  // back-facing
	 result = (info.orientation - degrees + 360) % 360;
	}
	            } else{
	                    result=90;
	                // do something for phones running an SDK before froyo
	            }
	       
	            
	         camera.setDisplayOrientation(result);
	     }
	   
	   
	@Override
	protected void onResume() {
		super.onResume();				
		surfaceHolder=preview.mHolder;
	    camera = Camera.open();	
	    try {    	
			camera.setPreviewDisplay(surfaceHolder);// for Camera preview working after phone lock...
	       
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    camera.startPreview();		
    	preview.setCamera(camera);	   		
		setCameraDisplayOrientation(act, cameraId, camera);
		cameraid=0;	
		if(isFlashOn == true)
		{
			Parameters parameters=camera.getParameters();
			parameters.setFlashMode(Parameters.FLASH_MODE_ON);
			camera.setParameters(parameters);
		}	
		
	}
	
	@Override
	protected void onPause() {
		
		if (camera != null) {
			camera.stopPreview();
			camera.setPreviewCallback(null);// for Camera preview working after phone lock...
			preview.setCamera(null);			
			camera.release();
			camera = null; 
		}
		super.onPause();
	}	

	ShutterCallback shutterCallback = new ShutterCallback() {
		@Override
		public void onShutter() {
			// Log.d(TAG, "onShutter'd");
		}
	};

	PictureCallback rawCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// Log.d(TAG, "onPictureTaken - raw");
		}
	};
	
	 

	PictureCallback jpegCallback = new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Runtime.getRuntime().maxMemory();
			Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);  			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			bitmap.recycle();
			dialog.dismiss();
			Intent i=new Intent(CameraActivity.this, SavePicActivity.class);
            i.putExtra("data","");
			i.putExtra("path", "");
			startActivity(i);
			Matrix matrix=new Matrix();	
//			Log.d(TAG, "CAMID........."+cameraid);
			//cameraid=Camera.CameraInfo.CAMERA_FACING_BACK;
			if(cameraid == Camera.CameraInfo.CAMERA_FACING_BACK){
			matrix.postRotate(90);
			//Log.d(TAG, "CAMID OF Back........."+cameraid);
			}else if(cameraid == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				matrix.postRotate(270);
				//Log.d(TAG, "CAMID of Front........."+cameraid);
			}
			//Log.i(TAG, "...."+ byteArray);
    // Use  BitmapFactory.Options options to make bytearray small and for fast processing
			 BitmapFactory.Options options = new BitmapFactory.Options();
			 options.inSampleSize = 5; 
			cambitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length,options);			
			// to make image in portrait mode.			
			cambitmap = Bitmap.createBitmap(cambitmap, 0, 0, cambitmap.getWidth(), cambitmap.getHeight(), matrix, true);
	//		FileOutputStream outStream = null;
//			try {
//				// Write to SD Card
//				fileName = String.format("/sdcard/camtest/%d.jpg",
//						System.currentTimeMillis());
//				outStream = new FileOutputStream(fileName);
//				outStream.write(data);
//				outStream.close();
//				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);								
//				resetCam();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//			}
//			Log.d(TAG, "onPictureTaken - jpeg");						
		}
	};
	
	public void takeFocusedPicture() {
	    camera.autoFocus(mAutoFocusCallback1);
	}

	 private Camera openFrontFacingCameraGingerbread() {
         int cameraCount = 0;
         if (camera != null) {
        	 camera.stopPreview();
        	 camera.setPreviewCallback(null);
        	 preview.setCamera(null);
        	 camera.release();
        	 camera = null;
     
     }
         
         Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
         cameraCount = Camera.getNumberOfCameras();
         
             if ( cameraid == Camera.CameraInfo.CAMERA_FACING_BACK) {
                 try {
                         cameraid=Camera.CameraInfo.CAMERA_FACING_FRONT;
                         camera =  Camera.open(CameraInfo.CAMERA_FACING_FRONT);
                         
                 } catch (RuntimeException e) {
                     Log.e("", "Camera failed to open: " + e.getLocalizedMessage());
                     e.printStackTrace();
                 }
              }
             else{
                     cameraid=Camera.CameraInfo.CAMERA_FACING_BACK;
                     camera =  Camera.open();
             }
             preview.switchCamera(camera);
         setCameraDisplayOrientation(CameraActivity.this, cameraid, camera);
         camera.startPreview();
         
         if(isFlashOn == true)
 		{
 			Parameters parameters=camera.getParameters();
 			parameters.setFlashMode(Parameters.FLASH_MODE_ON);
 			camera.setParameters(parameters);
 		}
         return camera;
     }
	 
	 private void Flashonoff(){
		 if(isFlashOn == false){
//				Log.i(TAG, "Flash is turned on!");
//				camera=Camera.open();
			    Parameters parameters=camera.getParameters();
				parameters.setFlashMode(Parameters.FLASH_MODE_ON);
				camera.setParameters(parameters);
				//camera.startPreview();
				isFlashOn=true;
				flashbtn.setBackgroundResource(R.drawable.flashsel87);
								
			}else if(isFlashOn == true){
//				Log.i(TAG, "Flash is turned off!");
//				camera = Camera.open();
				Parameters parameters=camera.getParameters();					
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(parameters);
				//camera.startPreview();					
				isFlashOn=false;
				flashbtn.setBackgroundResource(R.drawable.flash78);
				
			}
	 }
	 public void onBackPressed() {
		finish();
	 };
	 
	 Camera.AutoFocusCallback mAutoFocusCallback1 = new Camera.AutoFocusCallback() {
		    @Override
		    public void onAutoFocus(boolean success, Camera camera) {
		    	camera.takePicture(shutterCallback, rawCallback, jpegCallback);
		    }
		};
		
		
}// class
