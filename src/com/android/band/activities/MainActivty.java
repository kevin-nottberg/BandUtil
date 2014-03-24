package com.android.band.activities;

import com.android.band.render.MarcherList;
import com.android.band.render.RenderRewrite;
import com.android.band.render.ScreenHandler;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

/* 
 * This is the main class that will handle the screen
 * that links the user to all the different features of 
 * the Dot Book, Dot Book Renderer, Tuner, Metrophnome
 * Don't know if this will be self drawn or use the android
 * APIs to create the display yet.
 */

public class MainActivty extends Activity implements OnTouchListener {

	MarcherList mList;
	RenderRewrite render;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		Log.d("debug", "In onCreate");
		
		mList = new MarcherList( this );
				
		ScreenHandler handlr = new ScreenHandler( mList, this );
		handlr.init();
		
		Log.d("onCreate", "Done with marching list");
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		render = new RenderRewrite( this, mList, handlr );
		Log.d("debug", "Finished making the DotBook render object");
		setContentView( render );
		
	}
	
	/*
	public class MainScreen extends View {

		public MainScreen( Context context ) {
			super(context);
			
		}
		
		@Override
		protected void onDraw( Canvas canvas ){
			
			
			invalidate();
		}
		
	}
	*/
	
	@Override
	protected void onResume() {
		super.onResume();
		render.resume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		render.pause();
	}
	
	@Override
	public boolean onTouch( View v, MotionEvent event) {
		//int halfHeight = view.getHeight() / 2;
		int maxCount = event.getPointerCount();
		int action = MotionEventCompat.getActionMasked( event );
		Log.d("KevinApp", "OnTouch: " + String.valueOf( action ));
		for( int i = 0; i < maxCount; i++ )
		{
			//int mActivePointerId = event.getPointerId(i);
			// int pointerIndex = event.findPointerIndex(mActivePointerId);
			//int index = MotionEventCompat.getActionIndex(event);
			switch( action )
			{
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
			case MotionEvent.ACTION_MOVE:
				Log.d( "KevinApp", "OnTouch" );
				
				//If on what button was pressed
				//if ( MotionEventCompat.getY( event, i) < halfHeight )
				{
					//curPointPosXtwo = (int) MotionEventCompat.getX( event, i );
					// curPointPosYtwo = (int) MotionEventCompat.getY(event, i);
					//view.invalidate();
				}
				//if ( MotionEventCompat.getY( event, i) > halfHeight )
				{
					//curPointPosXone = (int) MotionEventCompat.getX( event, i );
					// curPointPosYone = (int) MotionEventCompat.getY(event, i);
				}
			break;
			}
		}	
		return true;
	}
}
