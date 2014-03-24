package com.android.band.render;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RenderRewrite extends SurfaceView implements Runnable {
	
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean playing = false;
	Paint paint;
	MarcherList marcherList;
	ScreenHandler screenHandler;
	//PlayerManager playerManager;
	
	
	public RenderRewrite( Context context, MarcherList list, ScreenHandler handlr ) {
		super( context );
		paint = new Paint();
		paint.setColor( Color.WHITE );
		holder = getHolder();
		screenHandler = handlr;
		screenHandler.setState( State.READY );
	}
	
	@Override
	public void run() {
		screenHandler.setState( State.INITRUN );
		long startTime = System.nanoTime();
		while( playing ) {
			
			if ( screenHandler.state == State.INITRUN ) {
				if(!holder.getSurface().isValid())
					continue; 
				Canvas canvas = holder.lockCanvas();
				
				screenHandler.initHndler( canvas );
				
				holder.unlockCanvasAndPost( canvas );
				
				screenHandler.setState( State.RUNNING );
			}
			
			// Executes when the state is running and needs to be updated and drawn
			if( screenHandler.state == State.RUNNING ) {
				float deltaTime = ( System.nanoTime() - startTime ) / 1000000000.0f;
				startTime = System.nanoTime();
				float beatsPassed = screenHandler.getBeatsPassed( deltaTime );
				Log.d( "renderRe", "DeltaTime: " + deltaTime );
				Log.d( "renderRe", "BeatsPassed: " + beatsPassed );
				screenHandler.update( beatsPassed );
				if(!holder.getSurface().isValid())   
					continue;
				Canvas canvas = holder.lockCanvas();
				canvas.drawRGB( 148, 214, 107 );
				paint.setColor( Color.WHITE );
				screenHandler.present( canvas, paint );
				// Draw buttons
				holder.unlockCanvasAndPost( canvas );
			}
			
			// Executes when the user paused the player not android and the view still needs drawing
			if( screenHandler.state == State.USRPAUSE ) {
				Canvas canvas = holder.lockCanvas();
				canvas.drawRGB( 0, 0, 0 );
				paint.setColor( Color.WHITE );
				screenHandler.present( canvas, paint );
				// 	Draw buttons
				holder.unlockCanvasAndPost(canvas);
			}
			
			try {
				Thread.sleep( 380 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}	
	}
	
	public void resume() {
		//marcherList.resume();
		//playerManager.setState( State.READY );
		playing = true;
		renderThread = new Thread(this);
		screenHandler.setState( State.RUNNING );
		renderThread.start();
	}
	
	public void pause() {
		playing = false;
		screenHandler.setState( State.SYSPAUSE );
		while( true ) {
			try {	
				renderThread.join();
				return;
			} catch ( InterruptedException e ) {
				
			}
		}
	}
}
