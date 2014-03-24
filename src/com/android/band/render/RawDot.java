package com.android.band.render;

public class RawDot {
	
	private int BPM;
	private int SetCount;
	private int side;
	private int horizontal;
	private String horizontalDir;
	private int horizontalSteps;
	private String vertical;
	private String verticalDir;
	private int verticalSteps;
	
	public int getBPM() {
		return BPM;
	}
	  
	public void setBpm( int BPM ) {
		this.BPM = BPM;
	}
	  
	public int getSetCount() {
		return SetCount;
	}
	  
	public void setCount( int SetCount ) {
		this.SetCount = SetCount;
	}
	
	public void setSide( int sid ) {
		side = sid;
	}
	
	public int getSide() {
		return side;
	}
	
	public int getHorizontal() {
		return horizontal;
	}
	
	public void setHorizontal( int horiz ) {
		horizontal = horiz;
	}
	
	public String getHorizontalDirection() {
		return horizontalDir;
	}
	
	public void setHorizontalDirection( String horizDir ) {
		horizontalDir = horizDir;
	}
	
	public int getHorizontalStep() {
		return horizontalSteps;
	}
	
	public void setHorizontalStep( int steps ) {
		horizontalSteps = steps;
	}
	
	public String getVertical() {
		return vertical;
	}
	
	public void setVertical( String vert ) {
		verticalDir = vert;
	}
	
	public void setVerticalDir( String vertDir ) {
		verticalDir = vertDir;
	}
	
	public int getVerticalStep() {
		return verticalSteps;
	}
	
	public void setVerticalStep( int steps ) {
		verticalSteps = steps;
	}
}
