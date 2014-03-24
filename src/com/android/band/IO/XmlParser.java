package com.android.band.IO;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class XmlParser {
	
	private Document doc;
	Context context;
	
	public XmlParser( Context cont) {
		context = cont;
	}
	
	public String getValue(Element item, String str) {
		Log.d("debugMarcherList", "getting value");
	    NodeList n = item.getElementsByTagName(str);
	    Log.d("debugMarcherList", "got the value");       
	    return this.getElementValue(n.item(0));
	}
	 
	public final String getElementValue( Node elem ) {
		Node child;
		if( elem != null){
			if (elem.hasChildNodes()){
				for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
					if( child.getNodeType() == Node.TEXT_NODE  ){
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
	
	public Document getDomElement(String xml){
		Log.d("debugMarcherList", "getting the dom element");
		Log.d("parserError", "" + xml);
		AssetManager assets = context.getAssets();
		try {
		InputStream fXmlFile = assets.open(xml);
		Log.d("debugMarcherList", "" + fXmlFile.toString());
		Log.d("debugMarcherList", "created the file");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Log.d("debugMarcherList", "Created the document builder fact");
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Log.d("debugMarcherList", "Created the document builder");
		doc = dBuilder.parse(fXmlFile, null);
		
		Log.d("debugMarcherList", ""+doc);
	 
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
 
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                Log.d("Exceptions", "ParserException");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Exceptions", "IOException");

                return null;
            } catch (SAXException e) {
				Log.d("Exceptions", "SaxExceptions");
				e.printStackTrace();
			}
                // return DOM
            return doc;
    }
}
