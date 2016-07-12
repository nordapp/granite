package org.i3xx.util.qtuple.impl;

public class Monade extends Tuple {
	
	/** The value */
	private final String value;
	
	/**
	 * @param value The value of a monade
	 */
	public Monade(String value, Type type) {
		super(null, null, type);
		
		this.value = value;
	}
	
	/**
	 * @param left The operand tuple
	 */
	public Monade(Tuple left, Type type) {
		super(left, null, type);
		
		this.value = null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return value==null ? super.toString() : 
			type==Type.KEY ? value : 
				type==Type.VALUE ? value : 
					"<"+type+">"+value;
	}
}
