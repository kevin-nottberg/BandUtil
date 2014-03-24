package com.android.band.render;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/* 
 * This class will manage the math 
 * for the dotBook for every Individual
 * marcher and will make all the calls to
 * the dotBook
 */

public class Marcher {
	
	public float oldX;
	public float oldY;
	public float currX;
	public float currY;
	public float moveX;
	public float moveY;
	public float newY;
	public float newX;
	public int currDot;
	public int BPM;
	public int setCount;
	public DotBook dotBook;
	public String iden;
	
	public Marcher( Context context ) {
		dotBook = new DotBook(context);
		currDot = 0;
	}
	
	public void init( String path ) {
		Log.d("debugMarcherList", "In the marcher init");
		dotBook.init( path );
		Dot dot = dotBook.getDot( currDot );
		currX = dot.getX();
		currY = dot.getY();
		oldX = currX;
		oldY = currY;
		setCount = dot.getSetCount();
		BPM = dot.getBPM();
		Log.d("Marcher" , " BPM " + BPM );
		
		dot = dotBook.getDot( currDot + 1 );
		newX = dot.getX();
		newY = dot.getY();
		
		moveX = ( newX - currX ) / setCount;
		moveY = ( newY - currY ) / setCount;
		
		Log.d( "Dot", "Init dotX: " + oldX );
		Log.d( "Dot", "Init Set Count: " + setCount );
		Log.d( "Dot", "Init BPM: " + BPM );
		Log.d( "Dot", "Init dotY: " + oldY );
		Log.d("debugMarcherList", "Out of the dotbook and marcher init()");
	}
	
	/**
	@param beatsPassed is the change in time divided by how much time a beat would take up in a minute based on the BPM. Should be updates.
	*/
	
	public void update( float beatsPassed ) {
		currX += ( moveX * beatsPassed );
		currY += ( moveY * beatsPassed );
	}
	
	/**
	 * Function is called when the set x and y was reached and then the y and x move needs to be updated 
	 * It will then handle all the I/O for grabing the new X and Y from the dotbook
	 */
	public void fullUpdate( float beatsPassed ) {
		// Load new newX and newY from the dotbook
		// Then do new math and calculate the new xMove and YMove
		// newY = I/O to get new coordinate
		// newX = I/O to get new coordinate
		// MATH!!
		currDot++;
		oldX = newX;
		oldY = newY;
		Dot dot = dotBook.getDot( currDot );
		BPM = dot.getBPM();
		setCount = dot.getSetCount();
		newX = dot.getX();
		newY = dot.getY();
		moveX = ( newX - currX ) / setCount;
		moveY = ( newY - currY ) / setCount;
		currX += (moveX * beatsPassed);
		currY += (moveY * beatsPassed);
	}
	
	public void resume() {
		
	}
	
	public void draw( Canvas canvas, Paint paint ) {
		canvas.drawCircle( currX, currY, 4.0f, paint );
		Log.d( "Drawing", "CurrX: " + currX );
		Log.d( "Drawing", "CuurY: " + currY );
		//Log.d( "Marcher", "Dot: " + dotBook.getDot(1) );
	}
	
	public int getBPM() {
		return BPM;
	}
	
	public int getSetCount() {
		return setCount;
	}
	
	public String getId() {
		iden = dotBook.getId();
		return iden;
	}
}