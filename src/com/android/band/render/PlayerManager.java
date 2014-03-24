/*
package com.android.band.render;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PlayerManager {
	
	State state;
	Context context;
	float BPM;
	float beatCoeificient;
	MarcherList marcherList;
	ScreenHandler screenHandler;
	
	public enum State {
		RUNNING, PAUSED, READY, SYSPAUSE, USRPAUSE, INITRUN, SELECTIVERUN 
	};
	
	public PlayerManager( MarcherList mList, Context cont ) {
		marcherList = mList;
		context = cont;
		BPM = marcherList.getBPM();
	}
	
	public void update( float beatsPassed ) {
		marcherList.update( beatsPassed );
	}
	
	public void present( Canvas canvas, Paint paint ) {
		marcherList.draw( canvas, paint );
	}
	
	public void init() {
		marcherList.init();
	}
	
	public void initHndler( Canvas canvas ) {
		screenHandler = new ScreenHandler( canvas );
	}
	
	public void setState( State st ) {
		state = st;
	}
	
	public State getState() {
		return state;
	}
	
	public float getBeatsPassed( float deltaTime ) {
		return deltaTime / .375f;
	}
}
*/