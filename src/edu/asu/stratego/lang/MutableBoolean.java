package edu.asu.stratego.lang;

public class MutableBoolean {
	private boolean value;
	
	public MutableBoolean(boolean inBool) {
		value = inBool;
	}
	
	public void setTrue() {
		value = true;
	}
	public void setFalse() {
		value = false;
	}
	public boolean getValue() {
		return value;
	}
}
