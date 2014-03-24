package com.android.band.render;

public class Dot {
	
	private int BPM; 
	private int SetCount;
	private int X;
	private int Y;
	private int DotNum;
	  
	public int getBPM() {
		return BPM;
	}
	  
	public void setBpm(int BPM) {
		this.BPM = BPM;
	}
	  
	public int getSetCount() {
		return SetCount;
	}
	  
	public void setCount(int SetCount) {
		this.SetCount = SetCount;
	}
	  
	public int getX() {
		return X;
	}
	  
	public void setX(int x) {
		X = x;
	}
	  
	public int getY() {
		return Y;
	}
	  
	public void setY(int Y) {
		this.Y = Y;
	}
	  
	public int getDot() {
		return DotNum;
	}
	  
	public void setDot(int DotNum) {
		this.DotNum = DotNum;
	}

	@Override
	public String toString() {
		return "Dot [BPM=" + BPM + ", SetCount=" + SetCount + ", DotNum="
				+ DotNum + ", X=" + X + ", Y=" + Y + "]";
	}
} 
