package com.android.band.render;

import java.io.IOException;
import java.util.ArrayList;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class MarcherList {
	
	public ArrayList<Marcher> marcherList;
	ArrayList<String> filePaths;
	String[] files;
	int beatCounter;
	int setCount;
	
	public MarcherList( Context context ) {
		marcherList = new ArrayList<Marcher>();
		filePaths = new ArrayList<String>();
		beatCounter = 0;
		AssetManager assets = context.getAssets();
		try {
			files = assets.list("");
			for( int i = 0; i <= files.length - 4; i++) {
				filePaths.add(files[i]);
			}
			Log.d("Exceptions", "" + assets.list("assets"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for( int i = 0; i <= filePaths.size(); i++) {
			Marcher marcher = new Marcher(context);
			marcherList.add(marcher);
		}
	}
	
	public void init() {
		Log.d("debugMarcherList", "Started with the init");
		for( int i = 0; i < filePaths.size(); i++ ) {
			Log.d("debugMarcherList", " init i: " + i);
			marcherList.get( i ).init( filePaths.get(i) );
		}
	}
	
	public void draw( Canvas canvas, Paint paint ){
		for( int i = 0; i < marcherList.size(); i++ ) {
			// Create
			marcherList.get( i ).draw( canvas, paint );
		}
		Log.d( "Dot", "MarcherList size: "+ marcherList.size() );
	}
	
	/**
	 * @param beatsPassed is the change in time divided by how much time a beat would take up in a minute based on the BPM. Should be updates.
	 */
	
	public void update( float beatsPassed ) {
		// Loop through the the list and call update function and passed the beatsPassed
		Log.d( "Update", "Updating" );
		Log.d( "Update", "Dot: " + reachedDot() );
		if( reachedDot() ) {
			Log.d( "Update", "Call full update" );
			fullUpdate( beatsPassed );
		} else {
			Log.d( "Update", "Calling normal update" );
			for( int i = 0; i < marcherList.size(); i++ ) {
				marcherList.get( i ).update( beatsPassed );
			}
		}
		
		for ( int i = 0; i < marcherList.size(); i++ ) {
			marcherList.get( i ).update( beatsPassed );
		}
		setCount = getSetCount();
		beatCounter += beatsPassed;
		Log.d( "Dot", "beatCounter: " + beatCounter );
	}

	
	public boolean reachedDot() {
		Log.d( "Update", "In reached dot" );
		if( beatCounter >= setCount ) {
			return true;
		} else {
			return false;
		}
	}
	
	public void fullUpdate( float beatsPassed ) {
		Log.d( "FullUpdate", "Reached full update" );
		for ( int i = 0; i < marcherList.size() - 1; i++ ) {
			Log.d( "FullUpdate", "MarcherList Size:" + marcherList.size() );
			Marcher marcher = marcherList.get( i );
			marcher.fullUpdate( beatsPassed );
		}
		beatCounter = 0;
		Log.d( "FullUpdate", "Out of full update" );
	}
	
	public int getBPM() {
		// Return the BPM listed
		return marcherList.get( 1 ).getBPM();
	}
	
	public int getSetCount() {
		// I/O To retrieve the set count and pass it to the Renderer 
		return marcherList.get( 1 ).getSetCount();
	}
}
