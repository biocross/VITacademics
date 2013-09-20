package com.karthikb351.vitacad;


import com.karthikb351.vitinfo2.R;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class OverlayService extends Service {
	View v;
	Context cntx;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private static Point getDisplaySize(final Display display) {
	    final Point point = new Point();
	    try {
	        display.getSize(point);
	    } catch (java.lang.NoSuchMethodError ignore) { // Older device
	        point.x = display.getWidth();
	        point.y = display.getHeight();
	    }
	    return point;
	}
	
	int back;
	String msg;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle b = intent.getExtras();
		msg = b.getString("mssg");
		Log.i("OVERLAY_SERVICE", msg);
		back = b.getInt("background");
		go();
	    return START_STICKY;
	}
	 
	
	public void go(){
		cntx =this;
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size = getDisplaySize(display);
		int width = size.x;
		int height = size.y;
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				-width,
				height,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
				PixelFormat.TRANSLUCENT);
		v = LayoutInflater.from(getBaseContext()).inflate(R.layout.overlay_service,null);
		((TextView) v.findViewById (R.id.txt_notification)).setText(msg); 
		v.setBackgroundColor(back);
		wm.addView(v, params);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if (v != null){
			((WindowManager)getSystemService(WINDOW_SERVICE)).removeView(v);
			v = null;
		}
	}
	

}
