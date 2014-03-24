package com.android.band.render;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.android.band.IO.XmlParser;

import android.content.Context;
import android.util.Log;


/**
 * This handles all the I/O for the dot book 
 * renderer atleast. There might be parallels to
 * the dot book class used for just a virtual dot book
 */

public class DotBook {
	static final String BPM = "bpm";
	static final String DOT = "dot";
	static final String SETCOUNT = "setcount";
	static final String X = "x";
	static final String Y = "y";
	static final String ID = "id";
	Context context;
	String iden;
	
	//Create and two dimensional array that will take dot number and store the x and y coordinates 
	private ArrayList<Dot> dotBook;
	
	
	public DotBook( Context cont ) {
		// Use the identifier to store the x y coords and to be able to retrieve the dot book
		dotBook = new ArrayList<Dot>();
		context = cont;
	}
	
	
	public void init(String path) {
		Log.d("debugMarcherList", "In the dot book init");
		XmlParser parser = new XmlParser( context );
		Log.d("debugMarcherList", "" + path);
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
			Dot dot = new Dot();
			dot.setBpm(Integer.parseInt(parser.getValue(e, BPM)));
			dot.setCount(Integer.parseInt(parser.getValue(e, SETCOUNT)));
			dot.setX(Integer.parseInt(parser.getValue(e, X)));
			dot.setY(Integer.parseInt(parser.getValue(e, Y)));
			dotBook.add(dot);
			Log.d("debugMarcherList", ""+dotBook.size());
			Log.d( "DotBook", "BPM: " + i + " " + dot.getBPM() );
			Log.d( "DotBook", "SetCount: " + i + " " + dot.getSetCount() );
			Log.d( "DotBook", "X: " + i + " " + dot.getX() );
			Log.d( "DotBook", "Y: "+ i + " " + dot.getY() );
		}
		Log.d("debugMarcherList", "Done with the init");
	}
	
	/**
	 * Method takes the input from human language
	 * it then calculates the x y and stores it 
	 * into a two dimensional array based on the dotNumber
	 * 
	 * @param yardLine is the horizontal line of the field
	 * @param horizontalStep is the number of steps in the yard line 4 is middle 
	 * @param verticalPosition is the "frontYardLine", "frontHash", "backHash", "backYardLine" 
	 * @param frontBehind is if you are in front of the hash or yard line or behind the hash or yard line
	 * @param verticalStep is the number of steps on the vertical axis 0 if on the hash
	 * @param dotNumber is the the order in which the dot will be stored in the array
	 */
	public void addDot( int yardLine, int horizontalStep, String verticalPosition, String frontBehind, int verticalStep, int dotNumber ) {
		// Analyze the parameters and then store the x and y of the dot
	}
	
	public Dot getDot( int currDot ) {
		// Return the x and y of the dot to the renderer
		return dotBook.get( currDot );
	}
	
	/**
	 * Function returns the BPM that was stored in the
	 * dot book xml
	 * 
	 * @return The bpm that was read in from the xml
	 */
	public int getBPM() {
		return dotBook.get(1).getBPM();
	}
	
	/**
	 * Function returns the set count that was stored in the
	 * dot book xml. Set count is really just how many beats
	 * you have to get from one dot to the next dot
	 * 
	 * @return The set count that was read in from the xml
	 */
	public int getSetCount() {
		return 0;
	}
	
	public String getId() {
		return iden;
	}
	
	/**
	 * Writes the most recent additions to
	 * the dot book to a xml.
	 * Should be called in the onPause() onStop() 
	 */
	public void writeToXml() {
		
	}
}
