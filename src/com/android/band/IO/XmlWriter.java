package com.android.band.IO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.content.Context;

public class XmlWriter {
	
	Document doc;
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Element rootElement;
	
	public XmlWriter() {
			docFactory = DocumentBuilderFactory.newInstance();
			try {
				docBuilder = docFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void startNewDoc() {
		doc = docBuilder.newDocument();
		Document doc = docBuilder.newDocument();
		rootElement = doc.createElement("config");
		doc.appendChild(rootElement);
	}
	
	public void addDot( int bpm, int setcount, int x, int y ) {
		
		Element dot = doc.createElement("dot");
		rootElement.appendChild(dot);
		
		Element bpmElement = doc.createElement("bpm");
		bpmElement.appendChild( doc.createTextNode( Integer.toString( bpm ) ) );
		dot.appendChild( bpmElement );
 
		// lastname elements
		Element setcountElement = doc.createElement("setcount");
		setcountElement.appendChild( doc.createTextNode( Integer.toString( setcount ) ) );
		dot.appendChild( setcountElement );
 
		// nickname elements
		Element xElement = doc.createElement("x");
		xElement.appendChild( doc.createTextNode( Integer.toString( x ) ) );
		dot.appendChild( xElement );
 
		// salary elements
		Element yElement = doc.createElement("y");
		yElement.appendChild( doc.createTextNode( Integer.toBinaryString( y ) ) );
		dot.appendChild( yElement );
	}
	
	
	public void finishAndWriteDoc( Context context ) {
		// Write the new doc to the android assets.
		FileOutputStream fileOut = null;
		Transformer transformer = null;
		try {
			fileOut = context.openFileOutput( "dotBook.xml", Context.MODE_WORLD_WRITEABLE );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMSource source = new DOMSource(doc);
		StreamResult streamResult = new StreamResult( fileOut );
		try {
			transformer.transform(source, streamResult);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
