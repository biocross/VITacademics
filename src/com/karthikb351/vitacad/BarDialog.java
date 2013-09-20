package com.karthikb351.vitacad;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

public class BarDialog {
	int background;
	String message;
	Context cntx;
	Intent i;
	
	public BarDialog(Context cntx){
		this.cntx = cntx;
	}
	
	public void setText(String msg){
		this.message = msg;
	}
	
	public void setColor(int Color){
		this.background = Color;
	}
	
	public void Show(){
		i = new Intent (cntx,OverlayService.class);
		i.putExtra("background", background);
		i.putExtra("mssg", message);
		cntx.startService(i);
	}
	
	public void Dismiss(){
		
		cntx.stopService(new Intent(cntx, OverlayService.class));
	}
	
	public void Toast(int time){
		Show();
		 new CountDownTimer(time, 1000) {
		     public void onTick(long millisUntilFinished) {}

		     public void onFinish() {
		         Dismiss();
		     }
		  }.start();
		
	}
	
	
}
