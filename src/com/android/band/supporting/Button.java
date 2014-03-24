package com.android.band.supporting;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Button {
	
	private Rect rect;
	private String unpressedImgLink;
	private String pressedImgLink;
	private String currentImgLink;
	private int x;
	private int y;
	Bitmap unpressed;
	Bitmap pressed;
	
	public Button( String unpressImgLink, String pressImgLink, int startx, int starty ) {
		x = startx;
		y = starty;
		unpressedImgLink = unpressImgLink;
		pressedImgLink = pressImgLink;
	}
	
	public void getCreateBitmap() {
		// Handles the I/O to retrieve the bitmap.
	}
	
	public boolean isTouched( int pointerX, int pointerY ) {
		return false;
	}
	
	public Bitmap getPressedImg() {
		return pressed;
	}
	
	public Bitmap getUnpressedImg() {
		return unpressed;
	}
	
	public void setStartX( int changedX ) {
		x = changedX;
	}
	
	public void setStartY( int changedY ) {
		y = changedY;
	}
}
