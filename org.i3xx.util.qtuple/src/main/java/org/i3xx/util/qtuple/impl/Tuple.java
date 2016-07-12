package org.i3xx.util.qtuple.impl;

public class Tuple {
	
	/** The type of the tuple */
	public enum Type {KEY, VALUE, ROOT, AND, OR, NOT}
	
	/** The name of the tuple */
	private final Tuple right;
	
	/** The next tuple */
	private final Tuple left;
	
	/** The type of the tuple */
	protected final Type type;
	
	public Tuple(Tuple left, Tuple right, Type type) {
		this.right = right;
		this.left = left;
		this.type = type;
	}
	
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the right
	 */
	public Tuple getRight() {
		return right;
	}

	/**
	 * @return the left
	 */
	public Tuple getLeft() {
		return left;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append('<');
		buf.append(type);
		buf.append('>');
		buf.append('(');
		if(left!=null) {
			buf.append(left.toString());
		}
		if(right!=null) {
			buf.append(right.getType()==Type.VALUE ? '=' : ',');
			buf.append(right.toString());
		}
		buf.append(')');
		
		return buf.toString();
	}
}
