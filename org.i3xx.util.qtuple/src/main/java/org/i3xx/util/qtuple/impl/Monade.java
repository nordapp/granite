package org.i3xx.util.qtuple.impl;

public class Monade extends Tuple {
	
	public static final int LIKE = 0x1;
	
	/** The value */
	private final String value;
	
	/** The flags */
	private final int flags;
	
	/**
	 * @param value The value of a monade
	 * @param flags The flags to describe the resolving of the value
	 * @param type The type of the monade
	 */
	public Monade(String value, int flags, Type type) {
		super(null, null, type);
		
		this.value = value;
		this.flags = flags;
	}
	
	/**
	 * @param value The value of a monade
	 * @param type The type of the monade
	 */
	public Monade(String value, Type type) {
		super(null, null, type);
		
		this.value = value;
		this.flags = 0;
	}
	
	/**
	 * @param left The operand tuple
	 * @param type The type of the monade
	 */
	public Monade(Tuple left, Type type) {
		super(left, null, type);
		
		this.value = null;
		this.flags = 0;
	}
	
	/**
	 * @param value The template value monade
	 * @param flags The flags to describe the resolving of the value
	 */
	public Monade(Monade temp, int flags) {
		super(null, null, temp.type);
		
		this.value = temp.value;
		this.flags = flags;
	}
	
	/**
	 * @return
	 */
	@Override
	public int getFlags() {
		return flags;
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
