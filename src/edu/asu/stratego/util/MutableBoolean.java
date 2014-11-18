package edu.asu.stratego.util;

/**
 * A mutable wrapper class for the boolean primitive type.
 */
public class MutableBoolean {
    
	private boolean value;
	
	/**
	 * Creates a new instance of MutableBoolean.
	 * @param value initial boolean value
	 */
	public MutableBoolean (boolean value) {
		this.value = value;
	}
	
	/**
	 * Sets the MutableBoolean value to true;
	 */
	public void setTrue() {
		value = true;
	}
	
	/**
	 * Sets the MutableBoolean value to false.
	 */
	public void setFalse() {
		value = false;
	}
	
	/**
	 * @return the value of MutableBoolean
	 */
	public boolean getValue() {
		return value;
	}
}
