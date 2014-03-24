package com.android.band.supporting;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;

import com.android.band.IO.XmlParser;
import com.android.band.render.RawDot;

public class RawDotBook {
	// Constants
	static final String BPM = "bpm";
	static final String DOT = "dot";
	static final String SETCOUNT = "setcount";
	static final String SIDE = "side";
	static final String HORIZONTAL = "horiz";
	static final String HORIZONTALDIRECTION = "horizDir";
	static final String HORIZONTALSTEP = "horiStep";
	static final String VERTICAL = "vert";
	static final String VERTICALDIRECTION = "vertdir";
	static final String VERTICALSTEP = "vertStep";
	
	// Other variables and arrays
	Context context;
	ArrayList<RawDot> dotBook = new ArrayList<RawDot>();
	
	public RawDotBook( Context cont ) {
		context = cont;
	}
	
	public void init( String path ) {
		XmlParser parser = new XmlParser( context );
		Document doc = parser.getDomElement(path);
		Log.d("debugMarcherList", "Got parser and created got the doc");
		Log.d("debugMarcherList", ""+doc);
		//NodeList id = doc.getElementsByTagName(ID);
		//Element el = ( Element ) id.item( 0 );
		//iden = parser.getElementValue(el);
		NodeList nl = doc.getElementsByTagName(DOT);
		Log.d("debugMarcherList", "Got the node list");
		Log.d("debugMarcherList", ""+ nl.getLength());
		for( int i = 0; i < nl.getLength(); i++ ) {
			Element e = (Element) nl.item(i);
			RawDot dot = new RawDot();
			dot.setBpm(Integer.parseInt(parser.getValue(e, BPM)));
			dot.setCount(Integer.parseInt(parser.getValue(e, SETCOUNT)));
			dot.setSide(Integer.parseInt(parser.getValue(e, SIDE)));
			dot.setHorizontal(Integer.parseInt(parser.getValue(e, HORIZONTAL)));
			dot.setHorizontalDirection(parser.getValue(e, HORIZONTALDIRECTION));
			dot.setHorizontalStep(Integer.parseInt(parser.getValue(e, HORIZONTALSTEP)));
			dot.setVertical(parser.getValue(e, VERTICAL));
			dot.setVerticalDir(parser.getValue(e, VERTICALDIRECTION));
			dot.setVerticalStep(Integer.parseInt(parser.getValue(e, VERTICALSTEP)));
			//dotBook.add(dot);
			Log.d("debugMarcherList", ""+dotBook.size());
			Log.d( "DotBook", "BPM: " + i + " " + dot.getBPM() );
			Log.d( "DotBook", "SetCount: " + i + " " + dot.getSetCount() );
			
		}
		Log.d("debugMarcherList", "Done with the init");
	}
	
	public void convertAndWrite() {
		int FIELD = 100;
		int SCREEN = 20;
		// int HORPIXMARG = getTheHorPixMarg();
		// int VERTPIXMARG = getTheVertPixMarg();
		for( int i = 0; i < dotBook.size(); i++ ) {
			int x;
			int y;
			int setcount;
			int bpm;
			
			// Horizontal pixel if-block
			if( dotBook.get( i ).getSide() == 1 ) {
				int margCoeficient = ( (dotBook.get( i ).getHorizontal() * SCREEN) / FIELD );
				// x = HORPIXMARG * margCoeficient;
			}
			if( dotBook.get( i ).getSide() == 2 ) {
				int margCoeficient = ( (dotBook.get( i ).getHorizontal() * SCREEN) / FIELD ) * 2;
				// x = HORPIXMARG * margCoeficient;
			}
			
			// Vertical pixel if-block
			if ( dotBook.get( i ).getVertical().equals( "front" )) {
				// y = VERTPIXMARG * 2;
			}
			if ( dotBook.get( i ).getVertical().equals( "back" )) {
				// y = VERTPIXMARG;
			}
			
			setcount = dotBook.get( i ).getSetCount();
			bpm = dotBook.get( i ).getBPM();
		}
	}
}
