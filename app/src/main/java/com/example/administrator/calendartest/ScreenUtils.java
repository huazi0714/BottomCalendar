package com.example.administrator.calendartest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenUtils {
	
	private static Bitmap takeScreenShot(Activity activity ,int x,int y ,int width,int height){ 
		View view = activity.getWindow().getDecorView(); 
		view.setDrawingCacheEnabled(true); 
		view.buildDrawingCache();
		
		Bitmap bitmap = view.getDrawingCache(); 
		Bitmap b = Bitmap.createBitmap(bitmap, x, y, width, height); 
		view.destroyDrawingCache(); 
		return b; 
	} 


		
	public static void savePic(Bitmap b,String strFileName){ 
		File fileRoot = new File(Environment.getExternalStorageDirectory().toString(), "golfplus/share");
		if(!fileRoot.exists()){
			fileRoot.mkdirs();
		}
		File file = new File(fileRoot, strFileName);
		if(file.isFile()){
			return;
		}
		FileOutputStream fos = null; 
		try{ 
			fos = new FileOutputStream("sdcard/golfplus/share/"+strFileName); 
			if (null != fos){ 
				b.compress(Bitmap.CompressFormat.PNG, 100, fos); 
				fos.flush(); 
				fos.close(); 
			} 
		}catch (FileNotFoundException e){ 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
	} 


		/**
		 * @param activity
		 * @param x 控件X坐标
		 * @param y 控件Y坐标
		 * @param width 控件宽度
		 * @param height 控件高度
		 */
		public static void shoot(Activity activity,int x,int y,int width,int height){
			Bitmap b = ScreenUtils.takeScreenShot(activity,x,y,width,height);
			//Bitmap newBitmap = ScreenUtils.resizeImage(b,70,70);
			ScreenUtils.savePic(b, "certificate.png"); 
			//Bitmap n = ScreenUtils.getSmallBitmap("sdcard/certificate.png");
			//ScreenUtils.savePic(n, "sdcard/certificate.png"); 
		}
		
		public static void shoot1(Activity activity,int x,int y,int width,int height){
			Bitmap b = ScreenUtils.takeScreenShot(activity,x,y,width,height);
			Bitmap newBitmap = ScreenUtils.resize(b);
			ScreenUtils.savePic(newBitmap, "certificate1.png");  
		}
		
		public static Bitmap shootScreen(Activity activity,int x,int y,int width,int height){
			return ScreenUtils.takeScreenShot(activity,x,y,width,height);
		}
		
		public static Bitmap resizeImage(Bitmap bitmap, int w, int h){    
	        Bitmap BitmapOrg = bitmap;    
	        int width = BitmapOrg.getWidth();    
	        int height = BitmapOrg.getHeight();
	        int newWidth = w;    
	        int newHeight = h;  
	        float scaleWidth = ((float) newWidth) / width;    
	        float scaleHeight = ((float) newHeight) / height;    
	  
	        Matrix matrix = new Matrix();    
	        matrix.postScale(scaleWidth, scaleHeight);     
	        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,    
	                        height, matrix, true);    
	        return resizedBitmap;    
	    } 
		
		public static Bitmap resize(Bitmap bmp){
			int bmpWidth = bmp.getWidth();  
            int bmpHeight = bmp.getHeight();
            float scaleWidth = 1;  
            float scaleHeight = 1;  
            //设置图片放大但比例  
            double scale = 0.3;  
            //计算这次要放大的比例  
            scaleWidth = (float)(scaleWidth*scale);  
            scaleHeight = (float)(scaleHeight*scale);  
            //产生新的大小但Bitmap对象  
            Matrix matrix = new Matrix();  
            matrix.postScale(scaleWidth, scaleHeight);  
            Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,bmpHeight,matrix,true); 
            return resizeBmp;
		}
		
		public static Bitmap getSmallBitmap(String filePath) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);
	        options.inSampleSize = calculateInSampleSize(options, 70, 70);
	        options.inJustDecodeBounds = false;
	        return BitmapFactory.decodeFile(filePath, options);
	    }
		
		public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;
			if (height > reqHeight || width > reqWidth) {
				final int heightRatio = Math.round((float) height/ (float) reqHeight);
				final int widthRatio = Math.round((float) width / (float) reqWidth);
				inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			}
			return inSampleSize;
		}
		
		public static String initImagePath(Activity activity){
			String url="";
			try{
				if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())&& Environment.getExternalStorageDirectory().exists()){
					url = Environment.getExternalStorageDirectory().getAbsolutePath()+"/golfplus/share/icon.png";
				}else{
					url = activity.getApplication().getFilesDir().getAbsolutePath()+"/golfplus/share/icon.png";
				}
			}catch(Throwable t){
				t.printStackTrace();
			}
			return url;
		}

		public static int getScreenWidth(Activity activity) {
			DisplayMetrics  dm = new DisplayMetrics();    
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			return dm.widthPixels;
		}

		public static int getScreenHeight(Activity activity) {
			DisplayMetrics  dm = new DisplayMetrics();    
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			return dm.heightPixels;
		}

		public static int getStateBarHeight(Activity activity){
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int width = dm.widthPixels;  //屏幕宽
			int height = dm.heightPixels;  //屏幕高
			Rect frame = new Rect();
			activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
			int statusBarHeight = frame.top;  //状态栏高
			return statusBarHeight;
		}
		
		public static DisplayMetrics getScreenDisplay(Context context){
	    	return context.getResources().getDisplayMetrics();
	    }
		
		public static int getResolution(Activity context){
			DisplayMetrics  dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int w = dm.widthPixels;
			int h = dm.heightPixels;
			if(h>=1920||w>=1080){
				return 1;
			}else if(h>=1280|| h>=720){
				return 2;
			}else{
				return 3;
			}
		}

}
